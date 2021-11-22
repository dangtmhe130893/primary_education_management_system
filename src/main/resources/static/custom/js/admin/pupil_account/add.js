let popupPupilAccountVue;
let filterVue;
$(document).ready(function () {

    filterVue = new Vue({
        el: "#filter",
        data: {
            grade: "0",
            classId: "0",
            listClass: [],
        },
        methods: {
            loadListClass() {
                let self = this;
                $.ajax({
                    type: "GET",
                    url: "/api/class/getListByGrade?grade=" + self.grade,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listClass = response.data;
                            self.classId = "0";
                        }
                    }
                })
            },

        },
        watch: {
            grade() {
                listAccountTable.ajax.reload();
                this.loadListClass();
            },
            classId() {
                listAccountTable.ajax.reload();
            }
        },
        mounted() {
            this.loadListClass();
        }
    })


    popupPupilAccountVue = new Vue({
        el: "#modal_add_pupil_account",
        data: {
            grade: "",
            listClass: [],
            classId: "",
            id: "",
            name: "",
            email: "",
            phone: "",
            password: "",
            passwordConfirm: "",
            sex: 1,
            address: "",
            fatherName: "",
            motherName: "",

            isUpdate: false,

            isShowErrorGrade: false,
            isShowErrorClass: false,
        },
        watch: {
            id(value) {
                this.isUpdate = !!value;
            },
            grade() {
                this.loadListClass();
            }
        },
        methods: {
            loadListClass() {
                let self = this;
                $.ajax({
                    type: "GET",
                    url: "/api/class/getListByGrade?grade=" + self.grade,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listClass = response.data;
                        }
                    }
                })
            },
            savePupilAccount() {
                if (!$("#form-pupil-account").valid()) {
                    return;
                }

                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    name: this.name,
                    email: this.email,
                    phone: this.phone,
                    password: this.password,
                    sex: this.sex,
                    address: this.address,
                    fatherName: this.fatherName,
                    motherName: this.motherName,
                    grade: this.grade,
                    classId: this.classId,
                }

                if (this.id) {
                    data.id = this.id;
                } else {
                    data.password = this.password;
                }

                $.ajax({
                    type: "POST",
                    url: "/api/pupil_account/save",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        if (response.status.code === 1000) {
                            listAccountTable.ajax.reload();
                            $('#modal_add_pupil_account').modal("hide");
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                        }
                    }
                })
            },
            detail() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/pupil_account/detail/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.grade = data.grade;
                            self.listClass = data.listClass;
                            self.classId = data.classId;
                            self.name = data.name;
                            self.email = data.email;
                            self.phone = data.phone;
                            self.sex = data.gender;
                            self.address = data.address;
                            self.fatherName = data.fatherName;
                            self.motherName = data.motherName;
                        }
                    }
                })
            },
            delete() {
                let self = this;

                $.ajax({
                    type: "POST",
                    url: "/api/pupil_account/delete/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            listAccountTable.ajax.reload();
                            window.alert.show("success", "Xóa thành công", 2000);
                            $("#modal_delete_pupil").modal("hide");
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", 2000);
                        }
                    }
                })
            },
            validateGrade() {
                this.isShowErrorGrade = !this.grade;
            },
            validateClass() {
                this.isShowErrorClass = !this.classId;
            },
            validateForm() {
                this.validateGrade();
                this.validateClass();
                return !this.isShowErrorGrade && !this.isShowErrorClass;
            },
            resetPopup() {
                this.classId = "";
                this.listClass = [];
                this.grade = "";
                this.id = "";
                this.name = "";
                this.email = "";
                this.phone = "";
                this.password = "";
                this.passwordConfirm = "";
                this.sex = 1;
                this.address = "";
                this.fatherName = "";
                this.motherName = "";
                this.isShowErrorGrade = false;
                this.isShowErrorClass = false;

            }
        },
        mounted() {

            let self = this;

            $('#modal_add_pupil_account').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

            $("#form-pupil-account").validate({
                errorElement: "p",
                errorClass: "error-message",
                errorPlacement: function (error, element) {
                    error.insertBefore(element);
                },
                ignore: [],
                rules: {
                    name: {
                        required: true,
                        maxlength: 200,
                    },
                    email: {
                        required: true,
                        maxlength: 200,
                        validateFormatEmail: true,
                    },
                    phone: {
                        required: true,
                        validateFormatPhone: true,
                    },
                    password: {
                        required: true,
                        minlength: 8,
                        maxlength: 32,
                    },
                    passwordConfirm: {
                        required: true,
                        equalTo: "#password"
                    },
                    address: {
                        required: true,
                        maxlength: 200,
                    },
                },
                messages: {
                    name: {
                        required: "Trường này là bắt buộc",
                        maxlength: "Tối đa 200 ký từ",
                    },
                    email: {
                        required: "Trường này là bắt buộc",
                        maxlength: "Tối đa 200 ký từ",
                    },
                    phone: {
                        required: "Trường này là bắt buộc",
                    },
                    password: {
                        required: "Trường này là bắt buộc",
                        minlength: "Mật khẩu từ 8 đến 32 ký tự",
                        maxlength: "Mật khẩu từ 8 đến 32 ký tự",
                    },
                    passwordConfirm: {
                        required: "Trường này là bắt buộc",
                        equalTo: "Mật khẩu và mật khẩu xác nhận không khớp",
                    },
                    address: {
                        required: "Trường này là bắt buộc",
                        maxlength: "Tối đa 200 ký từ",
                    },
                }

            });

            $.validator.addMethod("validateFormatEmail", function (value) {
                let regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                return regex.test(value);
            }, "Định dạng không chính xác");

            $.validator.addMethod("validateFormatPhone", function (value) {
                let regex = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
                return regex.test(value);
            }, "Định dạng không chính xác");
        }
    })

    $(document).on("click", ".detail-pupil", function () {
        popupPupilAccountVue.id = Number($(this).attr('id').replace('btn_detail_', ''));
        popupPupilAccountVue.detail();
    })
})