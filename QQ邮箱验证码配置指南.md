# QQ邮箱验证码配置指南

## ✅ 已完成的工作

### 1. 添加JavaMail依赖

已在以下模块添加`spring-boot-starter-mail`依赖：
- ✅ [父pom.xml](file://F:\code\ideaPro\RecruitmentSystem\pom.xml)
- ✅ [server/pom.xml](file://F:\code\ideaPro\RecruitmentSystem\server\pom.xml)

---

### 2. 创建工具类和服务

#### EmailCodeUtil - 邮箱验证码工具类
**位置**: [EmailCodeUtil.java](file://F:\code\ideaPro\RecruitmentSystem\common\src\main\java\com\lin\common\utils\EmailCodeUtil.java)

**功能**:
- ✅ 生成6位随机验证码
- ✅ 存储验证码（内存Map，生产环境建议用Redis）
- ✅ 验证验证码（5分钟有效期）
- ✅ 发送频率限制（60秒间隔）
- ✅ 一次性使用（验证成功后自动删除）

#### EmailService - 邮件服务接口
**位置**: [EmailService.java](file://F:\code\ideaPro\RecruitmentSystem\server\src\main\java\com\lin\server\service\EmailService.java)

#### EmailServiceImpl - 邮件服务实现
**位置**: [EmailServiceImpl.java](file://F:\code\ideaPro\RecruitmentSystem\server\src\main\java\com\lin\server\service\impl\EmailServiceImpl.java)

**功能**:
- ✅ 发送HTML格式验证码邮件
- ✅ 精美的邮件模板（渐变色、响应式设计）
- ✅ 支持简单文本邮件
- ✅ 异常处理和日志记录

---

### 3. 更新Auth模块

已将手机号改为邮箱：
- ✅ [AuthService.java](file://F:\code\ideaPro\RecruitmentSystem\server\src\main\java\com\lin\server\service\AuthService.java) - 接口方法签名
- ✅ [AuthServiceImpl.java](file://F:\code\ideaPro\RecruitmentSystem\server\src\main\java\com\lin\server\service\impl\AuthServiceImpl.java) - 实现逻辑
- ✅ [AuthController.java](file://F:\code\ideaPro\RecruitmentSystem\server\src\main\java\com\lin\server\controller\AuthController.java) - 请求参数

---

## 🔧 QQ邮箱配置步骤

### 第一步：开启QQ邮箱SMTP服务

1. **登录QQ邮箱**
   - 访问：https://mail.qq.com
   - 使用您的QQ号登录

2. **进入设置**
   - 点击顶部"设置"
   - 选择"账户"标签

3. **开启SMTP服务**
   - 向下滚动找到"POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务"
   - 找到"IMAP/SMTP服务"
   - 点击"开启"

4. **获取授权码**
   - 系统会要求您发送短信验证
   - 验证通过后，会显示一个**16位授权码**
   - **重要**：复制并保存这个授权码（只显示一次）

---

### 第二步：配置application.yml

打开 `server/src/main/resources/application.yml`，修改邮件配置：

```yaml
spring:
  mail:
    host: smtp.qq.com
    port: 587
    username: 123456789@qq.com  # 替换为您的QQ邮箱
    password: abcdefghijklmnop  # 替换为您的16位授权码
    protocol: smtp
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          ssl:
            trust: smtp.qq.com
```

**注意**：
- `username`: 填写您的完整QQ邮箱地址
- `password`: 填写刚才获取的**授权码**（不是QQ密码！）

---

### 第三步：使用环境变量（推荐用于生产环境）

为了安全起见，建议使用环境变量而不是硬编码：

```yaml
spring:
  mail:
    username: ${MAIL_USERNAME:123456789@qq.com}
    password: ${MAIL_PASSWORD:abcdefghijklmnop}
```

然后在启动时设置环境变量：

**Windows PowerShell**:
```powershell
$env:MAIL_USERNAME="123456789@qq.com"
$env:MAIL_PASSWORD="abcdefghijklmnop"
mvn spring-boot:run
```

**Linux/Mac**:
```bash
export MAIL_USERNAME="123456789@qq.com"
export MAIL_PASSWORD="abcdefghijklmnop"
mvn spring-boot:run
```

---

## 📋 API接口说明

### 1. 发送验证码

**接口**: `POST /auth/send-code`

**请求体**:
```json
{
  "email": "user@example.com"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "验证码已发送",
  "data": null
}
```

**业务逻辑**:
1. 检查邮箱是否已注册
2. 检查发送频率（60秒内只能发送一次）
3. 生成6位随机验证码
4. 存储验证码（5分钟有效期）
5. 发送HTML格式邮件

---

### 2. 重置密码

**接口**: `POST /auth/reset-password`

**请求体**:
```json
{
  "email": "user@example.com",
  "code": "123456",
  "password": "newpassword123"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "密码重置成功",
  "data": null
}
```

**业务逻辑**:
1. 验证验证码是否正确
2. 检查验证码是否过期（5分钟）
3. 根据邮箱查找用户
4. 更新密码（MD5加密）
5. 删除已使用的验证码

---

## 🎨 邮件模板预览

发送的验证码邮件包含：
- 📧 **主题**: "招聘系统 - 邮箱验证码"
- 🎨 **样式**: 渐变色背景、圆角卡片、响应式设计
- 🔢 **验证码**: 大号字体、渐变色背景、醒目显示
- ℹ️ **提示**: 
  - 验证码有效期5分钟
  - 请勿将验证码告知他人
  - 如非本人操作请忽略

---

## ⚙️ 配置说明

### 验证码配置（EmailCodeUtil）

```java
private static final int CODE_LENGTH = 6;              // 验证码长度
private static final long EXPIRE_TIME = 5 * 60 * 1000L; // 5分钟有效期
private static final long SEND_INTERVAL = 60 * 1000L;   // 60秒发送间隔
```

如需修改，直接编辑 [EmailCodeUtil.java](file://F:\code\ideaPro\RecruitmentSystem\common\src\main\java\com\lin\common\utils\EmailCodeUtil.java)

---

### QQ邮箱SMTP配置参数

| 参数 | 值 | 说明 |
|------|-----|------|
| host | smtp.qq.com | QQ邮箱SMTP服务器 |
| port | 587 | TLS端口（推荐） |
| port(SSL) | 465 | SSL端口（可选） |
| username | 您的QQ邮箱 | 完整邮箱地址 |
| password | 授权码 | 16位授权码（非QQ密码） |

---

## 🧪 测试示例

### 使用curl测试

```bash
# 1. 发送验证码
curl -X POST http://localhost:8080/auth/send-code \
  -H "Content-Type: application/json" \
  -d '{"email":"123456789@qq.com"}'

# 2. 重置密码（收到验证码后）
curl -X POST http://localhost:8080/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "email":"123456789@qq.com",
    "code":"123456",
    "password":"newpassword123"
  }'
```

---

### 前端对接示例

```typescript
// 1. 发送验证码
import { sendCode } from '@/api/auth'

const handleSendCode = async () => {
  try {
    await sendCode(email)
    message.success('验证码已发送')
    // 开始倒计时60秒
    startCountdown()
  } catch (error) {
    message.error(error.response?.data?.message || '发送失败')
  }
}

// 2. 重置密码
import { resetPassword } from '@/api/auth'

const handleResetPassword = async () => {
  try {
    await resetPassword({
      email: form.email,
      code: form.code,
      password: form.newPassword
    })
    message.success('密码重置成功')
    router.push('/login')
  } catch (error) {
    message.error(error.response?.data?.message || '重置失败')
  }
}
```

---

## ⚠️ 常见问题

### 1. 认证失败 (AuthenticationFailedException)

**原因**: 授权码错误或未开启SMTP服务

**解决**:
1. 确认已开启QQ邮箱IMAP/SMTP服务
2. 确认使用的是**授权码**而非QQ密码
3. 重新获取授权码并更新配置

---

### 2. 连接超时 (ConnectTimeoutException)

**原因**: 网络问题或防火墙阻止

**解决**:
1. 检查网络连接
2. 确认防火墙允许访问smtp.qq.com:587
3. 尝试使用SSL端口465

---

### 3. 发送邮件失败但无报错

**原因**: 可能被QQ邮箱判定为垃圾邮件

**解决**:
1. 检查QQ邮箱"已发送"文件夹确认是否发送成功
2. 检查收件人垃圾邮件文件夹
3. 避免频繁发送相同内容

---

### 4. 验证码一直提示错误

**原因**: 验证码已过期或已被使用

**解决**:
1. 确认在5分钟内输入
2. 验证码只能使用一次
3. 重新发送新的验证码

---

## 🔒 安全建议

### 1. 生产环境配置

- ✅ 使用环境变量存储敏感信息
- ✅ 使用Redis存储验证码（而非内存Map）
- ✅ 添加IP限流防止恶意攻击
- ✅ 记录发送日志便于追踪

### 2. 验证码安全

- ✅ 6位数字验证码（100万种组合）
- ✅ 5分钟有效期
- ✅ 一次性使用
- ✅ 60秒发送间隔限制

### 3. 邮箱验证

- ✅ 发送前检查邮箱格式
- ✅ 发送前检查邮箱是否注册
- ✅ 使用正则表达式验证邮箱格式

---

## 🚀 下一步优化

### 1. 集成Redis（强烈推荐）

当前使用内存Map存储验证码，重启后会丢失。建议集成Redis：

```java
@Autowired
private RedisTemplate<String, String> redisTemplate;

public void storeCode(String email, String code) {
    String key = "auth:code:" + email;
    redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
}
```

### 2. 添加验证码重试限制

防止暴力破解：

```java
// 记录验证失败次数
String failKey = "auth:fail:" + email;
Integer failCount = redisTemplate.opsForValue().get(failKey);
if (failCount != null && failCount >= 5) {
    throw new BusinessException("验证失败次数过多，请稍后再试");
}
```

### 3. 支持多个邮箱服务商

```yaml
# 阿里云邮箱
spring:
  mail:
    host: smtp.mxhichina.com
    port: 465

# Gmail
spring:
  mail:
    host: smtp.gmail.com
    port: 587
```

---

## 📊 总结

✅ **已完成**:
- JavaMail依赖配置
- 邮箱验证码工具类
- 邮件发送服务
- Auth模块整合
- QQ邮箱配置

✅ **核心功能**:
- 6位随机验证码
- 5分钟有效期
- 60秒发送间隔
- HTML精美邮件
- 验证码一次性使用

✅ **可以立即测试**!

配置好QQ邮箱授权码后，即可测试完整的邮箱验证码功能！🎉
