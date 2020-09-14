package com.stylefeng.guns.modular.cancelOrder.util;

/**
 * erp和商品适配工具类
 */
public class CategoryUtil {



    //1、日化，2调料，3零食，4方便面，5酒水
    public static Integer getBigcategory(int categoryId) {

        switch (categoryId) {
            case 1:
                return 5;
//            case 2:
//                break;
//            case 3:
//                break;
//            case 4:
//                break;
//            case 5:
//                break;
//            case 6:
//                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return 5;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return 3;
            case 25:
            case 28:
            case 29:
            case 30:
                return 3;
            case 31:
                return 4;
            case 34:
                return 5;
            case 38:
            case 39:
            case 40:
                return 1;
            case 42:
            case 43:
            case 44:
                return 1;
            case 46:
                return 5;
            case 53:
            case 56:
            case 58:
                return 3;
            case 555:
            case 1365:
                return 5;
            default:
                return 1;
        }
    }


}
