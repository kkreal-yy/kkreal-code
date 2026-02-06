package com.kkreal.auth.interceptor;

import com.kkreal.auth.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不拦截登录和注册接口
        String requestURI = request.getRequestURI();
        if (isPublicEndpoint(requestURI)) {
            return true;
        }

        // 从请求头中获取token
        String token = extractToken(request);
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is missing");
            return false;
        }

        try {
            // 验证token
            String username = jwtUtil.getUsernameFromToken(token);
            if (username != null && jwtUtil.validateToken(token, username)) {
                // 将用户信息存储到request中，便于后续使用
                request.setAttribute("username", username);
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return false;
            }
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Expired token");
            return false;
        }
    }

    /**
     * 判断是否为公共接口（无需认证）
     */
    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.equals("/auth/login") || 
               requestURI.equals("/auth/register") ||
               requestURI.equals("/auth/refresh") ||
               requestURI.startsWith("/swagger") ||
               requestURI.startsWith("/v3/api-docs") ||
               requestURI.equals("/v3/api-docs.yaml") ||
               requestURI.equals("/doc.html") ||
               requestURI.startsWith("/swagger-ui/") ||
               requestURI.startsWith("/swagger-resources/") ||
               requestURI.startsWith("/webjars/") ||
               requestURI.startsWith("/actuator/");
    }

    /**
     * 从请求头中提取token
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
