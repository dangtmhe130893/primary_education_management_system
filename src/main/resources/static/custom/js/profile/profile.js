$(document).ready(function () {
    let profileVue = new Vue({
        el: "#form_profile",
        data: {
            name: '',
            email: '',
            phone: '',
            birthday: '',
            address: '',
        },
        methods: {
            getProfile() {
                let vm = this;
                window.loader.show();
                jQuery.get("/api/user/profile", function (response) {
                    window.loader.hide();
                    if (response) {
                        vm.name = response.name;
                        vm.email = response.email;
                        vm.phone = response.phone;
                        if (response.birthday) {
                            vm.birthday = moment(response.birthday).format('YYYY/MM/DD')
                        }
                        vm.address = response.address;
                    }
                });
            },
            saveAccount: function (e) {
                let vm = this;
                e.preventDefault();
                let user = {
                    "name": vm.name,
                    "email": vm.email,
                    "phone": vm.phone,
                    "birthday": $("#birthday").val() === '' ? null : $("#birthday").val(),
                    "address": vm.address,
                }
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/api/user/updateProfile",
                    data: JSON.stringify(user),
                    beforeSend: function () {
                        window.loader.show();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    },
                    success: function (response) {
                        window.loader.hide();
                        if (response.status.code === 1000) {
                            window.alert.show("success", "Thành công", 1000);
                        } else if (response.status.code === 1001) {
                            window.alert.show("error", "電Email đã tồn tại", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                        }
                    }
                })
            },

        },
        mounted() {
            let self = this;
            self.getProfile();
            configOneDateNotTime('birthday');
        }
    })

})