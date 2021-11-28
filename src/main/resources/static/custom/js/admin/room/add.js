$(document).ready(function () {

    let formRoomVue = new Vue({
        el: "#modal_add_room",
        data: {
            id: "",
            nameRoom: "",

            isShowErrorNameRoom: false,
            isShowErrorNameRoomLength: false,
        },
        methods: {
            saveRoom() {
                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    name: this.nameRoom,
                }
                if (this.id) {
                    data.id = this.id;
                }

                $.ajax({
                    type: "POST",
                    url: "/api/room/save",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();

                        if (response.status.code === 1000) {
                            tableRoom.ajax.reload();
                            $("#modal_add_room").modal("hide");
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else if (response.status.code === 1004) {
                            window.alert.show("error", "Tên phòng học đã tồn tại", 2000);
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
                    url: "/api/room/detail/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.id = data.id;
                            self.nameRoom = data.name;
                        }
                    }
                })
            },

            deleteRoom() {
                let self = this;

                $.ajax({
                    type: "POST",
                    url: "/api/room/delete/" + self.id,
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        $("#modal_delete_room").modal("hide");

                        if (response.status.code === 1000) {
                            tableRoom.ajax.reload();
                            window.alert.show("success", "Xóa thành công", 2000);
                        } else {
                            window.alert.show("error", "Xóa thất bại", 2000);
                        }
                    }
                })
            },

            validateForm() {
                this.validateNameRoom();
                this.validateNameRoomLength();
                return !this.isShowErrorNameRoom && !this.isShowErrorNameRoomLength;
            },
            validateNameRoom() {
                this.isShowErrorNameRoom = this.nameRoom.trim() == "";
            },
            validateNameRoomLength() {
                this.isShowErrorNameRoomLength = this.nameRoom.length > 20;
            },

            resetPopup() {
                this.id = "";
                this.nameRoom = "";

                this.isShowErrorNameRoom = false;
                this.isShowErrorNameRoomLength = false;

            }
        },
        mounted() {
            let self = this;

            $('#modal_add_room').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

            $('#modal_delete_room').on('hidden.bs.modal', function () {
                self.id = "";
            })
        },
    })

    $(document).on("click", ".detail-room", function () {
        formRoomVue.id = Number($(this).attr('id').replace("btn_detail_", ""));
        formRoomVue.detail();
    })

    $(document).on("click", ".delete-room", function () {
        $("#modal_delete_room").modal("show");
        formRoomVue.id = Number($(this).attr('id').replace("btn_delete_", ""));
    })

    $(document).on("click", "#btn_submit_delete", function () {
        formRoomVue.deleteRoom();
    })

    $(document).on("click", "#btn_search_room", function () {
        tableRoom.ajax.reload();
    })

    $("#search_room_id").bind('keypress', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            tableRoom.ajax.reload();
        }
    });


})