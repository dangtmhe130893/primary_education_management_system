var configStartDateForCompanyAdmin = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        timePickerSeconds: true,
        singleDatePicker: true,
        autoApply: true,
        startDate: moment().set({hour: 0, minute: 0, second: 0}).subtract(30, 'days'),
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD HH:mm:ss',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    }).on('hide.daterangepicker', function (ev, picker) {
        picker.startDate.set({hour: 0, minute: 0, second: 0})
    });
};

var configEndDateForCompanyAdmin = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        timePickerSeconds: true,
        singleDatePicker: true,
        autoApply: true,
        startDate: moment().set({hour: 23, minute: 59, second: 59}).add(0, 'hours'),
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD HH:mm:ss',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    }).on('hide.daterangepicker', function (ev, picker) {
        picker.startDate.set({hour: 23, minute: 59, second: 59})
    });
};


var configOneDate = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        singleDatePicker: true,
        autoApply: true,
        showDropdowns: true,
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD HH:mm',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    });
};

var configStartDateSlider = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        singleDatePicker: true,
        autoApply: true,
        showDropdowns: true,
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD HH:mm',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    });
};

var configEndDateSlider = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        singleDatePicker: true,
        autoApply: true,
        showDropdowns: true,
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD HH:mm',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    });
};

var configOneDateNotTime = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: false,
        singleDatePicker: true,
        autoApply: true,
        "timePicker24Hour": true,
        locale: {
            format: 'YYYY/MM/DD',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    });
};

var configDateRangerPickerNotMinDate = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        singleDatePicker: true,
        autoApply: true,
        timePicker24Hour: true,
        showDropdowns: true,
        locale: {
            format: 'YYYY/MM/DD HH:mm',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    })
};

var validateTimeOnHideDatePicker = function (idStartTimeElement, idEndTimeElement, callbackValidateFunction) {
    $("#" + idStartTimeElement + ",#" + idEndTimeElement).on('hide.daterangepicker', function () {
        callbackValidateFunction();
    });
};

var setStartDateForDatePicker = function (idElement) {
    var el = $("#" + idElement);
    var time = el.val();
    if (!time.includes("無期限")) {
        el.data('daterangepicker').setStartDate(el.val());
    }
};

var configDateRangerPickerWithoutDate = function (idElement) {
    $("#" + idElement).daterangepicker({
        timePicker: true,
        singleDatePicker:true,
        timePicker24Hour: true,
        timePickerIncrement: 1,
        timePickerSeconds: false,
        pick12HourFormat: true,
        locale: {
            format: 'HH:mm',
            cancelLabel: 'キャンセル',
            applyLabel: 'OK'
        }
    }).on('show.daterangepicker', function (ev, picker) {
        picker.container.find(".calendar-table").hide();
    });
};