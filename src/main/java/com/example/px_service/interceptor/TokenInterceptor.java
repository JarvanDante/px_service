package com.example.px_service.interceptor;

import com.example.px_service.common.code.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.util.JwtUtil;
import com.example.px_service.util.UserContext;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public TokenInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 预处理：Controller 执行前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            throw new BizException(BizCode.TOKEN_MISSING);
        }

        // 增加这一行：如果 token 以 "Bearer " 开头，就截取掉
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }


        try {
            Long userId = jwtUtil.getUserIdFromToken(token);
            Long siteId = jwtUtil.getSiteIdFromToken(token);
            Long channelId = jwtUtil.getChannelIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);

            //存储到ThreadLocal
            UserContext.setUserId(userId);
            UserContext.setUsername(username);
            UserContext.setSiteId(siteId);
            UserContext.setChannelId(channelId);
            UserContext.setExtra("token", token);
//            request.setAttribute("userId", userId);
//            request.setAttribute("siteId", siteId);
//            request.setAttribute("channelId", channelId);
//            request.setAttribute("username", username);
        } catch (ExpiredJwtException e) {
            throw new BizException(BizCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new BizException(BizCode.TOKEN_INVALID);
        }

        return true;
    }

    /**
     * 后处理：Controller 执行后，视图渲染前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        Long userId = UserContext.getUserId();
        if (userId != null) {
            // 记录请求完成
//            请求日志
        }
        System.out.println(">>> postHandle: Controller 执行完毕，正在渲染视图");
    }


    /**
     * 完成处理：整个请求结束
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println(">>> afterCompletion: 请求完全结束");
        // 务必在这里清理 ThreadLocal，防止内存泄漏 例如：UserContext.clear();
        UserContext.clear();
    }
}
