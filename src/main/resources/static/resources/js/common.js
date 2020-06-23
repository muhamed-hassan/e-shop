function getItems(itemAction, requestUrl) {
    showPreloader();
    $("#items_container").remove();
    sendAuthorizedRequest(requestUrl, 'GET')
    .done(function (data, textStatus, jqXHR) {
        if (jqXHR.status == 200) {
            let itemsJson = data.items;
            let itemsHtml = `<div class='row'>`;
            for (var index = 0; index < itemsJson.length; index++) {
                let item = itemsJson[index];
                let itemActionButton = "";
                if (requestUrl.includes("users")) {
                    itemActionButton += `<button id='user_${item.id}' type='button' class='btn btn-sm btn-warning' onclick='changeUserState(${item.id},${!item.active})'>${item.active ? itemAction[1] : itemAction[0]}</button>`;
                } else {
                    switch (itemAction) {
                        case "Edit":
                            itemActionButton += `<button type='button' class='btn btn-sm btn-warning' onclick='preEditItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                            break;
                        case "Delete":
                            itemActionButton += `<button type='button' class='btn btn-sm btn-danger' onclick='deleteItem("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>${itemAction}</button>`;
                            break;
                    }
                }

                let viewDetailsBtn = !requestUrl.includes('vendors') && !requestUrl.includes('categories') ?
                        `<button type='button' class='btn btn-sm btn-secondary' onclick='getDetails("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>View</button>`
                        :
                        "";
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
            let finalContent = `<div id='items_container'>${itemsHtml}</div>`;
            if ($('#products_search_bar').length > 0) {
                $("#main").append(finalContent);
            } else {
                $("#main").html(finalContent);
            }
        } else if (jqXHR.status == 404) {
            // data not found
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log(jqXHR); //show error message
    }).always(function() {
        removePreloader();
    });
}

function getProductMoreDetails(product) {
    return $.when(sendAuthorizedRequest('/vendors/' + product.vendorId, 'GET'),
                    sendAuthorizedRequest('/categories/' + product.categoryId, 'GET'),
                    $.Deferred().resolve(product));
}

function getDetails(requestUrl) {
    showPreloader();
    if (requestUrl.includes("products")) {
        sendAuthorizedRequest(requestUrl, 'GET')
        .then(function(data, textStatus, jqXHR) {
                if (jqXHR.status == 200) {
                    return getProductMoreDetails(data);
                } else if (jqXHR.status == 404) {
                    throw new Error(`Product with id: ${requestUrl.substring(requestUrl.lastIndexOf('/') + 1)} doesn't exist`);
                }
            }, function(jqXHR, textStatus, errorThrown) {
                throw new Error(errorThrown);
          })
        .then(function(vendorResponse, categoryResponse, product) {
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
                $("#main").html(itemDetailsHtml);
        }, function(errorThrown) {
            console.error(errorThrown);
        }).always(function(){
            removePreloader();
        });
    } else {
        sendAuthorizedRequest(requestUrl, 'GET')
        .done(function (data, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                let itemDetailsHtml = `<div class='row'>`;
                Object.keys(data).forEach(function(key) {
                    if (key != 'id' && key != 'active') {
                        itemDetailsHtml += generateItemDetailHtml(key, data[key]);
                    }
                });
                itemDetailsHtml += `</div>`;
                $("#main").html(itemDetailsHtml);
            } else if (jqXHR.status == 404) {
                throw new Error('Data not found');
            }})
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.error(errorThrown);
        });
    }
}














