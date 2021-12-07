$(document).ready(function () {
    window.loader.show();
    jQuery.get("/api/pupil/tuition", "", function (response) {
        if (response.status.code === 1000) {
            let data = response.data;
            $("#total").text(data.total);
            let history = data.history;
            let payed = 0;
            history.forEach(h => {
                payed += h.quantity;
                let html = `<p>- ${h.createdTime}: ${h.quantity} VND</p>`
                $("#history_pay_tuition").append(html)
            })
            $("#payed").text(payed);
            if (payed >= data.total) {
                $("#status").text("Đã hoàn thành");
            } else {
                $("#status").text("Chưa hoàn thành, còn thiếu: " + (data.total - payed) + " VND");
            }
        }
        window.loader.hide();
    });
})