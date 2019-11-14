$(function () {

    var pageIndex = 1;
    var pageSize = 10;
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    var productInfo = "/o2o/shopadmin/getproductlistbyshop";

    var listUrl = '/o2o/shopadmin/listawardsbyshop';
    var modifyUrl = '/o2o/shopadmin/modifyaward';

    getAndShowAwardList();

    /**
     * 获得并展示商品列表
     */
    function getAndShowAwardList() {
        var url = listUrl + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize;
        $.getJSON(url, function (data) {
            handList(data);
        });

    }

    /**
     * 搜索
     */
    $(".btn-search").click(function () {
        var productName = $("#input-search").val();
        $.ajax({
            url: listUrl + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&productName=" + productName,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handList(data);
                }
            }
        })
    });

    function handList(data) {

        $(".award-management-table tbody").empty();

        var productsHtml = "";
        $.map(data.awardPageInfo.list, function (value, index) {

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
                + '<td class="text-center">' + value.awardName + '</td>'
                + '<td class="text-center">' + value.point + '</td>'
                + '<td class="text-center">' +
                '<a href="#" class="btn btn-primary btn-sm award-edit-btn" data-toggle="modal" data-target="#modify-shop-award" data-id="' + value.awardId + '">编辑</a>&nbsp;' +
                '<a href="" class="btn btn-danger btn-sm award-status-btn" data-status="' + status + '" data-id="' + value.awardId + '">' + textOp + '</a>&nbsp;' +
                '<a href="" class="btn btn-info btn-sm">预览</a>&nbsp;' + '</td>'
                + '</tr>';
        });

        $(".award-management-table tbody").html(productsHtml);

        showPagination(data.awardPageInfo);
    }

    $(document).on("click", ".page-item", function () {
        pageIndex = $(this).data("id");
        $.ajax({
            url: listUrl + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize,
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
    $(document).on("click", ".award-status-btn", function () {

        var award = {};
        award.awardId = $(this).data("id");
        award.enableStatus = $(this).data("status");

        if (confirm("确定吗?")) {
            $.ajax({
                url: modifyUrl,
                type: "POST",
                data: {
                    awardStr: JSON.stringify(award),
                    statusChange: false
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        getAndShowAwardList();
                    } else {
                        alert("操作失败");
                    }
                }
            });
        }
    });


});