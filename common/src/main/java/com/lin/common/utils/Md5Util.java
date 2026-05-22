package com.lin.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class Md5Util {

    /**
     * MD5加密
     *
     * @param source 原始字符串
     * @return 加密后的32位十六进制字符串
     */
    public static String md5(String source) {
        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("加密源不能为空");
        }

        try {
            // 获取MD5消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // 计算哈希值
            byte[] digest = md.digest(source.getBytes());
            
            // 转换为十六进制字符串
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }

    /**
     * MD5加密（带盐值）
     *
     * @param source 原始字符串
     * @param salt   盐值
     * @return 加密后的32位十六进制字符串
     */
    public static String md5WithSalt(String source, String salt) {
        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("加密源不能为空");
        }
        if (salt == null || salt.isEmpty()) {
            throw new IllegalArgumentException("盐值不能为空");
        }
        
        // 将盐值与原始字符串组合后加密
        return md5(source + salt);
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            // 将每个字节转换为两位十六进制数
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return md5(rawPassword).equals(encodedPassword);
    }

    /**
     * 验证密码（带盐值）
     *
     * @param rawPassword     原始密码
     * @param salt            盐值
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matchesWithSalt(String rawPassword, String salt, String encodedPassword) {
        if (rawPassword == null || salt == null || encodedPassword == null) {
            return false;
        }
        return md5WithSalt(rawPassword, salt).equals(encodedPassword);
    }
}
