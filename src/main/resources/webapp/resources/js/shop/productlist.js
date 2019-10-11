$(function () {
    var productPostUrl = '/shopping/shopadmin/modifyproduct';
    var productInfo = "/shopping/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";

    function getProductList() {
        $.getJSON(productInfo, function (data) {

            console.info(data);
            var productsHtml = "";

            $.map(data.productList, function (value, index) {

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
                    + '<td>' + value.priority + '</td>'
                    + '<td>' +
                    '<a href="#" class="btn btn-primary btn-sm product-edit-btn" data-toggle="modal" data-target="#product-edit" data-id="' + value.productId + '">编辑</a>&nbsp;' +
                    '<a href="" class="btn btn-danger btn-sm product-status-btn" data-status="' + status + '" data-id="' + value.productId + '">' + textOp + '</a>&nbsp;' +
                    '<a href="" class="btn btn-info btn-sm">预览</a>&nbsp;' + '</td>'
                    + '</tr>';
            });

            $("#product-wrap").html(productsHtml);

        });
    }

    getProductList();

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
                        getProductList();
                    } else {
                        alert("操作失败");
                    }
                }
            });
        }

    });

});