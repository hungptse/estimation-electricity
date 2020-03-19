var LIST_KEY = {
    LIST_PRODUCT: "LIST_PRODUCT"
};
function getListProductDoc() {
    var doc = parseStringToDoc(localStorage.getItem(LIST_KEY.LIST_PRODUCT));
    return doc;
}
function nodeListToArray(list) {
    var arr = [];
    if (list.length) {
        for (var i = 0; i < list.length; i++) {
            arr.push(list.item(i));
        }
    }
    return arr;
}
function arrayToXMLDoc(arr) {
    var str = "<rootElm>";
    arr[0].forEach(function (item) {
        str += item.outerHTML;
    });
    str += "</rootElm>";
    return parseStringToDoc(str);
}
function initUI() {
    var root = document.getElementById("root");
    var productList = document.createElement("div");
    productList.id = "product-list";
    var addedList = document.createElement("div");
    addedList.id = "added-list";
    root.appendChild(document.createElement("hr"));
    root.appendChild(productList);
    var search = createSearch("Name or Code", function () {
        // @ts-ignore
        var value = document.getElementById("txtSearch").value;
        var result = nodeListToArray(getListProductDoc().childNodes).map(function (node) { return nodeListToArray(node.childNodes).filter(function (n) { return n.childNodes.item(2).textContent.toLowerCase().indexOf(value.toLowerCase()) != -1; }); });
        if (result[0].length > 0) {
            renderProductTable(arrayToXMLDoc(result));
        }
        else {
            getXHR("webservice/product/findLikeByNameOrCode", {
                "search": value
            }).then(function (res) {
                renderProductTable(res);
            });
        }
    });
    productList.appendChild(search);
    getXHR("webservice/product", {
        "size": 100,
        "page": 1
    }).then(function (res) {
        localStorage.setItem(LIST_KEY.LIST_PRODUCT, parseDocToString(res));
        renderProductTable(res);
        //
        // createTable("main-table",res, productList, "Product Table", {
        //     "Name": 5,
        //     "Code": 1,
        //     "Wattage (W)": 11,
        // }, 10, { action: addToList, actionTitle: "Add", isAction: true });
    });
    var xmlString = "<productEntities/>";
    var addedTable = createTable("added-list", parseStringToDoc(xmlString), "Product Table", {
        "Name": 2,
        "Code": 0,
        "Wattage (W)": 5,
    }, 10, { action: null, actionTitle: "Add", isAction: false });
    addedList.appendChild(addedTable);
}
function renderProductTable(xmlDoc) {
    var productList = document.getElementById("product-list");
    removeChildById("main-table");
    var productTable = createTable("main-table", xmlDoc, "Product Table", {
        "Name": 2,
        "Code": 0,
        "Wattage (W)": 5,
    }, 10, { action: addToList, actionTitle: "Add", isAction: true });
    productList.appendChild(productTable);
}
function parseStringToDoc(xmlString) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "text/xml");
    return xmlDoc;
}
function parseDocToString(xmlDoc) {
    return new XMLSerializer().serializeToString(xmlDoc.documentElement);
}
function createSearch(title, onkeyup) {
    var divTag = document.createElement("div");
    var titleTag = document.createElement("span");
    titleTag.innerText = title + " ";
    var inputTag = document.createElement("input");
    inputTag.id = "txtSearch";
    inputTag.type = "text";
    var searchBtn = document.createElement("input");
    searchBtn.type = "submit";
    searchBtn.value = "Search";
    inputTag.onkeyup = onkeyup;
    divTag.appendChild(titleTag);
    divTag.appendChild(inputTag);
    divTag.appendChild(searchBtn);
    return divTag;
}
function objectToQueryParam(obj) {
    var result = "";
    // @ts-ignore
    Object.entries(obj).forEach(function (_a) {
        var key = _a[0], value = _a[1];
        result += key + "=" + value + "&";
    });
    return result.slice(0, result.length - 1);
}
function button(name, onclick) {
    var btn = document.createElement("button");
    btn.innerText = name;
    btn.onclick = onclick;
    btn.style.marginLeft = "3px";
    btn.style.marginRight = "3px";
    return btn;
}
function removeChildById(tagId) {
    var tag = document.getElementById(tagId);
    if (tag) {
        document.getElementById(tagId).parentNode.removeChild(tag);
    }
}
function createTable(id, doc, title, columns, ITEM_MAX_PAGE, _a) {
    var _b = _a === void 0 ? {} : _a, _c = _b.isAction, isAction = _c === void 0 ? false : _c, _d = _b.actionTitle, actionTitle = _d === void 0 ? "Add" : _d, _e = _b.action, action = _e === void 0 ? null : _e;
    // removeChildById(id);
    removeChildById("notification");
    var divTag = document.createElement("div");
    divTag.style.width = "100%";
    divTag.style.alignContent = "right";
    divTag.id = id;
    var table = document.createElement("table");
    table.style.width = "100%";
    var rootNode = doc.childNodes.item(0);
    table.style.border = "1px solid black";
    table.border = "1";
    table.createCaption().innerText = title;
    var row = table.createTHead().insertRow();
    row.insertCell(0).innerText = "No.";
    Object.keys(columns).forEach(function (key, index) {
        row.insertCell(index + 1).innerText = key;
    });
    if (isAction) {
        row.insertCell().innerText = "Action";
    }
    var page = 1;
    var tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
    renderTableBody("table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action });
    var maxPage = rootNode.childNodes.length % 10 == 0 ? rootNode.childNodes.length / ITEM_MAX_PAGE : Math.floor((rootNode.childNodes.length / ITEM_MAX_PAGE)) + 1;
    var text = document.createElement("code");
    var pagingSection = document.createElement("div");
    pagingSection.style.textAlign = "right";
    pagingSection.style.marginTop = "10px";
    pagingSection.appendChild(button("First", function () {
        page = 1;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action });
    }));
    pagingSection.appendChild(button("Pre", function () {
        if (page > 1) {
            page--;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action });
    }));
    text.innerText = page + "/" + maxPage;
    pagingSection.appendChild(text);
    pagingSection.appendChild(button("Next", function () {
        if (page < maxPage) {
            page++;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action });
    }));
    pagingSection.appendChild(button("Last", function () {
        page = maxPage;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action });
    }));
    divTag.appendChild(table);
    divTag.appendChild(pagingSection);
    if (rootNode.hasChildNodes()) {
        return divTag;
    }
    else {
        var text_1 = document.createElement("h3");
        text_1.id = "notification";
        text_1.innerText = "No data found";
        return text_1;
    }
}
function getPagingData(page, ITEM_MAX_PAGE, rootNode) {
    var tableData = [];
    var start = (page - 1) * ITEM_MAX_PAGE;
    for (var i = start; i < start + ITEM_MAX_PAGE; i++) {
        if (rootNode.childNodes.item(i)) {
            tableData.push(rootNode.childNodes.item(i));
        }
    }
    return tableData;
}
function renderTableBody(id, table, tableData, columns, _a) {
    var _b = _a === void 0 ? {} : _a, _c = _b.isAction, isAction = _c === void 0 ? false : _c, _d = _b.actionTitle, actionTitle = _d === void 0 ? "Action" : _d, _e = _b.action, action = _e === void 0 ? null : _e;
    removeChildById(id);
    var body = table.createTBody();
    body.id = id;
    var _loop_1 = function (i) {
        var child = tableData[i];
        var row = body.insertRow(i);
        if (child.hasChildNodes()) {
            row.insertCell(0).innerText = i + 1 + "";
            Object.keys(columns).forEach(function (key, index) {
                var content = child.childNodes.item(columns[key]).textContent;
                if (!content) {
                    content = "No available";
                }
                row.insertCell(index + 1).innerText = child.childNodes.item(columns[key]).textContent;
            });
            if (isAction) {
                var actionBtn = document.createElement("button");
                actionBtn.innerHTML = actionTitle;
                // @ts-ignore
                actionBtn.onclick = function (e) {
                    e.preventDefault();
                    action(child);
                };
                row.insertCell().appendChild(actionBtn);
            }
        }
    };
    for (var i = 0; i < tableData.length; i++) {
        _loop_1(i);
    }
}
function addToList(value) {
    console.log(value);
    var root = document.getElementById("added-list");
    var xmlDoc = parseStringToDoc(localStorage.getItem("data")).childNodes[0].appendChild(value);
    //
    var addedList = createTable("added-list", xmlDoc, "List", {
        "Name": 2,
        "Code": 0,
        "Wattage (W)": 5,
    }, 10);
    root.appendChild(addedList);
    renderProductTable(parseStringToDoc(localStorage.getItem(LIST_KEY.LIST_PRODUCT)));
    initUI();
    // localStorage.setItem("data",xmlDoc);
    // document.getElementById("root").appendChild(root);
}
function getXHR(url, data) {
    var xhr = new XMLHttpRequest();
    if (Object.keys(data).length !== 0) {
        url += "?" + objectToQueryParam(data);
    }
    xhr.open("GET", url, true);
    // @ts-ignore
    return new Promise(function (resolve, reject) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 300) {
                    reject("Error, status code = " + xhr.status);
                }
                else {
                    resolve(xhr.responseXML);
                }
            }
        };
        xhr.send(data);
    });
}
function postXHR(url, data) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    // @ts-ignore
    return new Promise(function (resolve, reject) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status >= 300) {
                    reject("Error, status code = " + xhr.status);
                }
                else {
                    resolve(xhr.responseXML);
                }
            }
        };
        xhr.send(data);
    });
}
