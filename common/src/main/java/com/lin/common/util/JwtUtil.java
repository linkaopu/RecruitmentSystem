package com.lin.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成、解析和验证JWT Token
 */
@Slf4j
public class JwtUtil {
    
    /**
     * 默认密钥（生产环境应该从配置文件读取）
     */
    private static final String SECRET_KEY = "recruitment-system-secret-key-2024-very-long-and-secure";
    
    /**
     * 默认过期时间：7天（单位：毫秒）
     */
    private static final long DEFAULT_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;
    
    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    
    /**
     * Token请求头名称
     */
    public static final String HEADER_NAME = "Authorization";
    
    /**
     * Claims中的用户ID键名
     */
    public static final String CLAIM_USER_ID = "userId";
    
    /**
     * Claims中的用户名片名
     */
    public static final String CLAIM_USER_NAME = "userName";
    
    /**
     * Claims中的角色键名
     */
    public static final String CLAIM_ROLE = "role";
    
    /**
     * 生成密钥
     */
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * 生成JWT Token
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @return JWT Token字符串
     */
    public static String generateToken(Integer userId, String userName) {
        return generateToken(userId, userName, null, DEFAULT_EXPIRATION);
    }
    
    /**
     * 生成JWT Token（带角色信息）
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @param role     用户角色
     * @return JWT Token字符串
     */
    public static String generateToken(Integer userId, String userName, String role) {
        return generateToken(userId, userName, role, DEFAULT_EXPIRATION);
    }
    
    /**
     * 生成JWT Token（自定义过期时间）
     *
     * @param userId     用户ID
     * @param userName   用户名
     * @param expiration 过期时间（毫秒）
     * @return JWT Token字符串
     */
    public static String generateToken(Integer userId, String userName, long expiration) {
        return generateToken(userId, userName, null, expiration);
    }
    
    /**
     * 生成JWT Token（完整参数）
     *
     * @param userId     用户ID
     * @param userName   用户名
     * @param role       用户角色
     * @param expiration 过期时间（毫秒）
     * @return JWT Token字符串
     */
    public static String generateToken(Integer userId, String userName, String role, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USER_ID, userId);
        claims.put(CLAIM_USER_NAME, userName);
        if (role != null && !role.isEmpty()) {
            claims.put(CLAIM_ROLE, role);
        }
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        String token = Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
        
        log.debug("生成JWT Token成功，用户ID: {}, 用户名: {}", userId, userName);
        return token;
    }
    
    /**
     * 从Token中获取Claims
     *
     * @param token JWT Token
     * @return Claims对象
     */
    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析JWT Token失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 从Token中获取用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     */
    public static Integer getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get(CLAIM_USER_ID, Integer.class);
    }
    
    /**
     * 从Token中获取用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public static String getUserNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get(CLAIM_USER_NAME, String.class);
    }
    
    /**
     * 从Token中获取用户角色
     *
     * @param token JWT Token
     * @return 用户角色
     */
    public static String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get(CLAIM_ROLE, String.class);
    }
    
    /**
     * 验证Token是否有效
     *
     * @param token JWT Token
     * @return true-有效，false-无效
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims != null && !isTokenExpired(claims);
        } catch (Exception e) {
            log.error("验证JWT Token失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查Token是否已过期
     *
     * @param claims Claims对象
     * @return true-已过期，false-未过期
     */
    private static boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
    
    /**
     * 获取Token的过期时间
     *
     * @param token JWT Token
     * @return 过期时间
     */
    public static Date getExpirationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        return claims.getExpiration();
    }
    
    /**
     * 从请求头中提取Token（去除Bearer前缀）
     *
     * @param authHeader Authorization请求头
     * @return 纯Token字符串
     */
    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return authHeader;
    }
}
