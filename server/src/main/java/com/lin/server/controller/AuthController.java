package com.lin.server.controller;

import com.lin.common.result.Result;
import com.lin.common.utils.JwtUtil;
import com.lin.pojo.dto.ChangePasswordDTO;
import com.lin.pojo.dto.LoginDTO;
import com.lin.pojo.dto.RegisterDTO;
import com.lin.pojo.dto.ResetPasswordDTO;
import com.lin.pojo.dto.SendCodeDTO;
import com.lin.pojo.vo.LoginVO;
import com.lin.server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证模块", description = "用户登录、注册、登出等认证相关接口")
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "支持账号密码登录和手机验证码登录")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("收到登录请求，用户名: {}", loginDTO.getUsername());
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册账号")
    public Result<LoginVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("收到注册请求，用户名: {}", registerDTO.getUsername());
        LoginVO loginVO = authService.register(registerDTO);
        return Result.success("注册成功", loginVO);
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "退出登录，使Token失效")
    public Result<Void> logout(
            @Parameter(description = "JWT Token", required = true) 
            @RequestHeader("Authorization") String token) {
        // 移除Bearer前缀
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        authService.logout(token);
        return Result.success("登出成功", null);
    }
    
    /**
     * 发送邮箱验证码
     */
    @PostMapping("/send-code")
    @Operation(summary = "发送邮箱验证码", description = "向指定邮箱发送6位验证码")
    public Result<Void> sendCode(@Valid @RequestBody SendCodeDTO sendCodeDTO) {
        log.info("收到发送验证码请求，邮箱: {}", sendCodeDTO.getEmail());
        authService.sendCode(sendCodeDTO.getEmail());
        return Result.success("验证码已发送", null);
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "通过邮箱验证码重置密码")
    public Result<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        log.info("收到重置密码请求，邮箱: {}", resetPasswordDTO.getEmail());
        authService.resetPassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getCode(), resetPasswordDTO.getPassword());
        return Result.success("密码重置成功", null);
    }
    
    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    @Operation(summary = "修改密码", description = "登录后修改密码，需要验证旧密码")
    public Result<Void> changePassword(
            @Parameter(description = "JWT Token", required = true) 
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        // 移除Bearer前缀并解析用户ID
        String pureToken = JwtUtil.extractToken(token);
        Integer userId = JwtUtil.getUserIdFromToken(pureToken);
        if (userId == null) {
            throw new IllegalArgumentException("无效的Token");
        }
        authService.changePassword(userId, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return Result.success("密码修改成功", null);
    }
}
