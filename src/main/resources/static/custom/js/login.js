$(document).ready(function () {
    $("#login_btn").click(function (e) {
        if ($("#email").val() == null || $("#email").val() === ''
            || $("#password").val() == null || $("#password").val() === '') {
            window.alert.show("error", "Thông tin đăng nhập không chính xác", 2000);
        } else {
            e.preventDefault();
            var param = {
                email: $("#email").val(),
                password: $("#password").val()
            };
            $.ajax({
                type: "POST",
                url: "/login",
                data: param,
                beforeSend: function () {
                    window.loader.show();
                },
                success: function (response) {
                    window.loader.hide();
                    var obj = JSON.parse(response);
                    window.loader.hide();
                    if (obj.redirectUrl === "" || obj.redirectUrl == null) {
                        window.location.href = "/redirectHandler";
                    } else {
                        window.location.href = obj.redirectUrl;
                    }
                },
                error: function () {
                    window.loader.hide();
                    window.alert.show("error", "Thông tin đăng nhập không chính xác", "2000");
                }
            });
        }

    });

    $('body').keypress(function (e) {
        if (e.which === 13) {
            $('#login_btn').click();//Trigger login button click event
        }
    });

})
