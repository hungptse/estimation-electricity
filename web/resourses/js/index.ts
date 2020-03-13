function initUI() {
    const root = document.getElementById("root");
    getXHR("webservice/product", {
        "size": 100,
        "page": 1
    }).then(res => {
        createSearch(res, root, "Name", () => {
            // @ts-ignore
            let value = document.getElementById("txtSearch").value;
            console.log(value)
        });
        createTable(res, root, "Product Table" ,{
            "Name": 5,
            "Code": 1,
            "Wattage (W)" : 11
        });
    });
}
function createSearch(doc :Document, root: HTMLElement, title : string, callBackSearch) {
    const divTag = document.createElement("div");
    const titleTag = document.createElement("span");
    titleTag.innerText =  title + " ";

    const inputTag = document.createElement("input");
    inputTag.id = "txtSearch";
    inputTag.type = "text";

    const searchBtn = document.createElement("input");
    searchBtn.type = "submit";
    searchBtn.value = "Search";

    inputTag.onkeydown = callBackSearch;

    divTag.appendChild(titleTag);
    divTag.appendChild(inputTag);
    divTag.appendChild(searchBtn);
    root.appendChild(divTag);
}
function objectToQueryParam(obj) {
    let result = "";
    // @ts-ignore
    Object.entries(obj).forEach(([key, value]) => {
        result += `${key}=${value}&`
    });
    return result.slice(0, result.length - 1);
}

function createTable(doc: Document, root: HTMLElement,  title : string, columns: Object) {
    const table = document.createElement("table");
    const rootNode = doc.childNodes.item(0);
    table.style.border = "1px solid black";
    table.border = "1";
    table.createCaption().innerText = title;
    const row = table.createTHead().insertRow();
    row.insertCell(0).innerText = "No.";
    Object.keys(columns).forEach((key, index) => {
        row.insertCell(index + 1).innerText = key;
    });


    for (let i = 0; i < rootNode.childNodes.length; i++) {
        const child = rootNode.childNodes.item(i);
        const row =  table.createTBody().insertRow();
        if (child.hasChildNodes()){
            row.insertCell(0).innerText = i + 1 + "";
            Object.keys(columns).forEach((key, index) => {
                row.insertCell(index + 1).innerText = child.childNodes.item(columns[key]).textContent;
            });
        }
    }
    if (rootNode.hasChildNodes()){
        root.appendChild(table);
    } else {
        const text = document.createElement("h3");
        text.innerText = "No data found";
        root.appendChild(text);
    }
}


function getXHR(url, data) {
    const xhr = new XMLHttpRequest();
    if (Object.keys(data).length !== 0) {
        url += "?" + objectToQueryParam(data);
    }
    xhr.open("GET", url, true);
    // @ts-ignore
    return new Promise((resolve, reject) => {
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                if (xhr.status >= 300) {
                    reject("Error, status code = " + xhr.status)
                } else {
                    resolve(xhr.responseXML);
                }
            }
        };
        xhr.send(data);
    });
}

function postXHR(url, data) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    // @ts-ignore
    return new Promise((resolve, reject) => {
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                if (xhr.status >= 300) {
                    reject("Error, status code = " + xhr.status)
                } else {
                    resolve(xhr.responseXML);
                }
            }
        };
        xhr.send(data);
    });
}