let tableTuition;

$(document).ready(function (){
    let columnDefinitions = [
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "fee", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "updatedTime", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageClass = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "createdTime",
            "sortDir": sortDir
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

    tableClass = $("#tuition_table").DataTable({
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
            getPageClass(data, callback, "/api/tuition/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    return `<button type="button" data-toggle="modal" data-target="#modal_add_tuition" id="btn_detail_${data.id}" class="btn btn-sm btn-primary detail-tuition">Chi tiết</button>`;
                },
                "targets": 3
            },
        ]
    });
})