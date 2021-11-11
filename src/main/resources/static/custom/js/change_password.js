$(document).ready(function () {
    jQuery.validator.addMethod("hasCharacter", function (password) {
        var length = password.length;
        ch = '';
        while (length--) {
            ch = password.charAt(length);
            if (ch <= '0' || ch >= '9') {
                return true; // we have found a character here
            }
        }
        return false; // the loop is done, yet we didn't find any character
    }, "Password must has at least 1 character");

    jQuery.validator.addMethod("hasDigist", function (password) {
        var length = password.length;
        ch = '';
        while (length--) {
            ch = password.charAt(length);
            if (ch >= 0 || ch <= 9) {
                return true; // we have found a digist here
            }
        }
        return false; // the loop is done, yet we didn't find any character
    }, "Password must has at least 1 character");

    jQuery.validator.addMethod("notEqual", function (value, element, options) {
        // get all the elements passed here with the same class
        var elems = $(".not-same");
        // the value of the current element
        var valueToCompare = value;
        // count
        var matchesFound = 0;
        // loop each element and compare its value with the current value
        // and increase the count every time we find one
        jQuery.each(elems, function () {
            thisVal = $(this).val();
            if (thisVal == valueToCompare) {
                matchesFound++;
            }
        });
        // count should be either 0 or 1 max
        if (this.optional(element) || matchesFound <= 1) {
            //elems.removeClass('error');
            return true;
        } else {
            //elems.addClass('error');
        }
    }, "Password must has at least 1 character")


    $("#change_password_form").validate({
        errorElement: 'span',
        errorClass: 'error-message',
        focusInvalid: false,
        ignore: "",
        rules: {
            old_password: {
                required: true
            },
            new_password: {
                required: true,
                minlength: 8,
                maxlength: 40,
                hasCharacter: true,
                notEqual: ["#old_password"],
                hasDigist: true,
            },
            confirm_password: {
                equalTo: "#new_password"
            }
        },
        messages: {
            new_password: {
                hasCharacter: "少なくとも1つの英字を入れてください。",
                notEqual: "新しいパスワードは古い古いパスワードと同じではありません。",
                hasDigist: "少なくとも1つの数字を入れてください。"
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
            $(".btn-changepassword").prop("disabled", true);
            $.ajax({
                type: "POST",
                url: "/api/v1/web/user/changePassword",
                data: $("#change_password_form").serialize(),
                success: function (response) {
                    console.log(response);
                    switch (response.status.code) {
                        case 1007:
                            window.alert.show("error", $("#password_wrong").html(), "2000")
                            setTimeout(function () {
                                $(".btn-changepassword").prop("disabled", false);
                            }, 2000)
                            break;
                        case 4:
                            window.alert.show("error", $("#server_error").html(), "2000")
                            setTimeout(function () {
                                $(".btn-changepassword").prop("disabled", false);
                            }, 2000)
                            break;
                        case 1000:
                            window.alert.show("success", $("#success").html(), "2000");
                            setTimeout(function () {
                                window.location.href = '/redirectHandler';
                            }, 2000);
                            break;
                    }
                }
            })
        }

    });
})