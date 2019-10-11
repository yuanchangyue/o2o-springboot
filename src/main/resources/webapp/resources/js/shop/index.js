$(function () {

    var pageIndex = 1;
    var pageSize = 99;
    var shopList = "/o2o/frontend/listshops";
    var headLine = "/o2o/frontend/listmainpageinfo";

    function showShopList() {
        var shopListUrl = shopList + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize;
        $.getJSON(shopListUrl, function (data) {

            var shopCardHtml = "";

            data.shopPageInfo.list.map(function (item, index) {
                shopCardHtml += '<section class="shop-card">' +
                    '    <p class="shop-name">' + item.shopName + '</p>' +
                    '    <p class="shop-description">' + item.shopDesc + '</p>' +
                    '    <ul class="list-img">' +
                    '        <li><img' +
                    '                src="https://cube.elemecdn.com/2/ff/442a38bf56edea173c0dfa9b8e5fdjpeg.jpeg?x-oss-process=image/format,webp/resize,w_130,h_130,m_fixed"' +
                    '                class="img-thumbnail"></li>' +
                    '        <li><img' +
                    '                src="https://cube.elemecdn.com/2/ff/442a38bf56edea173c0dfa9b8e5fdjpeg.jpeg?x-oss-process=image/format,webp/resize,w_130,h_130,m_fixed"' +
                    '                class="img-thumbnail"></li>' +
                    '        <li><img' +
                    '                src="https://cube.elemecdn.com/2/ff/442a38bf56edea173c0dfa9b8e5fdjpeg.jpeg?x-oss-process=image/format,webp/resize,w_130,h_130,m_fixed"' +
                    '                class="img-thumbnail"></li>' +
                    '    </ul>' +
                    '</section>';
            });

            $(".shop-list-warp").html(shopCardHtml);

        })
    }

    showShopList();

    $.getJSON(headLine, function (data) {

        if (data.success) {
            $.map(data.shopCategoryList, function (item, index) {
                var indexCategoryHtml = '<div class="col-xs-3 col-md-4 " id="' + item.shopCategoryId + '">' +
                    '            <div class="shop-category-info text-center">' +
                    '                <img src="' + item.shopCategoryImg + '" alt=""  class="img-circle img-responsive">' +
                    '                <h5>' + item.shopCategoryName + '</h5>' +
                    '            </div>' +
                    '        </div>';
                $("#index-category").append(indexCategoryHtml);

            });

            $.map(data.headLineList, function (item, index) {
                var indexBannerHtml = '<div class="item" >' +
                    '            <img src="' + item.lineImg + '" style="object-fit:fill;" alt="...">' +
                    '            <div class="carousel-caption">' +
                    '            </div></div>';
                $("#index-banner").append(indexBannerHtml);
            });

            $("#index-banner").find("div").first().addClass("active");
        }
    })


});