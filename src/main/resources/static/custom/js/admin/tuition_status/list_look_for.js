$(document).ready(function () {
    let columnDefinitions = [
        {"data": "codePupil", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "pupilName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionRequire", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionReceived", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "doneTuition", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "pupilId", "orderable": false, "defaultContent": "", "class": 'text-center'},
    ];

    let getPageLookForTuitionPupil = function (requestData, renderFunction, link_api) {
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "numberPupil",
            "sortDir": "desc",
            "keyword": $("#input-search_pupil").val(),
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

    let pageLookForTuitionPupil = $("#look_for_tuition_pupil").DataTable({
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
            getPageLookForTuitionPupil(data, callback, "/api/history_pay_tuition/getPage?type=all_pupil&classId=" + 0);
        },
        columnDefs: [
            {
                "render": function (status) {
                    if (status) {
                        return '<button disabled class="btn btn-sm btn-success " >Đã thu đủ</button>';
                    } else {
                        return '<button disabled class="btn btn-sm btn-secondary" >Chưa thu đủ</button>';
                    }
                },
                "targets": 4
            },
            {
                "render": function (pupilId) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_update_tuition" id="btn_update_${pupilId}" class="btn btn-sm btn-primary detail-pupil">Cập nhật</button>`;
                },
                "targets": 5
            },
        ]
    });

    $("#btn_search_pupil").on("click", function () {
        pageLookForTuitionPupil.ajax.reload();
    })

    $("#input-search_pupil").bind('keypress', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            pageLookForTuitionPupil.ajax.reload();
        }
    });

    let pupilId;
    $(document).on("click", ".detail-pupil", function () {
        pupilId = Number($(this).attr("id").replace("btn_update_", ""));
        updateTuitionVue.detail();
    })

    let updateTuitionVue = new Vue({
        el: "#modal_update_tuition",
        data: {
            pupilName: "",
            tuitionTotal: "",
            tuitionRemain: "",
            tuitionAdd: 0,

            isShowErrorRequireTuitionAdd: false,
            isShowErrorTuitionAdd: false,
        },
        methods: {
            detail() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/history_pay_tuition/detail/" + pupilId,
                    success: function (response) {
                        if (response.status.code === 1000) {
                            let data = response.data;

                            self.pupilName = data.pupilName;
                            self.tuitionTotal = data.tuitionTotal;
                            self.tuitionRemain = data.tuitionRemain;

                        }
                    }
                })
            },
            validateForm() {
                this.validateTuitionAdd();
                this.validateTuitionAddRequire();
                return !this.isShowErrorRequireTuitionAdd && !this.isShowErrorTuitionAdd;
            },
            validateTuitionAddRequire() {
                this.isShowErrorTuitionAdd = this.tuitionAdd > this.tuitionRemain;
            },
            validateTuitionAdd() {
                this.isShowErrorRequireTuitionAdd = this.tuitionAdd <= 0;
            },
            updateTuition() {
                let self = this;
                if (!this.validateForm()) {
                    return;
                }

                let data = {
                    pupilId: pupilId,
                    tuitionAdd: self.tuitionAdd,
                }
                $.ajax({
                    type: "POST",
                    url: "/api/history_pay_tuition/updateTuition",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    beforeSend: function () {
                        window.loader.show();
                    },
                    success: function (response) {
                        window.loader.hide();
                        if (response.status.code === 1000) {
                            pageLookForTuitionPupil.ajax.reload();
                            $("#modal_update_tuition").modal("hide");
                            pageTuitionAllSchool.ajax.reload();
                            pageTuitionGroupByGrade.ajax.reload();
                            pageTuitionGroupByClass.ajax.reload();
                            pageTuitionAllPupil.ajax.reload();
                            window.alert.show("success", "Cập nhật học phí thành công!", 2000);
                        } else {
                            window.alert.show("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau", 2000);
                        }
                    }

                })
            },
            resetPopup() {
                pupilId = "";
                this.pupilName = "";
                this.tuitionTotal = "";
                this.tuitionRemain = "";
                this.tuitionAdd = 0;

                this.isShowErrorRequireTuitionAdd = false;
                this.isShowErrorTuitionAdd = false;
            }
        },
        mounted() {
            let self = this;

            $('#modal_update_tuition').on('hidden.bs.modal', function () {
                self.resetPopup();
            })
        }
    })

})