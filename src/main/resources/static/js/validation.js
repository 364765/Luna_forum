//Username input validation
function validateUsername(username) {
    const regexp = /[a-zA-ZæøåÆØÅ\.\ \-]{2,20}/;
    const ok = regexp.test(username);
    if (!ok) {
        $("#wrongUsername").html("Username must consist of 2-20 letters");
        return false;
    } else {
        $("#wrongUsername").html("");
        return true;
    }
}

//Password input validation
function validatePassword(password) {
    const regexp = /(?=.*[a-zA-ZæøåÆØÅ])(?=.*\d)[a-zA-ZæøåÆØÅ\d]{8,}/;
    const ok = regexp.test(password);
    if (!ok) {
        $("#wrongPassword").html("Password must consist of atleast 8 characters, contain 1 letter and 1 number");
        return false;
    } else {
        $("#wrongPassword").html("");
        return true;
    }
}
