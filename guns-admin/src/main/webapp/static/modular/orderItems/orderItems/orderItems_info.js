/**
 * 初始化订单详情详情对话框
 */
var orderItemInfoDlg = {
    orderItemInfoData : {}
};

/**
 * 清除数据
 */
orderItemInfoDlg.clearData = function() {
    this.orderItemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
orderItemInfoDlg.set = function(key, val) {
    this.orderItemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
orderItemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
orderItemInfoDlg.close = function() {
    parent.layer.close(window.parent.orderItem.layerIndex);
}

/**
 * 收集数据
 */
orderItemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('productName')
    .set('sn')
    .set('unit')
    .set('unitState')
    .set('count')
    .set('noValency')
    .set('salesPrice')
    .set('disPrice')
    .set('money')
    .set('model')
    .set('brand')
    .set('reArea')
    .set('client')
    .set('saleDate')
    .set('openBillStore')
    .set('lib')
    .set('payType')
    .set('paymentAmount')
    .set('customerName')
    .set('deliveryAddress')
    .set('saleNo')
    .set('ecNo')
    .set('totalQnty')
    .set('moneyAmount')
    .set('actualAmount')
    .set('discountAmount');
}

/**
 * 提交添加
 */
orderItemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderItem/add", function(data){
        Feng.success("添加成功!");
        window.parent.orderItem.table.refresh();
        orderItemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderItemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
orderItemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderItem/update", function(data){
        Feng.success("修改成功!");
        window.parent.orderItem.table.refresh();
        orderItemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderItemInfoData);
    ajax.start();
}

$(function() {

});
