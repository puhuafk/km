/**
 * 订单详情管理初始化
 */
var orderItem = {
    id: "orderItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
orderItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '商品名称', field: 'productName', visible: true, align: 'center', valign: 'middle'},
            {title: '条形码', field: 'sn', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '单位说明', field: 'unitState', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '正常价', field: 'noValency', visible: true, align: 'center', valign: 'middle'},
            {title: '销售价', field: 'salesPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '折后价', field: 'disPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
            {title: '规格/型号', field: 'model', visible: true, align: 'center', valign: 'middle'},
            {title: '品牌', field: 'brand', visible: true, align: 'center', valign: 'middle'},
            {title: '库区', field: 'reArea', visible: true, align: 'center', valign: 'middle'},
            {title: '客户', field: 'client', visible: true, align: 'center', valign: 'middle'},
            {title: '销售日期', field: 'saleDate', visible: true, align: 'center', valign: 'middle'},
            {title: '开单门店', field: 'openBillStore', visible: true, align: 'center', valign: 'middle'},
            {title: '库区', field: 'lib', visible: true, align: 'center', valign: 'middle'},
            {title: '付款方式', field: 'payType', visible: true, align: 'center', valign: 'middle'},
            {title: '付款金额', field: 'paymentAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '收货人', field: 'customerName', visible: true, align: 'center', valign: 'middle'},
            {title: '收货地址', field: 'deliveryAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单号', field: 'saleNo', visible: true, align: 'center', valign: 'middle'},
            {title: '电商单号', field: 'ecNo', visible: true, align: 'center', valign: 'middle'},
            {title: '总数量', field: 'totalQnty', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'moneyAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '折后金额', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '已优惠', field: 'discountAmount', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
orderItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        orderItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单详情
 */
orderItem.openAddorderItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderItem/orderItem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单详情详情
 */
orderItem.openorderItemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单详情详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderItem/orderItem_update/' + orderItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单详情
 */
orderItem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/orderItem/delete", function (data) {
            Feng.success("删除成功!");
            orderItem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderItemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单详情列表
 */
orderItem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    orderItem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = orderItem.initColumn();
    var table = new BSTable(orderItem.id, "/orderItem/list", defaultColunms);
    table.setPaginationType("client");
    orderItem.table = table.init();
});
