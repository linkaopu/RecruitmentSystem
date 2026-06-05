package com.lin.server.filter;

import com.lin.common.result.Result;
import com.lin.common.util.BaseContextUtil;
import com.lin.common.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT认证过滤器
 * 用于验证请求中的JWT Token，并将用户信息存储到ThreadLocal中
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 不需要验证Token的路径列表
     */
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/auth/login",          // 登录
            "/api/auth/register",       // 注册
            "/api/auth/send-code",      // 发送验证码
            "/api/auth/reset-password", // 重置密码
            "/api/swagger-ui/**",       // Swagger UI
            "/api/v3/api-docs/**",      // Swagger API文档
            "/api/error",                // 错误页面
            "/api/jobs/hot",      // 热门职位
            "/api/jobs/latest"    // 最新职位
    );

    /**
     * 路径匹配器
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * ObjectMapper用于序列化响应
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestUri = request.getRequestURI();
        String method = request.getMethod(); // 获取请求方法
        log.debug("请求URI: {}", requestUri);
        
        // 检查是否为不需要验证的路径
        if (isExcludedPath(requestUri)) {
            log.debug("路径 {} 不需要JWT验证", requestUri);
            filterChain.doFilter(request, response);
            return;
        }



        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }


        // 2. 【精准放行】只放行 GET /api/jobs
        if ("GET".equalsIgnoreCase(method) && "/api/jobs".equals(requestUri)) {
            log.debug("精准放行 GET /api/jobs 接口");
            filterChain.doFilter(request, response);
            return;
        }
        
        // 从请求头获取Token
        String authHeader = request.getHeader(JwtUtil.HEADER_NAME);
        log.debug("Authorization header: {}", authHeader);



        if (authHeader == null || authHeader.isEmpty()) {
            log.warn("请求 {} 缺少Authorization请求头", requestUri);
            sendErrorResponse(response, 401, "未登录，请先登录");
            return;
        }
        
        // 提取Token（去除Bearer前缀）
        String token = JwtUtil.extractToken(authHeader);
        
        if (token == null || token.isEmpty()) {
            log.warn("请求 {} 的Token为空", requestUri);
            sendErrorResponse(response, 401, "无效的Token");
            return;
        }
        
        // 验证Token
        if (!JwtUtil.validateToken(token)) {
            log.warn("请求 {} 的Token无效或已过期", requestUri);
            sendErrorResponse(response, 401, "Token已过期，请重新登录");
            return;
        }
        
        // 解析Token中的用户信息
        Integer userId = JwtUtil.getUserIdFromToken(token);
        String userName = JwtUtil.getUserNameFromToken(token);
        String role = JwtUtil.getRoleFromToken(token);
        
        if (userId == null) {
            log.warn("请求 {} 的Token中用户ID为空", requestUri);
            sendErrorResponse(response, 401, "无效的Token");
            return;
        }
        
        // 将用户信息存储到ThreadLocal中
        BaseContextUtil.setUserId(userId);
        BaseContextUtil.setUserName(userName);
        BaseContextUtil.setRole(role);
        
        log.debug("用户ID: {}, 用户名: {}, 角色: {} 已存入ThreadLocal", userId, userName, role);
        
        try {
            // 继续过滤器链
            filterChain.doFilter(request, response);
        } finally {
            // 请求结束后清除ThreadLocal数据，防止内存泄漏
            BaseContextUtil.clear();
            log.debug("ThreadLocal已清除");
        }
    }
    
    /**
     * 判断路径是否在排除列表中
     *
     * @param requestUri 请求路径
     * @return true-需要排除，false-需要验证
     */
    private boolean isExcludedPath(String requestUri) {
        for (String excludedPath : EXCLUDED_PATHS) {
            if (pathMatcher.match(excludedPath, requestUri)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 发送错误响应
     *
     * @param response 响应对象
     * @param code     错误码
     * @param message  错误信息
     */
    private void sendErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        
        Result<Void> result = Result.error(code, message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
