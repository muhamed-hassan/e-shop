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
    $('#content').append(preloaderHtml);
}

function removePreloader() {
    $('#preloader').remove();
}

function sendAuthorizedRequest(requestUrl, httpMethod) {
    return $.ajax({url: requestUrl,
                    type: httpMethod,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
                                'Accept': 'application/json'}
                });
}

function sendAuthorizedRequest(requestUrl, httpMethod, requestBody, contentType) {
    return $.ajax({url: requestUrl,
                    type: httpMethod,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
                                'Content-Type': contentType,
                                'Accept': 'application/json'},
                    data: requestBody
                });
}

function createSelectMenuFrom(items, menuName, selectedItemKey) {
    let itemsSelectOptionsHtml = `<select id='${menuName}' class='form-control w-25' required>`;
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

function clearMessagesSection() {
    $('#msg_alert').html('');
}

function showModal(configs) {
    if (configs.large) {
        $(".modal-dialog").addClass("modal-xl");
    }
    $('#modal_title').html(configs.title);
    $('#modal_body').html(configs.body);
    if (configs.actionBtn == null) {
        $('#modal_action_btn').hide();
    } else {
        $('#modal_action_btn').attr('onclick', configs.actionBtn.onclick);
        $('#modal_action_btn').html(configs.actionBtn.label);
    }
    $('#modal').modal('show');    
}

function hideModal() {
    $('#modal').modal('hide');
}

$('#modal').on('hidden.bs.modal', function () {
    resetModal();
});

function resetModal() {
    $(".modal-dialog").removeClass('modal-xl');
    $('#modal_title').html('');
    $('#modal_body').html('');
    $('#modal_action_btn').removeAttr('onclick');
    $('#modal_action_btn').html('');
    $('#modal_action_btn').show();
}

function showDataNotFound() {
    if ($("#items_container").length == 0) {
        $("#content").append(`<div id='items_container'><p class="font-weight-bold text-center">data not found ...!!</p></div>`);
    } else {
        $("#items_container").html(`<p class="font-weight-bold text-center">data not found ...!!</p>`);
    }
    
}

