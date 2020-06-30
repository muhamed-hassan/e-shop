function getItems(itemAction, requestUrl) {
    showPreloader();
    $('#items_container').html('');
    sendAuthorizedRequest(requestUrl, GET)
    .done(function (data, textStatus, jqXHR) {
        let itemsJson = data.items;
        if (itemsJson != null) {
            let itemsHtml = `<div class='row'>`;
            for (var index = 0; index < itemsJson.length; index++) {
                let item = itemsJson[index];
                let itemActionButton = '';
                if (requestUrl.includes('users')) {
                    itemActionButton += `<button id='user_${item.id}' type='button' class='btn btn-sm btn-warning' onclick='changeUserState(${item.id},${!item.active})'>${item.active ? itemAction[1] : itemAction[0]}</button>`;
                } else {
                    switch (itemAction) {
                        case 'Edit':
                            itemActionButton += `<button type='button' class='btn btn-sm btn-warning' onclick='preEditItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                            break;
                        case 'Delete':
                            itemActionButton += `<button type='button' class='btn btn-sm btn-danger' onclick='preDeleteItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                            break;
                    }
                }
    
                let viewDetailsBtn = !requestUrl.includes('vendors') && !requestUrl.includes('categories') ?
                        `<button type='button' class='btn btn-sm btn-secondary' onclick='getDetails("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>View</button>`
                        :
                        '';
                itemsHtml += `<div class='col-md-4'>
                                <div class='card mb-4 shadow-sm'>
                                    <div class='card-body'>
                                        <p class='card-text text-center'>${item.name}</p>
                                            <div class='d-flex justify-content-between align-items-center'>
                                                <div class='btn-group'>
                                                    ${viewDetailsBtn}
                                                    ${itemActionButton}
                                                </div>
                                            </div>
                                    </div>
                                </div>
                            </div>`;
            }
            itemsHtml += "</div>";
            if ($("#items_container").length == 0 && $("#paginator").length == 0) {
                let finalContent = `<div id='items_container'>${itemsHtml}</div>`;
                let paginator = createPaginator(data.allSavedItemsCount, requestUrl, itemAction);            
                finalContent += `${paginator}`; 
                if ($('#products_search_bar').length == 0) {
                    $("#content").html(finalContent); 
                } else {
                    $("#content").append(finalContent); 
                }                
            } else if ($("#paginator").length == 0) {
                $("#items_container").html(itemsHtml);
                $("#content").append(createPaginator(data.allSavedItemsCount, requestUrl, itemAction));
            } else {
                $("#items_container").html(itemsHtml);
            }  
        } else {
            showDataNotFound();
        }                     
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to fetch data', 'danger');
    }).always(function() {
        removePreloader();        
    });
}

function getProductDetails(requestUrl) {    
    sendAuthorizedRequest(requestUrl, GET)
    .then(function(data, textStatus, jqXHR) {
            return $.when(sendAuthorizedRequest('/vendors/' + data.vendorId, GET),
                            sendAuthorizedRequest('/categories/' + data.categoryId, GET),
                            $.Deferred().resolve(data));
        }, function(jqXHR, textStatus, errorThrown) {
            throw new Error(errorThrown);
    }).then(function(vendorResponse, categoryResponse, product) {
            let itemDetailsHtml = "<div class='row'>";
            Object.keys(product).forEach(function(key) {
                if (key != 'id' && key != 'active' && key != 'imageUploaded') {
                    switch (key) {
                        case 'vendorId':
                            itemDetailsHtml += generateItemDetailHtml('vendor', vendorResponse[0].name);
                            break;
                        case 'categoryId':
                            itemDetailsHtml += generateItemDetailHtml('category', categoryResponse[0].name);
                            break;
                        default:
                            itemDetailsHtml += generateItemDetailHtml(key, product[key]);
                    }
                }
            });
            let imageSrc;
            if (product.imageUploaded) {
                imageSrc = `/products/images/${product.id}`;
            } else {
                imageSrc = `resources/images/empty.jpg`;
            }
            itemDetailsHtml += `    <img id='image_of_product' src='${imageSrc}' width='500' height='333'>
                                </div>`;
        showModal({
            large: true,
            title: 'View product details',
            body: itemDetailsHtml
        });
    }, function(errorThrown) {
        showMessage('Failed to load the details', 'danger');
    }).always(function() {
        removePreloader();        
    });
}

function getUserDetails(requestUrl) {
    sendAuthorizedRequest(requestUrl, GET)
    .done(function (data, textStatus, jqXHR) {
        let itemDetailsHtml = `<div class='row'>`;
        Object.keys(data).forEach(function(key) {
            if (key != 'id' && key != 'active') {
                itemDetailsHtml += generateItemDetailHtml(key, data[key]);
            }
        });
        itemDetailsHtml += `</div>`;
        showModal({
            large: true,
            title: 'View user details',
            body: itemDetailsHtml
        });
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to load the details', 'danger');
    }).always(function() { 
        removePreloader();
    });
}

function getDetails(requestUrl) {
    clearMessagesSection();
    showPreloader();
    if (requestUrl.includes("products")) {
        getProductDetails(requestUrl);
    } else if (requestUrl.includes("users")) {
        getUserDetails(requestUrl);
    }
}


