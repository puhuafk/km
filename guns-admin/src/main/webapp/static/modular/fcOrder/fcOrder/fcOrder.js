/**
 * 电商后台订单（视图）管理管理初始化
 */
var FcOrder = {
    id: "FcOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FcOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '电商订单号', field: 'sn', visible: true, align: 'center', valign: 'middle'},
            {title: '交易金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '客户名称', field: 'consignee', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '平台红包金额', field: 'couponDiscount', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'null', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
FcOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FcOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加电商后台订单（视图）管理
 */
FcOrder.openAddFcOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加电商后台订单（视图）管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fcOrder/fcOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看电商后台订单（视图）管理详情
 */
FcOrder.openFcOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '电商后台订单（视图）管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fcOrder/fcOrder_update/' + FcOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除电商后台订单（视图）管理
 */
FcOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/fcOrder/delete", function (data) {
            Feng.success("删除成功!");
            FcOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("fcOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询电商后台订单（视图）管理列表
 */
FcOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    FcOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FcOrder.initColumn();
    var table = new BSTable(FcOrder.id, "/fcOrder/list", defaultColunms);
    table.setPaginationType("client");
    FcOrder.table = table.init();
});
