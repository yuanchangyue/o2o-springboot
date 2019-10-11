$(function () {
    //  var productId = getQueryString("productId");
    var categoryUrl = '/shopping/shopadmin/getproductcategorylist';
    var productPostUrl = '/shopping/shopadmin/modifyproduct';
    var productInfo = "/shopping/shopadmin/productlist";
    var productId = '';
    var infoUrl = '';

    $(document).on("click", ".product-edit-btn", function () {

        $(".modal-title").text("商品修改");
        productId = $(this).data("id");
        infoUrl = "/shopping/shopadmin/getproductbyid?productId=" + productId;


        if (productId) {
            getInfo(productId);
            isEdit = true;
        } else {
            getCategory();
            productPostUrl = '/shopping/shopadmin/addproduct';
        }

        function getInfo(id) {
            getInfo();
        }

    });


    $(document).on("click", ".product-add-btn", function () {

        $(".modal-title").text("商品添加");
        getCategory();
        productPostUrl = '/shopping/shopadmin/addproduct';

    });

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

    function getInfo() {

        $.getJSON(infoUrl, function (data) {
            if (data.success) {

                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);

                var optionHtml = "";
                var optionArr = data.productCategoryList;


                optionArr.map(function (value, index) {

                    /* if (product.productCategory.productCategoryId !== 'null' || product.productCategory.productCategoryId !== '') {
                                 var optionSelected = product.productCategory.productCategoryId;
                                 var isSelected = optionSelected === value.productCategoryId ? 'selected' : '';
                             }*/

                    var isSelected = '';
                    optionHtml += '<option ' + isSelected + ' data-value="' + value.productCategoryId + '">'
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
                    alert("商品提交失败！");
                    $('#captcha_img').click();
                }
            }
        })
    })

});
