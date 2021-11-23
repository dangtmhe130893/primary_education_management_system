$(document).ready(function () {
    let id_global;
    let accountVue = new Vue({
        el: "#modal_add_account",
        data: {
            isUpdateMan: false,
            validNameUser: true,
            validNameUserLength: true,
            validEmailUser: true,
            validEmailUserType: true,
            validEmailUserLength: true,
            validPhoneUser: true,
            validPhoneUserType: true,
            validPhoneUserLength: true,
            validPassword: true,
            validPasswordLength: true,
            validEqualPassword: true,

        },
        methods: {
            resetForm: function () {
                $("#account_name").val("");
                $("#account_email").val("");
                $("#account_phone").val("");
                $("#account_password").val("");
                $("#password_confirm").val("");
                $("#select_company").val("");

                this.validNameUser = true;
                this.validNameUserLength = true;
                this.validEmailUser = true;
                this.validEmailUserType = true;
                this.validEmailUserLength = true;
                this.validPhoneUser = true;
                this.validPhoneUserType = true;
                this.validPhoneUserLength = true;
                this.validPassword = true;
                this.validPasswordLength = true;
                this.validEqualPassword = true;

                $("#select_role").val("").trigger("change");
            },
            detail: function (data) {
                let listNameRole = data.roles.map(role => role.name);
                $("#select_role").val(listNameRole).trigger("change");
                $("#account_name").val(data.name);
                $("#account_email").val(data.email);
                $("#account_phone").val(data.phone);
            },
            validateForm: function () {
                this.validateNameUser();
                if (this.validNameUser) {
                    this.validateNameUserLength();
                }
                this.validateEmailUser();
                if (this.validEmailUser) {
                    this.validateEmailUserLength();
                    if (this.validEmailUserLength) {
                        this.validateEmailUserType();
                    }
                }
                this.validatePhoneUser();
                if (this.validPhoneUser) {
                    this.validatePhoneUserLength();
                    if (this.validPhoneUserLength) {
                        this.validatePhoneUserType();
                    }
                }
                this.validatePassword();
                if (this.validPassword) {
                    this.validatePasswordLength();
                    if (this.validPasswordLength) {
                        this.validateEqualPassword();
                    }
                }
                return this.validNameUser && this.validNameUserLength && this.validEmailUserType && this.validEmailUser
                    && this.validEmailUserLength && this.validPassword && this.validPasswordLength
                    && this.validEqualPassword && this.validPhoneUserType && this.validPhoneUser && this.validPhoneUserLength;

            },
            validateNameUser: function () {
                let note = $("#account_name").val();
                this.validNameUser = !(note === null || note === undefined || note.trim() === "");
            },
            validateNameUserLength: function () {
                let note = $("#account_name").val();
                this.validNameUserLength = note.length < 100;
            },
            validateEmailUser: function () {
                let note = $("#account_email").val();
                this.validEmailUser = !(note === null || note === undefined || note.trim() === "");
            },

            validateEmailUserType: function () {
                let note = $("#account_email").val();
                let regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                this.validEmailUserType = regex.test(note);
            },
            validateEmailUserLength: function () {
                let note = $("#account_email").val();
                this.validEmailUserLength = note.length < 100;
            },
            validatePhoneUser: function () {
                let note = $("#account_phone").val();
                this.validPhoneUser = !(note === null || note === undefined || note.trim() === "");
            },
            validatePhoneUserType: function () {
                let note = $("#account_phone").val();
                let regex = /^[0-9\-\(\)\s]+$/;
                this.validPhoneUserType = regex.test(note);
            },
            validatePhoneUserLength: function () {
                let note = $("#account_phone").val();
                this.validPhoneUserLength = note.length < 20;
            },
            validatePassword: function () {
                let note = $("#account_password").val();
                if (this.isUpdateMan) {
                    return true;
                } else {
                    this.validPassword = !(note === null || note === undefined || note.trim() === "");
                }
            },
            validatePasswordLength: function () {
                let note = $("#account_password").val();
                if (this.isUpdateMan) {
                    if (note == "") {
                        return true;
                    }
                }
                this.validPasswordLength = !(note.length > 32 || note.length < 8);
            },

            validateEqualPassword: function () {
                let password = $("#account_password").val();
                let confirmPassword = $("#password_confirm").val();
                this.validEqualPassword = password === confirmPassword;
            },
            saveAccount: function (e) {
                e.preventDefault();
                if (!this.isUpdateMan) {
                    id_global = null;
                }
                if (this.validateForm()) {
                    let user = {
                        "name": $("#account_name").val(),
                        "email": $("#account_email").val(),
                        "phone": $("#account_phone").val(),
                        "password": $("#account_password").val(),
                        "listRoleName": $("#select_role").val().toString(),
                        "id": id_global,
                    }

                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/api/user/save",
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
                                $("#modal_add_manager").modal("hide");
                                setTimeout(function () {
                                    location.reload();
                                }, 1000);
                            } else if (response.status.code === 1001) {
                                window.alert.show("error", "Email đã tồn tại", 2000);

                            } else {
                                window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                            }
                        }
                    })
                }
            },
            loadRole() {
                $("#select_role").select2({
                    placeholder: '',
                });

            },
        },
        mounted() {
            let self = this;
            self.loadRole();

            $('#modal_add_account').on('hidden.bs.modal', function () {
                self.resetForm();
            })

        }
    })

    $(document).on('click', "#btn_add_account", function () {
        accountVue.isUpdateMan = false;
    });

    $(document).on('click', ".detail-acount", function () {
        accountVue.isUpdateMan = true;
        id_global = this.value;
        $.ajax({
            type: "GET",
            url: "/api/user/getDetail?userId=" + id_global,
            success: function (data) {
                accountVue.detail(data);
            }
        })
    });

})