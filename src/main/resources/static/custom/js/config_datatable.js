var configDataTableServerSide = (function () {
    var renderTable = function (config) {
        var inputId = config.element;
        var url = config.url;
        var columnDefinitions = config.columnDefinitions;
        var columnDefs = config.columnDefs;
        var fnRowCallback = config.fnRowCallback;
        var createdRow = config.createdRow;
        var columnOrder = config.columnOrder;
        var typeOrder;
        if (config.typeOrder != null) {
            typeOrder = config.typeOrder;
        } else {
            typeOrder = "desc";
        }
        var drawCallback = config.drawCallback;
        var table = $(inputId).DataTable({
            "language": {
                "url": "/libs/new_data_table/js/ja.json"
            },
            "lengthMenu": [
                [50, 100, 200],
                [50, 100, 200]
            ],
            rowId: 'id',
            "ordering": true,
            "serverSide": true,
            "order": [columnOrder, typeOrder],
            "searching": false,
            "columns": columnDefinitions,
            "ajax": function (data, callback) {
                getData(data, callback, url, columnDefinitions);
            },
            columnDefs: columnDefs,
            fnRowCallback: fnRowCallback,
            createdRow: createdRow,
            drawCallback: drawCallback
        });
        return table;
    }
    return function (config) {
        return renderTable(config);
    }
})();


var getData = function (requestData, renderFunction, url, columnDefinitions) {
    var sortField = columnDefinitions[requestData.order[0].column].data;
    var sortDir = requestData.order[0].dir;
    var params = {
        "page": (requestData.start / requestData.length) + 1,
        "size": requestData.length,
        "sortField": sortField,
        "sortDir": sortDir
    };
    jQuery.get(url, params, function (response) {
        var content = {
            "draw": requestData.draw,
            "recordsTotal": response.totalElements,
            "recordsFiltered": response.totalElements,
            "data": response.content
        };
        renderFunction(content);
    });
};