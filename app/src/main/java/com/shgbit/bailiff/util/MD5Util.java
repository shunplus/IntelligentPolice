package com.shgbit.bailiff.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 * <p>
 * Created by db on 2018/9/22.
 */
public class MD5Util {

    /**
     * 不带盐值的加密
     *
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String createMD5(String string) {
        String str = string;
        StringBuffer stringBuffer = null;
        String hexString = null;
        try {
            //1.指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //2.将需要加密的字符串转换成byte数组
            byte[] hash = digest.digest(str.getBytes("UTF-8"));
            //3.拼接数据
            stringBuffer = new StringBuffer();
            for (byte b : hash) {
                int i = b & 0xFF;
                //int类型的i需要转换成16进制
                hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        return stringBuffer.toString();
    }

    /**
     * 带盐值的加密
     *
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String createMD5(String string, String salt) {
        String str = string + salt;
        StringBuffer stringBuffer = null;
        String hexString = null;
        try {
            //1.指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //2.将需要加密的字符串转换成byte数组
            byte[] hash = digest.digest(str.getBytes("UTF-8"));
            //3.拼接数据
            stringBuffer = new StringBuffer();
            for (byte b : hash) {
                int i = b & 0xFF;
                //int类型的i需要转换成16进制
                hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        return stringBuffer.toString();
    }
}
