$(document).ready(function () {
    $(document).on('click', '#sendOrder', function () {
        console.log("yeah");
        $.ajax({
            url: 'sendOrder',
            type: 'POST',
            data: $("#sendOrderForm").serialize(),
            success: function (data) {
                console.log(data);
                $("#sendOrderForm").remove();
                //$("#body").html(data);
                //$("#sendOrderForm").remove();
                var loading = '<div style="margin: 100px 0 10px;" align=center>' +
                    '<img id="triplex" src="/static/image/triplex.gif" style="width: 80px; height: 80px;">' +
                    '<p>' + data + '</p></div>';
                $("#body").html(loading);
                checkingOrderResponse();
            }
        })
    });

    function checkingOrderResponse() {
        var timer = setTimeout(checkingOrderResponse, 25000);
        $.ajax({
            url: 'orderResponse',
            type: 'POST',
            error: timer,
            success: function (data) {
                console.log(data);
                clearTimeout(timer);
                var message = '<h3 align=center>check notifications to see if the car arrive</h3>';
                var carData = '<dl>' +
                    '<dt>car image</dt>' +
                    '<dd><img src="/static/image/CaddyCTS_V.jpg" alt="caddillac"/></dd>' +
                    '<dt>car gov number</dt>' +
                    '<dd>' + data.driver.govNumber + '</dd>' +
                    '<dt>car model</dt>' +
                    '<dd>' + data.driver.carModel + '</dd>' +
                    '<dt>car type</dt>' +
                    '<dd>' + data.driver.carClass + '</dd></dl>';
                $("#triplex").remove();
                $("#body").html(carData + message);
            }
        });
    };

    $("#orders tbody>tr").click(function () {
        $("#orderId").val($(this).attr('id'));
        $(this).addClass("selected").siblings().removeClass("selected");
    });

    var colNumber = 6 //number of table columns

    for (var i = 0; i < colNumber; i++) {
        var thWidth = $("table#orders").find("th:eq(" + i + ")").width();
        var tdWidth = $("table#orders").find("td:eq(" + i + ")").width();
        if (thWidth < tdWidth)
            $("table#orders").find("th:eq(" + i + ")").width(tdWidth);
        else
            $("table#orders").find("td:eq(" + i + ")").width(thWidth);
    }

    if ($("#accept").length > 0) {
        $(".progress-tracker li:nth-child(1)").addClass("active");
    } else if ($("#clientExpecting").length > 0) {
        $(".progress-tracker li:nth-child(1), .progress-tracker li:nth-child(2) ").addClass("active");
    } else if ($("#start").length > 0) {
        $(".progress-tracker li:nth-child(1), .progress-tracker li:nth-child(2), .progress-tracker li:nth-child(3)")
            .addClass("active");
    } else if ($("#end").length > 0) {
        $(".progress-tracker li:nth-child(1), .progress-tracker li:nth-child(2), " +
        ".progress-tracker li:nth-child(3), .progress-tracker li:nth-child(4)").addClass("active");
    }
});