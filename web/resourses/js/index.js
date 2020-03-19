var LIST_KEY = {
    LIST_PRODUCT: "LIST_PRODUCT",
    LIST_ADD: "LIST_ADD"
};
function initUI() {
    var root = document.getElementById("root");
    var productList = document.createElement("div");
    productList.id = "product-list";
    var addedList = document.createElement("div");
    addedList.id = "added-list";
    root.appendChild(productList);
    root.appendChild(document.createElement("hr"));
    root.appendChild(addedList);
    var search = createSearch("Name or Code", function () {
        // @ts-ignore
        var value = document.getElementById("txtSearch").value;
        var result = nodeListToArray(getDocFromStorage(LIST_KEY.LIST_PRODUCT).childNodes).map(function (node) { return nodeListToArray(node.childNodes).filter(function (n) { return n.childNodes.item(2).textContent.toLowerCase().indexOf(value.toLowerCase()) != -1; }); });
        if (result[0].length > 0) {
            renderProductTable(arrayXMLToXMLDoc(result[0], "productEntities"));
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
    if (getDocFromStorage(LIST_KEY.LIST_PRODUCT) == undefined) {
        getXHR("webservice/product", {
            "size": 100,
            "page": 1
        }).then(function (res) {
            localStorage.setItem(LIST_KEY.LIST_PRODUCT, parseDocToString(res));
            renderProductTable(res);
        });
    }
    else {
        renderProductTable(getDocFromStorage(LIST_KEY.LIST_PRODUCT));
    }
    if (getDocFromStorage(LIST_KEY.LIST_ADD) != undefined) {
        renderAddedList(getDocFromStorage(LIST_KEY.LIST_ADD));
    }
}
function renderAddedList(xmlDoc, isFill, maxPage) {
    if (isFill === void 0) { isFill = false; }
    if (maxPage === void 0) { maxPage = 10; }
    var addedList = document.getElementById("added-list");
    removeChildById("added-table");
    var isAction = true;
    if (isFill) {
        isAction = false;
    }
    var addTable = createTable("added-table", xmlDoc, "Added Table", {
        "Name": 2,
        "Code": 0,
        "Wattage (W)": 5,
    }, 10, { action: removeFromList, actionTitle: "Remove", isAction: isAction, isFill: isFill });
    addedList.appendChild(addTable);
    var nextStepBtn = button("Next", function () {
        fillTimePage();
    });
    addedList.appendChild(nextStepBtn);
}
function fillTimePage() {
    var root = document.getElementById("root");
    root.innerHTML = "";
    var addedList = document.createElement("div");
    addedList.id = "added-list";
    root.appendChild(addedList);
    if (getDocFromStorage(LIST_KEY.LIST_ADD) != undefined) {
        renderAddedList(getDocFromStorage(LIST_KEY.LIST_ADD), true, 100);
    }
    addedList.appendChild(button("Send", function () {
        sendTableToServer();
    }));
}
function sendTableToServer() {
    var doc = document.getElementById("added-tabletable-body");
    var arr = [];
    doc.childNodes.forEach(function (node) {
        arr.push({
            "id": node.childNodes.item(0).textContent,
            // @ts-ignore
            "value": node.childNodes.item(4).childNodes.item(0).childNodes.item(0).value,
            // @ts-ignore
            "unit": node.childNodes.item(4).childNodes.item(0).childNodes.item(1).value
        });
    });
    postXHR("webservice/estimate", arrayObjectToXML(arr, "products", "product")).then(function (res) {
    });
}
function arrayObjectToXML(arr, root, namedChild) {
    var str = "<" + root + ">";
    arr.forEach(function (item) {
        var tmp = "";
        tmp += "<" + namedChild + ">";
        Object.keys(item).forEach(function (obj) {
            tmp += "<" + obj + ">" + item[obj] + "</" + obj + ">";
        });
        tmp += "</" + namedChild + ">";
        str += tmp;
    });
    str += "</" + root + ">";
    return parseStringToDoc(str);
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
function button(name, onclick, _a) {
    var _b = _a === void 0 ? { marginLeft: "3px", marginRight: "3px" } : _a, marginLeft = _b.marginLeft, marginRight = _b.marginRight;
    var btn = document.createElement("button");
    btn.innerText = name;
    btn.onclick = onclick;
    btn.style.marginLeft = marginLeft;
    btn.style.marginRight = marginRight;
    return btn;
}
function removeChildById(tagId) {
    var tag = document.getElementById(tagId);
    if (tag) {
        document.getElementById(tagId).parentNode.removeChild(tag);
    }
}
function createTable(id, doc, title, columns, ITEM_MAX_PAGE, _a) {
    var _b = _a === void 0 ? {} : _a, _c = _b.isAction, isAction = _c === void 0 ? false : _c, _d = _b.actionTitle, actionTitle = _d === void 0 ? "Add" : _d, _e = _b.action, action = _e === void 0 ? null : _e, _f = _b.isFill, isFill = _f === void 0 ? false : _f;
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
    renderTableBody(id + "table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action, isFill: isFill });
    var maxPage = rootNode.childNodes.length % 10 == 0 ? rootNode.childNodes.length / ITEM_MAX_PAGE : Math.floor((rootNode.childNodes.length / ITEM_MAX_PAGE)) + 1;
    var text = document.createElement("code");
    var pagingSection = document.createElement("div");
    pagingSection.style.textAlign = "right";
    pagingSection.style.marginTop = "10px";
    pagingSection.appendChild(button("First", function () {
        page = 1;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action, isFill: isFill });
    }));
    pagingSection.appendChild(button("Pre", function () {
        if (page > 1) {
            page--;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action, isFill: isFill });
    }));
    text.innerText = page + "/" + maxPage;
    pagingSection.appendChild(text);
    pagingSection.appendChild(button("Next", function () {
        if (page < maxPage) {
            page++;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action, isFill: isFill });
    }));
    pagingSection.appendChild(button("Last", function () {
        page = maxPage;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction: isAction, actionTitle: actionTitle, action: action, isFill: isFill });
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
    var _b = _a === void 0 ? {} : _a, _c = _b.isAction, isAction = _c === void 0 ? false : _c, _d = _b.actionTitle, actionTitle = _d === void 0 ? "Action" : _d, _e = _b.action, action = _e === void 0 ? null : _e, _f = _b.isFill, isFill = _f === void 0 ? false : _f;
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
            if (isFill) {
                var spanInput = document.createElement("span");
                var textInput = document.createElement("input");
                textInput.type = "number";
                spanInput.appendChild(textInput);
                var unit_1 = document.createElement("select");
                var obj_1 = {
                    "phút/ngày": "m/d",
                    "giờ/ngày": "h/d"
                };
                Object.keys(obj_1).forEach(function (value, index) {
                    var unitOption = document.createElement("option");
                    unitOption.value = obj_1[value];
                    unitOption.innerText = value;
                    unit_1.appendChild(unitOption);
                });
                spanInput.appendChild(unit_1);
                row.insertCell().appendChild(spanInput);
            }
        }
    };
    for (var i = 0; i < tableData.length; i++) {
        _loop_1(i);
    }
}
function addToList(value) {
    var xmlDoc = getDocFromStorage(LIST_KEY.LIST_ADD);
    if (xmlDoc == undefined) {
        var arr = [value];
        setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(arr, "productEntities"));
        renderAddedList(arrayXMLToXMLDoc(arr, "productEntities"));
    }
    else {
        var newArr = nodeListToArray(xmlDoc.childNodes.item(0).childNodes);
        console.log(newArr.filter(function (item) { return item.childNodes.item(3).textContent == value.childNodes.item(3).textContent; }));
        if (newArr.filter(function (item) { return item.childNodes.item(3).textContent == value.childNodes.item(3).textContent; }).length == 0) {
            newArr.push(value);
        }
        ;
        setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(newArr, "productEntities"));
        renderAddedList(arrayXMLToXMLDoc(newArr, "productEntities"));
    }
}
function removeFromList(value) {
    var xmlDoc = getDocFromStorage(LIST_KEY.LIST_ADD);
    var newArr = nodeListToArray(xmlDoc.childNodes.item(0).childNodes);
    newArr = newArr.filter(function (item) { return item.childNodes.item(3).textContent !== value.childNodes.item(3).textContent; });
    setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(newArr, "productEntities"));
    renderAddedList(arrayXMLToXMLDoc(newArr, "productEntities"));
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
        xhr.send(null);
    });
}
function getDocFromStorage(key) {
    if (localStorage.getItem(key)) {
        var doc = parseStringToDoc(localStorage.getItem(key));
        return doc;
    }
    return undefined;
}
function setDocToStorage(key, value) {
    localStorage.setItem(key, parseDocToString(value));
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
function arrayXMLToXMLDoc(arr, root) {
    var str = "<" + root + ">";
    arr.forEach(function (item) {
        str += item.outerHTML;
    });
    str += "</" + root + ">";
    return parseStringToDoc(str);
}
function postXHR(url, data) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/xml");
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
