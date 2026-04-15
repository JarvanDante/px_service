package com.example.px_service.interceptor;

import com.example.px_service.common.context.UserContext;
import com.example.px_service.common.enums.BizCode;
import com.example.px_service.common.exception.BizException;
import com.example.px_service.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    private static final String REQUEST_START_TIME = "requestStartTime";
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
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
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
        Long siteId = UserContext.getSiteId();
        Long channelId = UserContext.getChannelId();

        //请求计算：耗时
        Long startedAt = (Long) request.getAttribute(REQUEST_START_TIME);
        long costMs = startedAt == null ? -1L : System.currentTimeMillis() - startedAt;

        String query = request.getQueryString();
        String path = query == null ? request.getRequestURI() : request.getRequestURI() + "?" + query;
        String clientIp = getClientIp(request);

        log.info("request postHandle method={} path={} status={} ip={} costMs={} userId={} siteId={} channelId={}",
                request.getMethod(),
                path,
                response.getStatus(),
                clientIp,
                costMs,
                userId,
                siteId,
                channelId
        );
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
        // 务必在这里清理 ThreadLocal，防止内存泄漏 例如：UserContext.clear();
        UserContext.clear();
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }
}
