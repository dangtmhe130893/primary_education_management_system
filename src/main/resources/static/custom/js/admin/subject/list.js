let tableSubject;

$(document).ready(function () {
    let columnDefinitions = [
        {"data": "name", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageSubject = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "name",
            "sortDir": sortDir,
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

    tableSubject = $("#subject_table").DataTable({
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
})