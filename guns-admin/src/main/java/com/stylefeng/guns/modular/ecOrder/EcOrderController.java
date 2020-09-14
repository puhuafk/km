package com.stylefeng.guns.modular.ecOrder;


import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 调取电商后台接口
 */
public class EcOrderController extends BaseController {

//    @Autowired
//    RestTemplate restTemplate;

    /**
     * 查询订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        return null;
    }


}
