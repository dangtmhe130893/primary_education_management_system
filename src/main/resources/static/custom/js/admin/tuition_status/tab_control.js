$(document).ready(function () {
    $(document).on("click", ".btn-tap", function () {
        $(".btn-tap").each(function () {
            $(this).removeClass("active");
        })
        $(this).addClass("active");


        $(".div_tuition_status").each(function () {
            $(this).addClass("hidden");
        })
        let tab = $(this).attr("id").replace("btn_show_", "");
        $("#div_tuition_" + tab).removeClass("hidden");
    });


    $(document).on("click", ".btn-sub-tap", function () {
        $(".btn-sub-tap").each(function () {
            $(this).removeClass("active");
        })
        $(this).addClass("active");


        $(".div_revenue").each(function () {
            $(this).addClass("hidden");
        })
        let tab = $(this).attr("id").replace("btn_show_", "");
        $("#div_show_" + tab).removeClass("hidden");
    });
})