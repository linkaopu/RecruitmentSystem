package com.lin.common.exception;

/**
 * 文件上传异常
 */
public class FileUploadException extends BusinessException {
    
    public FileUploadException(String message) {
        super(400, message);
    }
    
    public FileUploadException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 文件为空
     */
    public static FileUploadException fileEmpty() {
        return new FileUploadException("上传文件不能为空");
    }
    
    /**
     * 文件大小超限
     */
    public static FileUploadException fileSizeExceeded() {
        return new FileUploadException("文件大小超过限制");
    }
    
    /**
     * 文件类型不支持
     */
    public static FileUploadException fileTypeNotSupported() {
        return new FileUploadException("不支持的文件类型");
    }
    
    /**
     * 文件上传失败
     */
    public static FileUploadException uploadFailed() {
        return new FileUploadException("文件上传失败，请稍后重试");
    }
}
