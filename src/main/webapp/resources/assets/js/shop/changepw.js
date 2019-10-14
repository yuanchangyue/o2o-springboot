
$(function () {

    var changePasswordUrl = "/o2o/local/changelocalauthpw";
    var userType = $("#reset").data("usertype");
    $("#reset-btn").click(function () {
        var username = $("#usernameInputReset").val();
        var oldPassword = $("#oldPwInputReset").val();
        var newPassword = $("#newPwInputReset").val();
        var confirmPassword = $("#confirmPwInputReset").val();
        var verifyCode = $("#verifyInputReset").val();

        if (confirmPassword != newPassword) {
            alert("两次密码不一致，请重新输入!");
            return;
        }

        var formData = new FormData();
        formData.append("userName", username);
        formData.append("password", oldPassword);
        formData.append("newPassword", newPassword);
        if (!verifyCode) {
            alert("请输入验证码！");
            return;
        }
        formData.append("verifyCodeActual", verifyCode);

        $.ajax({
            url: changePasswordUrl,
            type: "POST",
            cache: false,
            processData: false,
            contentType: false,
            data: formData,
            success: function (data) {
                if (data.success) {
                    alert("密码重置成功!");
                    if (userType == 1) {
                        window.location.href = "/o2o";
                    } else {
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    alert("密码重置失败：" + data.errMsg);
                    $("#captcha_img_reset").click();
                }
            }
        });
    });

});