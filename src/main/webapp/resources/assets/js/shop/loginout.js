$(function () {
    $("#login-out").click(function () {
        $.ajax({
            url: "/o2o/local/loginout",
            type: "POST",
            cache: false,
            dateType: "json",
            success: function (data) {
                if (data.success) {
                    var usertype = $("#login-out").attr("usertype");
                    window.location.href = "/o2o/local/login?usertype=" + usertype;
                    return false;
                }
            },
            error: function (data, error) {
                alert(error);
            }
        })
    });
});

