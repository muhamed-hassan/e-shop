function showLoginForm() {
    let loginForm = `<form id='login_form' class='form-signin text-center'>
                        <h1 class='h3 mb-3 font-weight-normal'>Please sign in</h1>
                        <label for='username' class='sr-only'>Username</label>
                        <input id='username' type='text' class='form-control' placeholder='Username' required autofocus>
                        <label for='password' class='sr-only'>Password</label>
                        <input id='password' type='password' class='form-control' placeholder='Password' required>
                        <button class='btn btn-lg btn-primary btn-block' type='submit'>Sign in</button>
                    </form>`;
    $("#main").html(loginForm);

    $("#login_form").bind("submit", function (event) {
        showPreloader();
        event.preventDefault();
        let username = $("#username").val();
        let password = $("#password").val();
        let formParams = "username=" + username + "&password=" + password;
        $.ajax({
            url: "/authenticate",
            type: "POST",
            data: formParams,})
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
            showLogoutLink();})
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);})
        .always(function(){
            removePreloader();
        });
    });
}

function hideLoginForm() {
    $("#login_form").remove();
}

function getRole(authToken) {
    return JSON.parse(window.atob(authToken.split(".")[1])).rol[0];
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