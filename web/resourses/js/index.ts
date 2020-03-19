const LIST_KEY = {
    LIST_PRODUCT: "LIST_PRODUCT",
    LIST_ADD: "LIST_ADD"
};

function initUI() {
    const root = document.getElementById("root");
    const productList = document.createElement("div");
    productList.id = "product-list";
    const addedList = document.createElement("div");
    addedList.id = "added-list";
    root.appendChild(productList);
    root.appendChild(document.createElement("hr"));
    root.appendChild(addedList);
    const search = createSearch("Name or Code", () => {
        // @ts-ignore
        let value = document.getElementById("txtSearch").value;
        const result = nodeListToArray(getDocFromStorage(LIST_KEY.LIST_PRODUCT).childNodes).map(node => nodeListToArray(node.childNodes).filter(n => n.childNodes.item(2).textContent.toLowerCase().indexOf(value.toLowerCase()) != -1));
        if (result[0].length > 0) {
            renderProductTable(arrayXMLToXMLDoc(result[0], "productEntities"));
        } else {
            getXHR("webservice/product/findLikeByNameOrCode", {
                "search": value
            }).then(res => {
                renderProductTable(res);
            })
        }
    });
    productList.appendChild(search);
    if (getDocFromStorage(LIST_KEY.LIST_PRODUCT) == undefined) {
        getXHR("webservice/product", {
            "size": 100,
            "page": 1
        }).then(res => {
            localStorage.setItem(LIST_KEY.LIST_PRODUCT, parseDocToString(res));
            renderProductTable(res);
        });
    } else {
        renderProductTable(getDocFromStorage(LIST_KEY.LIST_PRODUCT));
    }
    if (getDocFromStorage(LIST_KEY.LIST_ADD) != undefined) {
        renderAddedList(getDocFromStorage(LIST_KEY.LIST_ADD));
    }

}

function renderAddedList(xmlDoc: XMLDocument, isFill = false, maxPage = 10) {
    const addedList = document.getElementById("added-list");
    removeChildById("added-table");
    var isAction = true;
    if (isFill) {
        isAction = false;
    }
    const addTable = createTable("added-table", xmlDoc, "Added Table", {
        "Name": 2,
        "Code": 0,
        "Wattage (W)": 5,
    }, 10, { action: removeFromList, actionTitle: "Remove", isAction: isAction, isFill });
    addedList.appendChild(addTable);

    const nextStepBtn = button("Next", () => {
        fillTimePage();
    });
    addedList.appendChild(nextStepBtn);
}

function fillTimePage() {
    const root = document.getElementById("root");
    root.innerHTML = "";
    const addedList = document.createElement("div");
    addedList.id = "added-list";
    root.appendChild(addedList);
    if (getDocFromStorage(LIST_KEY.LIST_ADD) != undefined) {
        renderAddedList(getDocFromStorage(LIST_KEY.LIST_ADD), true, 100);
    }
    addedList.appendChild(button("Send", () => {
        sendTableToServer();
    }))
}

function sendTableToServer() {
    const doc = document.getElementById("added-tabletable-body");
    var arr = [];
    doc.childNodes.forEach(node => {
        arr.push({
            "id": node.childNodes.item(0).textContent,
            // @ts-ignore
            "value": node.childNodes.item(4).childNodes.item(0).childNodes.item(0).value,
            // @ts-ignore
            "unit": node.childNodes.item(4).childNodes.item(0).childNodes.item(1).value
        })
    });
    postXHR("webservice/estimate", arrayObjectToXML(arr, "products", "product")).then(res => {

    });
}

function arrayObjectToXML(arr, root, namedChild) {
    let str = `<${root}>`;
    arr.forEach(item => {
        let tmp = "";
        tmp += `<${namedChild}>`;
        Object.keys(item).forEach(obj => {
            tmp += `<${obj}>${item[obj]}</${obj}>`;
        });
        tmp += `</${namedChild}>`;
        str += tmp;
    });
    str += `</${root}>`;
    return parseStringToDoc(str);
}

function renderProductTable(xmlDoc: XMLDocument) {
    const productList = document.getElementById("product-list");
    removeChildById("main-table");
    const productTable = createTable("main-table", xmlDoc, "Product Table", {
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

function parseDocToString(xmlDoc: XMLDocument) {
    return new XMLSerializer().serializeToString(xmlDoc.documentElement);
}

function createSearch(title: string, onkeyup) {
    const divTag = document.createElement("div");
    const titleTag = document.createElement("span");
    titleTag.innerText = title + " ";

    const inputTag = document.createElement("input");
    inputTag.id = "txtSearch";
    inputTag.type = "text";

    const searchBtn = document.createElement("input");
    searchBtn.type = "submit";
    searchBtn.value = "Search";

    inputTag.onkeyup = onkeyup;

    divTag.appendChild(titleTag);
    divTag.appendChild(inputTag);
    divTag.appendChild(searchBtn);
    return divTag;
}

function objectToQueryParam(obj) {
    let result = "";
    // @ts-ignore
    Object.entries(obj).forEach(([key, value]) => {
        result += `${key}=${value}&`
    });
    return result.slice(0, result.length - 1);
}

function button(name: string, onclick, {
    marginLeft, marginRight
} = { marginLeft: "3px", marginRight: "3px" }) {
    const btn = document.createElement("button");
    btn.innerText = name;
    btn.onclick = onclick;
    btn.style.marginLeft = marginLeft;
    btn.style.marginRight = marginRight;
    return btn;
}

function removeChildById(tagId: string) {
    const tag = document.getElementById(tagId);
    if (tag) {
        document.getElementById(tagId).parentNode.removeChild(tag)
    }
}

function createTable(id: string, doc: Document, title: string, columns: Object, ITEM_MAX_PAGE, { isAction = false, actionTitle = "Add", action = null, isFill = false } = {}) {
    // removeChildById(id);
    removeChildById("notification");
    const divTag = document.createElement("div");
    divTag.style.width = "100%";
    divTag.style.alignContent = "right";
    divTag.id = id;
    const table = document.createElement("table");
    table.style.width = "100%";
    const rootNode = doc.childNodes.item(0);
    table.style.border = "1px solid black";
    table.border = "1";
    table.createCaption().innerText = title;
    const row = table.createTHead().insertRow();
    row.insertCell(0).innerText = "No.";
    Object.keys(columns).forEach((key, index) => {
        row.insertCell(index + 1).innerText = key;
    });
    if (isAction) {
        row.insertCell().innerText = "Action";
    }

    let page = 1;
    let tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
    renderTableBody(id + "table-body", table, tableData, columns, { isAction, actionTitle, action, isFill });
    let maxPage = rootNode.childNodes.length % 10 == 0 ? rootNode.childNodes.length / ITEM_MAX_PAGE : Math.floor((rootNode.childNodes.length / ITEM_MAX_PAGE)) + 1;
    const text = document.createElement("code");
    const pagingSection = document.createElement("div");
    pagingSection.style.textAlign = "right";
    pagingSection.style.marginTop = "10px";
    pagingSection.appendChild(button("First", () => {
        page = 1;
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction, actionTitle, action, isFill });
    }));
    pagingSection.appendChild(button("Pre", () => {
        if (page > 1) {
            page--;
        }
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction, actionTitle, action, isFill });
    }));

    text.innerText = `${page}/${maxPage}`;
    pagingSection.appendChild(text);
    pagingSection.appendChild(button("Next", () => {
        if (page < maxPage) {
            page++;
        }
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction, actionTitle, action, isFill });
    }));
    pagingSection.appendChild(button("Last", () => {
        page = maxPage;
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody(id + "table-body", table, tableData, columns, { isAction, actionTitle, action, isFill });
    }));

    divTag.appendChild(table);
    divTag.appendChild(pagingSection);
    if (rootNode.hasChildNodes()) {
        return divTag;
    } else {
        const text = document.createElement("h3");
        text.id = "notification";
        text.innerText = "No data found";
        return text;
    }
}

function getPagingData(page, ITEM_MAX_PAGE, rootNode: ChildNode): ChildNode[] {
    let tableData: ChildNode[] = [];
    let start = (page - 1) * ITEM_MAX_PAGE;
    for (let i = start; i < start + ITEM_MAX_PAGE; i++) {
        if (rootNode.childNodes.item(i)) {
            tableData.push(rootNode.childNodes.item(i));
        }
    }
    return tableData;
}

function renderTableBody(id: string, table: HTMLTableElement, tableData, columns, { isAction = false, actionTitle = "Action", action = null, isFill = false } = {}) {
    removeChildById(id);
    const body = table.createTBody();
    body.id = id;
    for (let i = 0; i < tableData.length; i++) {
        const child = tableData[i];
        const row = body.insertRow(i);
        if (child.hasChildNodes()) {
            row.insertCell(0).innerText = i + 1 + "";
            Object.keys(columns).forEach((key, index) => {
                let content = child.childNodes.item(columns[key]).textContent;
                if (!content) {
                    content = "No available";
                }
                row.insertCell(index + 1).innerText = child.childNodes.item(columns[key]).textContent;
            });
            if (isAction) {
                const actionBtn = document.createElement("button");
                actionBtn.innerHTML = actionTitle;
                // @ts-ignore
                actionBtn.onclick = (e) => {
                    e.preventDefault();
                    action(child);
                };
                row.insertCell().appendChild(actionBtn);
            }
            if (isFill) {
                const spanInput = document.createElement("span");
                const textInput = document.createElement("input");
                textInput.type = "number";
                spanInput.appendChild(textInput);
                const unit = document.createElement("select");
                const obj = {
                    "phút/ngày": "m/d",
                    "giờ/ngày": "h/d"
                };
                Object.keys(obj).forEach((value, index) => {
                    let unitOption = document.createElement("option");
                    unitOption.value = obj[value];
                    unitOption.innerText = value;
                    unit.appendChild(unitOption);
                });
                spanInput.appendChild(unit);
                row.insertCell().appendChild(spanInput);
            }
        }
    }
}

function addToList(value) {
    const xmlDoc = getDocFromStorage(LIST_KEY.LIST_ADD);
    if (xmlDoc == undefined) {
        const arr = [value];
        setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(arr, "productEntities"));
        renderAddedList(arrayXMLToXMLDoc(arr, "productEntities"));
    } else {
        const newArr = nodeListToArray(xmlDoc.childNodes.item(0).childNodes);
        console.log(newArr.filter(item => item.childNodes.item(3).textContent == value.childNodes.item(3).textContent))
        if (newArr.filter(item => item.childNodes.item(3).textContent == value.childNodes.item(3).textContent).length == 0) {
            newArr.push(value);
        }
        ;
        setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(newArr, "productEntities"));
        renderAddedList(arrayXMLToXMLDoc(newArr, "productEntities"));
    }
}

function removeFromList(value) {
    const xmlDoc = getDocFromStorage(LIST_KEY.LIST_ADD);
    let newArr = nodeListToArray(xmlDoc.childNodes.item(0).childNodes);
    newArr = newArr.filter(item => item.childNodes.item(3).textContent !== value.childNodes.item(3).textContent);
    setDocToStorage(LIST_KEY.LIST_ADD, arrayXMLToXMLDoc(newArr, "productEntities"));
    renderAddedList(arrayXMLToXMLDoc(newArr, "productEntities"));
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
        xhr.send(null);
    });
}


function getDocFromStorage(key): XMLDocument {
    if (localStorage.getItem(key)) {
        const doc = parseStringToDoc(localStorage.getItem(key));
        return doc;
    }
    return undefined;
}

function setDocToStorage(key, value) {
    localStorage.setItem(key, parseDocToString(value));
}

function nodeListToArray(list: NodeListOf<ChildNode>): ChildNode[] {
    const arr = [];
    if (list.length) {
        for (let i = 0; i < list.length; i++) {
            arr.push(list.item(i));
        }
    }
    return arr;
}

function arrayXMLToXMLDoc(arr, root) {
    let str = `<${root}>`;
    arr.forEach(item => {
        str += item.outerHTML;
    });
    str += `</${root}>`;
    return parseStringToDoc(str);
}

function postXHR(url, data) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/xml");
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