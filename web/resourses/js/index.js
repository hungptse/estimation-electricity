function initUI() {
    getXHR("webservice/product").then(res => {
        console.log(res)
    });
}
function getXHR(url) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
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
        xhr.send();
    });
}