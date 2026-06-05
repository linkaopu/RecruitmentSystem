CREATE DATABASE if not EXISTS recruitment_system;


-- 设置字符集和引擎
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 删除已存在的表（按依赖顺序）
DROP TABLE IF EXISTS `system_logs`;
DROP TABLE IF EXISTS `notifications`;
DROP TABLE IF EXISTS `interviews`;
DROP TABLE IF EXISTS `favorites`;
DROP TABLE IF EXISTS `applications`;
DROP TABLE IF EXISTS `project_experiences`;
DROP TABLE IF EXISTS `education_history`;
DROP TABLE IF EXISTS `work_experiences`;
DROP TABLE IF EXISTS `resumes`;
DROP TABLE IF EXISTS `jobs`;
DROP TABLE IF EXISTS `departments`;
DROP TABLE IF EXISTS `users`;

-- ----------------------------
-- 1. 用户表
-- ----------------------------
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL COMMENT '密码哈希（如 bcrypt）',
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `role` enum('candidate','hr','admin') NOT NULL DEFAULT 'candidate',
  `avatar` varchar(255) DEFAULT NULL,
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 2. 部门表
-- ----------------------------
CREATE TABLE `departments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 3. 职位表（关联 HR 和部门，支持审核流程）
-- ----------------------------
CREATE TABLE `jobs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hr_id` int NOT NULL COMMENT '负责该职位的HR',
  `department_id` int NOT NULL COMMENT '所属部门ID',
  `title` varchar(100) NOT NULL,
  `location` varchar(50) NOT NULL,
  `salary_min` int DEFAULT NULL COMMENT '最低薪资（NULL表示面议）',
  `salary_max` int DEFAULT NULL COMMENT '最高薪资',
  `salary_display` varchar(50) NOT NULL COMMENT '薪资展示文案（如"15-25K·14薪"）',
  `is_negotiable` tinyint NOT NULL DEFAULT 0 COMMENT '是否面议（1-是，0-否）',
  `education` varchar(20) NOT NULL,
  `experience` varchar(50) NOT NULL,
  `headcount` int NOT NULL DEFAULT 1,
  `description` text NOT NULL,
  `requirements` text NOT NULL,
  `benefits` text,
  `status` enum('draft','pending','active','closed','rejected') NOT NULL DEFAULT 'draft' COMMENT '职位状态：草稿/待审核/已发布/关闭/驳回',
  `deadline` date DEFAULT NULL COMMENT '招聘截止日期',
  `view_count` int DEFAULT 0,
  `apply_count` int DEFAULT 0,
  `is_hot` tinyint DEFAULT 0,
  `approved_by` int DEFAULT NULL COMMENT '审核人ID（关联users）',
  `approved_at` datetime DEFAULT NULL COMMENT '审核通过时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '0-未删除 1-已删除',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hr_id` (`hr_id`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_status` (`status`),
  KEY `idx_deadline` (`deadline`),
  KEY `idx_is_delete` (`is_delete`),
  CONSTRAINT `fk_jobs_hr` FOREIGN KEY (`hr_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_jobs_dept` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_jobs_approver` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 4. 简历表（用户联系方式冗余作为快照）
-- ----------------------------
CREATE TABLE `resumes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `age` int DEFAULT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `education` varchar(50) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `skills` json DEFAULT NULL,
  `attachment_url` varchar(255) DEFAULT NULL,
  `attachment_name` varchar(255) DEFAULT NULL,
  `self_introduction` text,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `fk_resumes_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 5. 工作经历表
-- ----------------------------
CREATE TABLE `work_experiences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resume_id` int NOT NULL,
  `company` varchar(100) NOT NULL,
  `position` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `is_current` tinyint DEFAULT 0,
  `description` text,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_resume_id` (`resume_id`),
  CONSTRAINT `fk_work_resume` FOREIGN KEY (`resume_id`) REFERENCES `resumes` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 6. 教育经历表
-- ----------------------------
CREATE TABLE `education_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resume_id` int NOT NULL,
  `school` varchar(100) NOT NULL,
  `major` varchar(50) NOT NULL,
  `degree` varchar(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_resume_id` (`resume_id`),
  CONSTRAINT `fk_edu_resume` FOREIGN KEY (`resume_id`) REFERENCES `resumes` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 7. 项目经历表
-- ----------------------------
CREATE TABLE `project_experiences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resume_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `description` text,
  `technologies` json DEFAULT NULL,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_resume_id` (`resume_id`),
  CONSTRAINT `fk_project_resume` FOREIGN KEY (`resume_id`) REFERENCES `resumes` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 8. 职位申请表
-- ----------------------------
CREATE TABLE `applications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL,
  `resume_id` int NOT NULL,
  `user_id` int NOT NULL,
  `user_name` varchar(50) NOT NULL COMMENT '申请时用户名快照',
  `job_title` varchar(100) NOT NULL COMMENT '申请时职位标题快照',
  `status` enum('pending','screened','interview','hired','rejected') NOT NULL DEFAULT 'pending',
  `reject_reason` text,
  `applied_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_resume_id` (`resume_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_app_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_app_resume` FOREIGN KEY (`resume_id`) REFERENCES `resumes` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_app_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 9. 收藏表
-- ----------------------------
CREATE TABLE `favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `job_id` int NOT NULL,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_job` (`user_id`, `job_id`),
  KEY `idx_job_id` (`job_id`),
  CONSTRAINT `fk_fav_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_fav_job` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 10. 面试表（支持面试官外键、起止时间、会议链接）
-- ----------------------------
CREATE TABLE `interviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `application_id` int NOT NULL,
  `job_id` int NOT NULL,
  `user_id` int NOT NULL,
  `resume_id` int NOT NULL,
  `job_title` varchar(100) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `interviewer_id` int NOT NULL COMMENT '面试官用户ID',
  `interviewer_name` varchar(50) NOT NULL COMMENT '面试官姓名冗余',
  `start_time` datetime NOT NULL COMMENT '面试开始时间',
  `end_time` datetime NOT NULL COMMENT '面试结束时间',
  `location` varchar(100) DEFAULT NULL COMMENT '线下地址或线上会议链接',
  `method` enum('online','offline') NOT NULL,
  `result` enum('pending','pass','fail') NOT NULL DEFAULT 'pending',
  `notes` text,
  `notified` tinyint DEFAULT 0,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_application_id` (`application_id`),
  KEY `idx_interviewer_id` (`interviewer_id`),
  KEY `idx_start_time` (`start_time`),
  CONSTRAINT `fk_interview_app` FOREIGN KEY (`application_id`) REFERENCES `applications` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_interview_interviewer` FOREIGN KEY (`interviewer_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 11. 通知表（增加发送者字段）
-- ----------------------------
CREATE TABLE `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '接收者ID',
  `from_user_id` int DEFAULT NULL COMMENT '发送者ID（NULL表示系统）',
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `type` enum('interview','application','system') NOT NULL,
  `is_read` tinyint DEFAULT 0,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_from_user_id` (`from_user_id`),
  KEY `idx_is_read` (`is_read`),
  CONSTRAINT `fk_notify_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_notify_from_user` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- 12. 系统日志表（扩展操作类型和对象ID）
-- ----------------------------
CREATE TABLE `system_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `action_type` varchar(50) DEFAULT NULL COMMENT '操作类型如 LOGIN, APPLY, UPDATE_JOB',
  `action` varchar(255) NOT NULL,
  `target_id` int DEFAULT NULL COMMENT '操作对象ID',
  `ip` varchar(50) DEFAULT NULL,
  `user_agent` text,
  `is_delete` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_action_type` (`action_type`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `settings` (
  `id` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '固定id=1，仅一条配置',
  `site_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '网站名称',
  `company_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '公司名称',
  `contact_phone` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '联系电话',
  `contact_email` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '联系邮箱',
  `address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '公司地址',
  `description` TEXT COMMENT '企业简介',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='网站基础配置表';



-- 重新开启外键检查
SET FOREIGN_KEY_CHECKS = 1;