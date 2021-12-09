$(document).ready(function () {
    let classId = Number($("#class_id").val());

    let getAccount = function (requestData, renderFunction, link_api) {

        let sortDir = requestData.order[0].dir;
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "created_time",
            "sortDir": sortDir,
            "grade": "",
            "classId": classId,
            "type": "",
            "subjectId": 0,
            "search": $("#search_pupil_material").val(),
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
        {"data": "subjectName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "type", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "creator", "orderable": true, "defaultContent": "", "class": 'text-center'},
        {"data": "createdTime", "orderable": true, "defaultContent": "", "class": 'text-center'},
    ];
    let listPupilMaterialTable = $("#material_table").DataTable({
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
                    return `<p class="btn-show-content" style="color: blue; text-decoration: underline; cursor: pointer" data-name="${data.name}" data-content="${data.content}">Hiển thị</p>`;
                },
                "targets": 4
            },
            {
                "render": function (data) {
                    return '<a class="download_material" target="_blank" href="' + "/api/material/download/" + data.code + '" >Tải xuống</a>'
                },
                "targets": 5,
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

    $(document).on('click', "#btn_search_pupil_material", function () {
        listPupilMaterialTable.ajax.reload();
    })

    $("#search_pupil_material").bind('keypress', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            listPupilMaterialTable.ajax.reload();
        }
    });
})