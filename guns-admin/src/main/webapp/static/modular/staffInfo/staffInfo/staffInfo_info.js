/**
 * 初始化面试信息管理详情对话框
 */
var StaffInfoInfoDlg = {
    staffInfoInfoData : {}
};

/**
 * 清除数据
 */
StaffInfoInfoDlg.clearData = function() {
    this.staffInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffInfoInfoDlg.set = function(key, val) {
    this.staffInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StaffInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.StaffInfo.layerIndex);
}

/**
 * 收集数据
 */
StaffInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('sex')
    .set('age')
    .set('nation')
    .set('nativePlace')
    .set('education')
    .set('maStatus')
    .set('feStatus')
    .set('workStatus')
    .set('poStatus')
    .set('position')
    .set('pay')
    .set('phone')
    .set('buHistory')
    .set('selfEvaluation');
}

/**
 * 提交添加
 */
StaffInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.StaffInfo.table.refresh();
        StaffInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StaffInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/staffInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.StaffInfo.table.refresh();
        StaffInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffInfoInfoData);
    ajax.start();
}

$(function() {

});
