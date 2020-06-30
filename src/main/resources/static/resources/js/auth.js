function showLoginForm() {
    let loginForm = `<form id='login_form' class='form-signin text-center'>
                        <h1 class='h3 mb-3 font-weight-normal'>Please sign in</h1>
                        <label for='username' class='sr-only'>Username</label>
                        <input id='username' type='text' class='form-control' placeholder='Username' required autofocus>
                        <label for='password' class='sr-only'>Password</label>
                        <input id='password' type='password' class='form-control' placeholder='Password' required>
                        <button class='btn btn-lg btn-primary btn-block' type='submit'>Sign in</button>
                    </form>`;
    $("#content").html(loginForm);

    $("#login_form").bind("submit", function (event) {
        showPreloader();
        event.preventDefault();
        let username = $("#username").val();
        let password = $("#password").val();
        let formParams = "username=" + username + "&password=" + password;
        $.ajax({
            url: "/authenticate",
            type: "POST",
            data: formParams
        }).done(function (data, textStatus, jqXHR) {
            hideLoginForm();
            let authToken = jqXHR.getResponseHeader("Authorization").substring(7);
            localStorage.setItem("authToken", authToken);
            showInitialScreenBasedOnRole(getRole(authToken));
            clearMessagesSection();
        }).fail(function (jqXHR, textStatus, errorThrown) {
            showMessage(jqXHR.responseJSON.message || `Can't login`, 'danger');
        }).always(function() {
            removePreloader();            
        });
    });
}

function showInitialScreenBasedOnRole(role) {
    switch (role) {
        case "ROLE_ADMIN":
            showAdminMenu();
            break;
        case "ROLE_CUSTOMER":
            showProductsSearchBar();
            break;
    }
    showLogoutLink();
}

function hideLoginForm() {
    $("#login_form").remove();
}

function getRole(authToken) {
    return jwtAuthTokenToJson(authToken).rol[0];
}

function authTokenExpired(authToken) {
    return (new Date().getTime() - new Date(jwtAuthTokenToJson(authToken).exp * 1000).getTime()) >= 0;
}

function jwtAuthTokenToJson(authToken) {
    return JSON.parse(window.atob(authToken.split(".")[1]));
}

