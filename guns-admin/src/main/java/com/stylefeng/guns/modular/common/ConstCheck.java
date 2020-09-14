package com.stylefeng.guns.modular.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstCheck {


    public static Map<Integer, Integer> redPacket = new LinkedHashMap();  //退红包
    public static Map<Integer, Integer> returnSingle = new LinkedHashMap(); //退单品
    public static Map<Integer, Integer> returnAll = new LinkedHashMap();  //未出库退单
    public static Map<Integer, String> exOrderTypeMap = new LinkedHashMap();  //异常订单类型


//    public static final String baidu = "https://www.baidu.com/";
//    public static final String baidu2 = "https://www.baidu.com/";
//    public static final String baidu3 = "https://www.baidu.com/";
//    public static final String remoteEcUrl = "https:www.baidu.com";
//
//    static  String bjx = "赵钱孙李　周吴郑王　冯陈褚卫　蒋沈韩杨　朱秦尤许　何吕施张　孔曹严华　金魏陶姜\n" +
//            "戚谢邹喻　柏水窦章　云苏潘葛　奚范彭郎　鲁韦昌马　苗凤花方　俞任袁柳　酆鲍史唐\n" +
//            "费廉岑薛　雷贺倪汤　滕殷罗毕　郝邬安常　乐于时傅　皮卞齐康　伍余元卜　顾孟平黄\n" +
//            "和穆萧尹　姚邵湛汪　祁毛禹狄　米贝明臧　计伏成戴　谈宋茅庞　熊纪舒屈　项祝董梁\n" +
//            "杜阮蓝闵　席季麻强　贾路娄危　江童颜郭　梅盛林刁　钟徐邱骆　高夏蔡田　樊胡凌霍\n" +
//            "虞万支柯　咎管卢莫　经房裘缪　干解应宗　宣丁贲邓　郁单杭洪　包诸左石　崔吉钮龚\n" +
//            "程嵇邢滑　裴陆荣翁　荀羊於惠　甄魏加封　芮羿储靳　汲邴糜松　井段富巫　乌焦巴弓\n" +
//            "牧隗山谷　车侯宓蓬　全郗班仰　秋仲伊宫　宁仇栾暴　甘钭厉戎　祖武符刘　姜詹束龙\n" +
//            "叶幸司韶　郜黎蓟薄　印宿白怀　蒲台从鄂　索咸籍赖　卓蔺屠蒙　池乔阴郁　胥能苍双\n" +
//            "闻莘党翟　谭贡劳逄　姬申扶堵　冉宰郦雍　却璩桑桂　濮牛寿通　边扈燕冀　郏浦尚农\n" +
//            "温别庄晏　柴瞿阎充　慕连茹习　宦艾鱼容　向古易慎　戈廖庚终　暨居衡步　都耿满弘\n" +
//            "匡国文寇　广禄阙东　殴殳沃利　蔚越夔隆　师巩厍聂　晁勾敖融　冷訾辛阚　那简饶空\n" +
//            "曾毋沙乜　养鞠须丰　巢关蒯相　查后江红　游竺权逯　盖益桓公　万俟司马　上官欧阳\n" +
//            "夏侯诸葛　闻人东方　赫连皇甫　尉迟公羊　澹台公冶　宗政濮阳　淳于仲孙　太叔申屠\n" +
//            "公孙乐正　轩辕令狐　钟离闾丘　长孙慕容　鲜于宇文　司徒司空　亓官司寇　仉督子车\n" +
//            "颛孙端木　巫马公西　漆雕乐正　壤驷公良　拓拔夹谷　宰父谷粱　晋楚阎法　汝鄢涂钦\n" +
//            "段干百里　东郭南门　呼延归海　羊舌微生　岳帅缑亢　况后有琴　梁丘左丘　东门西门\n" +
//            "商牟佘佴　伯赏南宫　墨哈谯笪　年爱阳佟　第五言福　百家姓续";
//
//
//    static String name = "伊静琪倩琳晶婧琦依轩璇鑫欣莹帅鹏超杰可力中津磊俊博振浩明瑞";
//
//    public static  String[] bjxs ;
//    public static  String[] names ;
//    public static  String[] addresss={
//            "天津市红桥区佳莹快餐店",
//            "凤园里8号楼超市",
//            "佳音佳诺烟酒商行",
//            "天津市河东区东生香综合副食品中心",
//            "密辉烟酒超市",
//            "三辰烟酒店",
//            "廉利烟酒超市",
//            "天津市河东区东生香综合副食品中心",
//            "廉利烟酒超市",
//            "馋滋味零食店"
//    };
//
//    public static void main(String[] args) {
//
//
//
//        System.out.println(bjx);
//    }


    static {
//        bjx = bjx.replaceAll("\\s*", "");
//        bjx = bjx.replaceAll(" ", "");
//        bjx = bjx.replaceAll("　", "");
//
//        bjxs = bjx.split("");
//        names = name.split("");



//        25	2	24	[0],[24],	开发部	津小超开发部
//        28	1	24	[0],[24],	采购部	津小超采购部
//        29	3	24	[0],[24],	仓配部	津小超仓配部
//        30	4	24	[0],[24],	运营部	津小超运营部
//        31	5	24	[0],[24],	财务部	津小超财务部
//        32	6	24	[0],[24],	行政部	津小超行政部
//        33	7	24	[0],[24],	市场部	津小超市场部

        //减红包
        //财务--市场--运营--财务(核销)
        redPacket.put(1, 31);
        redPacket.put(2, 33);
        redPacket.put(3, 30);
        redPacket.put(4, 31);
        redPacket.put(5, 0);

        //退单品
        //财务--仓配部--采购--运营--财务
        returnSingle.put(1, 31);
        returnSingle.put(2, 29);
        returnSingle.put(3, 28);
        returnSingle.put(4, 30);
        returnSingle.put(5, 31);
        returnSingle.put(6, 0);

        //未出库退单
        //财务--仓配部--运营--财务
        returnAll.put(1, 31);
        returnAll.put(2, 29);
        returnAll.put(3, 30);
        returnAll.put(4, 31);
        returnAll.put(5, 0);

        //异常订单类型
        exOrderTypeMap.put(1, "减红包");
        exOrderTypeMap.put(2, "退单品");
        exOrderTypeMap.put(3, "未出库退单");
    }


    /**
     * 审核是否通过
     */
    public interface examIfPass {
        Integer NO_PASS = 1; //未通过
        Integer PASS = 2;  //已通过
    }

    /**
     * 订单数据来源
     */
    public interface orderDataSource {
        Integer EC = 1; //电商
        Integer ERP = 2;  //erp
    }

    /**
     * 异常订单订单状态
     */
    public interface examExState {
        Integer NO_HANDEL = 1;  //未处理
        Integer HANDEL = 2;   //已处理
    }


    /**
     * 异常订单类型
     */
    public interface exOrderExType {
        Integer JHB = 1;  //减红包
        Integer TDP = 2;  //退单品
        Integer TD = 3;   //退单

    }


    //---------订单数据字段(ecErpOrder)---------------------

    /**
     * 核销状态
     */
    public interface ecErpOrderIfcheck {
        Integer DHX = 0;  //未核销
        Integer YHX = 1;  //已核销
    }


    /**
     * 订单状态
     */
    public interface ecErpOrderOrderState {
        Integer WCK = 0;  //未出库
        Integer YCK = 1;   //已出库
    }


    /**
     * 是否出库
     */
    public interface ecErpOrderIfOutStrot {
        Integer WCK = 1;  //未出库
        Integer YCK = 2;   //已出库
    }


    //------------------------------


}
