let popupPupilAccountVue;
let filterVue;
$(document).ready(function () {
    let listAccountImport = [];

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
            isShowErrorBirthday: false,
            isShowErrorFormatBirthday: false,
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
                    birthday: $("#birthday").val(),
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
                        } else if (response.status.code === 1001) {
                            window.alert.show("error", "Email đã tồn tại", 2000);
                        }
                        else {
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
                            if (data.birthday) {
                                $("#birthday").val(moment(data.birthday).format('YYYY/MM/DD'));
                            }
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
            validateBirthday() {
                this.isShowErrorBirthday = $("#birthday").val() == "";
            },
            validateFormatBirthday() {
                this.isShowErrorFormatBirthday = moment($("#birthday").val()) > moment(new Date());
            },
            validateForm() {
                this.validateGrade();
                this.validateClass();
                this.validateBirthday();
                this.validateFormatBirthday();
                return !this.isShowErrorGrade && !this.isShowErrorClass && !this.isShowErrorBirthday && !this.isShowErrorFormatBirthday;
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
                this.isShowErrorBirthday = false;
                this.isShowErrorFormatBirthday = false;
                $("#birthday").val("");
            },

            previewListAccount() {
                let previewTable = '            <table class="table responsive display nowrap" id="preview_room_table">' +
                    '                                <thead>' +
                    '                                <tr>' +
                    '                                    <th style="width: 100%" class="text-center">Họ tên</th>' +
                    '                                    <th style="width: 100%" class="text-center">Khối</th>' +
                    '                                    <th style="width: 100%" class="text-center">Lớp</th>' +
                    '                                    <th style="width: 100%" class="text-center">Email</th>' +
                    '                                    <th style="width: 100%" class="text-center">Mật khẩu</th>' +
                    '                                    <th style="width: 100%" class="text-center">Số điện thoại</th>' +
                    '                                    <th style="width: 100%" class="text-center">Ngày sinh</th>' +
                    '                                    <th style="width: 100%" class="text-center">Giới tính</th>' +
                    '                                    <th style="width: 100%" class="text-center">Địa chỉ</th>' +
                    '                                    <th style="width: 100%" class="text-center">Họ tên bố</th>' +
                    '                                    <th style="width: 100%" class="text-center">Họ tên mẹ</th>' +
                    '                                </tr>' +
                    '                                </thead>' +
                    '                                <tbody>' +
                    '                                </tbody>' +
                    '                            </table>';

                $('#preview').html(previewTable);
                $('#preview_room_table').DataTable({
                    searching: false,
                    "ordering": false,
                    language: {
                        "url": "/libs/new_data_table/js/vie.json"
                    },
                    data: listAccountImport,
                    columns: [
                        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "className", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "email", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "password", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "phone", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "birthday", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "gender", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "address", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "fatherName", "orderable": false, "defaultContent": "", "class": 'text-center'},
                        {"data": "motherName", "orderable": false, "defaultContent": "", "class": 'text-center'},
                    ]
                })
            },
        },
        mounted() {

            let self = this;

            configOneDateNotTime('birthday');
            $("#birthday").val("");

            $('#modal_add_pupil_account').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

            $(document).on("change", "#input-file-excel", function () {
                console.log("file change")
                if (this.files[0] == undefined) {
                    console.log("file undefined")
                    return;
                }
                let formData = new FormData();
                formData.append("file", this.files[0]);
                $.ajax({
                    type: "POST",
                    url: "/api/pupil_account/importExcel",
                    data: formData,
                    processData: false,
                    contentType: false,
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        let code = response.status.code;
                        if (code === 1000) {
                            listAccountImport = response.data;
                            console.log(listAccountImport);
                            self.previewListAccount();

                            $("#modal_preview_pupil_account").modal("show");
                        } else if (code === 1008) {
                            window.alert.show("error", "Danh sách email import đã bị trùng", 5000);
                        } else {
                            let rowAndColError = response.data;
                            let rowError = rowAndColError[0];
                            let colError = rowAndColError[1];
                            if (code === 1007) {
                                window.alert.show("error", "Email import đã tồn tại tại hàng: " + rowError + ", cột: " + colError, 5000);
                            } else if (code === 1009) {
                                window.alert.show("error", "Dữ liệu hàng: " + rowError + ", cột: " + colError + " đang bị trống", 5000);
                            } else if (code === 1010) {
                                window.alert.show("error", "Định dạng dữ liệu import không chính xác tại hàng: " + rowError + ", cột: " + colError, 5000);
                            } else if (code === 1011) {
                                window.alert.show("error", "Tên lớp không tồn tại tại hàng: " + rowError + ", cột: " + colError, 5000);
                            }
                        }

                    }
                });

                $("#input-file-excel").val(null); // to the next import: file changed
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

    $("#btn_import").click(function () {
        $("#input-file-excel").trigger("click");
    })

    $("#modal_preview_pupil_account").on('hidden.bs.modal', function () {
        listAccountImport = [];
        $("#input-file-excel").val(null);
    })

    $(document).on("click", "#save_list_pupil_account", function () {
        if (listAccountImport.length === 0) {
            return;
        }
        $.ajax({
            type: "POST",
            url: "/api/pupil_account/saveList",
            data: JSON.stringify(listAccountImport),
            contentType: "application/json",
            beforeSend: function () {
                window.loader.show();
            },
            success: function (response) {
                window.loader.hide();
                if (response.status.code === 1000) {
                    $("#modal_preview_pupil_account").modal("hide");
                    listAccountTable.ajax.reload();
                    window.alert.show("success", "Import danh sách học sinh thành công", 2000);
                } else {
                    window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                }
            }
        })
    })

    $(document).on("click", ".detail-pupil", function () {
        popupPupilAccountVue.id = Number($(this).attr('id').replace('btn_detail_', ''));
        popupPupilAccountVue.detail();
    })
})