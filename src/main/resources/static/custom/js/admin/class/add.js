$(document).ready(function () {


    let formClassVue = new Vue({
        el: "#modal_add_class",
        data: {
            id: "",
            nameClass: "",
            grade: "",
            room: "",
            listGrade: [],

            isShowErrorNameClass: false,
            isShowErrorNameClassLength: false,
            isShowErrorGrade: false,
            isShowErrorRoom: false,
            isShowErrorRoomLength: false,
        },
        methods: {
            saveClass() {
                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    nameClass: this.nameClass,
                    grade: this.grade,
                    room: this.room,
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
                        $("#modal_add_class").modal("hide");

                        if (response.status.code === 1000) {
                            tableClass.ajax.reload();
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else {
                            window.alert.show("error", "Lưu thất bại", 2000);
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
                            self.id = data.id;
                            self.nameClass = data.name;
                            self.room = data.room;
                            self.grade = data.grade;
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
                            tableClass.ajax.reload();
                            window.alert.show("success", "Xóa thành công", 2000);
                        } else {
                            window.alert.show("error", "Xóa thất bại", 2000);
                        }
                    }
                })
            },

            loadListGrade() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/grade/getList",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listGrade = response.data;
                        }
                    }
                })
            },
            validateForm() {
                this.validateNameClass();
                this.validateNameClassLength();
                this.validateNameRoom();
                this.validateNameRoomLength();
                this.validateGrade();
                return !this.isShowErrorNameClass && !this.isShowErrorNameClassLength && !this.isShowErrorRoom && !this.isShowErrorRoomLength;
            },
            validateNameClass() {
                this.isShowErrorNameClass = this.nameClass.trim() == "";
            },
            validateNameClassLength() {
                this.isShowErrorNameClassLength = this.nameClass.length > 20;
            },
            validateNameRoom() {
                this.isShowErrorRoom = this.room.trim() == "";
            },
            validateNameRoomLength() {
                this.isShowErrorRoomLength = this.room.length > 20;
            },
            validateGrade() {
                this.isShowErrorGrade = !this.grade;
            },
            resetPopup() {
                this.id = "";
                this.nameClass = "";
                this.grade = "";
                this.room = "";

                this.isShowErrorNameClass = false;
                this.isShowErrorNameClassLength = false;
                this.isShowErrorGrade = false;
                this.isShowErrorRoom = false;
                this.isShowErrorRoomLength = false;
            }
        },
        mounted() {
            let self = this;
            self.loadListGrade();

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