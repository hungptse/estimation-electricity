function initUI() {
    getXHR("webservice/product",objectToQueryParam({
        "page" : 1,
        "size" : 10
    })).then(res => {
        console.log(res)
    });
}
function objectToQueryParam(obj) {
    var result = "";
    Object.entries(obj).forEach(([key, value]) => {
        result += `${key}=${value}&`
    });
    return result.slice(0, result.length - 1);
}
function getXHR(url, data) {
    const xhr = new XMLHttpRequest();
    if (data != ""){
        url += "?" + data;
    }
    xhr.open("GET", url, true);
    return new Promise((resolve, reject) => {
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4) {
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
    return new Promise((resolve, reject) => {
        xhr.onreadystatechange = () => {
            if (xhr.readyState == 4) {
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