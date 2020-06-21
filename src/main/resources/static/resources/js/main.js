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

//itemAction -> Edit, Delete
function getItems(itemAction, requestUrl) {
    $("#items_container").remove();

    sendRequest(requestUrl)
    .done(function (data, textStatus, jqXHR) {
        if (jqXHR.status == 200) {
            let itemsJson = data;
            let itemsHtml = "<div class='row'>";
            for (var index = 0; index < itemsJson.length; index++) {
                let item = itemsJson[index];
                let itemActionButton = "";
                if (Array.isArray(itemAction)) {
                    itemActionButton += "<button type='button' class='btn btn-sm btn-warning' onclick='editItem(" + item + ")'>" + (item.active ? itemAction[0] : itemAction[1]) + "</button>";
                } else {
                    switch (itemAction) {
                        case "Edit":
                            itemActionButton += "<button type='button' class='btn btn-sm btn-warning' onclick='editItem(" + item + ")'>" + itemAction + "</button>";
                            break;
                        case "Delete":
                            itemActionButton += "<button type='button' class='btn btn-sm btn-danger' onclick='deleteItem(" + item + ")'>" + itemAction + "</button>";
                            break;
                    }
                }

                itemsHtml += `<div class='col-md-4'>
                                <div class='card mb-4 shadow-sm'>
                                    <div class='card-body'>
                                        <p class='card-text text-center'>${item.name}</p>
                                            <div class='d-flex justify-content-between align-items-center'>
                                                <div class='btn-group'>
                                                    <button type='button' class='btn btn-sm btn-secondary' onclick='getDetails("${requestUrl.substring(0, requestUrl.lastIndexOf('?'))}/${item.id}")'>View</button>
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
    });
}

function getProductMoreDetails(product) {



return $.when(
                                            $.ajax({
                                                        url: '/vendors/' + product.vendorId,
                                                        type: "GET",
                                                        headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
                                                      }),
                                                      $.ajax({
                                                                       url: '/categories/' + product.categoryId,
                                                                       type: "GET",
                                                                       headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
                                                                     }),
                                                                     $.ajax({
                                                                                      url: '/products/' + product.id,
                                                                                      type: "GET",
                                                                                      headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken'),
                                                                                      'Accept': 'image/*'}
                                                                                    }),
                                            $.Deferred().resolve(product));
}

function getDetails(requestUrl) {

if (requestUrl.includes("products")) {
        // handle replacing categoryId with categoryName
        // handle replacing vendorId with vendorName
        // handle image fetching
        $.ajax({
            url: requestUrl,
            type: "GET",
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
          }).then(function(data, textStatus, jqXHR){
          if (jqXHR.status == 200) {
          return getProductMoreDetails(data);
          } else if (jqXHR.status == 404) {
          throw new Error(`Product with id: ${requestUrl.substring(requestUrl.lastIndexOf('/') + 1)} doesn't exist`);
          }
          //handle success

          },function(jqXHR, textStatus, errorThrown){
          throw new Error(errorThrown);
          })
          .then(function(vendorResponse, categoryResponse, imageResponse, product){
          let itemDetailsHtml = "<div class='row'>";


                        Object.keys(product).forEach(function(key) {
                        if (key != 'id' && key != 'active') {
                        if (key == 'vendorId') {
                        itemDetailsHtml += "<div class='col-md-6 mb-3'>" +
                                                "<p class='font-weight-bold'>vendor: </p>" +
                                                "<p class='font-weight-normal'>" + vendorResponse[0].name + "</p>" +

                                                                             "</div>";
                        } else if (key == 'categoryId') {
                        itemDetailsHtml += "<div class='col-md-6 mb-3'>" +
                                                "<p class='font-weight-bold'>category: </p>" +
                                                "<p class='font-weight-normal'>" + categoryResponse[0].name + "</p>" +

                                                                             "</div>";
                        } else {
                        itemDetailsHtml += "<div class='col-md-6 mb-3'>" +
                                                "<p class='font-weight-bold'>" + key + ": </p>" +
                                                "<p class='font-weight-normal'>" + product[key] + "</p>" +

                                                                             "</div>";
                        }


                                                     }
                        });

                        if (imageResponse[0] != '') {


                        var rawResponse = imageResponse[0]; // truncated for example

                       //$('#image_of_product').attr('src', `data:image/png;base64,${btoa(rawResponse)}`);


                        itemDetailsHtml += "<img id='image_of_product' src='" + (`data:image/png;base64,${btoa(rawResponse)}`) + "' width='500' height='333'>";
                        // append it to your page
                       // document.body.appendChild(outputImg);
                        }

                        itemDetailsHtml += "</div>";
                                              $("#main").html(itemDetailsHtml);

          },function(errorThrown){
          //handle failure
          console.error(errorThrown);
          });


      } else {

      $.ajax({
          url: requestUrl,
          type: "GET",
          headers: {'Authorization': 'Bearer ' + localStorage.getItem('authToken')}
        }).done(function (data, textStatus, jqXHR) {
            if (jqXHR.status == 200) {


            let itemDetailsHtml = "<div class='row'>";


            Object.keys(data).forEach(function(key) {
            if (key != 'id' && key != 'active') {

            itemDetailsHtml += "<div class='col-md-6 mb-3'>" +
            "<p class='font-weight-bold'>" + key + ": </p>" +
            "<p class='font-weight-normal'>" + data[key] + "</p>" +

                                         "</div>";
                                         }
            });

      //<img src='' width='500' height='333'> custom ajax request
            itemDetailsHtml += "</div>";
                    $("#main").html(itemDetailsHtml);

            }else if (jqXHR.status == 404) {
                     // data not found
                   }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    //show error message
                  });
      }





}

function isAuthTokenExpired() {
  return false;
}

function logout() {
  localStorage.removeItem("authToken");

  clearHeader();
  clearMain();
  showLoginForm();
}

function clearHeader() {
  $("#logout_link").remove();
  $("#admin_menu").remove();
}

function clearMain() {
  $("#main").empty();
}

function showLogoutLink() {
  $("#header").append(
    "<a id='logout_link' class='btn btn-outline-primary' href='#' onclick='logout()'>Logout</a>"
  );
}

function prepareEditItems(requestUrl) {

    getItems("Edit", requestUrl);
}


function prepareEditItemsOfUsers(requestUrl) {

  getItems(["Activate", "Deactivate"], requestUrl)
}

function prepareDeleteItems(requestUrl) {

  getItems("Delete", requestUrl)
}

function showAdminMenu() {
  let adminMenu =
    "<div id='admin_menu' class='container'>" +
    "<div class='row'>" +
    "<div class='col-3'>" +
    "<div class='dropdown'>" +
    "<button id='manage_users' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
    "Manage users" +
    "</button>" +
    "<div class='dropdown-menu' aria-labelledby='manage_users'>" +
    "<button class='dropdown-item' type='button' onclick='prepareEditItemsOfUsers(\"/users?start-position=0&sort-by=id&sort-direction=ASC\")'>Activate/Deactivate user(s)</button>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "<div class='col-3'>" +
    "<div class='dropdown'>" +
    "<button id='manage_products' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
    "Manage products" +
    "</button>" +
    "<div class='dropdown-menu' aria-labelledby='manage_products'>" +
    "<button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Product\")'>Add product</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareEditItems(\"/products?start-position=0&sort-by=id&sort-direction=ASC\")'>Edit product</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/products?start-position=0&sort-by=id&sort-direction=ASC\")'>Delete product</button>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "<div class='col-3'>" +
    "<div class='dropdown'>" +
    "<button id='manage_vendors' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
    "Manage vendors" +
    "</button>" +
    "<div class='dropdown-menu' aria-labelledby='manage_vendors'>" +
    "<button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Vendor\")'>Add vendor</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareEditItems(\"/vendors?start-position=0&sort-by=id&sort-direction=ASC\")'>Edit vendor</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/vendors?start-position=0&sort-by=id&sort-direction=ASC\")'>Delete vendor</button>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "<div class='col-3'>" +
    "<div class='dropdown'>" +
    "<button id='manage_categories' class='btn btn-secondary dropdown-toggle' type='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
    "Manage categories" +
    "</button>" +
    "<div class='dropdown-menu' aria-labelledby='manage_categories'>" +
    "<button class='dropdown-item' type='button' onclick='prepareAddFormOf(\"Category\")'>Add category</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareEditItems(\"/categories?start-position=0&sort-by=id&sort-direction=ASC\")'>Edit category</button>" +
    "<button class='dropdown-item' type='button' onclick='prepareDeleteItems(\"/categories?start-position=0&sort-by=id&sort-direction=ASC\")'>Delete category</button>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "</div>" +
    "</div>";

  $("#header").append(adminMenu);
}

function prepareAddFormOf(itemType) { }

function searchProducts(event) {
  event.preventDefault(); //prevent default action

  $("#items_container").remove();
  getItems("", "/products"); //view mode

  //reset search
}
function showProductsSearchBar() {
  let productsSearchBar =
    "<form id='products_search_bar' onsubmit='searchProducts(event)'> <div class='row'>" +
    "<div class='col-md-12 mb-3'>" +
    "<input class='form-control w-100' type='text' placeholder='Search' aria-label='Search'>" +
    "</div>" +
    "</div>" +
    "<div class='row'>" +
    "<div class='offset-4 col-md-2 mb-3'>" +
    "<div>" +
    "Sort direction" +
    "</div>" +
    "<div class='custom-control custom-radio'>" +
    "<input id='credit' name='paymentMethod' type='radio' class='custom-control-input' checked required>" +
    "<label class='custom-control-label' for='credit'>ASC</label>" +
    "</div>" +
    "<div class='custom-control custom-radio'>" +
    "<input id='debit' name='paymentMethod' type='radio' class='custom-control-input' required>" +
    "<label class='custom-control-label' for='debit'>DESC</label>" +
    "</div>" +
    "</div>" +
    "<div class='col-md-4 mb-3'>" +
    "<label for='state'>Sort by</label>" +
    "<select class='custom-select d-block w-50' id='state' required>" +
    "<option value=''>Choose...</option>" +
    "<option>California</option>" +
    "</select>" +
    "</div>" +
    "</div> </form>";

  $("#main").html(productsSearchBar);
}

function showLoginForm() {
  let loginForm =
    "<form id='login-form' class='form-signin text-center'>" +
    "<h1 class='h3 mb-3 font-weight-normal'>Please sign in</h1>" +
    "<label for='username' class='sr-only'>Username</label>" +
    "<input id='username' type='text' class='form-control' placeholder='Username' required autofocus>" +
    "<label for='password' class='sr-only'>Password</label>" +
    "<input id='password' type='password' class='form-control' placeholder='Password' required>" +
    "<button class='btn btn-lg btn-primary btn-block' type='submit'>Sign in</button>" +
    "</form>";

  $("#main").html(loginForm);

  $("#login-form").bind("submit", function (event) {
    event.preventDefault(); //prevent default action

    let username = $("#username").val();
    let password = $("#password").val();

    let formParams = "username=" + username + "&password=" + password;
    $.ajax({
      url: "/authenticate",
      type: "POST",
      data: formParams,
    })
      .done(function (data, textStatus, jqXHR) {
        hideLoginForm();
        let authToken = jqXHR.getResponseHeader("Authorization").substring(7);
        localStorage.setItem("authToken", authToken);

        let role = getRole(authToken);

        switch (role) {
          case "Admin":
            showAdminMenu();
            break;
          case "Customer":
            showProductsSearchBar();
            break;
        }
        showLogoutLink();
      })
      .fail(function (jqXHR, textStatus, errorThrown) {
        console.log(jqXHR);
        //show error message
      });
  });
}

function hideLoginForm() {
  $("#login-form").remove();
}

function getRole(authToken) {
  var decodedValue = JSON.parse(window.atob(authToken.split(".")[1]));
  return decodedValue.rol[0];
}
