/**
 * erp订单管理管理初始化
 */
var ErpSaleOrder = {
    id: "ErpSaleOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ErpSaleOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单号', field: 'sn', visible: true, align: 'center', valign: 'middle'},
            {title: '电商单号', field: 'zqpSn', visible: true, align: 'center', valign: 'middle'},
            {title: '销售时间', field: 'saleDate', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '订单状态', field: 'orderStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '审核状态', field: 'verifyStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '出入库状态', field: 'stockStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '客户名称', field: 'cName', visible: true, align: 'center', valign: 'middle'},
            {title: '折扣百分比', field: 'discountP', visible: true, align: 'center', valign: 'middle'},
            {title: '订单来源', field: 'source', visible: true, align: 'center', valign: 'middle'},
            {title: '开单门店', field: 'operator', visible: true, align: 'center', valign: 'middle'},
            {title: '库区ID', field: 'dLibrary', visible: true, align: 'center', valign: 'middle'},
            {title: '收款方式', field: 'pMethod', visible: true, align: 'center', valign: 'middle'},
            {title: '收货地址', field: 'rAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '收货人', field: 'tMan', visible: true, align: 'center', valign: 'middle'},
            {title: '承运单号', field: 'tNo', visible: true, align: 'center', valign: 'middle'},
            {title: '运费', field: 'tCost', visible: true, align: 'center', valign: 'middle'},
            {title: '折扣前总金额', field: 'tPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'dPrice', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ErpSaleOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ErpSaleOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加erp订单管理
 */
ErpSaleOrder.openAddErpSaleOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加erp订单管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/erpSaleOrder/erpSaleOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看erp订单管理详情
 */
ErpSaleOrder.openErpSaleOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'erp订单管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/erpSaleOrder/erpSaleOrder_update/' + ErpSaleOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除erp订单管理
 */
ErpSaleOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/erpSaleOrder/delete", function (data) {
            Feng.success("删除成功!");
            ErpSaleOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("erpSaleOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询erp订单管理列表
 */
ErpSaleOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ErpSaleOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ErpSaleOrder.initColumn();
    var table = new BSTable(ErpSaleOrder.id, "/erpSaleOrder/list", defaultColunms);
    table.setPaginationType("client");
    ErpSaleOrder.table = table.init();
});
