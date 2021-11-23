$(document).ready(function () {

    $("#get_code").on('click', function () {
        $("#get_code").prop("disabled", true);
        if ($("#email").val() == '' || $("#email").val() == null) {
            window.alert.show("error", "Đã có lỗi xảy ra", "2000");
            setTimeout(function () {
                $("#get_code").css("pointer-events", "auto");
            }, 2000)
        } else {
            let formData = new FormData();
            formData.append("email", $("#email").val());
            $.ajax({
                type: "POST",
                url: "/api/user/forgotPassword",
                data: formData,
                contentType: false,
                processData: false,
                dataType: "json",
                success: function (response) {
                    $("#get_code").prop("disabled", false);
                    console.log(response);
                    switch (response.status.code) {
                        case 4:
                            window.alert.show("error", "Đã có lỗi xảy ra.", "2000");
                            break;
                        case 1000:
                            setTimeout(function () {
                                window.alert.show("success", "Đã gửi thông tin xác thực qua email", "2000");
                            }, 1200)
                            break;
                    }
                }
            })
        }
    })
})