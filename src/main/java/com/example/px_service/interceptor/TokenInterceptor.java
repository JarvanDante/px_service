package com.example.px_service.interceptor;

import com.example.px_service.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            writeError(response, "token missing");
            return false;
        }

        // 增加这一行：如果 token 以 "Bearer " 开头，就截取掉
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }


        try {
            Long userId = JwtUtil.parseToken(token);

            request.setAttribute("userId", userId);
        } catch (Exception e) {
            writeError(response, "token invalid");
            return false;
        }

        return true;
    }

    private void writeError(HttpServletResponse response, String msg) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                "{\"code\":401,\"message\":\"" + msg + "\",\"data\":null}"
        );

    }


}
