package com.stylefeng.guns.modular.staffInfo.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.staffInfo.service.IStaffInfoService;
import com.stylefeng.guns.modular.system.model.StaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 面试信息管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-19 13:59:58
 */
@Controller
@RequestMapping("/staffInfo")
public class StaffInfoController extends BaseController {

    private String PREFIX = "/staffInfo/staffInfo/";

    @Autowired
    private IStaffInfoService staffInfoService;

    /**
     * 跳转到面试信息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "staffInfo.html";
    }

    /**
     * 跳转到添加面试信息管理
     */
    @RequestMapping("/staffInfo_add")
    public String staffInfoAdd() {
        return PREFIX + "staffInfo_add.html";
    }

    /**
     * 跳转到修改面试信息管理
     */
    @RequestMapping("/staffInfo_update/{staffInfoId}")
    public String staffInfoUpdate(@PathVariable Integer staffInfoId, Model model) {
        StaffInfo staffInfo = staffInfoService.selectById(staffInfoId);
        model.addAttribute("item", staffInfo);
        LogObjectHolder.me().set(staffInfo);
        return PREFIX + "staffInfo_edit.html";
    }

    /**
     * 获取面试信息管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, HttpServletRequest request, HttpServletResponse response) {
//        解决跨域问题
//        List<StaffInfo> list = staffInfoService.selectList(null);
//        JSONArray ja = JSONArray.fromObject(list);
//        String callback = request.getParameter("callback");
//        response.setContentType("text/html");
//        response.setCharacterEncoding("utf-8");
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//        } catch (IOException e) {
//            System.out.println("失败");
//            e.printStackTrace();
//        }
//        out.print(callback + "(" + ja.toString() + ")");
//        return null;
//        解决跨域问题
//        System.out.println(ja.toString());
//        return "jsonpcallback(" + ja.toString() + ")";
        List<StaffInfo> list=staffInfoService.selectList(null);

        return list;
    }

    /**
     * 新增面试信息管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(StaffInfo staffInfo) {
        staffInfoService.insert(staffInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除面试信息管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer staffInfoId) {
        staffInfoService.deleteById(staffInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改面试信息管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(StaffInfo staffInfo) {
        staffInfoService.updateById(staffInfo);
        return SUCCESS_TIP;
    }

    /**
     * 面试信息管理详情
     */
    @RequestMapping(value = "/detail/{staffInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("staffInfoId") Integer staffInfoId) {
        return staffInfoService.selectById(staffInfoId);
    }
}
