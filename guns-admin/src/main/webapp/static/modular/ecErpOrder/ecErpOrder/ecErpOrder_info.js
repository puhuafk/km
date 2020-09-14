/**
 * 初始化订单管理详情对话框
 */
var EcErpOrderInfoDlg = {
    ecErpOrderInfoData : {}
};

/**
 * 清除数据
 */
EcErpOrderInfoDlg.clearData = function() {
    this.ecErpOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EcErpOrderInfoDlg.set = function(key, val) {
    this.ecErpOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EcErpOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EcErpOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.EcErpOrder.layerIndex);
}

/**
 * 收集数据
 */
EcErpOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('ecNo')
    .set('saleNo')
    .set('createdate')
    .set('amount')
    .set('source')
    .set('customerName')
    .set('deliveryAddress')
    .set('outStorePer')
    .set('outStoreTime')
    .set('orderState')
    .set('payMode')
    .set('payer')
    .set('payee')
    .set('sentPer')
    .set('openBillStore')
    .set('payType')
    .set('saleDate')
    .set('lib')
    .set('totalQnty')
    .set('discountAmount')
    .set('actualAmount');
}

/**
 * 提交添加
 */
EcErpOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ecErpOrder/add", function(data){
        Feng.success("添加成功!");
        window.parent.EcErpOrder.table.refresh();
        EcErpOrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ecErpOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EcErpOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ecErpOrder/update", function(data){
        Feng.success("修改成功!");
        window.parent.EcErpOrder.table.refresh();
        EcErpOrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ecErpOrderInfoData);
    ajax.start();
}

$(function() {

});
