$(function (message) {

    var listUrl = '/shopping/shopadmin/getproductcategorylist';
    var addUrl = '/shopping/shopadmin/addproductcategorys';
    var deleteUrl = '/shopping/shopadmin/removeproductcategory';

    getlist();

    function getlist() {

        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var tempHtml = '';
                dataList.map(function (value, index) {
                    var number = index + 1;
                    tempHtml += '<tr class="new"><th scope="row">' + number + '</th><td>'
                        + value.productCategoryName + '</td>'
                        + '<td>' + value.priority + '</td>'
                        + '<td><a href="#" class="btn btn-danger delete" data-id="' + value.productCategoryId + '">删除</a></td>'
                        + '</tr>'
                    ;

                });
                $('.category-wrap').append(tempHtml);
            }
        });
    }

    $('#add').click(function () {

        var tempHtml = '<tr class="temp">' +
            '<th class="row">#</th>' +
            '<th><input type="text" class="category"></th>\n' +
            '<th><input  type="number" class="priority"></th>\n' +
            '<th><a href="#" class="btn btn-danger delete">删除</a></th>\n' +
            '</tr>\n';
        $('.category-wrap').append(tempHtml);
    });

    $('#commit').click(function () {
        var temp = $('.temp');
        var productCategoryList = [];
        temp.map(function (value, index) {
            var tempObj = {};
            tempObj.productCategoryName = $(index).find('.category').val();
            tempObj.priority = $(index).find(".priority").val();
            if (tempObj.priority && tempObj.productCategoryName) {
                productCategoryList.push(tempObj);
            }
        });

        $.ajax(
            {
                url: addUrl,
                type: 'POST',
                data: JSON.stringify(productCategoryList),
                contentType: 'application/json',
                success: function (data) {
                    if (data.success) {
                        alert("添加成功");
                        getlist();
                    } else {
                        console.log("提交失败");
                    }
                }
            }
        )

    });

    $('.category-wrap').on("click", '.new .delete', function (e) {
        var id = $(this).data("id");
        console.log(id);

        deleteShopCategory();

        function deleteShopCategory() {
            if (confirm("确定删除该类别吗？")) {

                $.ajax({
                    url: deleteUrl,
                    type: 'POST',
                    data: {
                        productCategoryId: id
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            console.log("删除成功");
                            getlist();
                        } else {
                            console.log("删除失败");
                        }
                    }
                })
            }
        }

    });

    $('.category-wrap').on("click", '.temp .delete', function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();
    });


});