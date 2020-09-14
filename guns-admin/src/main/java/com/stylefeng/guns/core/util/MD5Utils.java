package com.stylefeng.guns.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Title: </p>
 *
 * <p> Description: </p>
 * <p>Copyright: www.inspur.com</p>
 *
 * <p>Company: www.inspur.com</p>
 *
 * @author chenqifu
 * @version 1.0
 * @2013-7-31
 */
public class MD5Utils {

	   /** 
     * MD5 加密 
     */  
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }  
  
        return md5StrBuff.toString();  
    } 
    
	   /** 
  * MD5 加密 
  */  
 public static String getGBKMD5Str(String str) {
     MessageDigest messageDigest = null;

     try {  
         messageDigest = MessageDigest.getInstance("MD5");

         messageDigest.reset();  
         messageDigest.update(str.getBytes("GBK"));  
     } catch (NoSuchAlgorithmException e) {
         System.out.println("NoSuchAlgorithmException caught!");
         System.exit(-1);
     } catch (UnsupportedEncodingException e) {
         e.printStackTrace();  
     }  

     byte[] byteArray = messageDigest.digest();  

     StringBuffer md5StrBuff = new StringBuffer();

     for (int i = 0; i < byteArray.length; i++) {              
         if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
             md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
         else  
             md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
     }  

     return md5StrBuff.toString();  
 } 
}
