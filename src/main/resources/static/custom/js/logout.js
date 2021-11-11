$(document).ready(function () {
    $("#logoutLink").click(function (e) {
        e.preventDefault();
        window.loader.show();
        $.get("/logout", {}, function () {
            window.loader.hide();
            window.location = "/login";
        });
    });
});