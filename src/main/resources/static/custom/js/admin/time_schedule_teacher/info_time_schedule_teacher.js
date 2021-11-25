$(document).ready(function (){

    let columnDefinitions = [
        {"data": null, "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "numberPupil", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "nameRoom", "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageInfoTeachClass = function (requestData, renderFunction, link_api) {

        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "classId",
            "sortDir": "asc",
        };
        window.loader.show();
        jQuery.get(link_api, params, function (response) {
            let content = {
                "draw": requestData.draw,
                "recordsTotal": response.totalElements,
                "recordsFiltered": response.totalElements,
                "data": response.content
            };
            console.log(content);
            renderFunction(content);
            window.loader.hide();
        });
    };


    let tableInfoTimeScheduleTeacher = $("#info-time-schedule-teacher-table").DataTable({
        "language": {
            "url": "/libs/new_data_table/js/vie.json"
        },
        "lengthMenu": [
            [5, 10, 20],
            [5, 10, 20]
        ],

        "searching": false,
        "ordering": true,
        "pagingType": "full_numbers",
        "serverSide": true,
        "columns": columnDefinitions,
        "ajax": function (data, callback) {
            getPageInfoTeachClass(data, callback, "/api/timeSchedule/getPageInfoTimeScheduleTeacher");
        },
        columnDefs: [
            {
                "render": function (data) {
                    return `<a href="/admin/teach_class/${data.seoNameClass}">${data.nameClass}</a>`
                },
                "targets": 0
            },

        ],

    });
})