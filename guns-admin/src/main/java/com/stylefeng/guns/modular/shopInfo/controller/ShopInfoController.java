package com.stylefeng.guns.modular.shopInfo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.shopInfo.service.IShopInfoService;
import com.stylefeng.guns.modular.system.model.ShopInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 市场部训店记录管理控制器
 *
 * @author fengshuonan
 * @Date 2018-11-30 15:05:57
 */
@Slf4j
@Controller
@RequestMapping("/shopInfo")
public class ShopInfoController extends BaseController {

    private String PREFIX = "/shopInfo/shopInfo/";

    @Autowired
    private IShopInfoService shopInfoService;


    @Value("${web.upload-path}")
    private String path;

    /**
     * 跳转到市场部训店记录管理首页
     */
    @RequestMapping("/shop_mobile")
    public String mobileIndex() {
        return PREFIX + "shop_mobile.html";
    }


    /**
     * 手机端首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shopInfo.html";
    }

    /**
     * 跳转到添加市场部训店记录管理
     */
    @RequestMapping("/shopInfo_add")
    public String shopInfoAdd() {
        return PREFIX + "shopInfo_add.html";
    }

    /**
     * 跳转到修改市场部训店记录管理
     */
    @RequestMapping("/shopInfo_update/{shopInfoId}")
    public String shopInfoUpdate(@PathVariable Integer shopInfoId, Model model) {
        ShopInfo shopInfo = shopInfoService.selectById(shopInfoId);
        model.addAttribute("item", shopInfo);
        LogObjectHolder.me().set(shopInfo);
        return PREFIX + "shopInfo_edit.html";
    }

    /**
     * 跳转到修改市场部训店记录管理
     */
    @ResponseBody
    @RequestMapping("/shopInfo_edit/{shopInfoId}")
    public Object shopInfoEdit(@PathVariable Integer shopInfoId) {
        ShopInfo shopInfo = shopInfoService.selectById(shopInfoId);
        LogObjectHolder.me().set(shopInfo);
        return shopInfo;
    }


    /**
     * 获取市场部训店记录管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper wrapper = new EntityWrapper();
        ShiroUser shiroUser = (ShiroUser)this.getSession().getAttribute("shiroUser");
        if (shiroUser.getAccount().equals("shichang")) {
            return shopInfoService.selectList(null);
        }else {
                wrapper.eq("creater", shiroUser.getAccount());
            return shopInfoService.selectList(wrapper);
        }

    }

    /**
     * 新增市场部训店记录管理
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(ShopInfo shopInfo) {
        shopInfoService.insert(shopInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除市场部训店记录管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer shopInfoId) {
        shopInfoService.deleteById(shopInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改市场部训店记录管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ShopInfo shopInfo) {
        shopInfoService.updateById(shopInfo);
        return SUCCESS_TIP;
    }

    /**
     * 市场部训店记录管理详情
     */
    @RequestMapping(value = "/detail/{shopInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("shopInfoId") Integer shopInfoId) {
        return shopInfoService.selectById(shopInfoId);
    }

    /**
     * 店铺地图展示
     */
    @RequestMapping(value = "/baidu")
    public String baidu() {
        return PREFIX+"baidu.html";
    }


}
