$(function () {

    var pageIndex = 1;
    var pageSize = 8;
    var shopName = "";
    var getShopList = "/o2o/shopadmin/getshoplist";

    getList();

    /**
     * 请求后台获得商铺列表
     */
    function getList() {
        $.ajax({
            url: getShopList + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize,
            type: "get",
            dataType: "json",
            success: function (data) {
                console.info(data);
                if (data.success) {
                    handleList(data.shopPageInfo);
                    //handleUser(data.user);
                }
            }
        });
    }

    /**
     * 搜索
     */
    $(".btn-search").click(function () {
        var shopName = $("#input-search").val();
        $.ajax({
            url: getShopList + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&shopName=" + shopName,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.shopPageInfo);
                }
            }
        })
    });

    /**
     * 点击事件(分页操作)
     */
    $(document).on("click", ".page-item", function () {
        pageIndex = $(this).data("id");
        $.ajax({
            url: getShopList + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize,
            type: "GET",
            dataType: "json",
            success: function (data) {
                handleList(data.shopPageInfo);
                //handleUser(data.user);
            }
        })
    });

    /**
     * 处理显示列表
     * @param data
     */
    function handleList(data) {
        $('.shop-table-body').empty();
        var html = "";
        var shopList = data.list;
        shopList.map(function (value, index) {
            var number = index + 1;
            html += '<tr>'
                + '<th >' + number + '</th>'
                + '<td class="align-middle"><div><a href="#" class="weight-700">' + value.shopName + '</a></div></td>'
                + '<td class="align-middle"><div class="weight-400">' + shopStatus(value.enableStatus) + '</div></td>'
                + '<td class="data-label Actions" class="text-md-center dropdown dropleft"">' + goShop(value.enableStatus, value.shopId) + '</td>'
                + '</tr>';
        });
        $('.shop-table-body').html(html);
        showPagination(data);
    }

    /**
     * 处理商品状态
     * @param status
     * @returns {string}
     */
    function shopStatus(status) {
        if (status === 0) {
            return '审核中';
        } else if (status === -1) {
            return '商铺非法';
        } else {
            return '审核通过';
        }
    }

    function goShop(status, id) {
        if (status === 1) {
            return '<a href="shopoperation?shopId=' + id + '" class="btn btn-primary btn-sm shop-edit-btn">编辑</a>&nbsp;&nbsp;' +
                '<a href="#" class="btn btn-primary btn-sm more-btn" data-id="' + id + '" id="actionDropdown" data-toggle="dropdown" aria-expanded="false">' +
                '<span class="material-icons align-middle" style="font-size: 15px;">arrow_drop_down</span></a>' +
                '<div class="dropdown-menu dropdown-menu-right" aria-labelledby="actionDropdown" x-placement="left-start" style="position: absolute; transform: translate3d(24px, 13px, 0px); top: 0px; left: 0px; will-change: transform;">' +
                '    <a class="dropdown-item" href="productlist?shopId=' + id + '" > 商品管理 </a>' +
                '    <a class="dropdown-item" href="productcategorymanagement?shopId=' + id + '">商品类别管理</a>' +
                '</div>';
        } else {
            return "";
        }
    }

    $(document).on("mouseover mouseout", ".more-btn", function () {
        console.info("hover");
        sendShopIdToServer($(this).data("id"));
    });


});