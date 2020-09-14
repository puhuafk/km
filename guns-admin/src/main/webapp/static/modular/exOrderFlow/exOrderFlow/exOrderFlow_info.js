/**
 * 初始化异常订单流程管理详情对话框
 */
var ExOrderFlowInfoDlg = {
    exOrderFlowInfoData : {}
};

/**
 * 清除数据
 */
ExOrderFlowInfoDlg.clearData = function() {
    this.exOrderFlowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderFlowInfoDlg.set = function(key, val) {
    this.exOrderFlowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderFlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExOrderFlowInfoDlg.close = function() {
    parent.layer.close(window.parent.ExOrderFlow.layerIndex);
}

/**
 * 收集数据
 */
ExOrderFlowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('exOrderId')
    .set('flowType')
    .set('sysDeptId1')
    .set('sysDeptId2')
    .set('sysDeptId3')
    .set('sysDeptId4')
    .set('sysDeptId5')
    .set('createtime')
    .set('operator');
}

/**
 * 提交添加
 */
ExOrderFlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderFlow/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExOrderFlow.table.refresh();
        ExOrderFlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderFlowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExOrderFlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderFlow/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExOrderFlow.table.refresh();
        ExOrderFlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderFlowInfoData);
    ajax.start();
}

$(function() {

});
