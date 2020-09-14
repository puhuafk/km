/**
 * ERP订单Item管理初始化
 */
var ErpOrderItem = {
    id: "ErpOrderItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ErpOrderItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '主订单ID', field: 'oId', visible: true, align: 'center', valign: 'middle'},
            {title: '品名', field: 'pName', visible: true, align: 'center', valign: 'middle'},
            {title: '原厂件号', field: 'barCode', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'library', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'num', visible: true, align: 'center', valign: 'middle'},
            {title: '默认单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '换算数量', field: 'unitE', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单价', field: 'sPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'nPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '折扣前单价', field: 'dPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
            {title: '规格', field: 'spec', visible: true, align: 'center', valign: 'middle'},
            {title: '品牌', field: 'brands', visible: true, align: 'center', valign: 'middle'},
            {title: '产地', field: 'madeIn', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ErpOrderItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ErpOrderItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加ERP订单Item
 */
ErpOrderItem.openAddErpOrderItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加ERP订单Item',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/erpOrderItem/erpOrderItem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看ERP订单Item详情
 */
ErpOrderItem.openErpOrderItemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'ERP订单Item详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/erpOrderItem/erpOrderItem_update/' + ErpOrderItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除ERP订单Item
 */
ErpOrderItem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/erpOrderItem/delete", function (data) {
            Feng.success("删除成功!");
            ErpOrderItem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("erpOrderItemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询ERP订单Item列表
 */
ErpOrderItem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ErpOrderItem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ErpOrderItem.initColumn();
    var table = new BSTable(ErpOrderItem.id, "/erpOrderItem/list", defaultColunms);
    table.setPaginationType("client");
    ErpOrderItem.table = table.init();
});
