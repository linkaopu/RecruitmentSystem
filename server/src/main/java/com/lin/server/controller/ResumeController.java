package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;
import com.lin.common.util.PdfFileUtil;
import com.lin.pojo.dto.CreateResumeDTO;
import com.lin.pojo.entity.Resume;
import com.lin.server.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 简历控制器
 */
@Slf4j
@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
@Tag(name = "简历模块", description = "简历管理相关接口")
public class ResumeController {

    private final ResumeService resumeService;

    /**
     * 获取当前用户的简历
     */
    @GetMapping("/me")
    @Operation(summary = "获取当前用户的简历", description = "获取当前登录用户的简历信息")
    public Result<Resume> getMyResume() {
        log.info("获取当前用户的简历");
        Resume resume = resumeService.getMyResume();
        return Result.success(resume);
    }

    /**
     * 获取简历列表（管理员）
     */
    @GetMapping
    @Operation(summary = "获取简历列表", description = "分页获取简历列表（管理员）")
    public Result<PageResult<Resume>> getResumes(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取简历列表，page: {}, pageSize: {}", page, pageSize);
        PageResult<Resume> pageResult = resumeService.getResumes(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 创建简历
     */
    @PostMapping
    @Operation(summary = "创建简历", description = "创建新的简历")
    public Result<Resume> createResume(@RequestBody CreateResumeDTO dto) {
        log.info("创建简历，姓名: {}", dto.getName());
        Resume created = resumeService.createResume(dto);
        return Result.success("简历创建成功", created);
    }

    /**
     * 更新简历
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新简历", description = "更新简历信息")
    public Result<Resume> updateResume(
            @Parameter(description = "简历ID") @PathVariable Integer id,
            @RequestBody CreateResumeDTO dto) {
        log.info("更新简历，ID: {}", id);
        Resume updated = resumeService.updateResume(id, dto);
        return Result.success("简历更新成功", updated);
    }

    /**
     * 删除简历
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除简历", description = "删除简历记录")
    public Result<Void> deleteResume(@Parameter(description = "简历ID") @PathVariable Integer id) {
        log.info("删除简历，ID: {}", id);
        resumeService.deleteResume(id);
        return Result.success("简历删除成功", null);
    }

    /**
     * 获取简历详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取简历详情", description = "根据ID获取简历详情")
    public Result<Resume> getResumeDetail(@Parameter(description = "简历ID") @PathVariable Integer id) {
        log.info("获取简历详情，ID: {}", id);
        Resume resume = resumeService.getResumeDetail(id);
        return Result.success(resume);
    }

    /**
     * 上传简历附件
     */
    @PostMapping("/upload")
    @Operation(summary = "上传简历附件", description = "上传PDF格式的简历附件")
    public Result<Map<String, String>> uploadResume(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("上传简历附件，文件名: {}", file.getOriginalFilename());
        String filePath = PdfFileUtil.savePdfFile(file);
        Map<String, String> result = new HashMap<>();
        result.put("url", filePath);
        result.put("name", file.getOriginalFilename());
        return Result.success("简历附件上传成功", result);
    }

    /**
     * 导出简历
     */
    @GetMapping("/export")
    @Operation(summary = "导出简历", description = "导出简历数据")
    public Result<Void> exportResumes() {
        log.info("导出简历");
        // TODO: 实现导出功能
        return Result.success("导出成功", null);
    }

    /**
     * 下载简历附件
     */
    @GetMapping("/download")
    @Operation(summary = "下载简历附件", description = "根据附件路径下载简历文件（PDF/Word等）")
    public ResponseEntity<Resource> downloadAttachment(
            @Parameter(description = "附件存储路径") @RequestParam("attachment_url") String attachmentUrl) {
        log.info("下载简历附件，路径: {}", attachmentUrl);

        // 解析文件路径
        String storageDir = PdfFileUtil.getStorageDir();
        String pathPrefix = PdfFileUtil.getPathPrefix();

        String fileName = attachmentUrl;
        if (fileName.startsWith(pathPrefix)) {
            fileName = fileName.substring(pathPrefix.length());
        }

        // 安全检查：防止路径穿越
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("非法的文件路径");
        }

        Path filePath = Paths.get(storageDir, fileName);
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }

        // 设置下载响应头
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replace("+", "%20");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename*=UTF-8''" + encodedFileName);

        // 根据文件扩展名设置 Content-Type
        MediaType mediaType = getMediaType(fileName);
        if (mediaType != null) {
            headers.setContentType(mediaType);
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }

    /**
     * 根据文件扩展名获取 MediaType
     */
    private MediaType getMediaType(String fileName) {
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (lowerName.endsWith(".doc")) {
            return MediaType.valueOf("application/msword");
        } else if (lowerName.endsWith(".docx")) {
            return MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
