/**
 * 请求后台验证码
 * @param img
 */
function changeVerityCode(img) {
    img.src = "/o2o/Kaptcha?" + Math.floor(Math.random() * 100);
}

function handleUser(user) {
    var personalInfoHtml = '<img id="userLoginDropdownImg" src="' + user.profileImg + '" class="mr-2 rounded" width="28">' + user.name;
    $("#userLoginDropdown").html(personalInfoHtml);
}

$.ajax({
    url: '/o2o/shopadmin/getuser',
    type: 'GET',
    success: function (data) {
        console.info(data);
        if (data.success) {
            handleUser(data.user);
        }
    }
});

/**
 * 获得地址中的字段
 * @param name
 * @returns {string}
 */
function getQueryString(name) {
    var regExp = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var temp = window.location.search.substr(1).match(regExp);
    if (temp != null) {
        return decodeURIComponent(temp[2]);
    }
    return '';
}

/**
 * 遍历显示页码
 * @param data 分页数据
 */
function showPagination(data) {
    var pagination = $(".pagination");
    pagination.empty();

    var paginationHtml = '';
    var pagePreviousHtml = '<li class="page-item page-previous" data-id="1"><a class="page-link" href="#">首页</a></li>';
    var pageNextHtml = '<li class="page-item page-next" data-id="' + data.pages + '"><a class="page-link" href="#">末页</a></li>';

    data.navigatepageNums.map(function (item, index) {
        if (item === data.pageNum) {
            paginationHtml += '<li class="page-item active" data-id="' + item + '"><a class="page-link">' + item + '</a></li>';
        } else {
            paginationHtml += '<li class="page-item" data-id="' + item + '"><a class="page-link">' + item + '</a></li>';
        }
    });

    $(".page-total-page").text(data.pages);
    $(".page-current-nums").text(data.pageNum);

    if (data.isFirstPage) {
        pagePreviousHtml = "";
    }
    if (data.isLastPage) {
        pageNextHtml = "";
    }

    pagination.append(pagePreviousHtml);
    pagination.append(paginationHtml);
    pagination.append(pageNextHtml);

}

var saveCurrentShop = '/o2o/shopadmin/getshopmanagementinfo';

/**
 * 传送id到后台服务器,保存当前操作的的商铺
 * @param id
 */
function sendShopIdToServer(id) {
    $.ajax({
        url: saveCurrentShop,
        type: 'POST',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        data: {
            shopId: id
        },
        success: function (data) {
            console.info("传递成功！");
        }
    });
}

/**
 * 展示个人中心
 */
function showDropdownMenuPersonal() {
    var dropdownMenuPersonalHtml = ' <a class="dropdown-item" href="#">个人中心</a>' +
        '<a class="dropdown-item" href="#" data-toggle="modal"' +
        '   data-target="#bind-account">账号绑定</a>' +
        '<a class="dropdown-item" id="reset" href="#" data-toggle="modal"' +
        '   data-target="#reset-pw" data-usertype="2">修改密码</a>' +
        '<a class="dropdown-item" target="_blank">帮助&支持</a>' +
        '<div class="dropdown-divider"></div>' +
        '<a id="login-out" usertype="2" class="dropdown-item" href="sign-in.html">注销</a>';
    $("#dropdown-menu-personal").html(dropdownMenuPersonalHtml);
}

showDropdownMenuPersonal();