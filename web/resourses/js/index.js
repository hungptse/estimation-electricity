function initUI() {
    var root = document.getElementById("root");
    getXHR("webservice/product", {
        "size": 100,
        "page": 1
    }).then(function (res) {
        createSearch(res, root, "Name", function () {
            // @ts-ignore
            var value = document.getElementById("txtSearch").value;
            console.log(value);
        });
        createTable(res, root, "Product Table", {
            "Name": 5,
            "Code": 1,
            "Wattage (W)": 11
        });
    });
}
function createSearch(doc, root, title, callBackSearch) {
    var divTag = document.createElement("div");
    var titleTag = document.createElement("span");
    titleTag.innerText = title + " ";
    var inputTag = document.createElement("input");
    inputTag.id = "txtSearch";
    inputTag.type = "text";
    var searchBtn = document.createElement("input");
    searchBtn.type = "submit";
    searchBtn.value = "Search";
    inputTag.onkeydown = callBackSearch;
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
function createTable(doc, root, title, columns) {
    var table = document.createElement("table");
    var rootNode = doc.childNodes.item(0);
    table.style.border = "1px solid black";
    table.border = "1";
    table.createCaption().innerText = title;
    var row = table.createTHead().insertRow();
    row.insertCell(0).innerText = "No.";
    Object.keys(columns).forEach(function (key, index) {
        row.insertCell(index + 1).innerText = key;
    });
    var _loop_1 = function (i) {
        var child = rootNode.childNodes.item(i);
        var row_1 = table.createTBody().insertRow();
        if (child.hasChildNodes()) {
            row_1.insertCell(0).innerText = i + 1 + "";
            Object.keys(columns).forEach(function (key, index) {
                row_1.insertCell(index + 1).innerText = child.childNodes.item(columns[key]).textContent;
            });
        }
    };
    for (var i = 0; i < rootNode.childNodes.length; i++) {
        _loop_1(i);
    }
    if (rootNode.hasChildNodes()) {
        root.appendChild(table);
    }
    else {
        var text = document.createElement("h3");
        text.innerText = "No data found";
        root.appendChild(text);
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
