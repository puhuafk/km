/**
 * 市场部训店记录管理管理初始化
 */
var ShopInfo = {
    id: "ShopInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShopInfo.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '店铺名称', field: 'shopName', visible: true, align: 'center', valign: 'middle'},
            {title: '店铺地址', field: 'shopAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '联系方式', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '营业执照图片', field: 'businessLicense', visible: false, align: 'center', valign: 'middle'},
            {title: '店铺类型', field: 'shopType', visible: false, align: 'center', valign: 'middle'},
            {title: '店铺周边信息', field: 'aroundInfo', visible: false, align: 'center', valign: 'middle'},
            {title: '店铺周边流量', field: 'aroundFlow', visible: false, align: 'center', valign: 'middle'},
            {title: '店铺面积', field: 'shopArea', visible: true, align: 'center', valign: 'middle'},
            {title: '店铺经营年限', field: 'shopLife', visible: true, align: 'center', valign: 'middle'},
            {title: '店员数量', field: 'shopStaffNum', visible: true, align: 'center', valign: 'middle'},
            {title: '店主姓名', field: 'ownerName', visible: true, align: 'center', valign: 'middle'},
            {title: '店主性别', field: 'ownerSex', visible: true, align: 'center', valign: 'middle'},
            {title: '店主年龄', field: 'ownerAge', visible: false, align: 'center', valign: 'middle'},
            {title: '店主籍贯', field: 'ownerPlace', visible: false, align: 'center', valign: 'middle'},
            {title: '店主性格', field: 'ownerCharacter', visible: false, align: 'center', valign: 'middle'},
            {title: '店主类型（沟通）', field: 'ownerType', visible: false, align: 'center', valign: 'middle'},
            {title: '店主文化', field: 'ownerCulture', visible: false, align: 'center', valign: 'middle'},
            {title: '营业时间', field: 'businessHours', visible: false, align: 'center', valign: 'middle'},
            {title: '经营品种', field: 'businessScope', visible: false, align: 'center', valign: 'middle'},
            {title: '社区服务', field: 'communityService', visible: false, align: 'center', valign: 'middle'},
            {title: '是否注册津小超', field: 'ifRegistes', visible: false, align: 'center', valign: 'middle'},
            {title: '是否下单', field: 'ifPlaceOrder', visible: false, align: 'center', valign: 'middle'},
            {title: '是否习惯独立下单', field: 'ifIndependentOrder', visible: false, align: 'center', valign: 'middle'},
            {title: '常用平台', field: 'commonPlatform', visible: false, align: 'center', valign: 'middle'},
            {title: '平台订货比例', field: 'platformOrderRatio', visible: false, align: 'center', valign: 'middle'},
            {title: '手机平台', field: 'mobilePlatform', visible: false, align: 'center', valign: 'middle'},
            {title: '每日营业额', field: 'dailyTurnover', visible: false, align: 'center', valign: 'middle'},
            {title: '周边便利店', field: 'nearbyShop', visible: false, align: 'center', valign: 'middle'},
            {title: '億米潜力预估', field: 'potentialPrediction', visible: false, align: 'center', valign: 'middle'},
            {title: '纬度', field: 'lat', visible: false, align: 'center', valign: 'middle'},
            {title: '经度', field: 'lng', visible: false, align: 'center', valign: 'middle'},
            {title: '用户活跃度', field: 'userActivity', visible: false, align: 'center', valign: 'middle'},
            {title: '其他描述', field: 'memo', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */

ShopInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ShopInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加市场部训店记录管理
 */
ShopInfo.openAddShopInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加市场部训店记录管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shopInfo/shopInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看市场部训店记录管理详情
 */
ShopInfo.openShopInfoDetail = function () {
    console.log(this)
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '市场部训店记录管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/shopInfo/shopInfo_update/' + ShopInfo.seItem.id
        });
        this.layerIndex = index;
        console.log(ShopInfo.seItem.id)
        // var url='/shopInfo/shopInfo_update/' + ShopInfo.seItem.id
        // flag = true;
        // // 选项卡菜单已存在
        // $('.J_menuTab',parent.document).each(function () {
        //     console.log($(this).data('id'))
        //     console.log(url)
        //     if ($(this).data('id') == url) {
        //         console.log($(this).hasClass('active'))
        //         if (!$(this).hasClass('active')) {
        //             $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
        //
        //             // 显示tab对应的内容区
        //             $('.J_mainContent .J_iframe',parent.document).each(function () {
        //                 console.log($(this).data('id'))
        //                 if ($(this).data('id') == url) {
        //                     $(this).show().siblings('.J_iframe').hide();
        //                     return false;
        //                 }
        //             });
        //         }
        //         flag = false;
        //         return false;
        //     }
        // });
        // if(flag){
        //     $('.J_menuTab',parent.document).removeClass('active');
        //     $('.J_menuTabs .page-tabs-content ',parent.document).append(' <a href="javascript:;" class="J_menuTab active" data-id="'+url+'">订单详情页<i class="fa fa-times-circle"></i></a>');
        //     var str1 = '<iframe class="J_iframe" name="iframe23" width="100%" height="100%" src="' + url + '" frameborder="0" data-id="' + url + '" seamless style="display:inline"></iframe>';
        //     $('.J_mainContent',parent.document).find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
        // }
    }
};

/**
 * 删除市场部训店记录管理
 */
ShopInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/shopInfo/delete", function (data) {
            Feng.success("删除成功!");
            ShopInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("shopInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询市场部训店记录管理列表
 */
ShopInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ShopInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShopInfo.initColumn();
    var table = new BSTable(ShopInfo.id, "/shopInfo/list", defaultColunms);
    table.setPaginationType("client");
    ShopInfo.table = table.init();
});
