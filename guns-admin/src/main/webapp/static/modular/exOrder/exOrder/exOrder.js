/**
 * 异常订单管理管理初始化
 */
var ExOrder = {
    id: "ExOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '订单单号', field: 'orderSn', visible: true, align: 'center', valign: 'middle'},
            {title: '异常类型（1红包减款、2已出库退单、3单品退单）', field: 'exType', visible: true, align: 'center', valign: 'middle'},
            {title: '异常原因', field: 'exReason', visible: true, align: 'center', valign: 'middle'},
            {title: '异常状态（是否处理：1未处理、2已经处理）', field: 'exState', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'operator', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人id', field: 'operatorId', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '流程步骤标识', field: 'stepSign', visible: true, align: 'center', valign: 'middle'},
            {title: '流程id', field: 'flowId', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单号', field: 'saleSn', visible: true, align: 'center', valign: 'middle'},
            {title: '来源（1电商后台,2erp系统）', field: 'dataStatue', visible: true, align: 'center', valign: 'middle'},
            {title: '出库时间', field: 'outHouTime', visible: true, align: 'center', valign: 'middle'},
            {title: '出库人', field: 'outHouPer', visible: true, align: 'center', valign: 'middle'},
            {title: '审批部门id', field: 'examDepId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加异常订单管理
 */
ExOrder.openAddExOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加异常订单管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/exOrder/exOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看异常订单管理详情
 */
ExOrder.openExOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '异常订单管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/exOrder/exOrder_update/' + ExOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除异常订单管理
 */
ExOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/exOrder/delete", function (data) {
            Feng.success("删除成功!");
            ExOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("exOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 导出excel
 */
ExOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/exOrder/excel", function (data) {
            // Feng.success("删除成功!");
            // ExOrder.table.refresh();
        }, function (data) {
            Feng.error("导出失败!" + data.responseJSON.message + "!");
        });
        ajax.set("exOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询异常订单管理列表
 */
ExOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ExOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExOrder.initColumn();
    var table = new BSTable(ExOrder.id, "/exOrder/list", defaultColunms);
    table.setPaginationType("client");
    ExOrder.table = table.init();
});
