$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();

    //scroll item active
    if($(".active-menu").length > 0){
        $('.left-menu').animate({
            scrollTop: $(".active-menu").offset().top
        }, 1000);
    }

    $(".control-block-menu").on('click', function () {
        var parentClass = $(this).parent().attr('class').replace("block-menu ", "");
        var icon = $(this).find('.block-icon');
        if (icon.hasClass("fa-angle-double-down")) {
            icon.removeClass("fa-angle-double-down");
            icon.addClass("fa-angle-double-right");
            $("." + parentClass + " .menu").addClass("d-none");
            $(".block-icon-sub-menu").addClass("d-none")
        } else {
            icon.addClass("fa-angle-double-down");
            icon.removeClass("fa-angle-double-right");
            $("." + parentClass + " .menu").removeClass("d-none");
            $(".block-icon-sub-menu").removeClass("d-none")
        }
    });

    $(".has-sub-menu").on('click', function () {
        var icon = $(this).find('.block-icon-sub-menu');
        var parentClass = $(this).parent().attr('class');
        if (icon.hasClass("fa-angle-double-down")) {
            icon.removeClass("fa-angle-double-down");
            icon.addClass("fa-angle-double-right");
            $("." + parentClass + " .sub-menu").addClass("d-none");
        } else {
            showItem();
            toggle = !toggle;
            icon.addClass("fa-angle-double-down");
            icon.removeClass("fa-angle-double-right");
            $("." + parentClass + " .sub-menu").removeClass("d-none");
        }
    });
    var toggle = true;
    var movePageTitleToLeft = function () {
        $(".header-page-title").animate({left: "75px"}, 20)
    };

    var movePageTitleToRight = function () {
        $(".header-page-title").animate({left: "235px"}, 20);
    }

    toogleLeftMenu = function () {
        if (toggle) {
            hideItem();
            movePageTitleToLeft();
        } else {
            showItem();
            movePageTitleToRight();
        }
        toggle = !toggle;
    };

    $("#icon_menu").click(function () {
        toogleLeftMenu();
    });


//    $("#icon_menu").hover(function () {
//        if ($(this).hasClass("icon-hide")) {
//            setTimeout(function () {
//                $("#icon_menu .fa-bars").addClass("d-none");
//                $("#icon_menu .fa-arrow-right").removeClass("d-none");
//            }, 200);
//        } else {
//            setTimeout(function () {
//                $("#icon_menu .fa-bars").addClass("d-none");
//                $("#icon_menu .fa-arrow-left").removeClass("d-none");
//            }, 200);
//        }
//    }, function () {
//        $("#icon_menu .fa-bars").removeClass("d-none");
//        $("#icon_menu .fa-arrow-left").addClass("d-none");
//        $("#icon_menu .fa-arrow-right").addClass("d-none");
//
//    });

    var hideItem = function () {
    	    $("#icon_menu .fa-arrow-left").addClass("d-none");
	        $("#icon_menu .fa-arrow-right").removeClass("d-none");
            $(".left-menu").addClass('left-menu-not-active');
            $(".header-left-menu .text-header-left-menu").addClass("d-none");
            $(".block-menu span").addClass('d-none');
            $(".block-icon, .block-icon-sub-menu").addClass("d-none");
            $(".menu").addClass("menu-mini");
            $("#icon_menu").addClass('icon-hide');
            //header
            $(".header-menu").removeClass("header-mini");
            //content
            $(".page-content").addClass("page-content-big");
    }

    var showItem = function () {
    	$("#icon_menu .fa-arrow-left").removeClass("d-none");
        $("#icon_menu .fa-arrow-right").addClass("d-none");
        $(".left-menu").removeClass('left-menu-not-active');
        $(".header-left-menu .text-header-left-menu").removeClass("d-none");
        $(".block-menu span").removeClass('d-none');
        $(".block-icon, .block-icon-sub-menu").removeClass("d-none");
        $(".menu").removeClass("menu-mini");
        $("#icon_menu").removeClass('icon-hide');
        //header
        $(".header-menu").addClass("header-mini");
        //content
        $(".page-content").removeClass("page-content-big");
    }


})