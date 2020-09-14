/**
 * 初始化电商后台订单（视图）管理详情对话框
 */
var FcOrderInfoDlg = {
    fcOrderInfoData : {}
};

/**
 * 清除数据
 */
FcOrderInfoDlg.clearData = function() {
    this.fcOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FcOrderInfoDlg.set = function(key, val) {
    this.fcOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FcOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FcOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.FcOrder.layerIndex);
}

/**
 * 收集数据
 */
FcOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('sn')
    .set('amount')
    .set('consignee')
    .set('createDate')
    .set('couponDiscount');
}

/**
 * 提交添加
 */
FcOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fcOrder/add", function(data){
        Feng.success("添加成功!");
        window.parent.FcOrder.table.refresh();
        FcOrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fcOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FcOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fcOrder/update", function(data){
        Feng.success("修改成功!");
        window.parent.FcOrder.table.refresh();
        FcOrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fcOrderInfoData);
    ajax.start();
}

$(function() {

});
