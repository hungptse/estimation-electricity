
function initUI() {
    const root = document.getElementById("root");
    const productList = document.createElement("div");
    root.appendChild(productList);
    getXHR("webservice/product", {
        "size": 100,
        "page": 1
    }).then(res => {
        createSearch(res, productList, "Name or Code", () => {
            // @ts-ignore
            let value = document.getElementById("txtSearch").value;
            getXHR("webservice/product/findLikeByNameOrCode", {
                "search": value
            }).then(res => {
                createTable("main-table",res, productList, "Product Table", {
                    "Name": 5,
                    "Code": 1,
                    "Wattage (W)": 11,
                }, 10, { action: addToList, actionTitle: "Add", isAction: true });
            })
        });

        createTable("main-table",res, productList, "Product Table", {
            "Name": 5,
            "Code": 1,
            "Wattage (W)": 11,
        }, 10, { action: addToList, actionTitle: "Add", isAction: true });


        onstorage = function (ev) {
            console.log(ev)
        };

        const addedlist = document.createElement("div");

        var xmlString = "<productEntities></productEntities>";
        localStorage.setItem("data",xmlString);

        root.appendChild(addedlist);
        createTable("added-list",parseStringToDoc(xmlString), addedlist, "Product Table", {
            "Name": 5,
            "Code": 1,
            "Wattage (W)": 11,
        }, 10, { action: null, actionTitle: "Add", isAction: false });
    });
}
function parseStringToDoc(xmlString) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xmlString, "text/xml");
    return xmlDoc;
}

function createSearch(doc: Document, root: HTMLElement, title: string, onkeyup) {
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

function button(name: string, onclick) {
    const btn = document.createElement("button");
    btn.innerText = name;
    btn.onclick = onclick;
    btn.style.marginLeft = "3px";
    btn.style.marginRight = "3px";
    return btn;
}

function removeChildById(tagId: string) {
    const tag = document.getElementById(tagId);
    if (tag) {
        document.getElementById(tagId).parentNode.removeChild(tag)
    }
}

function createTable(id: string, doc: Document, root: HTMLElement, title: string, columns: Object, ITEM_MAX_PAGE, { isAction = false, actionTitle = "Add", action = null } = {}) {
    removeChildById(id);
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
    renderTableBody("table-body",table, tableData, columns, { isAction, actionTitle, action });
    let maxPage = rootNode.childNodes.length % 10 == 0 ? rootNode.childNodes.length / ITEM_MAX_PAGE : Math.floor((rootNode.childNodes.length / ITEM_MAX_PAGE)) + 1;
    const text = document.createElement("code");
    const pagingSection = document.createElement("div");
    pagingSection.style.textAlign = "right";
    pagingSection.style.marginTop = "10px";
    pagingSection.appendChild(button("First", () => {
        page = 1;
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body",table, tableData, columns, { isAction, actionTitle, action });
    }));
    pagingSection.appendChild(button("Pre", () => {
        if (page > 1) {
            page--;
        }
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body",table, tableData, columns, { isAction, actionTitle, action });
    }));

    text.innerText = `${page}/${maxPage}`;
    pagingSection.appendChild(text);
    pagingSection.appendChild(button("Next", () => {
        if (page < maxPage) {
            page++;
        }
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body",table, tableData, columns, { isAction, actionTitle, action });
    }));
    pagingSection.appendChild(button("Last", () => {
        page = maxPage;
        text.innerText = `${page}/${maxPage}`;
        tableData = getPagingData(page, ITEM_MAX_PAGE, rootNode);
        renderTableBody("table-body",table, tableData, columns, { isAction, actionTitle, action });
    }));

    divTag.appendChild(table);
    divTag.appendChild(pagingSection);
    if (rootNode.hasChildNodes()) {
        root.appendChild(divTag);
    } else {
        const text = document.createElement("h3");
        text.id = "notification";
        text.innerText = "No data found";
        root.appendChild(text);
    }
}

function getPagingData(page, ITEM_MAX_PAGE, rootNode: ChildNode) {
    let tableData = [];
    let start = (page - 1) * ITEM_MAX_PAGE;
    for (let i = start; i < start + ITEM_MAX_PAGE; i++) {
        if (rootNode.childNodes.item(i)) {
            tableData.push(rootNode.childNodes.item(i));
        }
    }
    return tableData;
}

function renderTableBody(id: string,table: HTMLTableElement, tableData, columns, { isAction = false, actionTitle = "Action", action = null } = {}) {
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
        }
    }
}

function addToList(value) {
    const root = document.createElement("div");

    const xmlDoc = parseStringToDoc(localStorage.getItem("data")).childNodes[0].appendChild(value);

    createTable("added-list",xmlDoc, root, "List", {
        "Name": 5,
        "Code": 1,
        "Wattage (W)": 11,
    }, 10);
    localStorage.setItem("data",xmlDoc);
    // document.getElementById("root").appendChild(root);
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