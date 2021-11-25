$(document).ready(function () {
    let grade = $("#grade").val();
    let classId = $("#class_id").val();
    let subjectId = $("#subject_id").val();


    let columnDefinitions = [
        {"data": "name", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "gender", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "phone", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
    ];

    let getPagePupil = function (requestData, renderFunction, link_api) {

        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "id",
            "sortDir": "asc",
            "search": "",
            "grade": grade,
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

    let teachClassTable = $("#teach_class_table").DataTable({
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
            getPagePupil(data, callback, "/api/pupil_account/getPage");
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
                "targets": 1
            },
        ]
    });
})