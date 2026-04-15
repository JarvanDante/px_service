package com.example.px_service.util;

import java.util.HashMap;
import java.util.Map;

public class UserContext {
    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<Long> SITE_ID_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<Long> CHANNEL_ID_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<String> USERNAME_HOLDER = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Object>> EXTRA_HOLDER = new ThreadLocal<>();

    /**
     * 设置用户ID
     *
     * @param userId
     */
    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }


    /**
     * 设置站点id
     *
     * @param siteId
     */
    public static void setSiteId(Long siteId) {
        SITE_ID_HOLDER.set(siteId);
    }

    /**
     * 获取站点id
     *
     * @return
     */
    public static Long getSiteId() {
        return SITE_ID_HOLDER.get();
    }

    /**
     * 设置渠道id
     *
     * @param channelId
     */
    public static void setChannelId(Long channelId) {
        CHANNEL_ID_HOLDER.set(channelId);
    }

    /**
     * 获取渠道id
     *
     * @return
     */
    public static Long getChannelId() {
        return CHANNEL_ID_HOLDER.get();
    }

    /**
     * 设置username
     *
     * @param username
     */
    public static void setUsername(String username) {
        USERNAME_HOLDER.set(username);
    }


    /**
     * 获取username
     *
     * @return
     */
    public static String getUsername() {
        return USERNAME_HOLDER.get();
    }

    /**
     * 设置扩展信息
     *
     * @param key
     * @param value
     */
    public static void setExtra(String key, Object value) {
        Map<String, Object> map = EXTRA_HOLDER.get();
        if (map == null) {
            map = new HashMap<>();
            EXTRA_HOLDER.set(map);
        }
        map.put(key, value);
    }

    /**
     * 获取扩展信息
     *
     * @param key
     * @return
     */
    public static Object getExtra(String key) {
        Map<String, Object> map = EXTRA_HOLDER.get();
        return map != null ? map.get(key) : null;
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
        USERNAME_HOLDER.remove();
        SITE_ID_HOLDER.remove();
        CHANNEL_ID_HOLDER.remove();
        EXTRA_HOLDER.remove();
    }

    public static boolean isLogin() {
        return USER_ID_HOLDER.get() != null;
    }
}
