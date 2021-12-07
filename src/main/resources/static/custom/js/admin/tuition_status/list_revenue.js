let pageTuitionAllSchool;
let pageTuitionGroupByGrade;
let pageTuitionGroupByClass;
let pageTuitionAllPupil;

$(document).ready(function () {
    let columnDefinitionsAllSchool = [
        {"data": "numberPupil", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionRequire", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionReceived", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "doneTuition", "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let columnDefinitionsGroupByGrade = [
        {"data": "grade", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "numberPupil", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionRequire", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionReceived", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "doneTuition", "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let columnDefinitionsGroupByClass = [
        {"data": "className", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "numberPupil", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionRequire", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionReceived", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "doneTuition", "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let columnDefinitionsAllPupil = [
        {"data": "pupilName", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionRequire", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "tuitionReceived", "orderable": false, "defaultContent": "", "class": 'text-center'},
        {"data": "doneTuition", "orderable": false, "defaultContent": "", "class": 'text-center'}
    ];

    let getPageRevenueTuition = function (requestData, renderFunction, link_api) {
        let params = {
            "page": (requestData.start / requestData.length) + 1,
            "size": requestData.length,
            "sortField": "numberPupil",
            "sortDir": "desc",
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

    pageTuitionAllSchool = $("#tuition_all_school_table").DataTable({
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
        "columns": columnDefinitionsAllSchool,
        "ajax": function (data, callback) {
            getPageRevenueTuition(data, callback, "/api/history_pay_tuition/getPage?type=all_school");
        },
        columnDefs: [
            {
                "render": function (status) {
                    if (status) {
                        return '<button disabled class="btn btn-sm btn-success " >Đã thu đủ</button>';
                    } else {
                        return '<button disabled class="btn btn-sm btn-secondary" >Chưa thu đủ</button>';
                    }
                },
                "targets": 3
            },
        ]
    });

    pageTuitionGroupByGrade = $("#tuition_group_by_grade").DataTable({
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
        "columns": columnDefinitionsGroupByGrade,
        "ajax": function (data, callback) {
            getPageRevenueTuition(data, callback, "/api/history_pay_tuition/getPage?type=group_by_grade");
        },
        columnDefs: [
            {
                "render": function (status) {
                    if (status) {
                        return '<button disabled class="btn btn-sm btn-success " >Đã thu đủ</button>';
                    } else {
                        return '<button disabled class="btn btn-sm btn-secondary" >Chưa thu đủ</button>';
                    }
                },
                "targets": 4
            },
        ]
    });

    pageTuitionGroupByClass = $("#tuition_group_by_class").DataTable({
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
        "columns": columnDefinitionsGroupByClass,
        "ajax": function (data, callback) {
            getPageRevenueTuition(data, callback, "/api/history_pay_tuition/getPage?type=group_by_class");
        },
        columnDefs: [
            {
                "render": function (status) {
                    if (status) {
                        return '<button disabled class="btn btn-sm btn-success " >Đã thu đủ</button>';
                    } else {
                        return '<button disabled class="btn btn-sm btn-secondary" >Chưa thu đủ</button>';
                    }
                },
                "targets": 4
            },
        ]
    });

    pageTuitionAllPupil = $("#tuition_all_pupil").DataTable({
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
        "columns": columnDefinitionsAllPupil,
        "ajax": function (data, callback) {
            if (!selectClassVue.classId) {
                let content = {
                    "draw": data.draw,
                    "recordsTotal": 0,
                    "recordsFiltered": 0,
                    "data": []
                };
                callback(content);
            } else {
                getPageRevenueTuition(data, callback, "/api/history_pay_tuition/getPage?type=all_pupil&classId=" + selectClassVue.classId);
            }
        },
        columnDefs: [
            {
                "render": function (status) {
                    if (status) {
                        return '<button disabled class="btn btn-sm btn-success " >Đã thu đủ</button>';
                    } else {
                        return '<button disabled class="btn btn-sm btn-secondary" >Chưa thu đủ</button>';
                    }
                },
                "targets": 3
            },
        ]
    });

    let selectClassVue = new Vue({
        el: "#div-select-class",
        data: {
            listClass: [],
            classId: "",
        },
        watch: {
            classId: function (classId){
                if (!classId) {
                    return;
                }
                pageTuitionAllPupil.ajax.reload();
            }
        },
        methods: {
            getListClass() {
                let self = this;

                $.ajax({
                    type: "GET",
                    url: "/api/class/getList",
                    success: function (response) {
                        self.listClass = response.data;
                    }
                })
            }
        },
        mounted() {
            let self = this;
            self.getListClass();
        }
    })

})