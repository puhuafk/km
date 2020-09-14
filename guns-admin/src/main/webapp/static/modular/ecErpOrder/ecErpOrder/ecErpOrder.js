/**
 * 订单管理管理初始化
 */
var EcErpOrder = {
    id: "EcErpOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
EcErpOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '电商单号', field: 'ecNo', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单号', field: 'saleNo', visible: true, align: 'center', valign: 'middle'},
            {title: '订单创建日期', field: 'createdate', visible: true, align: 'center', valign: 'middle'},
            {title: '金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '来源', field: 'dataSource', visible: true, align: 'center', valign: 'middle'},
            {title: '客户名称', field: 'customerName', visible: true, align: 'center', valign: 'middle'},
            {title: '收货地址', field: 'deliveryAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '出库人', field: 'outStorePer', visible: true, align: 'center', valign: 'middle'},
            {title: '出库时间', field: 'outStoreTime', visible: true, align: 'center', valign: 'middle'},
            {title: '订单状态  确认 备货（订单可关闭） 出库', field: 'orderState', visible: true, align: 'center', valign: 'middle'},
            {title: '结算方式', field: 'payMode', visible: true, align: 'center', valign: 'middle'},
            {title: '交款人', field: 'payer', visible: true, align: 'center', valign: 'middle'},
            {title: '收款人', field: 'payee', visible: true, align: 'center', valign: 'middle'},
            {title: '送货员', field: 'sentPer', visible: true, align: 'center', valign: 'middle'},
            {title: '开单门店', field: 'openBillStore', visible: true, align: 'center', valign: 'middle'},
            {title: '付款方式', field: 'payType', visible: true, align: 'center', valign: 'middle'},
            {title: '销售日期', field: 'saleDate', visible: true, align: 'center', valign: 'middle'},
            {title: '库区', field: 'lib', visible: true, align: 'center', valign: 'middle'},
            {title: '总数量', field: 'totalQnty', visible: true, align: 'center', valign: 'middle'},
            {title: '优惠金额', field: 'discountAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '实际付款金额', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
EcErpOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        EcErpOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单管理
 */
EcErpOrder.openAddEcErpOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ecErpOrder/ecErpOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单管理详情
 */
EcErpOrder.openEcErpOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ecErpOrder/ecErpOrder_update/' + EcErpOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单管理
 */
EcErpOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ecErpOrder/delete", function (data) {
            Feng.success("删除成功!");
            EcErpOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ecErpOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单管理列表
 */
EcErpOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    EcErpOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = EcErpOrder.initColumn();
    var table = new BSTable(EcErpOrder.id, "/ecErpOrder/list", defaultColunms);
    table.setPaginationType("client");
    EcErpOrder.table = table.init();
});
