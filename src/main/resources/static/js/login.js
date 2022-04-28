//Validate user input
function validateAndLogin() {
    const usernameOK = validateUsername($("#username").val());
    const passwordOK = validatePassword($("#password").val());
    if (usernameOK && passwordOK) {
        login();
    }
}

//Logout function
function logout() {
    const url = "/logout";
    $.get(url, function () {
        window.location.href = 'login.html';
    })
}

//Login function
function login() {
    const user = {
        username: $("#username").val(),
        password: $("#password").val()
    }
    const url = "/login";
    $.get(url, user, function (loggedIn) {
        if (loggedIn) {
            window.location.href = 'index.html';
        } else {
            $("#error").html("Wrong username of password");
        }
    })
        .fail(function () {
                $("#error").html("Server error - try again later");
            }
        );
}