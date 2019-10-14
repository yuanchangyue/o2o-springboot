$(function () {
    var productId = getQueryString("productId");
    var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    var productInfo = "/o2o/shopadmin/productlist";

    var isEdit = false;

    var infoUrl = "/o2o/shopadmin/getproductbyid?productId=" + productId;

    if (productId) {

        $("#page-title").text("o2o商品管理后台 - 商品编辑");
        $(".form-title").text("商品编辑");
        $(".form-des").text("请修改以下信息,点击保存完成商品编辑！");
        $(".form-header").text("商品编辑表单");
        $("#submit").text("保存");

        getInfo(productId);
        isEdit = true;

    } else {

        $("#page-title").text("o2o商品管理后台 - 商品注册");
        $(".form-title").text("商品注册");
        $(".form-des").text("请填写以下信息,点击注册进行商品注册！");
        $(".form-header").text("商品注册表单");
        $("#submit").text("注册");

        getCategory();
        productPostUrl = '/o2o/shopadmin/addproduct';
    }

    function getInfo(id) {

        $.getJSON(infoUrl, function (data) {
            if (data.success) {

                console.info(data);

                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#point').val(product.point);
                console.info("product.point" + product.point);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);

                var optionHtml = "";
                var optionArr = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;

                optionArr.map(function (value, index) {

                    var isSelected = optionSelected === value.productCategoryId ? 'selected' : '';

                    optionHtml += '<option ' + isSelected + ' data-value="' + value.productCategoryId + '">'
                        + value.productCategoryName + '</option>';

                });

                $('#product-category').html(optionHtml);

            }

        })

    }

    function getCategory() {

        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = "";
                productCategoryList.map(function (value, index) {
                    optionHtml += '<option  data-value="'
                        + value.productCategoryId + '">'
                        + value.productCategoryName + '</option>';
                });
                $('#product-category').html(optionHtml);
            }
        })

    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {

        if ($('.detail-img').length < 6) {
            $('.detail-img:last-child').after('<input class="form-control-file detail-img" type="file" id="detail-img">');
        }

    });

    $('#submit').click(function () {
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.point = $('#point').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory = {
            productCategoryId: $('#product-category option:checked').data('value')
        };
        product.productId = productId;

        var thumbnail = $("#small-img")[0].files[0];
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);

        $('.detail-img').map(
            function (value, index) {

                if ($('.detail-img')[value].files.length > 0) {
                    formData.append("productImg" + value,
                        $('.detail-img')[value].files[0])
                }

            });

        formData.append("productStr", JSON.stringify(product));

        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            console.log("请输入验证码！");
            return;
        }

        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: productPostUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.success) {
                    alert("商品提交成功！");
                    window.location.href = productInfo;
                    $('#captcha_img').click();
                } else {
                    alert("商品提交失败！" + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        })
    })


});