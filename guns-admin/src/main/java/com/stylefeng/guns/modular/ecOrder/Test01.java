package com.stylefeng.guns.modular.ecOrder;

import com.stylefeng.guns.core.util.Base64Util;
import com.stylefeng.guns.core.util.HttpClientProxy;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test01 {

//    public Page<JSONObject> queryList(Pageable pageable, String keyword) {
//        Page<JSONObject> page = new Page<JSONObject>(pageable);
//        String pathUrl = "http://www.jxcdemo.com/bcs/member/trade/list.jhtml";
//        String token = "16d44bda455c02faf92c35bc23bec38a";
//        String account = "13389035585";
//        Map<String, Object> mapParam = new HashMap<String, Object>();
////            mapParam.put("type", type == null ? "" : "");
//        mapParam.put("keyword", keyword == null ? "" : keyword);
//        mapParam.put("pageNumber", pageable.getPageNumber());
//        mapParam.put("pageSize", pageable.getPageSize());
//        mapParam.put("account", account);
//        mapParam.put("token", Base64Util.encode("{\"token\":\"" + token + "\",\"timestamp\":\"" + new Date().getTime() + "\"}"));
//        String string = HttpClientProxy.doGet(pathUrl, mapParam);
//        if (string != null && !string.equals("") && JSONUtils.mayBeJSON(string)) {
//            JSONObject json = JSONObject.fromObject(string);
//            if ("success".equals(json.getJSONObject("message").getString("type"))) {
//                JSONObject data = json.getJSONObject("data");
//                long total = data.getJSONObject("count").getLong("total");
//                page = new Page<JSONObject>(data.getJSONArray("list"), total, pageable);
//            }
//        }
//        return page;
//    }


    public static void main(String[] args) {
        String pathUrl = "http://www.jxcdemo.com/bcs/member/trade/list.jhtml";
        String token = "16d44bda455c02faf92c35bc23bec38a";
        String account = "13389035585";
        Map<String, Object> mapParam = new HashMap<String, Object>();
//        mapParam.put("keyword", keyword == null ? "" : keyword);
        mapParam.put("pageNumber", 500);
        mapParam.put("pageSize", 1);
        mapParam.put("account", account);
        mapParam.put("token", Base64Util.encode("{\"token\":\"" + token + "\",\"timestamp\":\"" + new Date().getTime() + "\"}"));
        String string = HttpClientProxy.doGet(pathUrl, mapParam);
        if (string != null && !string.equals("") && JSONUtils.mayBeJSON(string)) {
            JSONObject json = JSONObject.fromObject(string);
            if ("success".equals(json.getJSONObject("message").getString("type"))) {
                JSONObject data = json.getJSONObject("data");
                long total = data.getJSONObject("count").getLong("total");
//                page = new Page<JSONObject>(data.getJSONArray("list"), total, pageable);
                System.out.println(data.getJSONArray("list"));
            }
        }


    }










}
