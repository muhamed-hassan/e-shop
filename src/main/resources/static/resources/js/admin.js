let tmpUploadedImage;

function showAdminMenu() {
    let adminMenu = `<div id='admin_menu' class='container'>
                        <div class='row'>
                            <div class='col-3'>
                                <div class='dropdown'>
                                    <button id='manage_users' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                        Manage users
                                    </button>
                                    <div class='dropdown-menu' aria-labelledby='manage_users'>
                                        <button class='dropdown-item' type='button' onclick='prepareEditItemsOfUsers(\"/users?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Activate/Deactivate user(s)
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class='col-3'>
                                <div class='dropdown'>
                                    <button id='manage_products' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                        Manage products
                                    </button>
                                    <div class='dropdown-menu' aria-labelledby='manage_products'>
                                        <button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Product\")'>
                                            Add product
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareEditItems(\"/products?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Edit product
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/products?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Delete product
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class='col-3'>
                                <div class='dropdown'>
                                    <button id='manage_vendors' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                        Manage vendors
                                    </button>
                                    <div class='dropdown-menu' aria-labelledby='manage_vendors'>
                                        <button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Vendor\")'>
                                            Add vendor
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareEditItems(\"/vendors?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Edit vendor
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/vendors?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Delete vendor
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class='col-3'>
                                <div class='dropdown'>
                                    <button id='manage_categories' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                        Manage categories
                                    </button>
                                    <div class='dropdown-menu' aria-labelledby='manage_categories'>
                                        <button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Category\")'>
                                            Add category
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareEditItems(\"/categories?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Edit category
                                        </button>
                                        <button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/categories?start-position=0&sort-by=id&sort-direction=ASC\")'>
                                            Delete category
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>`;
    $('#header').append(adminMenu);
}

/* ********************************************************************************************************************************** */

function prepareEditItemsOfUsers(requestUrl) {
    resetPaginator();
    clearMessagesSection();
    getItems([ACTIVATE, DEACTIVATE], requestUrl);    
}

function changeUserState(id, inversedState) {
    showPreloader();
    sendAuthorizedRequest(`/users/${id}`, PATCH, JSON.stringify({active: inversedState}), 'application/json')
    .done(function (data, textStatus, jqXHR) {        
        let elementId = `#user_${id}`;
        if ($(elementId).text() == ACTIVATE) {
            $(elementId).text(DEACTIVATE);
        } else {
            $(elementId).text(ACTIVATE);
        }
        $(elementId).attr('onclick', `changeUserState(${id},${!inversedState})`);
        showMessage('User updated successfully', 'success');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to update this user', 'danger');
    }).always(function() {
        removePreloader();
    });
}

/* ********************************************************************************************************************************** */

function prepareEditItems(requestUrl) {
    resetPaginator();
    clearMessagesSection();
    getItems("Edit", requestUrl);
}

function preEditItem(requestUrlOfItem) {    
    let isProduct = requestUrlOfItem.includes("products"), 
        isVendor = requestUrlOfItem.includes("vendors"), 
        isCategory = requestUrlOfItem.includes("categories");
    if (isProduct) {
        showPreloader();
        $.when($.Deferred().resolve(requestUrlOfItem.substr(requestUrlOfItem.lastIndexOf("/") + 1)),
                sendAuthorizedRequest(requestUrlOfItem, GET),
                sendAuthorizedRequest('/vendors', GET),
                sendAuthorizedRequest('/categories', GET)
        ).then(function(productId, productResponse, vendorsResponse, categoriesResponse) {
            let product = productResponse[0];
            let vendors = vendorsResponse[0];
            let categories = categoriesResponse[0];
            let vendorsSelectOptionsHtml = createSelectMenuFrom(vendors, VENDOR_ID, product.vendor_id);
            let categoriesSelectOptionsHtml = createSelectMenuFrom(categories, CATEGORY_ID, product.category_id);
            let htmlContent = `<div id='product_under_edit' class='row'>`;
            Object.keys(product)
                    .forEach(function(key) {
                        if (key != 'image_uploaded') {
                            switch (key) {
                                case VENDOR_ID:
                                    htmlContent += `<div class='col-md-6 mb-3'>
                                                        <p class='font-weight-bold text-capitalize'>vendor: </p>
                                                        ${vendorsSelectOptionsHtml}
                                                    </div>`;
                                    break;
                                case CATEGORY_ID:
                                    htmlContent += `<div class='col-md-6 mb-3'>
                                                        <p class='font-weight-bold text-capitalize'>category: </p>
                                                        ${categoriesSelectOptionsHtml}
                                                    </div>`;
                                    break;
                                default:
                                    htmlContent += `<div class='col-md-6 mb-3'>
                                                        <p class='font-weight-bold text-capitalize'>${key}: </p>
                                                        <input id='${key}' type='text' value='${product[key]}' class='form-control'/>
                                                    </div>`;
                            }
                        }
                    });
            let imageSrc;
            if (product.image_uploaded) {
                imageSrc = `/products/images/${productId}`;
            } else {
                imageSrc = `resources/images/empty.jpg`;
            }
            htmlContent += `    <div class='col-md-6 mb-3'>
                                        <p class='font-weight-bold text-capitalize'>image: </p>
                                        <input id='image' type='file' class='form-control-file'>
                                    </div>
                                    <img id='image_of_product' src='${imageSrc}' width='500' height='333'>
                                </div>
                                <button onclick='editProduct(${productId})' class='form-control btn btn-primary'>Save</button>`;                                
            $("#content").html(htmlContent);
            attachImageChangeListener();
        }, function(errorThrown) {
            showMessage('Failed to load the details of this item', 'danger');
        }).always(function() {
            removePreloader();
        });
    } else if (isVendor || isCategory) {
        sendAuthorizedRequest(requestUrlOfItem, GET)
        .done(function(data, textStatus, jqXHR) {
            clearMain();
            let type;
            if (isVendor) {
                type = 'Vendor';
            } else if (isCategory) {
                type = 'Category';
            }
            showModal({
                large: false,
                title: `Edit ${type}`,
                body: `<div id='new_product_classification' class='row'>
                            <div class='col-md-6 mb-3'>
                                <p class='font-weight-bold text-capitalize'>name: </p>
                                <input id='product_classification_name' type='text' class='form-control' value='${data.name}'>
                            </div>
                        </div>`,
                actionBtn: {
                    onclick: `editProductClassification(\"${requestUrlOfItem}\",\"${type}\")`,
                    label: 'Save'
                }
            });
        }).fail(function(errorThrown) {
            showMessage('Failed to load the details of this item', 'danger');
        });        
    }
}

function editProductClassification(requestUrl, type) {
    showPreloader();
    let payload = {'name': $('#product_classification_name').val()};
    sendAuthorizedRequest(requestUrl, PATCH, JSON.stringify(payload), 'application/json')
    .done(function(data, textStatus, jqXHR) {
        showMessage(`${type} edited successfully`, 'success');
        $('#content').empty();
    }).fail(function(errorThrown) {
        showMessage(`Failed to edit this ${type}`, 'danger');
    }).always(function() {
        removePreloader();
        hideModal();
    }); 
}

function uploadImageIfAny(productId) {
    if (tmpUploadedImage != null) {
        let formData = new FormData();
        formData.append('file', tmpUploadedImage);
        return $.ajax({
            url: `/products/${productId}/images`,
            type: POST,
            data: formData,
            processData: false,
            contentType: false,
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
        }); 
    }
}

function editProduct(savedProductId) {
    showPreloader();
    let editProductFormElements = $('#product_under_edit input[type=text], select');
    let payload = {};
    for (let index = 0; index < editProductFormElements.length; index++) {    
        payload[$(editProductFormElements[index]).attr('id')] = $(editProductFormElements[index]).is('select') ? 
                                                                    $(editProductFormElements[index]).children('option:selected').val() : 
                                                                    $(editProductFormElements[index]).val();
    }
    $.when(sendAuthorizedRequest(`/products/${savedProductId}`, PATCH, JSON.stringify(payload), 'application/json')
    ).then(function(data, textStatus, jqXHR) { 
        return uploadImageIfAny(savedProductId);                        
    }).done(function(data, textStatus, jqXHR) {
        showMessage('Product edited successfully', 'success');
        $('#content').empty();
        tmpUploadedImage = null;
    }).fail(function(errorThrown) {        
        showMessage(errorThrown.responseJSON.message || 'Failed to edit this product', 'danger');
    }).always(function() {
        removePreloader();
    });    
}

/* ********************************************************************************************************************************** */

function prepareDeleteItems(requestUrl) {
    resetPaginator();
    clearMessagesSection();
    getItems('Delete', requestUrl);
}

function preDeleteItem(requestUrlOfItem) {
    showModal({
        large: false,
        title: 'Delete Confirmation',
        body: 'Are you sure you want to delete this item ?',
        actionBtn: {
            onclick: `deleteItem(\"${requestUrlOfItem}\")`,
            label: 'Ok'
        }
    });
}

function deleteItem(requestUrl) {
    showPreloader();
    sendAuthorizedRequest(requestUrl, DELETE)
    .done(function (data, textStatus, jqXHR) {        
        $("#content").empty();
        showMessage('Item deleted successfully', 'success');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage(jqXHR.responseJSON.message || 'Failed to delete this item', 'danger');
    }).always(function() {
        removePreloader();
        hideModal();
    });
}

/* ********************************************************************************************************************************** */

function prepareAddFormOf(itemType) {
    clearMessagesSection();
    switch (itemType) {
        case 'Product':
            createProductAddForm();
            break;
        case 'Vendor':
            createProductClassificationAddForm('/vendors', itemType);
            break;
        case 'Category':    
            createProductClassificationAddForm('/categories', itemType);
            break;
    }
}

function createProductAddForm() {
    $.when(sendAuthorizedRequest('/vendors', GET),
            sendAuthorizedRequest('/categories', GET)
    ).then(function(vendorsResponse, categoriesResponse) {
        let vendors = vendorsResponse[0];
        let categories = categoriesResponse[0];
        let vendorsSelectOptionsHtml = createSelectMenuFrom(vendors, VENDOR_ID);
        let categoriesSelectOptionsHtml = createSelectMenuFrom(categories, CATEGORY_ID);
        let htmlContent = `<div id='new_product' class='row'>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>name: </p>
                                    <input id='name' type='text' class='form-control' required>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>price: </p>
                                    <input id='price' type='number' class='form-control' required>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>quantity: </p>
                                    <input id='quantity' type='number' class='form-control' required>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>description: </p>
                                    <input id='description' type='text' class='form-control'>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>category: </p>
                                    ${categoriesSelectOptionsHtml}                                    
                                </div>
                                <div class='col-md-6 mb-3">
                                    <p class='font-weight-bold text-capitalize'>vendor: </p>
                                    ${vendorsSelectOptionsHtml}                                                                                                                   
                                </div>  
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>image: </p>
                                    <input id='image' type='file' class='form-control-file'>
                                </div>                                  
                            </div>
                            <button onclick='addProduct()' class='form-control btn btn-primary'>Save</button>`;
        $('#content').html(htmlContent);
        attachImageChangeListener();
    }, function(errorThrown) {
        showMessage(errorThrown, 'danger');
    }).always(function() {
        removePreloader();        
    });
}

function createProductClassificationAddForm(requestUrl, type) {
    clearMain();
    showModal({
        large: false,
        title: `Add ${type}`,
        body: `<div id='new_product_classification' class='row'>
                    <div class='col-md-6 mb-3'>
                        <p class='font-weight-bold text-capitalize'>name: </p>
                        <input id='product_classification_name' type='text' class='form-control'>
                    </div>
                </div>`,
        actionBtn: {
            onclick: `addProductClassification(\"${requestUrl}\",\"${type}\")`,
            label: 'Save'
        }
    });
}

function addProductClassification(requestUrl, type) {
    showPreloader();
    let payload = {'name': $('#product_classification_name').val()};    
    sendAuthorizedRequest(requestUrl, POST, JSON.stringify(payload), 'application/json')
    .done(function(data, textStatus, jqXHR) {
        showMessage(`${type} added successfully`, 'success');
        $('#content').empty();
    }).fail(function(errorThrown) {
        showMessage(`Failed to add this ${type}`, 'danger');
    }).always(function() {
        removePreloader();
        hideModal();
    });
}

function addProduct() {
    let addProductFormElements = $('#new_product input[type=text], input[type=number], select');
    let payload = {};
    let errors = [];
    for (let index = 0; index < addProductFormElements.length; index++) {
        let element = $(addProductFormElements[index]);
        let value = element.is('select') ? element.children("option:selected").val() : element.val();
        if (element[0].hasAttribute('required') && value == '') {
            errors.push(element.attr('id').replace('_id', ''));
            continue;
        }
        payload[element.attr('id')] = value;
    }
    if (errors.length > 0) {        
        showMessage(`Those item(s) ${errors.join(', ')} should be filled`, 'danger');
        return;
    }
    showPreloader();
    $.when(sendAuthorizedRequest('/products', POST, JSON.stringify(payload), 'application/json')
    ).then(function(data, textStatus, jqXHR) {
        let locationHeader = jqXHR.getResponseHeader('Location');
        let savedProductId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);  
        return uploadImageIfAny(savedProductId);                          
    }).done(function(data, textStatus, jqXHR) {
        showMessage('Product added successfully', 'success');
        $('#content').empty();
        tmpUploadedImage = null;
    }).fail(function(errorThrown) {
        showMessage('Failed to add this product', 'danger');
    }).always(function() {
        removePreloader();        
    });
}

function attachImageChangeListener() {
    $('#image').bind('change', function(event) {
        tmpUploadedImage = event.target.files[0];
    });
}






