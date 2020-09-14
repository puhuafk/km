/**
 * 初始化erp订单管理详情对话框
 */
var ErpSaleOrderInfoDlg = {
    erpSaleOrderInfoData : {}
};

/**
 * 清除数据
 */
ErpSaleOrderInfoDlg.clearData = function() {
    this.erpSaleOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ErpSaleOrderInfoDlg.set = function(key, val) {
    this.erpSaleOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ErpSaleOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ErpSaleOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.ErpSaleOrder.layerIndex);
}

/**
 * 收集数据
 */
ErpSaleOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('sn')
    .set('zqpSn')
    .set('saleDate')
    .set('totalPrice')
    .set('orderStatus')
    .set('verifyStatus')
    .set('stockStatus')
    .set('cName')
    .set('discountP')
    .set('source')
    .set('operator')
    .set('dLibrary')
    .set('pMethod')
    .set('rAddress')
    .set('tMan')
    .set('tNo')
    .set('tCost')
    .set('tPrice')
    .set('dPrice');
}

/**
 * 提交添加
 */
ErpSaleOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/erpSaleOrder/add", function(data){
        Feng.success("添加成功!");
        window.parent.ErpSaleOrder.table.refresh();
        ErpSaleOrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.erpSaleOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ErpSaleOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/erpSaleOrder/update", function(data){
        Feng.success("修改成功!");
        window.parent.ErpSaleOrder.table.refresh();
        ErpSaleOrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.erpSaleOrderInfoData);
    ajax.start();
}

$(function() {

});
