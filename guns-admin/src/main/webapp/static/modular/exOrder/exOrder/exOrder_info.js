/**
 * 初始化异常订单管理详情对话框
 */
var ExOrderInfoDlg = {
    exOrderInfoData : {}
};

/**
 * 清除数据
 */
ExOrderInfoDlg.clearData = function() {
    this.exOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderInfoDlg.set = function(key, val) {
    this.exOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.ExOrder.layerIndex);
}

/**
 * 收集数据
 */
ExOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderSn')
    .set('exType')
    .set('exReason')
    .set('exState')
    .set('operator')
    .set('operatorId')
    .set('createtime')
    .set('stepSign')
    .set('flowId')
    .set('saleSn')
    .set('dataStatue')
    .set('outHouTime')
    .set('outHouPer')
    .set('examDepId');
}

/**
 * 提交添加
 */
ExOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrder/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExOrder.table.refresh();
        ExOrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrder/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExOrder.table.refresh();
        ExOrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderInfoData);
    ajax.start();
}

$(function() {

});
