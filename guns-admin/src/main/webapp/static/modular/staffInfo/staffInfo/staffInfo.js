/**
 * 面试信息管理管理初始化
 */
var StaffInfo = {
    id: "StaffInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StaffInfo.initColumn = function () {
    return [
        // {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名  1', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '性别2', field: 'sex', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '民族', field: 'nation', visible: true, align: 'center', valign: 'middle'},
            {title: '籍贯', field: 'nativePlace', visible: true, align: 'center', valign: 'middle'},
            {title: '学历', field: 'education', visible: true, align: 'center', valign: 'middle'},
            {title: '婚姻情况', field: 'maStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '生育状况', field: 'feStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '工作情况', field: 'workStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '政治面貌', field: 'poStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '应聘岗位', field: 'position', visible: true, align: 'center', valign: 'middle'},
            {title: '薪资要求', field: 'pay', visible: true, align: 'center', valign: 'middle'},
            {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '工作经历', field: 'buHistory', visible: true, align: 'center', valign: 'middle'},
            {title: '自我评价', field: 'selfEvaluation', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
StaffInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StaffInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加面试信息管理
 */
StaffInfo.openAddStaffInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加面试信息管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/staffInfo/staffInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看面试信息管理详情
 */
StaffInfo.openStaffInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '面试信息管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/staffInfo/staffInfo_update/' + StaffInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除面试信息管理
 */
StaffInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/staffInfo/delete", function (data) {
            Feng.success("删除成功!");
            StaffInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("staffInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询面试信息管理列表
 */
StaffInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    StaffInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = StaffInfo.initColumn();
    var table = new BSTable(StaffInfo.id, "/staffInfo/list", defaultColunms);
    table.setPaginationType("client");
    StaffInfo.table = table.init();
});
