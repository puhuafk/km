/**
 * 初始化异常订单审核管理详情对话框
 */
var ExOrderExamInfoDlg = {
    exOrderExamInfoData : {}
};

/**
 * 清除数据
 */
ExOrderExamInfoDlg.clearData = function() {
    this.exOrderExamInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderExamInfoDlg.set = function(key, val) {
    this.exOrderExamInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExOrderExamInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExOrderExamInfoDlg.close = function() {
    parent.layer.close(window.parent.ExOrderExam.layerIndex);
}

/**
 * 收集数据
 */
ExOrderExamInfoDlg.collectData = function() {
    this
    .set('id')
    .set('exOrderId')
    .set('sysDeptId')
    .set('sysUserId')
    .set('opinion')
    .set('operateTime')
    .set('operator')
    .set('createtime')
    .set('examState');
}

/**
 * 提交添加
 */
ExOrderExamInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderExam/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExOrderExam.table.refresh();
        ExOrderExamInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderExamInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExOrderExamInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/exOrderExam/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExOrderExam.table.refresh();
        ExOrderExamInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exOrderExamInfoData);
    ajax.start();
}

$(function() {

});
