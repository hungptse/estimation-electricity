function initUI() {
    var root = document.getElementById("root");
    getXHR("webservice/product", {
        "size": 100,
        "page": 1
    }).then(function (res) {
        createSearch(res, root, "Name or Code", function () {
            // @ts-ignore
            var value = document.getElementById("txtSearch").value;
            getXHR("webservice/product/findLikeByNameOrCode", {
                "search": value
            }).then(function (res) {
                createTable(res, root, "Product Table", {
                    "Name": 5,
                    "Code": 1,
                    "Wattage (W)": 11,
                    "Image": 4
                }, 10);
            });
        });
        createTable(res, root, "Product Table", {
            "Name": 5,
            "Code": 1,
            "Wattage (W)": 11,
            "Image": 4
        }, 10);
    });
}
function createSearch(doc, root, title, onkeyup) {
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
    root.appendChild(divTag);
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
function createTable(doc, root, title, columns, ITEM_MAX_PAGE) {
    removeChildById("table-data");
    removeChildById("notification");
    var divTag = document.createElement("div");
    divTag.style.width = "100%";
    divTag.style.alignContent = "right";
    divTag.id = "table-data";
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
    var page = 1;
    var tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
    renderTableBody(table, tableData, columns);
    var maxPage = rootNode.childNodes.length % 10 == 0 ? rootNode.childNodes.length / ITEM_MAX_PAGE : Math.floor((rootNode.childNodes.length / ITEM_MAX_PAGE)) + 1;
    var text = document.createElement("code");
    var pagingSection = document.createElement("div");
    pagingSection.style.textAlign = "right";
    pagingSection.style.marginTop = "10px";
    pagingSection.appendChild(button("First", function () {
        page = 1;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(table, tableData, columns);
    }));
    pagingSection.appendChild(button("Pre", function () {
        if (page > 1) {
            page--;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(table, tableData, columns);
    }));
    text.innerText = page + "/" + maxPage;
    pagingSection.appendChild(text);
    pagingSection.appendChild(button("Next", function () {
        if (page < maxPage) {
            page++;
        }
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(table, tableData, columns);
    }));
    pagingSection.appendChild(button("Last", function () {
        page = maxPage;
        text.innerText = page + "/" + maxPage;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(table, tableData, columns);
    }));
    divTag.appendChild(table);
    divTag.appendChild(pagingSection);
    // table.appendChild(pagingSection);
    if (rootNode.hasChildNodes()) {
        root.appendChild(divTag);
    }
    else {
        var text_1 = document.createElement("h3");
        text_1.id = "notification";
        text_1.innerText = "No data found";
        root.appendChild(text_1);
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
function renderTableBody(table, tableData, columns) {
    removeChildById("table-body");
    var body = table.createTBody();
    body.id = "table-body";
    var _loop_1 = function (i) {
        var child = tableData[i];
        var row = body.insertRow(i);
        if (child.hasChildNodes()) {
            row.insertCell(0).innerText = i + 1 + "";
            Object.keys(columns).forEach(function (key, index) {
                row.insertCell(index + 1).innerText = child.childNodes.item(columns[key]).textContent;
            });
        }
    };
    for (var i = 0; i < tableData.length; i++) {
        _loop_1(i);
    }
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
