package com.stylefeng.guns.modular.cancelOrder.test;

import com.stylefeng.guns.core.util.Base64Util;
import com.stylefeng.guns.core.util.HttpRequest;
import com.stylefeng.guns.modular.cancelOrder.CangpeiController;

import java.text.ParseException;

public class test01 {

     static String  url = "";

    /**
     * 测试电商接口详情接口速度
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        url = "http://www.jinxiaochao.com";
        long start = System.currentTimeMillis();
        String st = HttpRequest.sendGet(url + "/bcs/member/trade/list.jhtml",
                "account=13389035585&token=" +
                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")
                        + "&pageNumber=" + 1 + "&pageSize=200"
        );

        CangpeiController cangpeiController = new CangpeiController();


        System.out.println(cangpeiController.ecJsonToOrderList(st).size());
        long end = System.currentTimeMillis();
        System.out.println(st);
        System.out.println("共用时间："+(end-start));

    }

}
