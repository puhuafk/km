package com.stylefeng.guns.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by hp on 15-3-29.
 */
public class Base64Util {
    private Logger logger = LoggerFactory.getLogger(Base64Util.class);

    private static final int RANGE = 0xff;
    //自定义码表 可随意变换字母排列顺序，然后会自动生成解密表
    private static final char[] Base64ByteToStr = new char[] {
            'a', '1', 'd', 'G', '8', 'g', 'J', 'h', 'H', 'k', 'M', 'I', 'm',
            'q', '3', 'E', 'o', 'p', 'D', 'y', 's', '6', 'r', 'v', 'j', 'x',
            'A', 'f', 'B', 'z', 'C', 'F', 'K', 'i', 'T', 'L', 'l', 'S', 'w', 
            'N', 'U', 'O', '4', 'P', 't', 'W', 'R', 'b', 'V', 'X', 'Z', 'Y',
            '0', 'c', '2', 'n', 'Q', '5', 'u', '7', 'e', '9', '-', '_'
    };

    private static byte[] StrToBase64Byte = new byte[128];

    private void generateDecoder() throws Exception {
        for(int i = 0; i <= StrToBase64Byte.length - 1; i++) {
            StrToBase64Byte[i] = -1;
        }
        for(int i = 0; i <= Base64ByteToStr.length - 1; i++) {
            StrToBase64Byte[Base64ByteToStr[i]] = (byte)i;
        }
    }

    private void showDecoder() throws Exception {
        String str = "";
        for(int i = 1; i <= StrToBase64Byte.length; i++) {
            str += (int)StrToBase64Byte[i - 1] + ",";
            if(i % 10 == 0 || i == StrToBase64Byte.length) {
                logger.info(str);
                str = "";
            }
        }
    }

    private String Base64Encode(byte[] bytes) throws Exception {
        StringBuilder res = new StringBuilder();
        //per 3 bytes scan and switch to 4 bytes
        for(int i = 0; i <= bytes.length - 1; i+=3) {
            byte[] enBytes = new byte[4];
            byte tmp = (byte)0x00;// save the right move bit to next position's bit
            //3 bytes to 4 bytes
            for(int k = 0; k <= 2; k++) {// 0 ~ 2 is a line
                if((i + k) <= bytes.length - 1) {
                    enBytes[k] = (byte) (((((int) bytes[i + k] & RANGE) >>> (2 + 2 * k))) | (int)tmp);//note , we only get 0 ~ 127 ???
                    tmp = (byte) (((((int) bytes[i + k] & RANGE) << (2 + 2 * (2 - k))) & RANGE) >>> 2);
                } else {
                    enBytes[k] = tmp;
                    tmp = (byte)64;//if tmp > 64 then the char is '=' hen '=' -> byte is -1 , so it is EOF or not print char
                }
            }
            enBytes[3] = tmp;//forth byte
            //4 bytes to encode string
            for (int k = 0; k <= 3; k++) {
                if((int)enBytes[k] <= 63) {
                    res.append(Base64ByteToStr[(int)enBytes[k]]);
                } else {
                    res.append('=');
                }
            }
        }
        return res.toString();
    }

    private byte[] Base64Decode(String val) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//destination bytes, valid string that we want
        byte[] srcBytes = val.getBytes();
        byte[] base64bytes = new byte[srcBytes.length];
        //get the base64 bytes (the value is -1 or 0 ~ 63)
        for(int i = 0; i <= srcBytes.length - 1; i++) {
            int ind = (int) srcBytes[i];
            base64bytes[i] = StrToBase64Byte[ind];
        }
        //base64 bytes (4 bytes) to normal bytes (3 bytes)
        for(int i = 0; i <= base64bytes.length - 1; i+=4) {
            byte[] deBytes = new byte[3];
            int delen = 0;// if basebytes[i] = -1, then debytes not append this value
            byte tmp ;
            for(int k = 0; k <= 2; k++) {
                if((i + k + 1) <= base64bytes.length - 1 && base64bytes[i + k + 1] >= 0) {
                    tmp = (byte) (((int)base64bytes[i + k + 1] & RANGE) >>> (2 + 2 * (2 - (k + 1))));
                    deBytes[k] = (byte) ((((int) base64bytes[i + k] & RANGE) << (2 + 2 * k) & RANGE) | (int) tmp);
                    delen++;
                }
            }
            for(int k = 0; k <= delen - 1; k++) {
                bos.write((int)deBytes[k]);
            }
        }
        return bos.toByteArray();
    }
    public static String decode(String data) {
        Base64Util nb = new Base64Util();
        try {
			nb.generateDecoder();
	        return new String(nb.Base64Decode(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
    public static String encode(String data) {
        Base64Util nb = new Base64Util();
        try {
			nb.generateDecoder();
	        return new String(nb.Base64Encode(data.getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }

    public static void main(String[] args) throws Exception {
        Base64Util nb = new Base64Util();
        nb.generateDecoder();
        //String srcStr = "testfewa,./;'p[097&^%$$##!@#FDGSERH中国测试中文";
//        String srcStr = "{\\\"name\\\":\\\"vicken\\\",\\\"age\\\":20   }";


        String srcStr = "123456";
        String str1 = Base64Util.encode(MD5Utils.getMD5Str(srcStr));
        System.out.println(str1);
        System.out.println(Base64Util.decode(str1));


        System.out.println(" source:" + srcStr);
        String enStr = nb.Base64Encode(srcStr.getBytes());
        System.out.println("encoder:" + enStr);
        String deStr = new String(nb.Base64Decode(enStr));
        System.out.println("decoder:" + deStr);
        srcStr = "{\"token\":\"16d44bda455c02faf92c35bc23bec38a\",\"timestamp\":\"1528249659\"}";
        System.out.printf(Base64Util.encode(srcStr));
    }
}