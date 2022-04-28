//Runs when document is finsihed loading
$(function () {
    $.post("/krypterPassord", function () {
        }
    )
});

//Checks if the user is idle
$(document).ready(function () {
    $.active = false;
    $('body').bind('click keypress', function () {
        $.active = true;
    });
    checkActivity(600000, 60000, 0); // timeout = 10 minutes, interval = 1 min.
});

function checkActivity(timeout, interval, elapsed) {
    if ($.active) {
        elapsed = 0;
        $.active = false;
        $.get('heartbeat');
    }
    if (elapsed < timeout) {
        elapsed += interval;
        setTimeout(function () {
            checkActivity(timeout, interval, elapsed);
        }, interval);
    } else {
        window.location = 'login.html'; //Redirect to login page.
    }
}