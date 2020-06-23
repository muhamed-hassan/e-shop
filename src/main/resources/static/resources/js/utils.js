function showPreloader() {
    let preloaderHtml = `<div id='preloader' class='offset-5 loadingio-spinner-spinner-bktd5cbe8lt'>
                            <div class='ldio-x75mdke3og'>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                               <div></div>
                            </div>
                         </div>`;
    $('#main').append(preloaderHtml);
}

function removePreloader() {
    $('#preloader').remove();
}

function sendAuthorizedRequest(requestUrl, httpMethod) {
    return $.ajax({url: requestUrl,
                    type: httpMethod,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
                });
}

function sendAuthorizedRequest(requestUrl, httpMethod, requestBody, contentType) {
    return $.ajax({url: requestUrl,
                    type: httpMethod,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
                                'Content-Type': contentType},
                    data: requestBody
                });
}

function createSelectMenuFrom(items, menuName, selectedItemKey) {
    let itemsSelectOptionsHtml = `<select id='${menuName}' class='form-control w-25'>`;
    for (var index = 0; index < items.length; index++) {
        itemsSelectOptionsHtml += `<option value='${items[index].id}' ${items[index].id == selectedItemKey ? 'selected' : ''}>${items[index].name}</option>`;
    }
    itemsSelectOptionsHtml += `</select>`;
    return itemsSelectOptionsHtml;
}

function generateItemDetailHtml(key, value) {
    return `<div class='col-md-6 mb-3'>
                <p class='font-weight-bold text-capitalize'>${key}: </p>
                <p class='font-weight-normal text-capitalize'>${value == null ? 'N/A' : value}</p>
            </div>`;
}

function showMessage(text, msgType) {
    let msg = `<div id='alert_msg' class='alert alert-${msgType}' role='alert'>
                    ${text}
                </div>`;
    $('#msg_alert').html(msg);
}