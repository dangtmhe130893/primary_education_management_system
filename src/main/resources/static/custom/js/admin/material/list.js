let listMaterialTable;
$(document).ready(function () {
    let deleteId;
    let getAccount = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "createdTime",
            "sortDir": sortDir,
            "search": $("#search_account").val(),
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

    let columnDefinitions = [
        {"data": "code", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "subjectName",  "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "grade",  "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "content",  "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null,  "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "createdTime", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": "id", "orderable": false,  "defaultContent": "", "class": 'text-center'}
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
        "order": [2, "desc"],
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getAccount(data, callback, "/api/material/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_material" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-material">Chi tiết</button>
                            <button style="margin-left: 10px" data-toggle="modal" id="btn_delete_${data.id}" data-target="#modal_delete_material" class="btn btn-sm btn-danger delete-material">Xóa</button>`;
                },
                "targets": 7
            },
        ]
    });
    $(document).on('click', "#btn_search_account", function () {
        listMaterialTable.ajax.reload();
    })

    listMaterialTable.on('draw', function () {
        $("#account_table_length").parent().addClass('col-md-2')
    })
    $(document).on('click', ".delelte-acount", function () {
        deleteId = null;
        deleteId = $(this).attr('data-id');
    });

    $(document).on('click', "#btn_submit_delete", function () {
        if (!deleteId) {
            window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", "2000");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/api/user/delete?userId=" + deleteId,
            error: function (xhr, ajaxOptions, thrownError) {
            },
            success: function (response) {
                if (response.status.code === 1000) {
                    $("#modal_delete_account").modal("hide");
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