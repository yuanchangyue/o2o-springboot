$(function () {

    var pageIndex = 1;
    var pageSize = 8;
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    var productInfo = "/o2o/shopadmin/getproductlistbyshop";

    getAndShowProductList();

    /**
     * 获得并展示商品列表
     */
    function getAndShowProductList() {

        var getProductListInfoUrl = productInfo + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize;

        $.getJSON(getProductListInfoUrl, function (data) {
            console.info(data);
            handList(data);
            //handleUser(data.user);
        });

    }

    /**
     * 搜索
     */
    $(".btn-search").click(function () {
        var productName = $("#input-search").val();
        $.ajax({
            url: productInfo + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&productName=" + productName,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    console.info(data);
                    handList(data);
                }
            }
        })
    });

    function handList(data) {

        console.info(data);
        $(".product-table-body").empty();

        var productsHtml = "";
        $.map(data.productPageInfo.list, function (value, index) {

            var textOp = "下架";
            var status = 0;
            if (value.enableStatus === 0) {
                textOp = "上架";
                status = 1;
            } else {
                status = 0;
            }
            productsHtml += '<tr>'
                + '<th scope="row">' + (index + 1) + '</th>'
                + '<td>' + value.productName + '</td>'
                + '<td>' + value.point + '</td>'
                + '<td>' +
                '<a href="#" class="btn btn-primary btn-sm product-edit-btn" data-toggle="modal" data-target="#product-edit" data-id="' + value.productId + '">编辑</a>&nbsp;' +
                '<a href="" class="btn btn-danger btn-sm product-status-btn" data-status="' + status + '" data-id="' + value.productId + '">' + textOp + '</a>&nbsp;' +
                '<a href="" class="btn btn-info btn-sm">预览</a>&nbsp;' + '</td>'
                + '</tr>';
        });
        $(".product-table-body").html(productsHtml);

        //handleUser(data.user);
        showPagination(data.productPageInfo);
    }

    $(document).on("click", ".page-item", function () {
        pageIndex = $(this).data("id");
        $.ajax({
            url: productInfo + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize,
            type: "GET",
            dataType: "json",
            success: function (data) {
                handList(data);
            }
        })
    });

    /**
     * 监听商品上下架功能
     */
    $(document).on("click", ".product-status-btn", function () {
        var status = $(this).data("status");
        var id = $(this).data("id");

        var product = {};
        product.productId = id;
        product.enableStatus = status;

        if (confirm("确定吗?")) {
            $.ajax({
                url: productPostUrl,
                type: "POST",
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        alert("操作成功");
                        getAndShowProductList();
                    } else {
                        alert("操作失败");
                    }
                }
            });
        }
    });

    $(document).on("click", ".product-edit-btn", function () {
        window.location.href = "/o2o/shopadmin/productoperation?productId=" + $(this).data("id");
    });

    $("#btn-add-product").click(function () {
        window.location.href = "/o2o/shopadmin/productoperation";
    });

});