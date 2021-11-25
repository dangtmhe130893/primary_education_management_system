$(document).ready(function () {
    let grade = $("#grade").val();
    let subjectId = $("#subject_id").val();


    let columnDefinitions = [
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "type", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
    ];

    let getPageMaterial = function (requestData, renderFunction, link_api) {

        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "id",
            "sortDir": "asc",
            "search": "",
            "type": "",
            "grade": grade,
            "subjectId": subjectId,
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

    let materialTable = $("#material_table").DataTable({
        "language": {
            "url": "/libs/new_data_table/js/vie.json"
        },
        "lengthMenu": [
            [20, 50, 100],
            [20, 50, 100]
        ],

        "searching": false,
        "ordering": true,
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getPageMaterial(data, callback, "/api/material/getPage");
        },
        columnDefs: [
            {
                "render": function (data) {
                    return `<p class="btn-show-content" style="color: blue; text-decoration: underline; cursor: pointer" data-name="${data.name}" data-content="${data.content}">Hiển thị</p>`;
                },
                "targets": 2
            },
            {
                "render": function (data) {
                    return '<a class="download_material" target="_blank" href="' + "/api/material/download/" + data.code  + '" >Tải xuống</a>'
                },
                "targets": 3,
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
})