package com.example.px_service.common.routes;

public final class ApiRoutes {

    private ApiRoutes() {
    }

    //版本组
    public static final String API_V1 = "/api/v1";
    //模块组
    public static final String FRONTEND = "/frontend";
    //业务组
    public static final String AUTH = API_V1 + FRONTEND + "/auth";
    public static final String USER = API_V1 + FRONTEND + "/user";

    //----------frontend完整路由----------
    //登录
    public static final String AUTH_LOGIN = AUTH + "/login";
    //注册
    public static final String AUTH_REGISTER = AUTH + "/register";
    //查询用户信息
    public static final String USER_PROFILE = USER + "/profile";
    //查询用户列表
    public static final String USER_LIST = USER + "/list";
    //查询用户
    public static final String USER_ONE = USER + "/{id}";
    //删除用户
    public static final String USER_DELETE = USER + "/{id}";

    public static final String[] PUBLIC_WHITELIST = {
            AUTH_LOGIN,
            AUTH_REGISTER,
            "/error"
    };
}
