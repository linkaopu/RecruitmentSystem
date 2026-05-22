package com.lin.common.constant;

/**
 * 文件相关常量
 */
public class FileConstant {
    
    /**
     * 允许上传的简历文件类型
     */
    public static final String[] ALLOWED_RESUME_TYPES = {"pdf", "doc", "docx"};
    
    /**
     * 允许上传的图片类型
     */
    public static final String[] ALLOWED_IMAGE_TYPES = {"jpg", "jpeg", "png", "gif", "webp"};
    
    /**
     * 最大简历文件大小（10MB）
     */
    public static final long MAX_RESUME_SIZE = 10 * 1024 * 1024;
    
    /**
     * 最大图片文件大小（5MB）
     */
    public static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    
    /**
     * 简历上传路径
     */
    public static final String RESUME_UPLOAD_PATH = "/uploads/resumes/";
    
    /**
     * 头像上传路径
     */
    public static final String AVATAR_UPLOAD_PATH = "/uploads/avatars/";
    
    /**
     * 附件上传路径
     */
    public static final String ATTACHMENT_UPLOAD_PATH = "/uploads/attachments/";
}
