let listMaterialTable;
let filterVue;
$(document).ready(function () {
    let deleteId;

    filterVue = new Vue({
        el: "#filter_material",
        data: {
            grade: '',
            classId: '0',
            listClass: [],
            type: '',
            subjectId: 0,
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
                listMaterialTable.ajax.reload();
                this.loadListClass();
            },
            type() {
                listMaterialTable.ajax.reload();
            },
            subjectId() {
                listMaterialTable.ajax.reload();
            }
        },
        mounted() {
            let self = this;
            self.loadListClass();

            if ($("#select_subject").val() == null) {
                self.subjectId = $("#select_subject option:first").val();
            }
        }
    })

    let getAccount = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "created_time",
            "sortDir": sortDir,
            "grade": filterVue.grade,
            "classId": filterVue.classId,
            "type": filterVue.type,
            "subjectId": filterVue.subjectId,
            "search": $("#search_material").val(),
        };
        window.loader.show();
        jQuery.get(link_api, params, function (response) {
            let content = {
                "draw": requestData.draw,
                "recordsTotal": response.totalElements,
                "recordsFiltered": response.totalElements,
                "data": response.content
            };
            console.log(response.content);
            renderFunction(content);
            window.loader.hide();
        });
    };

    let columnDefinitions = [
        {"data": "code", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "subjectName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "type", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "creator", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": "createdTime", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];
    listMaterialTable = $("#material_table").DataTable({
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
        "order": [7, "desc"],
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getAccount(data, callback, "/api/material/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    let result = `<ul>`;
                    data.listNameClass.forEach(nameClass => {
                        result += `<li>${nameClass}</li>`
                    })
                    result += `</ul>`;

                    return result;
                },
                "targets": 4
            },
            {
                "render": function (data) {
                    return `<p class="btn-show-content" style="color: blue; text-decoration: underline; cursor: pointer" data-name="${data.name}" data-content="${data.content}">Hiển thị</p>`;
                },
                "targets": 6
            },
            {
                "render": function (data) {
                    return '<a class="download_material" target="_blank" href="' + "/api/material/download/" + data.code  + '" >Tải xuống</a>'
                },
                "targets": 7,
            },
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_material" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-material">Chi tiết</button>
                            <button style="margin-left: 10px" data-toggle="modal" data-id="${data.id}" data-target="#modal_delete_material" class="btn btn-sm btn-danger delete-material">Xóa</button>`;
                },
                "targets": 10
            },
        ],
        "drawCallback": function () {
            $(".btn-show-content").click(function () {
                $("#material_name").text($(this).attr('data-name'));
                $("#textarea_material_content").append($(this).attr('data-content'))
                $("#modal_material_content").modal('toggle');
            })

        },
    });
    $("#modal_material_content").on("hidden.bs.modal", function () {
        $("#textarea_material_content").empty();
    })

    $(document).on('click', "#btn_search_account", function () {
        listMaterialTable.ajax.reload();
    })

    listMaterialTable.on('draw', function () {
        $("#account_table_length").parent().addClass('col-md-2')
    })
    $(document).on('click', ".delete-material", function () {
        deleteId = null;
        deleteId = $(this).attr('data-id');
        console.log(deleteId)
    });
    $(document).on('click', "#btn_submit_delete", function () {
        if (!deleteId) {
            window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", "2000");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/api/material/delete/" + deleteId,
            error: function (xhr, ajaxOptions, thrownError) {
            },
            success: function (response) {
                if (response.status.code === 1000) {
                    $("#modal_delete_material").modal("hide");
                    listMaterialTable.ajax.reload();
                    deleteId = null;
                    window.alert.show("success", "Thành công", "2000");
                } else {
                    window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", "2000");
                }
            }
        })
    })

})