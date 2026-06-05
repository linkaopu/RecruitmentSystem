package com.lin.common.util;

/**
 * ThreadLocal上下文工具类
 * 用于存储当前请求的用户信息，如用户ID、用户名、角色等
 */
public class BaseContextUtil {

    /**
     * 用户ID的ThreadLocal
     */
    private static final ThreadLocal<Integer> userIdThreadLocal = new ThreadLocal<>();

    /**
     * 用户名的ThreadLocal
     */
    private static final ThreadLocal<String> userNameThreadLocal = new ThreadLocal<>();

    /**
     * 用户角色的ThreadLocal
     */
    private static final ThreadLocal<String> roleThreadLocal = new ThreadLocal<>();

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public static void setUserId(Integer userId) {
        userIdThreadLocal.set(userId);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static Integer getUserId() {
        return userIdThreadLocal.get();
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public static void setUserName(String userName) {
        userNameThreadLocal.set(userName);
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        return userNameThreadLocal.get();
    }

    /**
     * 设置用户角色
     *
     * @param role 用户角色
     */
    public static void setRole(String role) {
        roleThreadLocal.set(role);
    }

    /**
     * 获取用户角色
     *
     * @return 用户角色
     */
    public static String getRole() {
        return roleThreadLocal.get();
    }

    /**
     * 清除所有ThreadLocal数据
     * 应在请求结束时调用，防止内存泄漏
     */
    public static void clear() {
        userIdThreadLocal.remove();
        userNameThreadLocal.remove();
        roleThreadLocal.remove();
    }
}
