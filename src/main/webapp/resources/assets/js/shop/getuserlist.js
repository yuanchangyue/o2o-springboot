$(function () {
    var shopId = 1;
    var userName = '';
    var pageIndex = 1;
    var pageSize = 99;

    function getList() {
        var listUrl = '/o2o/shopadmin/listusershopmapsbyshop?pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&shopId=' + shopId + '&userName=' + userName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userShopMapList = data.userShopMapPageInfo.list;
                var tempHtml = '';
                console.info(userShopMapList);
                userShopMapList.map(function (item, index) {
                    tempHtml += '<tr>'
                        + '<td class="align-middle text-center"><img width="50px" src="' + item.user.profileImg + '" alt="" class="img-circle"></td>'
                        + '<td class="align-middle text-center">' + item.user.name + '</td>'
                        + '<td class="align-middle text-center">' + item.point + '</td>'
                        + '<td class="align-middle text-center">' + timeStamp2String(item.createTime) + '</td>'
                        + '</tr>'
                });
                $('.shop-user-table tbody').html(tempHtml);
            }
        });
    }

    $('#search').on('input', function (e) {
        userName = e.target.value;
        $('.usershopcheck-wrap').empty();
        getList();
    });
    getList();



});