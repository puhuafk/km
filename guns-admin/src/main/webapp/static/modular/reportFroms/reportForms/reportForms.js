/**
 * 商品销售统计表管理初始化
 */
var ReportForms = {
    id: "ReportFormsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ReportForms.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '序号', field: 'sel', visible: true, align: 'center', valign: 'middle'},
            {title: '查看', field: 'look', visible: true, align: 'center', valign: 'middle'},
            {title: '商品名称', field: 'productName', visible: true, align: 'center', valign: 'middle'},
            {title: '条形码', field: 'pn', visible: true, align: 'center', valign: 'middle'},
            {title: '规格/型号', field: 'spec', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '单位说明', field: 'unitThat', visible: true, align: 'center', valign: 'middle'},
            {title: '品牌', field: 'brands', visible: true, align: 'center', valign: 'middle'},
            {title: '销售数量', field: 'salesCount', visible: true, align: 'center', valign: 'middle'},
            {title: '销售金额', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '退货数量', field: 'returnCount', visible: true, align: 'center', valign: 'middle'},
            {title: '销售单价', field: 'sellingPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '退货金额', field: 'returnTotalPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '销售退货率', field: 'returnRate', visible: true, align: 'center', valign: 'middle'},
            {title: '开始日期', field: 'startDate', visible: true, align: 'center', valign: 'middle'},
            {title: '结束日期', field: 'endDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ReportForms.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ReportForms.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品销售统计表
 */
ReportForms.openAddReportForms = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品销售统计表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/reportForms/reportForms_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商品销售统计表详情
 */
ReportForms.openReportFormsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品销售统计表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/reportForms/reportForms_update/' + ReportForms.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商品销售统计表
 */
ReportForms.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/reportForms/delete", function (data) {
            Feng.success("删除成功!");
            ReportForms.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("reportFormsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询商品销售统计表列表
 */
ReportForms.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ReportForms.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ReportForms.initColumn();
    var table = new BSTable(ReportForms.id, "/reportForms/list", defaultColunms);
    table.setPaginationType("client");
    ReportForms.table = table.init();
});
