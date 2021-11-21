let tableClass;

$(document).ready(function (){
    let columnDefinitions = [
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "room", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "createdTime", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageClass = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "createdTime",
            "sortDir": sortDir,
            "keyword": $("#search_class_id").val(),
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

    tableClass = $("#class_table").DataTable({
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
        "order": [3, "desc"],
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getPageClass(data, callback, "/api/class/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_class" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-class">Chi tiết</button>
                            <button style="margin-left: 10px" data-toggle="modal" id="btn_delete_${data.id}" data-target="#modal_delete_class" class="btn btn-sm btn-danger delete-class">Xóa</button>`;
                },
                "targets": 4
            },
        ]
    });
})