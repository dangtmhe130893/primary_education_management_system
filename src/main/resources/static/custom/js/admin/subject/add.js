$(document).ready(function () {



    let formSubjectVue = new Vue({
        el: "#modal_add_subject",
        data: {
            id: "",
            subject: "",
            listTeacherCanTeach: [],

            isShowErrorNameSubject: false,
            isShowErrorNameSubjectLength: false,
            isShowErrorListTeacher: false,
        },
        methods: {
            loadListTeacherCanTeach() {
                let self = this;
                $("#select-teacher").empty();

                $.ajax({
                    type: "GET",
                    url: "/api/user/getListTeacherCanTeach",
                    success: function (response) {
                        if (response.status.code === 1000) {
                            self.listTeacherCanTeach = response.data;
                            self.listTeacherCanTeach.forEach(function (teacher) {
                                let html = `<option value="${teacher.id}">${teacher.name}</option>`;
                                $("#select-teacher").append(html);
                            });
                        }
                    }
                })
            },
            saveSubject() {
                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    subject: this.subject,
                    listTeacherIdString: $("#select-teacher").val().toString(),
                }
                if (this.id) {
                    data.id = this.id;
                }

                $.ajax({
                    type: "POST",
                    url: "/api/subject/save",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        $("#modal_add_subject").modal("hide");

                        if (response.status.code === 1000) {
                            tableSubject.ajax.reload();
                            window.alert.show("success", "Lưu thành công", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra", 2000);
                        }
                    }
                })
            },

            detail() {
                let self = this;
                $("#select-teacher").empty();

                $.ajax({
                    type: "GET",
                    url: "/api/subject/detail/" + self.id,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;
                            self.id = data.id;
                            self.subject = data.name;

                            let listNameTeacherTeaching = data.listTeacherTeaching.map(teacher => teacher.id);

                            data.listTeacherCanTeach.forEach(function (teacher) {
                                let html = `<option value="${teacher.id}">${teacher.name}</option>`;
                                $("#select-teacher").append(html);
                            });

                            $("#select-teacher").val(listNameTeacherTeaching).trigger("change");
                        }
                    }
                })
            },

            deleteSubject() {
                let self = this;

                $.ajax({
                    type: "POST",
                    url: "/api/subject/delete/" + self.id,
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        $("#modal_delete_subject").modal("hide");

                        if (response.status.code === 1000) {
                            tableSubject.ajax.reload();
                            window.alert.show("success", "Xóa thành công", 2000);
                        } else {
                            window.alert.show("error", "Xóa thất bại", 2000);
                        }
                    }
                })
            },

            validateForm() {
                this.validateNameSubject();
                this.validateNameSubjectLength();
                this.validateListTeacher();
                return !this.isShowErrorNameSubject && !this.isShowErrorNameSubjectLength && !this.isShowErrorListTeacher;
            },
            validateNameSubject() {
                this.isShowErrorNameSubject = this.subject.trim() == "";
            },
            validateNameSubjectLength() {
                this.isShowErrorNameSubjectLength = this.subject.length > 20;
            },
            validateListTeacher() {
                this.isShowErrorListTeacher = ! $("#select-teacher").val().toString();
            },
            resetPopup() {
                this.id = "";
                this.subject = "";
                this.listTeacher = [];

                this.isShowErrorNameSubject = false;
                this.isShowErrorNameSubjectLength = false;
                this.isShowErrorListTeacher = false;

                $("#select-teacher").val("").trigger("change");
            }
        },
        mounted() {
            let self = this;
            self.loadListTeacherCanTeach();

            $("#select-teacher").select2({
                placeholder: '',
            });

            $('#modal_add_subject').on('hidden.bs.modal', function () {
                self.resetPopup();
            })

            $('#modal_delete_subject').on('hidden.bs.modal', function () {
                self.id = "";
            })
        },
    })

    $(document).on("click", "#btn_add_subject", function () {
        formSubjectVue.loadListTeacherCanTeach();
    })

    $(document).on("click", ".detail-subject", function () {
        formSubjectVue.id = Number($(this).attr('id').replace("btn_detail_", ""));
        formSubjectVue.detail();
    })

    $(document).on("click", ".delete-subject", function () {
        $("#modal_delete_subject").modal("show");
        formSubjectVue.id = Number($(this).attr('id').replace("btn_delete_", ""));
    })

    $(document).on("click", "#btn_submit_delete", function () {
        formSubjectVue.deleteSubject();
    })

    $(document).on("click", "#btn_search_subject", function () {
        tableSubject.ajax.reload();
    })

    $("#input-search_subject").bind('keypress', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            tableSubject.ajax.reload();
        }
    });


})