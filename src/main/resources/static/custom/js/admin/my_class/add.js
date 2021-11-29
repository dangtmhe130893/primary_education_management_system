let popupDetailPupilAccountVue;
$(document).ready(function () {


    popupDetailPupilAccountVue = new Vue({
        el: "#modal_add_pupil_account",
        data: {
            grade: "",
            listClass: [],
            classId: "",
            id: "",
            name: "",
            email: "",
            phone: "",
            sex: 1,
            address: "",
            fatherName: "",
            motherName: "",

            isUpdate: false,

        },
        watch: {

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
            }
        },
        mounted() {

            let self = this;

            $('#modal_add_pupil_account').on('hidden.bs.modal', function () {
                self.resetPopup();
            })
        }
    })

    $(document).on("click", ".detail-pupil", function () {
        popupDetailPupilAccountVue.id = Number($(this).attr('id').replace('btn_detail_', ''));
        popupDetailPupilAccountVue.detail();
    })
})