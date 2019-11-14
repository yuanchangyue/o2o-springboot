$(function () {
    var shopId = 1;
    var awardName = '';

    function getList() {
        var listUrl = '/o2o/shopadmin/listuserawardmapsbyshop?pageIndex=1&pageSize=9999&shopId='
            + shopId + '&awardName=' + awardName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                console.info(data);
                var userAwardMapList = data.userAwardMapPageInfo.list;
                var tempHtml = '';
                userAwardMapList.map(function (item, index) {
                    tempHtml += '<tr>'
                        + '<td class="align-middle text-center">' + (index + 1) + '</td>'
                        + '<td class="align-middle text-center">' + item.award.awardName + '</td>'
                        + '<td class="align-middle text-center">' + timeStamp2String(item.createTime) + '</td>'
                        + '<td class="align-middle text-center">' + item.user.name + '</td>'
                        + '<td class="align-middle text-center">' + item.point + '</td>'
                        + '<td class="align-middle text-center">' + item.operator.name + '</td>'
                        + '</tr>';
                });
                $('.user-point-table tbody').html(tempHtml);
            }
        });
    }

    $('#search').on('input', function (e) {
        awardName = e.target.value;
        $('.awarddeliver-wrap').empty();
        getList();
    });

    getList();
});