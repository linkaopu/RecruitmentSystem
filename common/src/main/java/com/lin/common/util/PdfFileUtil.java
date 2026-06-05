package com.lin.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * PDF文件工具类
 */
@Slf4j
public class PdfFileUtil {

    /**
     * PDF文件保存目录
     */
    private static final String PDF_STORAGE_DIR = "server/src/main/resources/data/resumes_pdf";

    /**
     * PDF文件路径前缀（用于前端访问）
     */
    private static final String PDF_PATH_PREFIX = "/data/resumes_pdf/";

    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_CONTENT_TYPES = {
            "application/pdf",
            "application/x-pdf",
            "application/octet-stream"
    };

    /**
     * 保存PDF文件
     *
     * @param file 上传的PDF文件
     * @return 文件访问路径
     * @throws IOException 文件保存失败时抛出
     */
    public static String savePdfFile(MultipartFile file) throws IOException {
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (!isValidPdfContentType(contentType)) {
            throw new IllegalArgumentException("只允许上传PDF格式的文件");
        }

        // 检查文件扩展名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("文件扩展名必须为.pdf");
        }

        // 生成UUID文件名
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = uuid + ".pdf";

        // 构建保存路径
        Path storagePath = Paths.get(PDF_STORAGE_DIR);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
            log.info("创建PDF存储目录: {}", storagePath.toAbsolutePath());
        }

        // 保存文件
        Path filePath = storagePath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath);

        log.info("PDF文件保存成功，原文件名: {}, 新文件名: {}, 保存路径: {}",
                originalFilename, newFilename, filePath.toAbsolutePath());

        // 返回访问路径
        return PDF_PATH_PREFIX + newFilename;
    }

    /**
     * 验证PDF文件类型
     *
     * @param contentType 文件MIME类型
     * @return 是否为有效的PDF类型
     */
    private static boolean isValidPdfContentType(String contentType) {
        if (contentType == null) {
            return false;
        }
        for (String allowedType : ALLOWED_CONTENT_TYPES) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除PDF文件
     *
     * @param filePath 文件路径（相对于resources/data/resumes_pdf的路径）
     * @return 是否删除成功
     */
    public static boolean deletePdfFile(String filePath) {
        try {
            // 提取文件名（移除路径前缀）
            String fileName = filePath;
            if (fileName.startsWith(PDF_PATH_PREFIX)) {
                fileName = fileName.substring(PDF_PATH_PREFIX.length());
            }

            Path path = Paths.get(PDF_STORAGE_DIR, fileName);
            boolean deleted = Files.deleteIfExists(path);

            if (deleted) {
                log.info("PDF文件删除成功: {}", path.toAbsolutePath());
            } else {
                log.warn("PDF文件删除失败，文件不存在: {}", path.toAbsolutePath());
            }

            return deleted;
        } catch (IOException e) {
            log.error("删除PDF文件失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取文件存储目录
     *
     * @return 存储目录路径
     */
    public static String getStorageDir() {
        return PDF_STORAGE_DIR;
    }

    /**
     * 获取文件路径前缀
     *
     * @return 路径前缀
     */
    public static String getPathPrefix() {
        return PDF_PATH_PREFIX;
    }
}
