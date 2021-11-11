var fileSize3MB = 3145728;
var fileSize5MB = 5242880;
var fileSize30MB = 31457280;

function initCKEditor(element) {
    CKEDITOR.replace(element, {
        language: 'ja',
        height: 200,
        removePlugins: 'elementspath'
    })
    CKEDITOR.config.toolbar = [
        ['Styles', 'Format'],
        ['Bold', 'Italic', 'Underline', 'StrikeThrough', '-', 'Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'Find', 'Replace', '-', 'Outdent', 'Indent', '-', 'Print'],
        ['NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
        ['Image', 'Table', '-', 'Link', 'Flash', 'Smiley', 'TextColor', 'BGColor', 'Source']
    ];
}

function createSortOrderDatatable(){
    let fixHelperModified = function (e, tr) {
        let $originals = tr.children();
        let $helper = tr.clone();
        $helper.children().each(function (index) {
            $(this).width($originals.eq(index).width())
        });
        return $helper;
    }, updateIndex = function (e, ui) {
        $('td.index', ui.item.parent()).each(function (i) {
            $(this).html(i + 1);
        });
    };

    $("tbody").sortable({
        helper: fixHelperModified,
        stop: updateIndex,
    }).disableSelection();
}

$.validator.addMethod('notEmpty', function (value) {
    return value !== null && value !== undefined && value.trim() !== "";
}, 'このフィールドは必須です。');

$.validator.addMethod("phoneNumber", function (value, element, arg) {
    var phoneNumber = value.trim();
    for (var i = 0; i < phoneNumber.length; i++) {
        if (!$.isNumeric(phoneNumber[i]) && phoneNumber[i] != ")" && phoneNumber[i] != "(" && phoneNumber[i] != " " && phoneNumber[i] != "-")
            return false;
    }
    return true;
}, "無効な電話番号");
$.validator.addMethod("postalCode", function (value, element, arg) {
    var postalCode = value.trim();
    if (/^(?:[0-9]{3}-[0-9]{4})$/.test(postalCode)) {
        return true;
    }
    return false;
}, "郵便番号のフォマートは XXX-XXXX　です。");
$.validator.addMethod("valueNotGreaterThan", function (value, element, arg) {
    return value < arg.val();
}, "営業開始時間は営業終了時間より早くしてください。");

$.validator.addMethod("dateValidate", function (value, element, arg) {
    return value < moment(arg.val()).format("YYYY/MM/DD HH:mm");
}, "開始時間は終了時間 より早くしてください。");

$.validator.addMethod("ckeditorRequired", function (value, element, arg) {
    return !CKEDITOR.instances[arg].getData() == '';
}, "このフィールドは必須です。");

//Bỏ validate lengthMax
$.validator.addMethod("ckeditorMaxLength", function (value, element, arg) {
    return CKEDITOR.instances[arg].getData().length <= 10000;
}, "10000 文字以内で入力してください。");

$.validator.addMethod("storeRequired", function (value, element, arg) {
    for (var i = 0; i < arg.length; i++) {
        if (arg[i].status === false)
            return false;
    }
    return true;
}, "");

$.validator.addMethod("catalogRequired", function (value, element, arg) {
    for (var i = 0; i < arg.length; i++) {
        if (arg[i].valid === false)
            return false;
    }
    return true;
}, "");

$.validator.addMethod("youtubeLink", function (value, element, arg) {

    if (value != undefined || value != '') {
        var regExp = /(https:\/\/www.youtube.com\/watch\?v=)([^#\&\?]*).*/;
        var match = value.match(regExp);
        if (match) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }

}, "Youtubeリンクを入力してください。");

$.validator.addMethod("linkIncludeHTTP", function (value, element, arg) {
    if (value != undefined || value != '') {
        var regExp = /((http|https):\/\/)([^#\&\?]*).*/;
        var match = value.match(regExp);
        if (match) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}, "HTTP/HTTPSを含むリンクを入力してください。");


function validateUrl(el) {
    var url = $(el).val();

    if (url != undefined || url != '') {
        var regExp = /((http|https):\/\/)([^#\&\?]*).*/;
        var match = url.match(regExp);
        if (match) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

function validateInput(el) {
    var name = $(el).val();
    if (name != '') {
        return true;
    } else {

        return false;
    }
}

function validateLengthURL(el) {
    var name = $(el).val();
    if (name.length <= 255) {
        return true;
    } else {
        return false;
    }
}


function validateLengthInputImage(el) {
    var name = $(el).val();
    if (name.length <= 100) {
        return true;
    } else {
        return false;
    }
}

$(document).ready(function () {

    $(document).on('focus', '.form-control', function () {
        $(this).attr('autocomplete', 'off');
    });

    $("#hidden_menu").hide();

    $(".control-menu").click(function () {
        $("#hidden_menu").toggle();
    });

    $(document).mouseup(function (e) {
        var container = $(".control-menu");
        if (!container.is(e.target) && container.has(e.target).length === 0) {
            $("#hidden_menu").hide();
        }
    });

    window.alert = {
        show: function (type, message, interval) {
            if (type === "error") {
                $("#alert").removeClass("alert-success");
                $("#alert").addClass("alert-danger");
                $("#alert .error-icon").removeClass("hidden");
                $("#alert .success-icon").addClass("hidden");
            } else if (type === "success") {
                $("#alert").removeClass("alert-danger");
                $("#alert").addClass("alert-success");
                $("#alert .error-icon").addClass("hidden");
                $("#alert .success-icon").removeClass("hidden");
            }
            $("#alert").removeClass("hidden");
            $("#alert_message").html(message);
            $("#alert").animate({
                top: "50px"
            }, "slow");

            setTimeout(function () {
                $("#alert").animate({
                    top: "-200px"
                }, "slow");
            }, interval);
        }
    };

    window.loader = {
        show: function () {
            $("#loader").removeClass("hidden");
        },
        hide: function () {
            $("#loader").addClass("hidden");
        }

    }

    window.ProgressBar = function () {
        this.currentWidth = 1;
        this.show = function () {
            $("#progress_bar_container").removeClass("hidden");
            document.getElementById("progress_status").style.width = "2" + '%';
        },
            this.increaseProgress = function (toWidth) {
                var element = document.getElementById("progress_status");
                var id = setInterval(frame, 10);
                var self = this;

                function frame() {
                    if (self.currentWidth >= toWidth || self.currentWidth >= 100) {
                        clearInterval(id);
                    } else {
                        self.currentWidth++;
                        element.style.width = self.currentWidth + '%';
                    }
                }
            },
            this.hide = function () {
                $("#progress_bar_container").addClass("hidden");
            }
    };

});
