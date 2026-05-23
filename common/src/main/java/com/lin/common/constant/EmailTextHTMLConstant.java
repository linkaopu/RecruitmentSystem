package com.lin.common.constant;

public class EmailTextHTMLConstant {
    public static final String VERIFICATION_CODE_HTML = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body {
                            font-family: 'Microsoft YaHei', Arial, sans-serif;
                            background-color: #f5f5f5;
                            padding: 20px;
                        }
                        .container {
                            max-width: 600px;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 10px;
                            padding: 40px;
                            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        }
                        .header {
                            text-align: center;
                            margin-bottom: 30px;
                        }
                        .logo {
                            font-size: 28px;
                            font-weight: bold;
                            color: #1890ff;
                        }
                        .content {
                            text-align: center;
                            padding: 20px 0;
                        }
                        .code-box {
                            display: inline-block;
                            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
                            color: white;
                            font-size: 36px;
                            font-weight: bold;
                            letter-spacing: 8px;
                            padding: 20px 40px;
                            border-radius: 8px;
                            margin: 20px 0;
                        }
                        .tips {
                            color: #666;
                            font-size: 14px;
                            line-height: 1.8;
                            margin-top: 30px;
                            text-align: left;
                        }
                        .footer {
                            margin-top: 40px;
                            padding-top: 20px;
                            border-top: 1px solid #eee;
                            color: #999;
                            font-size: 12px;
                            text-align: center;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <div class="logo">招聘系统</div>
                        </div>
                        <div class="content">
                            <h2 style="color: #333;">邮箱验证码</h2>
                            <p style="color: #666; margin: 20px 0;">您的验证码如下：</p>
                            <div class="code-box">%s</div>
                            <div class="tips">
                                <p><strong>温馨提示：</strong></p>
                                <ul>
                                    <li>验证码有效期为 <strong>5分钟</strong></li>
                                    <li>请勿将验证码告知他人</li>
                                    <li>如非本人操作，请忽略此邮件</li>
                                </ul>
                            </div>
                        </div>
                        <div class="footer">
                            <p>此邮件由招聘系统自动发送，请勿回复</p>
                            <p>(c) 2024 招聘系统. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """;
}
