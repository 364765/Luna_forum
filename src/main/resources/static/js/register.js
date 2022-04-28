//Runs saveUser function is validation is sucessful
function validateAndSaveUser() {
    const usernameOK = validateUsername($("#username").val());
    const passwordOK = validatePassword($("#password").val());
    if (usernameOK && passwordOK) {
        saveUser();
    }
}

//Saves user account information
function saveUser() {
    const user = {
        username: $("#username").val(),
        password: $("#password").val()
    }
    const url = "/saveUser";
    $.post(url, user, function () {
        window.location.href = '../html/login.html';
    })
        .fail(function (status) {
            if (status.status == 422) {
                $("#error").html("Error in DB - try again later");
            } else {
                $("#error").html("Validating error - try again later");
            }
        });
};
