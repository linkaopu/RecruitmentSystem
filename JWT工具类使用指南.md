# JWT工具类使用指南

## ✅ 已完成的工作

### 1. 添加JWT依赖

已在以下模块的pom.xml中添加JWT依赖（jjwt 0.12.3）：
- ✅ [父pom.xml](file://F:\code\ideaPro\RecruitmentSystem\pom.xml) - 版本管理
- ✅ [common/pom.xml](file://F:\code\ideaPro\RecruitmentSystem\common\pom.xml) - 工具类依赖
- ✅ [server/pom.xml](file://F:\code\ideaPro\RecruitmentSystem\server\pom.xml) - 服务层依赖

**使用的库**: [JJWT (Java JWT)](https://github.com/jwtk/jjwt) 0.12.3

---

### 2. 创建JWT工具类

**文件位置**: [JwtUtil.java](file://F:\code\ideaPro\RecruitmentSystem\common\src\main\java\com\lin\common\utils\JwtUtil.java)

**核心功能**:
- ✅ 生成JWT Token（包含userId和userName）
- ✅ 解析Token获取用户信息
- ✅ 验证Token有效性
- ✅ 提取Bearer Token

---

## 📋 Token结构

生成的JWT Token包含以下Claims（负载信息）：

```json
{
  "userId": 1,              // 用户ID（Integer类型）
  "userName": "admin",      // 用户名（String类型）
  "role": "admin",          // 用户角色（可选，String类型）
  "iat": 1700000000,        // 签发时间（Issued At）
  "exp": 1700604800         // 过期时间（Expiration）
}
```

**默认配置**:
- 密钥: `recruitment-system-secret-key-2024-very-long-and-secure`
- 过期时间: **7天**（可自定义）
- 签名算法: **HMAC-SHA256**

---

## 🎯 常用方法说明

### 1. 生成Token

#### 基础用法（推荐）
```java
// 生成包含userId、userName和role的Token，有效期7天
String token = JwtUtil.generateToken(userId, userName, role);
```

#### 其他重载方法
```java
// 只包含userId和userName
String token = JwtUtil.generateToken(userId, userName);

// 自定义过期时间（单位：毫秒）
long expiration = 24 * 60 * 60 * 1000L; // 1天
String token = JwtUtil.generateToken(userId, userName, expiration);

// 完整参数
String token = JwtUtil.generateToken(userId, userName, role, expiration);
```

---

### 2. 解析Token

#### 获取用户ID
```java
Integer userId = JwtUtil.getUserIdFromToken(token);
if (userId != null) {
    System.out.println("用户ID: " + userId);
} else {
    System.out.println("Token无效或已过期");
}
```

#### 获取用户名
```java
String userName = JwtUtil.getUserNameFromToken(token);
```

#### 获取用户角色
```java
String role = JwtUtil.getRoleFromToken(token);
```

---

### 3. 验证Token

#### 验证Token是否有效
```java
boolean isValid = JwtUtil.validateToken(token);
if (isValid) {
    System.out.println("Token有效");
} else {
    System.out.println("Token无效或已过期");
}
```

#### 获取过期时间
```java
Date expiration = JwtUtil.getExpirationFromToken(token);
System.out.println("过期时间: " + expiration);
```

---

### 4. 从请求头提取Token

#### 去除Bearer前缀
```java
// 前端发送的请求头格式: "Authorization: Bearer eyJhbGci..."
String authHeader = request.getHeader("Authorization");
String pureToken = JwtUtil.extractToken(authHeader);
// 结果: "eyJhbGci..."（去除了"Bearer "前缀）
```

---

## 💻 实际应用场景

### 场景1: 用户登录时生成Token

```java
@PostMapping("/login")
public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
    // 1. 验证用户名和密码
    User user = userService.login(loginDTO);
    
    // 2. 生成JWT Token
    String token = JwtUtil.generateToken(
        user.getId(), 
        user.getUsername(), 
        user.getRole()
    );
    
    // 3. 返回Token和用户信息
    LoginVO loginVO = new LoginVO();
    loginVO.setToken(token);
    loginVO.setUser(convertToVO(user));
    
    return Result.success("登录成功", loginVO);
}
```

---

### 场景2: 拦截器中验证Token

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
                            HttpServletResponse response, 
                            Object handler) {
        // 1. 从请求头获取Token
        String authHeader = request.getHeader("Authorization");
        String token = JwtUtil.extractToken(authHeader);
        
        // 2. 验证Token
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        // 3. 解析用户信息并存入请求属性
        Integer userId = JwtUtil.getUserIdFromToken(token);
        String userName = JwtUtil.getUserNameFromToken(token);
        request.setAttribute("userId", userId);
        request.setAttribute("userName", userName);
        
        return true;
    }
}
```

---

### 场景3: Controller中获取当前用户

```java
@PostMapping("/change-password")
public Result<Void> changePassword(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody ChangePasswordRequest request) {
    
    // 1. 提取并解析Token
    String token = JwtUtil.extractToken(authHeader);
    Integer userId = JwtUtil.getUserIdFromToken(token);
    
    if (userId == null) {
        throw new IllegalArgumentException("无效的Token");
    }
    
    // 2. 修改密码
    authService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
    
    return Result.success("密码修改成功", null);
}
```

---

### 场景4: 注册后自动登录

```java
@PostMapping("/register")
public Result<LoginVO> register(@RequestBody RegisterDTO registerDTO) {
    // 1. 创建用户
    User user = authService.register(registerDTO);
    
    // 2. 自动生成Token（实现注册即登录）
    String token = JwtUtil.generateToken(
        user.getId(), 
        user.getUsername(), 
        user.getRole()
    );
    
    // 3. 返回Token
    LoginVO loginVO = new LoginVO();
    loginVO.setToken(token);
    loginVO.setUser(convertToVO(user));
    
    return Result.success("注册成功", loginVO);
}
```

---

## 🔧 配置说明

### 1. 修改密钥（生产环境）

在`application.yml`中配置：
```yaml
jwt:
  secret: your-very-long-and-secure-secret-key-at-least-32-characters
  expiration: 604800000  # 7天（毫秒）
```

然后修改JwtUtil读取配置：
```java
@Value("${jwt.secret}")
private String secretKey;

@Value("${jwt.expiration}")
private long expiration;
```

---

### 2. 修改过期时间

```java
// 1小时
long oneHour = 60 * 60 * 1000L;
String token = JwtUtil.generateToken(userId, userName, oneHour);

// 30天
long thirtyDays = 30L * 24 * 60 * 60 * 1000;
String token = JwtUtil.generateToken(userId, userName, thirtyDays);
```

---

## 🌐 前端对接示例

### TypeScript/JavaScript

```typescript
// 1. 登录
const loginResponse = await fetch('/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'admin',
    password: '123456',
    type: 'account'
  })
})

const data = await loginResponse.json()
const token = data.data.token

// 2. 保存Token
localStorage.setItem('token', token)
localStorage.setItem('userId', JwtUtil.getUserIdFromToken(token)) // 需要自己实现解析

// 3. 后续请求携带Token
const response = await fetch('/api/users', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
})

// 4. 修改密码
await fetch('/auth/change-password', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    oldPassword: '123456',
    newPassword: 'newpassword'
  })
})
```

---

## ⚠️ 注意事项

### 1. 安全性
- ✅ 密钥长度至少32个字符
- ✅ 不要在前端暴露密钥
- ✅ 生产环境使用环境变量配置密钥
- ✅ HTTPS传输Token

### 2. Token存储
- ✅ 前端建议使用`localStorage`或`sessionStorage`
- ✅ 后端可以使用Redis实现Token黑名单（用于登出）

### 3. 过期处理
```java
// 前端拦截器中处理Token过期
if (response.status === 401) {
  // Token过期或无效，跳转登录页
  localStorage.removeItem('token')
  window.location.href = '/login'
}
```

### 4. 刷新Token
如果需要实现Token自动刷新，可以：
- 方案1: 设置较长的过期时间（如7天）
- 方案2: 实现Refresh Token机制
- 方案3: 每次请求后延长Token有效期

---

## 📊 测试示例

### 单元测试

```java
@SpringBootTest
class JwtUtilTest {
    
    @Test
    void testGenerateAndParseToken() {
        // 生成Token
        String token = JwtUtil.generateToken(1, "admin", "admin");
        
        // 验证Token
        assertTrue(JwtUtil.validateToken(token));
        
        // 解析用户信息
        assertEquals(1, JwtUtil.getUserIdFromToken(token));
        assertEquals("admin", JwtUtil.getUserNameFromToken(token));
        assertEquals("admin", JwtUtil.getRoleFromToken(token));
    }
    
    @Test
    void testExpiredToken() {
        // 生成已过期的Token（1毫秒后过期）
        String token = JwtUtil.generateToken(1, "admin", 1L);
        
        // 等待1毫秒
        Thread.sleep(10);
        
        // 验证Token已失效
        assertFalse(JwtUtil.validateToken(token));
    }
}
```

---

## 🚀 总结

JWT工具类已经完整实现并集成到项目中：

✅ **核心功能**
- 生成包含userId和userName的Token
- 解析Token获取用户信息
- 验证Token有效性
- 支持Bearer Token提取

✅ **已集成的模块**
- AuthServiceImpl: 登录和注册时生成Token
- AuthController: 修改密码时解析Token

✅ **与前端对接**
- Token格式标准，兼容所有JWT库
- 支持Bearer认证方式
- 7天默认有效期

**可以立即使用！** 🎉
