package com.lin.server.service.impl;

import com.lin.common.exception.AuthException;
import com.lin.common.exception.BusinessException;
import com.lin.common.exception.UserException;
import com.lin.common.utils.EmailCodeUtil;
import com.lin.common.utils.JwtUtil;
import com.lin.common.utils.Md5Util;
import com.lin.pojo.dto.LoginDTO;
import com.lin.pojo.dto.RegisterDTO;
import com.lin.pojo.entity.User;
import com.lin.pojo.vo.LoginVO;
import com.lin.server.mapper.UserMapper;
import com.lin.server.service.AuthService;
import com.lin.server.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserMapper userMapper;
    private final EmailService emailService;
    
    // TODO: 后续需要注入JWT工具类和RedisTemplate
    
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        log.info("用户登录，用户名: {}, 登录类型: {}", loginDTO.getUsername(), loginDTO.getType());
        
        // 根据登录类型进行验证
        User user;
        if ("email".equals(loginDTO.getType())) {
            // 邮箱验证码登录
            log.info("使用邮箱验证码登录，用户名: {}", loginDTO.getUsername());
            
            // 验证邮箱验证码
            if (!EmailCodeUtil.verifyCode(loginDTO.getUsername(), loginDTO.getCode())) {
                throw new BusinessException("邮箱验证码错误或已过期");
            }
            
            // 根据邮箱查找用户
            user = userMapper.selectByEmail(loginDTO.getUsername());
            if (user == null) {
                throw UserException.notFound();
            }
        } else {
            // 账号密码登录
            user = userMapper.selectByUsername(loginDTO.getUsername());
            if (user == null) {
                throw UserException.notFound();
            }
            
            // 验证密码（前端传来的密码已经是MD5加密的，需要与数据库中的密码对比）
            String encryptedPassword = Md5Util.md5(loginDTO.getPassword());
            if (!encryptedPassword.equals(user.getPassword())) {
                throw UserException.passwordError(); // 密码错误
            }
        }
        
        // 生成JWT Token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        // 构建响应
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUser(convertToUserVO(user));
        
        log.info("用户登录成功，用户名: {}", user.getUsername());
        return loginVO;
    }
    
    @Override
    @Transactional
    public LoginVO register(RegisterDTO registerDTO) {
        log.info("用户注册，用户名: {}, 邮箱: {}, 手机号: {}", 
                registerDTO.getUsername(), registerDTO.getEmail(), registerDTO.getPhone());
        
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(registerDTO.getUsername());
        if (existingUser != null) {
            throw UserException.usernameExists();
        }
        
        // 检查邮箱是否已存在
        existingUser = userMapper.selectByEmail(registerDTO.getEmail());
        if (existingUser != null) {
            throw UserException.emailExists();
        }
        
        // 如果提供了手机号，检查手机号是否已存在
        if (registerDTO.getPhone() != null && !registerDTO.getPhone().isEmpty()) {
            existingUser = userMapper.selectByPhone(registerDTO.getPhone());
            if (existingUser != null) {
                throw UserException.phoneExists();
            }
        }
        
        // 验证邮箱验证码（如果提供了验证码）
        if (registerDTO.getCode() != null && !registerDTO.getCode().isEmpty()) {
            if (!EmailCodeUtil.verifyCode(registerDTO.getEmail(), registerDTO.getCode())) {
                throw new BusinessException("邮箱验证码错误或已过期");
            }
        }
        
        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        
        // 对密码进行MD5加密
        user.setPassword(Md5Util.md5(registerDTO.getPassword()));
        
        // 设置默认值
        user.setIsDelete(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // 插入数据库
        userMapper.insert(user);
        
        // 生成JWT Token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        // 构建响应
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUser(convertToUserVO(user));
        
        log.info("用户注册成功，用户名: {}", user.getUsername());
        return loginVO;
    }
    
    @Override
    public void logout(String token) {
        log.info("用户登出");
        // TODO: 将token加入黑名单（使用Redis）
    }
    
    @Override
    public void sendCode(String email) {
        log.info("发送邮箱验证码，邮箱: {}", email);
        
        // 检查发送频率限制
        if (!EmailCodeUtil.canSendCode(email)) {
            throw new BusinessException("发送验证码过于频繁，请60秒后再试");
        }
        
        // 生成验证码
        String code = EmailCodeUtil.generateCode();
        
        // 存储验证码
        EmailCodeUtil.storeCode(email, code);
        EmailCodeUtil.updateLastSendTime(email);
        
        // 发送邮件
        try {
            emailService.sendVerificationCode(email, code);
            log.info("验证码邮件发送成功，邮箱: {}", email);
        } catch (Exception e) {
            log.error("验证码邮件发送失败，邮箱: {}, 错误: {}", email, e.getMessage());
            // 发送失败时清除验证码
            EmailCodeUtil.clearCode(email);
            throw new BusinessException("验证码发送失败，请稍后重试");
        }
    }
    
    @Override
    public void resetPassword(String email, String code, String newPassword) {
        log.info("重置密码，邮箱: {}", email);
        
        // 验证验证码是否正确
        if (!EmailCodeUtil.verifyCode(email, code)) {
            throw new BusinessException("验证码错误或已过期");
        }
        
        // 根据邮箱查找用户
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            throw UserException.notFound();
        }
        
        // 更新密码（MD5加密）
        user.setPassword(Md5Util.md5(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
        
        log.info("密码重置成功，邮箱: {}", email);
    }
    
    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        log.info("修改密码，用户ID: {}", userId);
        
        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw UserException.notFound();
        }
        
        // 验证旧密码
        String encryptedOldPassword = Md5Util.md5(oldPassword);
        if (!encryptedOldPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("原密码错误");
        }
        
        // 更新新密码
        user.setPassword(Md5Util.md5(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
        
        log.info("密码修改成功，用户ID: {}", userId);
    }
    
    /**
     * 将User实体转换为LoginVO.UserVO
     */
    private LoginVO.UserVO convertToUserVO(User user) {
        LoginVO.UserVO userVO = new LoginVO.UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
