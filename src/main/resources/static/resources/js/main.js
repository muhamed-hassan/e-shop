const CURRENT_YEAR = new Date().getFullYear();
(function () {
    $("#footer").html(`Copyright all rights reserved for www.cairo-shop.com ${CURRENT_YEAR}`);
    let authToken = localStorage.getItem("authToken");
    if (authToken != null) {
        if (authTokenExpired(authToken)) {
            logout();
        } else {
            showInitialScreenBasedOnRole(getRole(authToken)); 
        }
    } else {
        logout();
    }
})();

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
    $("#header").append(`<a id='logout_link' class='btn btn-outline-primary' href='#' onclick='logout()'>Logout</a>`);
}