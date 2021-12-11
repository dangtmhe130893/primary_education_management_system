$(document).ready(function () {
    $("#logoutLink").click(function (e) {
        e.preventDefault();
        $.get("/logout", {}, function () {
            window.location = "/home";
        });
    });
});