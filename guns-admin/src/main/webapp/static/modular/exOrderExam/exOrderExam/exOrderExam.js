/**
 * 异常订单审核管理管理初始化
 */
var ExOrderExam = {
    id: "ExOrderExamTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ExOrderExam.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '异常订单id', field: 'exOrderId', visible: true, align: 'center', valign: 'middle'},
            {title: '审核部门id', field: 'sysDeptId', visible: true, align: 'center', valign: 'middle'},
            {title: '审核人id', field: 'sysUserId', visible: true, align: 'center', valign: 'middle'},
            {title: '审核意见', field: 'opinion', visible: true, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'operateTime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'operator', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '审核状态（1未审核，2已审核）', field: 'examState', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ExOrderExam.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExOrderExam.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加异常订单审核管理
 */
ExOrderExam.openAddExOrderExam = function () {
    var index = layer.open({
        type: 2,
        title: '添加异常订单审核管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/exOrderExam/exOrderExam_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看异常订单审核管理详情
 */
ExOrderExam.openExOrderExamDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '异常订单审核管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/exOrderExam/exOrderExam_update/' + ExOrderExam.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除异常订单审核管理
 */
ExOrderExam.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/exOrderExam/delete", function (data) {
            Feng.success("删除成功!");
            ExOrderExam.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("exOrderExamId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询异常订单审核管理列表
 */
ExOrderExam.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ExOrderExam.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ExOrderExam.initColumn();
    var table = new BSTable(ExOrderExam.id, "/exOrderExam/list", defaultColunms);
    table.setPaginationType("client");
    ExOrderExam.table = table.init();
});
