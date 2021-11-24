$(document).ready(function () {


    let formTuitionVue = new Vue({
        el: "#modal_add_tuition",
        data: {
            id: "",
            grade: "Khối 1",
            fee: "",

            isShowErrorFee: false,
        },
        methods: {
            saveTuition() {
                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    grade: this.grade,
                    fee: this.fee,
                }
                if (this.id) {
                    data.id = this.id;
                }

                $.ajax({
                    type: "POST",
                    url: "/api/tuition/save",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        $("#modal_add_tuition").modal("hide");

                        if (response.status.code === 1000) {
                            tableClass.ajax.reload();
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
                    url: "/api/tuition/detail/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.id = data.id;
                            self.fee = data.fee;
                            self.grade = data.grade;
                        }
                    }
                })
            },

            validateForm() {
                this.validateFee();
                return !this.isShowErrorFee;
            },
            validateFee() {
                this.isShowErrorFee = this.fee === "";
            },
            resetPopup() {
                this.id = "";
                this.grade = "Khối 1";
                this.fee = "";

                this.isShowErrorFee = false;
            }
        },
        mounted() {
            let self = this;

            $('#modal_add_tuition').on('hidden.bs.modal', function () {
                self.resetPopup();
            })
        },
    })

    $(document).on("click", ".detail-tuition", function () {
        formTuitionVue.id = Number($(this).attr('id').replace("btn_detail_", ""));
        formTuitionVue.detail();
    })

})