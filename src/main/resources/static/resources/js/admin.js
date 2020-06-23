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
    $("#header").append(adminMenu);
}

/* ********************************************************************************************************************************** */

function prepareEditItemsOfUsers(requestUrl) {
    getItems([ACTIVATE, DEACTIVATE], requestUrl);    
}

function changeUserState(id, inversedState) {
    showPreloader();
    let payload = {"id": id, active: inversedState};
    sendAuthorizedRequest('/users', 'PATCH', JSON.stringify(payload), 'application/json')
    .done(function (data, textStatus, jqXHR) {        
        let elementId = `#user_${id}`;
        if ($(elementId).text() == ACTIVATE) {
            $(elementId).text(DEACTIVATE);
        } else {
            $(elementId).text(ACTIVATE);
        }
        $(elementId).attr('onclick', `changeUserState(id,${!inversedState})`);
        showMessage('User updated successfully', 'success');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to update this user', 'danger');
    }).always(function() {
        removePreloader();
    });
}

/* ********************************************************************************************************************************** */

function prepareEditItems(requestUrl) {
    getItems("Edit", requestUrl);
}

function preEditItem(requestUrlOfItem) {
    if (requestUrlOfItem.includes("products")) {
        $.when(sendAuthorizedRequest(requestUrlOfItem, 'GET'),
                sendAuthorizedRequest('/vendors', 'GET'),
                sendAuthorizedRequest('/categories', 'GET')
        ).then(function(productResponse, vendorsResponse, categoriesResponse) {
            let product = productResponse[0];
            let vendors = vendorsResponse[0];
            let categories = categoriesResponse[0];
            let vendorsSelectOptionsHtml = createSelectMenuFrom(vendors, 'vendorId', product.vendorId);
            let categoriesSelectOptionsHtml = createSelectMenuFrom(categories, 'categoryId', product.categoryId);
            let htmlContent = `<div id='product_under_edit' class='row'>`;
            Object.keys(product)
                    .forEach(function(key) {
                        if (notSkippedField(key)) {
                            switch (key) {
                                case 'vendorId':
                                    htmlContent += `<div class='col-md-6 mb-3'>
                                                            <p class='font-weight-bold text-capitalize'>vendor: </p>
                                                            ${vendorsSelectOptionsHtml}
                                                        </div>`;
                                    break;
                                case 'categoryId':
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
            if (product.imageUploaded) {
                imageSrc = `/products/images/${product.id}`;
            } else {
                imageSrc = `resources/images/empty.jpg`;
            }
            htmlContent += `    <div class='col-md-6 mb-3'>
                                        <p class='font-weight-bold text-capitalize'>image: </p>
                                        <input id='image' type='file' class='form-control-file'>
                                    </div>
                                    <img id='image_of_product' src='${imageSrc}' width='500' height='333'>
                                </div>
                                <button onclick='editProduct(${product.id})' class='form-control btn btn-primary'>Save</button>`;                                
            $("#content").html(htmlContent);
            attachImageChangeListener();
        }, function(errorThrown) {
            showMessage('Failed to load the details of this item', 'danger');
        }).always(function() {
            removePreloader();
            clearMessagesSection();
        });
    } else {
        // show modal to edit name of either vendor or category
    }
}

function editProduct(savedProductId) {
    let editProductFormElements = $('#product_under_edit input[type=text], select');
    let payload = {'id': savedProductId, 'active': true};
    for (let index = 0; index < editProductFormElements.length; index++) {
        $(editProductFormElements[index]).attr('id')        
        payload[$(editProductFormElements[index]).attr('id')] = $(editProductFormElements[index]).is('select') ? 
                                                                    $(editProductFormElements[index]).children("option:selected").val() : 
                                                                    $(editProductFormElements[index]).val();
    }
    $.when(sendAuthorizedRequest('/products', 'PATCH', JSON.stringify(payload), 'application/json')
    ).then(function(data, textStatus, jqXHR) {
            if (tmpUploadedImage != null) {
                let formData = new FormData();
                formData.append('file', tmpUploadedImage);
                return $.ajax({
                    url: `/products/${savedProductId}`,
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
                }); 
            }                        
    }).done(function(data, textStatus, jqXHR) {
        showMessage('Product edited successfully', 'success');
        $('#content').empty();
    }).fail(function(errorThrown) {
        showMessage('Failed to edit this product', 'danger');
    }).always(function() {
        removePreloader();
    });    
}

/* ********************************************************************************************************************************** */

function prepareDeleteItems(requestUrl) {
    getItems("Delete", requestUrl)
}

function preDeleteItem(requestUrlOfItem) {    
    $('#delete_btn').attr('onclick', `deleteItem(\"${requestUrlOfItem}\")`);
}

function deleteItem(requestUrl) {
    showPreloader();
    sendAuthorizedRequest(requestUrl, 'DELETE')
    .done(function (data, textStatus, jqXHR) {        
        $("#content").empty();
        showMessage('Item deleted successfully', 'success');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        showMessage('Failed to delete this item', 'danger');
    }).always(function() {
        removePreloader();
        $('#confirm_delete_dialogue').modal('hide');
    });
}

/* ********************************************************************************************************************************** */

function prepareAddFormOf(itemType) {
    switch (itemType) {
        case 'Product':
            createProductAddForm();
            break;
        case 'Vendor':
        case 'Category':    
            createProductClassificationAddForm();
            break;
    }
}

function createProductAddForm() {
    $.when(sendAuthorizedRequest('/vendors', 'GET'),
            sendAuthorizedRequest('/categories', 'GET')
    ).then(function(vendorsResponse, categoriesResponse) {
        let vendors = vendorsResponse[0];
        let categories = categoriesResponse[0];
        let vendorsSelectOptionsHtml = createSelectMenuFrom(vendors, 'vendorId');
        let categoriesSelectOptionsHtml = createSelectMenuFrom(categories, 'categoryId');
        let htmlContent = `<div id='new_product' class='row'>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>name: </p>
                                    <input id='name' type='text' class='form-control'>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>price: </p>
                                    <input id='price' type='text' class='form-control'>
                                </div>
                                <div class='col-md-6 mb-3'>
                                    <p class='font-weight-bold text-capitalize'>quantity: </p>
                                    <input id='quantity' type='text' class='form-control'>
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
        $("#content").html(htmlContent);
        attachImageChangeListener();
    }, function(errorThrown) {
        console.error(errorThrown);
    }).always(function() {
        removePreloader();
        clearMessagesSection();
    });
}

function createProductClassificationAddForm() {
    // create it from modal: just  'name' field !
}

function addProduct() {
    showPreloader();
    let addProductFormElements = $('#new_product input[type=text], select');
    let payload = {};
    for (let index = 0; index < addProductFormElements.length; index++) {
        $(addProductFormElements[index]).attr('id')        
        payload[$(addProductFormElements[index]).attr('id')] = $(addProductFormElements[index]).is('select') ? 
                                                                    $(addProductFormElements[index]).children("option:selected").val() : 
                                                                    $(addProductFormElements[index]).val();
    }
    $.when(sendAuthorizedRequest('/products', 'POST', JSON.stringify(payload), 'application/json')
    ).then(function(data, textStatus, jqXHR) {
            let locationHeader = jqXHR.getResponseHeader('Location');
            let savedProductId = locationHeader.substring(locationHeader.lastIndexOf('/')+1);            
            let formData = new FormData();
            formData.append('file', tmpUploadedImage);
        return $.ajax({
            url: `/products/${savedProductId}`,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
        });             
    }).done(function(data, textStatus, jqXHR) {
        showMessage('Product added successfully', 'success');
        $('#content').empty();
    }).fail(function(errorThrown) {
        showMessage('Failed to add this product', 'danger');
    }).always(function() {
        removePreloader();
        clearMessagesSection();
    });
}

function attachImageChangeListener() {
    $("#image").bind('change', function(event) {
        tmpUploadedImage = event.target.files[0];
    });
}





