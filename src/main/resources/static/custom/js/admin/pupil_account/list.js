let listAccountTable;
$(document).ready(function (){
    let getAccount = function (requestData, renderFunction, link_api) {
        let sortField = columnDefinitions[requestData.order[0].column].sort;
        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": sortField,
            "sortDir": sortDir,
            "search": $("#search_account").val(),
            "grade": filterVue.grade == "0" ? "All" : filterVue.grade,
            "classId": filterVue.classId,
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
        {"data": "code", "orderable": false, "sort": "code", "defaultContent": "", "class": 'text-center'},
        {"data": "name", "orderable": true,"sort": "name",  "defaultContent": "", "class": 'text-center'},
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "className", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "email", "orderable": true,"sort": "email",  "defaultContent": "", "class": 'text-center'},
        {"data": "phone", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "gender", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "address", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "fatherName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "motherName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center',}
    ];
    listAccountTable = $("#account_table").DataTable({
        "language": {
            "url": "/libs/new_data_table/js/vie.json"
        },
        "lengthMenu": [
            [50, 100, 200],
            [50, 100, 200]
        ],

        "searching": false,
        rowId: 'id',
        "ordering": true,
        "order": [[1, "asc"]],
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getAccount(data, callback, "/api/pupil_account/getPage");
        },
        columnDefs: [
            {
                "render": function (gender) {
                    if (gender === 1) {
                        return "Nam";
                    } else if (gender === 2) {
                        return "Nữ";
                    } else {
                        return "Khác";
                    }
                },
                "targets": 6
            },
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_pupil_account" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-pupil">Chi tiết</button>
                            <button style="margin-left: 10px" data-toggle="modal" id="btn_delete_${data.id}" data-target="#modal_delete_pupil" class="btn btn-sm btn-danger delete-pupil">Xóa</button>`;
                },
                "targets": 10
            },
        ]
    });
    $(document).on('click', "#btn_search_account", function () {
        listAccountTable.ajax.reload();
    })

    listAccountTable.on('draw', function () {
        $("#account_table_length").parent().addClass('col-md-2')
    })

    $(document).on('click', ".delete-pupil", function () {
        popupPupilAccountVue.id = Number($(this).attr('id').replace('btn_delete_', ''));
    });

    $(document).on('click', "#btn_submit_delete", function () {
        if (!popupPupilAccountVue.id) {
            window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", "2000");
            return;
        }
        popupPupilAccountVue.delete();
    })
})