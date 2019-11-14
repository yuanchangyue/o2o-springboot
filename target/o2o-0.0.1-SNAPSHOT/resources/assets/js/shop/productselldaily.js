
$(function () {

    function iniProductSellDailyHistogram() {
        var initProductSellDailyUrl = "/o2o/shopadmin/listproductselldailyinfobyshop";
        $.getJSON(initProductSellDailyUrl, function (data) {
            if (data.success) {
                var myChart = echarts.init(document.getElementById('myChart'));
                // 指定图表的配置项和数据
                var option = generateStaticEchartPart();

                option.legend.data = data.legendDate;
                option.series = data.series;
                option.xAxis = data.xAxis;
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    }

    function generateStaticEchartPart() {
        return {
            color: ['#003366', '#006699', '#4cabce', '#e5323e', '#81ecec'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {},
            grid: {
                left: '1%',
                right: '1%',
                bottom: '1%',
                containLabel: true
            },
            xAxis: [{}],
            yAxis: [{type: 'value'}],
            series: []
        };
    }

    iniProductSellDailyHistogram();

    var pageIndex = 1;
    var pageSize = 5;
    var productName = '';
    var productSellDailyListUr = '/o2o/shopadmin/listuserproductmapbyshop';

    function getProductSellDailyList() {
        var url = productSellDailyListUr + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + '&product=' + productName;
        $.getJSON(url, function (data) {
            if (data.success) {
                showList(data.userProductMapPageInfo);
            }
        })
    }

    function showList(data) {
        var tempHtml = '';
        $.map(data.list, function (item, index) {
            tempHtml += '<tr>' +
                '<td class="text-center">' + (index + 1) + '</td>' +
                '<td class="text-center">' + item.product.productName + '</td>' +
                '<td class="text-center">' + timeStamp2String(item.createTime) + '</td>' +
                '<td class="text-center">' + item.user.name + '</td>' +
                '<td class="text-center">' + item.point + '</td>' +
                '<td class="text-center">' + item.operator.name + '</td>' +
                '</tr>';
        });
        $(".product-sell-list-table tbody").html(tempHtml);
        showPagination(data);
    }

    getProductSellDailyList();

    /**
     * 点击事件(分页操作)
     */
    $(document).on("click", ".page-item", function () {
        pageIndex = $(this).data("id");
        $.ajax({
            url: productSellDailyListUr + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize + '&product=' + productName,
            type: "GET",
            dataType: "json",
            success: function (data) {
                showList(data.userProductMapPageInfo);
            }
        })
    });
});