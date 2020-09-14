package com.stylefeng.guns.modular.cancelOrder;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {


    @GetMapping("/get")
    @ResponseBody
    public String Test01(String account, String token) {
        System.out.println(account);
        System.out.println(token);
        System.out.println("测试get接口");
        return "1";
    }

//    @PostMapping("/post")
//    @ResponseBody
//    public String Test02(HttpServletRequest request) {
//        System.out.println(request.getParameterMap());
//        System.out.println("访问的url:"+ request.getRequestURI()+"?"+request.getQueryString());
//        return "2";
//    }

    @PostMapping("/post")
    @ResponseBody
    public String Test02(String name, String age) {
        System.out.println(name);
        System.out.println(age);
        return "2";
    }


//    @PostMapping("/json")
//    @ResponseBody
//    public String TestJson(String remake, @RequestBody(required = false) Student student) {
////        System.out.println(name);
////        System.out.println(age);
////        System.out.println(remake);
//        System.out.println(student.toString());
//        return "2";
//    }


}
