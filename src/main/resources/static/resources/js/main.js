const CURRENT_YEAR = new Date().getFullYear();
let hasToken = localStorage.getItem("authToken") != null;
(function () {
    $("#footer").html(`Copyright all rights reserved for www.cairo-shop.com ${CURRENT_YEAR}`);
    if (hasToken) {
        if (isAuthTokenExpired()) {
            logout();
        }
    } else {
        logout();
    }
})();

function sendRequest(requestUrl) {
    return $.ajax({url: requestUrl,
                    type: "GET",
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
                });
}

function sendRequestWithBody(requestUrl, httpMethod, requestBody) {
    return $.ajax({url: requestUrl,
                    type: httpMethod,
                    headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')},
                    data: requestBody
                });
}

//itemAction -> Edit, Delete
function getItems(itemAction, requestUrl) {
    showPreloader();
    $("#items_container").remove();
    sendRequest(requestUrl)
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
    return $.when(sendRequest('/vendors/' + product.vendorId),
                    sendRequest('/categories/' + product.categoryId),
                    $.Deferred().resolve(product));
}

function getDetails(requestUrl) {
    showPreloader();
    if (requestUrl.includes("products")) {
        sendRequest(requestUrl)
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
        sendRequest(requestUrl)
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

function generateItemDetailHtml(key, value) {
    return `<div class='col-md-6 mb-3'>
                <p class='font-weight-bold text-capitalize'>${key}: </p>
                <p class='font-weight-normal text-capitalize'>${value == null ? 'N/A' : value}</p>
            </div>`;
}

function clearHeader() {
    $("#logout_link").remove();
    $("#admin_menu").remove();
}

function clearMain() {
    $("#main").empty();
}

function showLogoutLink() {
    $("#header").append(`<a id='logout_link' class='btn btn-outline-primary' href='#' onclick='logout()'>Logout</a>`);
}

function prepareEditItems(requestUrl) {
    getItems("Edit", requestUrl);
}

function changeUserState(id, inversedState) {
    showPreloader();
    let payload = {"id": id, active: inversedState};
    $.ajax({
        url: '/users',
        type: "PATCH",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
            'Content-Type' : 'application/json'},
        data: JSON.stringify(payload)})
    .done(function (data, textStatus, jqXHR) {
          if (jqXHR.status == 204) {
              // show success msg
              let elementId = `#user_${id}`;
              if ($(elementId).text() == 'Activate') {
                  $(elementId).text('Deactivate');
              } else {
                  $(elementId).text('Activate');
              }
              $(elementId).attr("onclick", `changeUserState(id,${!inversedState})`);
          } else {
              throw new Error(textStatus);
          }})
    .fail(function (jqXHR, textStatus, errorThrown) {
          console.error(errorThrown);
    }).always(function(){
        removePreloader();
    });
}

function prepareEditItemsOfUsers(requestUrl) {
    getItems(["Activate", "Deactivate"], requestUrl)
}

function preEditItem(requestUrlOfItem) {
    if (requestUrlOfItem.includes("products")) {
        // load full product
        // load all vendors
        // load all categories
        $.when(sendRequest(requestUrlOfItem),
                sendRequest('/vendors'),
                sendRequest('/categories')
        ).then(function(productResponse, vendorsResponse, categoriesResponse) {
            let product = productResponse[0];
            let vendors = vendorsResponse[0];
            let categories = categoriesResponse[0];

            let vendorsSelectOptionsHtml = `<select id='vendors'>`;
            for (var index = 0; index < vendors.length; index++) {
                vendorsSelectOptionsHtml += `<option value='${vendors[index].id}' ${vendors[index].id == product.vendorId ? 'selected' : ''}>${vendors[index].name}</option>`;
            }
            vendorsSelectOptionsHtml += `</select>`;

            let categoriesSelectOptionsHtml = `<select id='categories'>`;
            for (var index = 0; index < categories.length; index++) {
                categoriesSelectOptionsHtml += `<option value='${categories[index].id}' ${categories[index].id == product.categoryId ? 'selected' : ''}>${categories[index].name}</option>`;
            }
            categoriesSelectOptionsHtml += `</select>`;

                        let itemDetailsHtml = `<div id='product_under_edit' class='row'>`;
                        Object.keys(product).forEach(function(key) {
                            if (key != 'id' && key != 'active' && key != 'imageUploaded') {
                                switch (key) {
                                    case 'vendorId':
                                        itemDetailsHtml += `<div class='col-md-6 mb-3'>
                                                                           <p class='font-weight-bold text-capitalize'>vendor: </p>
                                                                           ${vendorsSelectOptionsHtml}
                                                                       </div>`;
                                        break;
                                    case 'categoryId':
                                        itemDetailsHtml += `<div class='col-md-6 mb-3'>
                                                                                                                   <p class='font-weight-bold text-capitalize'>category: </p>
                                                                                                                   ${categoriesSelectOptionsHtml}
                                                                                                               </div>`;
                                        break;
                                    default:
                                        itemDetailsHtml += `<div class='col-md-6 mb-3'>
                                                                  <p class='font-weight-bold text-capitalize'>${key}: </p>
                                                                  <input id='${key}' type='text' value='${product[key]}'/>
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
                        itemDetailsHtml += `    <img id='image_of_product' src='${imageSrc}' width='500' height='333'>
                                            </div>
                                            <button onclick='editItem()'>Save</button>`;
                        $("#main").html(itemDetailsHtml);
                }, function(errorThrown) {
                    console.error(errorThrown);
                }).always(function(){
                    removePreloader();
                });
    }
}

function editItem() {
    //select input elements in product_under_edit
}

function prepareDeleteItems(requestUrl) {
    getItems("Delete", requestUrl)
}

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

function prepareAddFormOf(itemType) {
    switch (itemType) {
        case 'Product':

        break;
        case 'Vendor':
                break;
                case 'Category':
                        break;
    }
}

function searchProducts(event) {
  event.preventDefault(); //prevent default action

  $("#items_container").remove();
  getItems("", "/products"); //view mode

  //reset search
}

function showProductsSearchBar() {
    let productsSearchBar = `<form id='products_search_bar' onsubmit='searchProducts(event)'>
                                <div class='row'>
                                    <div class='col-md-12 mb-3'>
                                        <input class='form-control w-100' type='text' placeholder='Search' aria-label='Search'>
                                    </div>
                                </div>
                                <div class='row'>
                                    <div class='offset-4 col-md-2 mb-3'>
                                        <div>Sort direction</div>
                                    <div class='custom-control custom-radio'>
                                        <input id='asc' name='asc' type='radio' class='custom-control-input' checked required>
                                        <label class='custom-control-label' for='asc'>ASC</label>
                                    </div>
                                    <div class='custom-control custom-radio'>
                                        <input id='desc' name='desc' type='radio' class='custom-control-input' required>
                                        <label class='custom-control-label' for='desc'>DESC</label>
                                    </div>
                                </div>
                                <div class='col-md-4 mb-3'>
                                    <label for='state'>Sort by</label>
                                    <select class='custom-select d-block w-50' id='sort_by' required>
                                        <option value=''>Choose...</option>
                                    </select>
                                </div>
                            </form>`;
    $("#main").html(productsSearchBar);
}




