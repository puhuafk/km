/**
 * 初始化商品销售统计表详情对话框
 */
var ReportFormsInfoDlg = {
    reportFormsInfoData : {}
};

/**
 * 清除数据
 */
ReportFormsInfoDlg.clearData = function() {
    this.reportFormsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportFormsInfoDlg.set = function(key, val) {
    this.reportFormsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReportFormsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ReportFormsInfoDlg.close = function() {
    parent.layer.close(window.parent.ReportForms.layerIndex);
}

/**
 * 收集数据
 */
ReportFormsInfoDlg.collectData = function() {
    this
    .set('sel')
    .set('look')
    .set('productName')
    .set('pn')
    .set('spec')
    .set('unit')
    .set('unitThat')
    .set('brands')
    .set('salesCount')
    .set('totalPrice')
    .set('returnCount')
    .set('sellingPrice')
    .set('returnTotalPrice')
    .set('returnRate')
    .set('startDate')
    .set('endDate');
}

/**
 * 提交添加
 */
ReportFormsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/reportForms/add", function(data){
        Feng.success("添加成功!");
        window.parent.ReportForms.table.refresh();
        ReportFormsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportFormsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ReportFormsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/reportForms/update", function(data){
        Feng.success("修改成功!");
        window.parent.ReportForms.table.refresh();
        ReportFormsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.reportFormsInfoData);
    ajax.start();
}

$(function() {

});
