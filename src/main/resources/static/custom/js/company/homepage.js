$(document).ready(function () {
    var companyId = $("#company_id").val();
    var dateFromGlobal = new Date();
    var dateToGlobal = new Date();

    //append list app
    jQuery.get("/api/v1/web/company/" + companyId + "/listApp", function (response) {
        response.forEach(function (mobileApp) {
            var html = '<option class="list-option-function" id="' + mobileApp.id + '" value="' + mobileApp.id + '">' + mobileApp.name + '</option>';
            $("#select_app").append(html);
        })
    });

    $("#rb-all").on('click', function () {
        $('#i-pickdate').click();
    })
    $('#i-pickdate').daterangepicker({
            startDate: moment().subtract(120, 'days'),
            endDate: moment(),
            autoUpdateInput: false,
            locale: {
                "customRangeLabel": "Text",
                format: 'YYYY/MM/DD',
                "separator": " - ",
                "applyLabel": "適用",
                "cancelLabel": "キャンセル",
                "fromLabel": "から",
                "toLabel": "まで",
                "customRangeLabel": "オプション",
                "daysOfWeek": [
                    '日', '月', '火', '水', '木', '金', '土'
                ],
                "monthNames": ['1月', '2月', '3月', '4月', '5月', '6月',
                    '7月', '8月', '9月', '10月', '11月', '12月'],
                "firstDay": 1
            },

            ranges: {
                '今日': [moment(), moment()],
                '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '7 日前': [moment().subtract(6, 'days'), moment()],
                '30 日前': [moment().subtract(29, 'days'), moment()],
                '今月': [moment().startOf('month'), moment().endOf('month')],
                '先月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            }
        },
        function (start, end) {
            dateFromGlobal = start.format('YYYY/MM/DD');
            dateToGlobal = end.format('YYYY/MM/DD');
            var appId = $("#select_app option:selected").val();
            $("#i-pickdate").val(dateFromGlobal +' - '+dateToGlobal);
            var params = {
                "dateFrom": dateFromGlobal,
                "dateTo": dateToGlobal,
                "appId": appId,
            };
            getStatisticalData(params);
        });

    $("#select_app").change(function () {
        var appId = $(this).val();
        var str = dateFromGlobal.toString();
        if (str.includes("GMT")) {
            var dateFrom = moment().startOf('month').format('YYYY/MM/DD');
            var dateTo = moment().endOf('month').format('YYYY/MM/DD');
            var params = {
                "dateFrom": dateFrom,
                "dateTo": dateTo,
                "appId": appId,
            };

            getStatisticalData(params);
        } else {
            var params = {
                "dateFrom": dateFromGlobal,
                "dateTo": dateToGlobal,
                "appId": appId,
            };

            getStatisticalData(params);
        }
    });

    $('input[name="datefilter"]').on('cancel.daterangepicker', function () {
        $(this).val('');
    });


//start highchart	
    var chart = {
        type: 'column'
    };
    var title = {
        text: null
    };
    var xAxis = {
        name: 'amount',
        categories: [],
        dataLabels: {
            enabled: true,
            rotation: -90,
            color: '#FFFFFF',
            align: 'right',
            format: '{point.y}',
            y: 10, // 10 pixels down from the top
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            }
        }
    };
    var yAxis = {
        min: 0,
        title: {
            text: null,
        }
    };
    var tooltip = {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y}</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    }
    var series = [{
        name: '会員数',
        data: []

    },
        {
            name: 'ダウンロード数',
            data: []
        }];

//api get data	
    function getStatisticalData(params) {

        var dates = [];
        var countDownloadedAppDatas = [];
        var countUserAppDatas = [];

        jQuery.get("/api/v1/app/" + companyId + "/reportForHomePage", params, function (response) {
            var data = response.data;
            $.each(data, function (index, value) {
                var date = value.date;
                dates.push(date);
                var reportDtos = value.reportDtos;
                countUserAppDatas.push(reportDtos[0].amount);
                countDownloadedAppDatas.push(reportDtos[1].amount);
            });

            xAxis.categories = dates;
            series[1].data = countDownloadedAppDatas;
            series[0].data = countUserAppDatas;
            respondCanvas();
        })
    }


    function respondCanvas() {
        var statisticalChart = Highcharts.chart('container_chart', {
            chart: chart,
            title: title,
            xAxis: xAxis,
            yAxis: yAxis,
            tooltip: tooltip,
            series: series
        })
    }

    //load page
    var startOfMonth = moment().startOf('month').format('YYYY/MM/DD');
    var endOfMonth = moment().endOf('month').format('YYYY/MM/DD');
    var appId = $("#select_app option:selected").val();
    var params = {
        "dateFrom": startOfMonth,
        "dateTo": endOfMonth,
        "appId": appId,
    };
    getStatisticalData(params);
})