// initialization
const CURRENT_YEAR = new Date().getFullYear();
let hasToken = localStorage.getItem('authToken') != null;
(function () {
    $('#footer').html('Copyright all rights reserved for www.cairo-shop.com ' + CURRENT_YEAR);

    if (hasToken) {
    if (isAuthTokenExpired()) {
    logout();
    }
    } else {
        logout();
    }
})();

function isAuthTokenExpired() {
return false;
}

function logout() {
localStorage.removeItem('authToken');

        clearHeader();
        clearMain();
        showLoginForm();

}

function clearHeader() {
    $('#logout-link').remove();
    $('#admin-menu').remove();
}

function clearMain() {
$("#main").empty();
}

function showLogoutLink() {
$('#header').append('<a id="logout-link" class="btn btn-outline-primary" href="#" onclick="logout()">Logout</a>');
}


function showAdminMenu() {

let adminMenu = '<div id="admin-menu" class="container">' +
'<div class="row">' +
  '<div class="col-3">' +
  '<div class="dropdown">' +
    '<button id="manage-users" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
      'Manage users' +
    '</button>' +
    '<div class="dropdown-menu" aria-labelledby="manage-users">' +
      '<button class="dropdown-item" type="button">Activate/Deactivate user(s)</button>' +
    '</div>' +
  '</div>' +
  '</div>' +
  '<div class="col-3">' +
  '<div class="dropdown">' +
    '<button id="manage-products" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
      'Manage products' +
    '</button>' +
    '<div class="dropdown-menu" aria-labelledby="manage-products">' +
      '<button class="dropdown-item" type="button">Add product</button>' +
      '<button class="dropdown-item" type="button">Edit product</button>' +
      '<button class="dropdown-item" type="button">Delete product</button>' +
    '</div>' +
  '</div>' +
  '</div>' +
    '<div class="col-3">' +
  '<div class="dropdown">' +
    '<button id="manage-vendors" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
      'Manage vendors' +
    '</button>' +
    '<div class="dropdown-menu" aria-labelledby="manage-vendors">' +
      '<button class="dropdown-item" type="button">Add vendor</button>' +
      '<button class="dropdown-item" type="button">Edit vendor</button>' +
      '<button class="dropdown-item" type="button">Delete vendor</button>' +
    '</div>' +
  '</div>' +
    '</div>' +
      '<div class="col-3">' +
  '<div class="dropdown">' +
    '<button id="manage-categories" class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
      'Manage categories' +
    '</button>' +
    '<div class="dropdown-menu" aria-labelledby="dropdownMenu2">' +
      '<button class="dropdown-item" type="button">Add category</button>' +
      '<button class="dropdown-item" type="button">Edit category</button>' +
      '<button class="dropdown-item" type="button">Delete category</button>' +
    '</div>' +
  '</div>' +
      '</div>' +
'</div>' +
      '</div>';

$('#header').append(adminMenu);
}


function showProductsSearchBar() {

let productsSearchBar = '<div class="row">' +
                                  '<div class="col-md-12 mb-3">' +
                                '<input class="form-control w-100" type="text" placeholder="Search" aria-label="Search">' +
                                  '</div>' +
                                '</div>' +
                                '<div class="row">' +
                                  '<div class="offset-4 col-md-2 mb-3">' +
                                    '<div>' +
                                      'Sort direction' +
                                    '</div>' +

                                    '<div class="custom-control custom-radio">' +
                                      '<input id="credit" name="paymentMethod" type="radio" class="custom-control-input" checked required>' +
                                      '<label class="custom-control-label" for="credit">ASC</label>' +
                                    '</div>' +
                                    '<div class="custom-control custom-radio">' +
                                      '<input id="debit" name="paymentMethod" type="radio" class="custom-control-input" required>' +
                                      '<label class="custom-control-label" for="debit">DESC</label>' +
                                    '</div>' +
                                  '</div>' +
                                  '<div class="col-md-4 mb-3">' +
                                    '<label for="state">Sort by</label>' +
                                    '<select class="custom-select d-block w-50" id="state" required>' +
                                      '<option value="">Choose...</option>' +
                                      '<option>California</option>' +
                                    '</select>' +
                                    '<div class="invalid-feedback">' +
                                      'Please provide a valid state.' +
                                    '</div>' +
                                  '</div>' +
                                '</div>';

                                $('#main').html(productsSearchBar);

}

function showLoginForm() {


let loginForm = '<form id="login-form" class="form-signin text-center">' +
          '<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>' +
          '<label for="username" class="sr-only">Username</label>' +
          '<input id="username" type="text" class="form-control" placeholder="Username" required autofocus>' +
          '<label for="password" class="sr-only">Password</label>' +
          '<input id="password" type="password" class="form-control" placeholder="Password" required>' +
          '<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>' +
        '</form>';

        $('#main').html(loginForm);

        $( "#login-form" ).bind( "submit", function(event) {
          event.preventDefault(); //prevent default action

          	let username = $('#username').val();
                  let password = $('#password').val();

                  let formParams = 'username=' + username + '&password=' + password;
          	$.ajax({
          		url : '/authenticate',
          		type: 'POST',
          		data : formParams,
          	}).done(function(data, textStatus, jqXHR ) {
                    hideLoginForm();
                    let authToken = jqXHR.getResponseHeader('Authorization').substring(7);
                    localStorage.setItem('authToken', authToken);

                    let role = getRole(authToken);

                            switch (role) {
                                case 'Admin':
                                showAdminMenu();
                                break;
                                case 'Customer':
                                            showProductsSearchBar();
                                            break;
                            }
                            showLogoutLink();
                  })
                  .fail(function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR );
                    //show error message
                  });
        });
}

function hideLoginForm() {
$( "#login-form" ).remove();
}




function getRole(authToken) {
    var decodedValue = JSON.parse(window.atob(authToken.split('.')[1]));
    return decodedValue.rol[0];
}

