/**
 * 异常订单流程管理管理初始化
 */
var ExOrderFlow = {
    id: "ExOrderFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExOrderFlow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '异常订单id', field: 'exOrderId', visible: true, align: 'center', valign: 'middle'},
            {title: '流程类型（1减红包、2退单品、3已出库退单）', field: 'flowType', visible: true, align: 'center', valign: 'middle'},
            {title: '流程1（部门id）', field: 'sysDeptId1', visible: true, align: 'center', valign: 'middle'},
            {title: '流程2（部门id）', field: 'sysDeptId2', visible: true, align: 'center', valign: 'middle'},
            {title: '流程3（部门id）', field: 'sysDeptId3', visible: true, align: 'center', valign: 'middle'},
            {title: '流程4（部门id）、以0结尾、查询到0本流程结束', field: 'sysDeptId4', visible: true, align: 'center', valign: 'middle'},
            {title: '流程5（部门id）', field: 'sysDeptId5', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'operator', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExOrderFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExOrderFlow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加异常订单流程管理
 */
ExOrderFlow.openAddExOrderFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加异常订单流程管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/exOrderFlow/exOrderFlow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看异常订单流程管理详情
 */
ExOrderFlow.openExOrderFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '异常订单流程管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/exOrderFlow/exOrderFlow_update/' + ExOrderFlow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除异常订单流程管理
 */
ExOrderFlow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/exOrderFlow/delete", function (data) {
            Feng.success("删除成功!");
            ExOrderFlow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("exOrderFlowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询异常订单流程管理列表
 */
ExOrderFlow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ExOrderFlow.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExOrderFlow.initColumn();
    var table = new BSTable(ExOrderFlow.id, "/exOrderFlow/list", defaultColunms);
    table.setPaginationType("client");
    ExOrderFlow.table = table.init();
});
