$(document).ready(function () {


    jQuery.validator.addMethod("hasCharacter", function (password) {
        let length = password.length;
        ch = '';
        while (length--) {
            ch = password.charAt(length);
            if (ch <= '0' || ch >= '9') {
                return true; // we have found a character here
            }
        }
        return false; // the loop is done, yet we didn't find any character
    }, "Password must has at least 1 character");

    $("#set_password").validate({
        lang: 'ja',
        errorElement: 'span',
        errorClass: 'error-message',
        focusInvalid: false,
        ignore: "",
        rules: {
            password: {
                required: true,
                minlength: 8,
                hasCharacter: true
            },
            confirm_password: {
                required: true,
                equalTo: "#password"
            },
            code: {
                required: true,
            }
        },
        errorPlacement: function (error, element) {
            $(element).before(error);
        },
        highlight: function (element) {
            $(element).addClass("has-error");
        },
        unhighlight: function (element) {
            $(element).removeClass("has-error");
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            let formData = new FormData();
            formData.append("password", $("#password").val());
            formData.append("token", $("#token").val());
            $.ajax({
                type: "POST",
                url: "/api/user/setPassword",
                data: formData,
                contentType: false,
                processData: false,
                dataType: "json",
                success: function (response) {
                    console.log(response)
                    switch (response.status.code) {
                        case 4:
                            window.alert.show("error", "Đã có lỗi xảy ra", "2000");
                            break;
                        case 1000:
                            window.alert.show("success", "Thành công", "2000");
                            setTimeout(function () {
                                window.location.href = '/login';
                            }, 2000);
                            break;
                    }
                }
            })
        }
    });
})