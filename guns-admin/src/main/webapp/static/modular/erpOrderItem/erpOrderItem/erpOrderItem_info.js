/**
 * 初始化ERP订单Item详情对话框
 */
var ErpOrderItemInfoDlg = {
    erpOrderItemInfoData : {}
};

/**
 * 清除数据
 */
ErpOrderItemInfoDlg.clearData = function() {
    this.erpOrderItemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ErpOrderItemInfoDlg.set = function(key, val) {
    this.erpOrderItemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ErpOrderItemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ErpOrderItemInfoDlg.close = function() {
    parent.layer.close(window.parent.ErpOrderItem.layerIndex);
}

/**
 * 收集数据
 */
ErpOrderItemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('oId')
    .set('pName')
    .set('barCode')
    .set('library')
    .set('num')
    .set('unit')
    .set('unitE')
    .set('sPrice')
    .set('nPrice')
    .set('dPrice')
    .set('money')
    .set('spec')
    .set('brands')
    .set('madeIn')
    .set('remark');
}

/**
 * 提交添加
 */
ErpOrderItemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/erpOrderItem/add", function(data){
        Feng.success("添加成功!");
        window.parent.ErpOrderItem.table.refresh();
        ErpOrderItemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.erpOrderItemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ErpOrderItemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/erpOrderItem/update", function(data){
        Feng.success("修改成功!");
        window.parent.ErpOrderItem.table.refresh();
        ErpOrderItemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.erpOrderItemInfoData);
    ajax.start();
}

$(function() {

});
