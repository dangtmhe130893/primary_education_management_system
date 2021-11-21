$(document).ready(function () {

    let columnDefinitions = [
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageSubject = function (requestData, renderFunction, link_api) {

        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "createdTime",
            "sortDir": "desc",
            "keyword": $("#input-search_subject").val(),
        };
        window.loader.show();
        jQuery.get(link_api, params, function (response) {
            let content = {
                "draw": requestData.draw,
                "recordsTotal": response.totalElements,
                "recordsFiltered": response.totalElements,
                "data": response.content
            };
            renderFunction(content);
            window.loader.hide();
        });
    };

    let tableSubject = $("#subject_table").DataTable({
        "language": {
            "url": "/libs/new_data_table/js/vie.json"
        },
        "lengthMenu": [
            [20, 50, 100],
            [20, 50, 100]
        ],

        "searching": false,
        rowId: 'id',
        "ordering": true,
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getPageSubject(data, callback, "/api/subject/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    let result = `<ul>`;

                    if(data.listTeacherTeaching) {
                        data.listTeacherTeaching.forEach(teacher => {
                            result += `<li>${teacher.name}</li>`
                        })
                    }

                    result += `</ul>`;

                    return result;
                },
                "targets": 1
            },
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_subject" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-subject">Chi tiết</button>
                            <button style="margin-left: 10px" data-toggle="modal" id="btn_delete_${data.id}" data-target="#modal_delete_subject" class="btn btn-sm btn-danger delete-subject">Xóa</button>`;
                },
                "targets": 2
            },
        ]
    });

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
                            window.alert.show("error", "Lưu thất bại", 2000);
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