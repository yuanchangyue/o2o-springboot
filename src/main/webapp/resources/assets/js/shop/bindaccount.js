$(function () {

    var binLocalAuthUrl = "/o2o/local/bindlocalauth";
    var userType = getQueryString("usertype");

    $("#bind-btn").click(function () {

        var userName = $("#usernameInput").val();
        var password = $("#pwInput").val();
        var verifyCode = $("#verifyInput").val();

        if (!verifyCode) {
            alert("请输入验证码!");
            return;
        }

        var needVerify = false;

        $.ajax({
            url: binLocalAuthUrl,
            type: "POST",
            dataType: "json",
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCode
            },
            success: function (data) {
                if (data.success) {
                    alert("绑定成功!");
                    if (userType == 1) {
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    alert("提交失败" + data.errMsg);
                    $("#captcha_img").click();
                }
            }
        })
    });
});