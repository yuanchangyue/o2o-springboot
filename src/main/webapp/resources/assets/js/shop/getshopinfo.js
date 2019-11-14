$(function () {

    var pageIndex = 0;
    var pageSize = 99;
    var getShopInfoUrl = "/o2o/shopadmin/getshopbyrequest";
    var getShopAuthMapByIdUrl = "/o2o/shopadmin/getshopauthmapbyid";
    var getShopAuthMapListUrl = "/o2o/shopadmin/listshopauthmapbyshopid?pageIndex=" + pageIndex + "&pageSize=" + pageSize;

    var modifyShopAuthMap = '/o2o/shopadmin/modifyshopauthmap';

    function showShopInfo(data) {
        var shop = data.shop;

        $("#shop-title").text(shop.shopName);
        $("#shop-address").text(shop.shopAddr);
        $("#shop-desc").text(shop.shopDesc);
        $("#shop-img").attr("src", getConetxtPath() + shop.shopImg);
        $("#shop-phone").text(shop.phone);

        var tempCategoryHtml = '';
        $.map(data.productCategoryList, function (item, index) {
            tempCategoryHtml += '<a class="btn mb-2 btn-light">' + item.productCategoryName + '</a>&nbsp;';
        });

        $("#shop-category").html(tempCategoryHtml);

    }

    $.getJSON(getShopInfoUrl, function (data) {
        if (data.success) {
            showShopInfo(data);
        }
    });

    function showShopAuthMap() {
        $.getJSON(getShopAuthMapListUrl, function (data) {
            if (data.success) {
                console.info(data);
                showShopAuthMapList(data.shopAuthMapList);
            }
        });
    }


    function getRestore(enableStatus, shopAuthId) {
        return enableStatus === 0 ? '<button class="btn mb-2 btn-outline-danger btn-restore-and-cancel-power"  data-enable="' + enableStatus + '"  data-type="' + shopAuthId + '">恢复</button>'
            : '<button class="btn mb-2 btn-danger btn-restore-and-cancel-power" data-enable="' + enableStatus + '" data-type="' + shopAuthId + '" >卸权</button>';
    }

    function getShopAuthMapEnable(item) {
        return item.titleFlag === 0 ? "不可操作" : '<button class="btn mb-2 btn-light btn-shop-auth-modify" data-toggle="modal" ' +
            'data-target="#modify-shop-auth" data-shop-auth="' + item.shopAuthId + '">编辑</button>&nbsp;' + getRestore(item.enableStatus, item.shopAuthId);
    }

    function showShopAuthMapList(data) {
        var tempShopAuthMapList = '';
        $.map(data.list, function (item, index) {
            tempShopAuthMapList += '<tr>' +
                '<td class="text-center">' + index + '</td>' +
                '<td class="text-center">' + item.employee.name + '</td>' +
                '<td class="text-center">' + item.title + '</td>' +
                '<td class="text-center">' + getShopAuthMapEnable(item) + '</td>' +
                '</tr>';
        });
        $(".shop-auth-table tbody").html(tempShopAuthMapList);
    }


    showShopAuthMap();

    $(document).on("click", ".btn-restore-and-cancel-power", function () {
        var id = $(this).data("type");
        var enable = $(this).data("enable");

        if (confirm("确定此操作吗？")) {

            var enableStatus = (enable === 1 ? 0 : 1);

            var shopAuth = {};
            shopAuth.employee = {};
            shopAuth.shopAuthId = id;
            shopAuth.enableStatus = enableStatus;

            $.ajax({
                url: modifyShopAuthMap,
                type: "POST",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: {
                    shopAuthMapStr: JSON.stringify(shopAuth),
                    statusChange: false
                },
                success: function (data) {
                    if (data.success) {
                        alert("修改成功");
                        showShopAuthMap();
                    } else {
                        alert("修改失败");
                    }
                }
            });
        }
    });

    $(document).on("click", ".btn-shop-auth-modify", function () {
        var shopAuthId = $(this).data("shop-auth");
        $.ajax({
            url: getShopAuthMapByIdUrl,
            type: "GET",
            data: {shopAuthMapId: shopAuthId},
            success: function (data) {
                if (data.success) {
                    $("#employee-name").val(data.shopAuthMap.employee.name);
                    $("#employee-position").val(data.shopAuthMap.title);
                    $("#save-btn").attr("data-shop-auth-id", shopAuthId);
                }
            }
        })
    });


    $("#save-btn").click(function () {

        var shopAuth = {};
        shopAuth.employee = {};
        shopAuth.employee.name = $("#employee-name").val();
        shopAuth.shopAuthId = $("#save-btn").data("shop-auth-id");
        shopAuth.title = $("#employee-position").val();

        var verifyCode = $("#verifyInput").val();
        if (!verifyCode) {
            alert("请输入验证码");
            return;
        }

        $.ajax({
            url: modifyShopAuthMap,
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: {
                shopAuthMapStr: JSON.stringify(shopAuth),
                verifyCodeActual: verifyCode
            },
            success: function (data) {
                if (data.success) {
                    alert("提交成功");
                    //$("#captcha_img").click();
                    window.location.href = "/o2o/shopadmin/shoppage";
                } else {
                    alert("提交失败");
                    $("#captcha_img").click();
                }
            }
        });

    });
});