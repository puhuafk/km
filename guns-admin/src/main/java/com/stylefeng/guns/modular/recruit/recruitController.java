package com.stylefeng.guns.modular.recruit;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/recruit")
public class recruitController extends BaseController {


    private String PREFIX = "/recruit/";

    /**
     * 添加求职信息
     */
    @RequestMapping("/add")
    public String index() {
        return PREFIX + "wang_form.html";
    }

    /**
     * 填写简历
     */
    @RequestMapping("/show")
    public String show() {
        return PREFIX + "wang_show.html";
    }


}
