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
    public static final String AUTH_LOGIN = AUTH + "/login";
    public static final String AUTH_REGISTER = AUTH + "/register";

    public static final String USER_PROFILE = USER + "/profile";

    public static final String USER_LIST = USER + "/list";

    public static final String USER_ONE = USER + "/{id}";

    public static final String[] PUBLIC_WHITELIST = {
            AUTH_LOGIN,
            AUTH_REGISTER,
            "/error"
    };
}
