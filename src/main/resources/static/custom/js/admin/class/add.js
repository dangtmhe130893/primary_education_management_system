$(document).ready(function () {


    let formClassVue = new Vue({
        el: "#modal_add_class",
        data: {
            id: "",
            nameClass: "",
            grade: "",
            roomId: "",
            listRoom: [],
            homeroomTeacherId: "",
            listTeacher: [],

            isShowErrorNameClass: false,
            isShowErrorNameClassLength: false,
            isShowErrorGrade: false,
            isShowErrorRoom: false,
            isShowErrorHomeroomTeacher: false,

        },
        methods: {
            loadListHomeroomTeacher() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/user/getListHomeroomTeacher",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listTeacher = response.data;
                        }
                    }
                })
            },
            loadListRoom() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/room/getListForClass",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listRoom = response.data;
                        }
                    }
                })
            },
            saveClass() {
                let self = this;

                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    nameClass: this.nameClass,
                    grade: this.grade,
                    roomId: this.roomId,
                    homeroomTeacherId: this.homeroomTeacherId,
                }
                if (this.id) {
                    data.id = this.id;
                }

                $.ajax({
                    type: "POST",
                    url: "/api/class/save",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();

                        if (response.status.code === 1000) {
                            tableClass.ajax.reload();
                            $("#modal_add_class").modal("hide");
                            window.alert.show("success", "Lưu thành công", 2000);
                            setTimeout(function (){
                                window.location.reload();
                            }, 2000)
                        } else if (response.status.code === 1004) {
                            window.alert.show("error", "Tên lớp đã tồn tại", 2000);
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
                    url: "/api/class/detail/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.listTeacher.push(data.homeroomTeacherCurrent);
                            self.listRoom.push(data.roomCurrent);

                            self.id = data.id;
                            self.nameClass = data.name;
                            self.roomId = data.roomId;
                            self.grade = data.grade;
                            self.homeroomTeacherId = data.homeroomTeacherId;
                        }
                    }
                })
            },

            deleteClass() {
                let self = this;

                $.ajax({
                    type: "POST",
                    url: "/api/class/delete/" + self.id,
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        $("#modal_delete_class").modal("hide");

                        if (response.status.code === 1000) {
                            window.alert.show("success", "Xóa thành công", 2000);
                            setTimeout(function (){
                                window.location.reload();
                            }, 2000)
                        } else {
                            window.alert.show("error", "Xóa thất bại", 2000);
                        }
                    }
                })
            },

            validateForm() {
                this.validateNameClass();
                this.validateNameClassLength();
                this.validateRoom();
                this.validateGrade();
                this.validateHomeroomTeacher();
                return !this.isShowErrorNameClass && !this.isShowErrorNameClassLength
                    && !this.isShowErrorRoom && !this.isShowErrorHomeroomTeacher && !this.isShowErrorGrade;
            },
            validateNameClass() {
                this.isShowErrorNameClass = this.nameClass.trim() == "";
            },
            validateNameClassLength() {
                this.isShowErrorNameClassLength = this.nameClass.length > 20;
            },
            validateRoom() {
                this.isShowErrorRoom = !this.roomId;
            },
            validateGrade() {
                this.isShowErrorGrade = !this.grade;
            },
            validateHomeroomTeacher() {
                this.isShowErrorHomeroomTeacher = !this.homeroomTeacherId;
            },
            resetPopup() {
                this.id = "";
                this.nameClass = "";
                this.grade = "";
                this.roomId = "";
                this.homeroomTeacherId = "";

                this.isShowErrorNameClass = false;
                this.isShowErrorNameClassLength = false;
                this.isShowErrorGrade = false;
                this.isShowErrorRoom = false;
                this.isShowErrorHomeroomTeacher = false;
            }
        },
        mounted() {
            let self = this;

            self.loadListRoom();
            self.loadListHomeroomTeacher();

            $('#modal_add_class').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

            $('#modal_delete_class').on('hidden.bs.modal', function () {
                self.id = "";
            })
        },
    })

    $(document).on("click", ".detail-class", function () {
        formClassVue.id = Number($(this).attr('id').replace("btn_detail_", ""));
        formClassVue.detail();
    })

    $(document).on("click", ".delete-class", function () {
        $("#modal_delete_class").modal("show");
        formClassVue.id = Number($(this).attr('id').replace("btn_delete_", ""));
    })

    $(document).on("click", "#btn_submit_delete", function () {
        formClassVue.deleteClass();
    })

    $(document).on("click", "#btn_search_class", function () {
        tableClass.ajax.reload();
    })

    $("#search_class_id").bind('keypress', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            tableClass.ajax.reload();
        }
    });


})