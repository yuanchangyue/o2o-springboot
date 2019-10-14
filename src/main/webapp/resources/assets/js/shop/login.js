$(function () {

    var loginUrl = "/o2o/local/logincheck";
    var userType = getQueryString("usertype");
    var loginCount = 0;

    $("#login-btn").click(function () {
        var username = $("#usernameInput").val();
        var password = $("#pwInput").val();
        var verifyCode = $("#verifyInput").val();
        var needVerify = false;

        //登录三次需要验证码
        if (loginCount >= 3) {
            //判空验证码
            if (!verifyCode) {
                alert("请输入密码");
                return;
            } else {
                needVerify = true;
            }
        }

        $.ajax({
            url: loginUrl,
            type: "POST",
            cache: false,
            async: false,
            dataType: "json",
            data: {
                userName: username,
                password: password,
                verifyCodeActual: verifyCode,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    alert("登录成功");
                    if (userType == 1) {
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    alert("登陆失败：" + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $("#verify-box").css("display", "block");
                    }
                    $("#captcha_img").click();
                }
            }
        });
    });

});