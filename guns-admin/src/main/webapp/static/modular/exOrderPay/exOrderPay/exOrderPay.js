/**
 * 异常订单首付款管理管理初始化
 */
var ExOrderPay = {
    id: "ExOrderPayTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExOrderPay.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '异常订单id', field: 'exOrderId', visible: true, align: 'center', valign: 'middle'},
            {title: '交款人', field: 'payer', visible: true, align: 'center', valign: 'middle'},
            {title: '收款人', field: 'payee', visible: true, align: 'center', valign: 'middle'},
            {title: '收款时间', field: 'payTime', visible: true, align: 'center', valign: 'middle'},
            {title: '交款人id', field: 'payerId', visible: true, align: 'center', valign: 'middle'},
            {title: '额度', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: 'pay_type', field: 'payType', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'operator', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExOrderPay.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExOrderPay.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加异常订单首付款管理
 */
ExOrderPay.openAddExOrderPay = function () {
    var index = layer.open({
        type: 2,
        title: '添加异常订单首付款管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/exOrderPay/exOrderPay_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看异常订单首付款管理详情
 */
ExOrderPay.openExOrderPayDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '异常订单首付款管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/exOrderPay/exOrderPay_update/' + ExOrderPay.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除异常订单首付款管理
 */
ExOrderPay.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/exOrderPay/delete", function (data) {
            Feng.success("删除成功!");
            ExOrderPay.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("exOrderPayId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询异常订单首付款管理列表
 */
ExOrderPay.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ExOrderPay.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExOrderPay.initColumn();
    var table = new BSTable(ExOrderPay.id, "/exOrderPay/list", defaultColunms);
    table.setPaginationType("client");
    ExOrderPay.table = table.init();
});
