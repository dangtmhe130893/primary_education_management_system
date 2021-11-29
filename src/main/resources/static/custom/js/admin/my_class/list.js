let listAccountTable;
$(document).ready(function (){
    let classId = $("#class_id").val();

    let getAccount = function (requestData, renderFunction, link_api) {

        if (!classId) {
            let content = {
                "draw": requestData.draw,
                "recordsTotal": 0,
                "recordsFiltered": 0,
                "data": [],
            };
            renderFunction(content);
            return;
        }
        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "createdTime",
            "sortDir": sortDir,
            "search": $("#input-search_pupil").val(),
            "grade": "All",
            "classId": classId,
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
        {"data": "name", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "className", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "email", "orderable": true, "defaultContent": "", "class": 'text-center'},
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
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_pupil_account" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-pupil">Chi tiết</button>`;
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
        popupDetailPupilAccountVue.id = Number($(this).attr('id').replace('btn_delete_', ''));
    });

    $(document).on('click', "#btn_submit_delete", function () {
        if (!popupDetailPupilAccountVue.id) {
            window.alert.show("error", "Đã có lỗi xảy ra, vui lòng thử lại", "2000");
            return;
        }
        popupDetailPupilAccountVue.delete();
    })
})