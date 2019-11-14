$(function () {

    var infoUrl = '/o2o/shopadmin/getawardbyid';
    var awardPostUrl = '/o2o/shopadmin/modifyaward';
    var addAwardUrl = '/o2o/shopadmin/addaward';

    $(document).on("click", ".award-edit-btn", function () {
        var awardId = $(this).data('id');
        $("#submit").attr("data-id", awardId);
        $("#submit").attr("data-type", 1);
        getInfo(awardId);
    });

    $(document).on("click", "#btn-add-award", function () {
        $("#submit").attr("data-type", 2);
        $('#award-name').val("");
        $('#award-priority').val("");
        $('#award-desc').val("");
        $('#award-point').val("");
    });

    function getInfo(awardId) {
        $.getJSON(infoUrl + '?awardId=' + awardId, function (data) {
            if (data.success) {
                console.info("+++++++");
                console.info(data);
                var award = data.award;
                $('#award-name').val(award.awardName);
                $('#award-priority').val(award.priority);
                $('#award-desc').val(award.awardDesc);
                $('#award-point').val(award.point);
            }
        });
    }

    $('#submit').click(function () {

        var type = $(this).data("type");
        var postUrl = type === 1 ? awardPostUrl : addAwardUrl;

        var award = {};
        award.awardId = $(this).data("id");
        award.awardName = $('#award-name').val();
        award.priority = $('#award-priority').val();
        award.awardDesc = $('#award-desc').val();
        award.point = $('#award-point').val();

        var thumbnail = $('#award-img')[0].files[0];

        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        formData.append('awardStr', JSON.stringify(award));

        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            alert('请输入验证码！');
            return;
        }

        formData.append("statusChange", true);
        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: postUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    alert('提交成功！');
                    window.location.href = "/o2o/shopadmin/pointpage";
                } else {
                    alert('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });
    });

});