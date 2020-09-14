/**
 * 初始化市场部训店记录管理详情对话框
 */
var ShopInfoInfoDlg = {
    shopInfoInfoData : {}
};

/**
 * 清除数据
 */
ShopInfoInfoDlg.clearData = function() {
    this.shopInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShopInfoInfoDlg.set = function(key, val) {
    this.shopInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShopInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShopInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.ShopInfo.layerIndex);
}

/**
 * 收集数据
 */
ShopInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('shopName')
    .set('shopAddress')
    .set('phone')
    .set('businessLicense')
    .set('shopType')
    .set('aroundInfo')
    .set('aroundFlow')
    .set('shopArea')
    .set('shopLife')
    .set('shopStaffNum')
    .set('ownerName')
    .set('ownerSex')
    .set('ownerAge')
    .set('ownerPlace')
    .set('ownerCharacter')
    .set('ownerType')
    .set('ownerCulture')
    .set('businessHours')
    .set('businessScope')
    .set('communityService')
    .set('ifRegistes')
    .set('ifPlaceOrder')
    .set('ifIndependentOrder')
    .set('commonPlatform')
    .set('platformOrderRatio')
    .set('mobilePlatform')
    .set('dailyTurnover')
    .set('nearbyShop')
    .set('potentialPrediction')
    .set('lat')
    .set('lng')
    .set('userActivity')
    .set('memo');
}

/**
 * 提交添加
 */
ShopInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shopInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.ShopInfo.table.refresh();
        ShopInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shopInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ShopInfoInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shopInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.ShopInfo.table.refresh();
        ShopInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shopInfoInfoData);
    ajax.start();
}

$(function() {

});
