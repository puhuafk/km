/**
 * 初始化异常订单首付款管理详情对话框
 */
var ExOrderPayInfoDlg = {
    exOrderPayInfoData : {}
};

/**
 * 清除数据
 */
ExOrderPayInfoDlg.clearData = function() {
    this.exOrderPayInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderPayInfoDlg.set = function(key, val) {
    this.exOrderPayInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderPayInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExOrderPayInfoDlg.close = function() {
    parent.layer.close(window.parent.ExOrderPay.layerIndex);
}

/**
 * 收集数据
 */
ExOrderPayInfoDlg.collectData = function() {
    this
    .set('id')
    .set('exOrderId')
    .set('payer')
    .set('payee')
    .set('payTime')
    .set('payerId')
    .set('amount')
    .set('payType')
    .set('createtime')
    .set('operator');
}

/**
 * 提交添加
 */
ExOrderPayInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderPay/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExOrderPay.table.refresh();
        ExOrderPayInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderPayInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExOrderPayInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderPay/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExOrderPay.table.refresh();
        ExOrderPayInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderPayInfoData);
    ajax.start();
}

$(function() {

});
