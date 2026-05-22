CREATE DATABASE if not EXISTS recruitment_system;

-- 关闭外键检查
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS system_logs;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS interviews;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS project_experiences;
DROP TABLE IF EXISTS education_history;
DROP TABLE IF EXISTS work_experiences;
DROP TABLE IF EXISTS resumes;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS users;
-- 开启外键检查
SET FOREIGN_KEY_CHECKS=1;

-- ----------------------------
-- 用户表
-- ----------------------------
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  phone VARCHAR(20) NOT NULL UNIQUE,
  role ENUM('candidate','hr','admin') NOT NULL DEFAULT 'candidate',
  avatar VARCHAR(255) DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ----------------------------
-- 部门表
-- ----------------------------
CREATE TABLE departments (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(255) DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ----------------------------
-- 职位表
-- ----------------------------
CREATE TABLE jobs (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  department VARCHAR(50) NOT NULL,
  location VARCHAR(50) NOT NULL,
  salary_min INT NOT NULL,
  salary_max INT NOT NULL,
  salary_display VARCHAR(50) NOT NULL,
  education VARCHAR(20) NOT NULL,
  experience VARCHAR(50) NOT NULL,
  headcount INT NOT NULL DEFAULT 1,
  description TEXT NOT NULL,
  requirements TEXT NOT NULL,
  benefits TEXT DEFAULT NULL,
  status ENUM('active','inactive') NOT NULL DEFAULT 'active',
  view_count INT DEFAULT 0,
  apply_count INT DEFAULT 0,
  is_hot TINYINT(1) DEFAULT 0,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ----------------------------
-- 简历表
-- ----------------------------
CREATE TABLE resumes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(100) NOT NULL,
  age INT DEFAULT NULL,
  gender ENUM('male','female') DEFAULT NULL,
  education VARCHAR(50) NOT NULL,
  avatar VARCHAR(255) DEFAULT NULL,
  skills JSON DEFAULT NULL,
  attachment_url VARCHAR(255) DEFAULT NULL,
  attachment_name VARCHAR(255) DEFAULT NULL,
  self_introduction TEXT DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ----------------------------
-- 工作经历表
-- ----------------------------
CREATE TABLE work_experiences (
  id INT PRIMARY KEY AUTO_INCREMENT,
  resume_id INT NOT NULL,
  company VARCHAR(100) NOT NULL,
  position VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  is_current TINYINT(1) DEFAULT 0,
  description TEXT DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE
);

-- ----------------------------
-- 教育经历表
-- ----------------------------
CREATE TABLE education_history (
  id INT PRIMARY KEY AUTO_INCREMENT,
  resume_id INT NOT NULL,
  school VARCHAR(100) NOT NULL,
  major VARCHAR(50) NOT NULL,
  degree VARCHAR(20) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE
);

-- ----------------------------
-- 项目经历表
-- ----------------------------
CREATE TABLE project_experiences (
  id INT PRIMARY KEY AUTO_INCREMENT,
  resume_id INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  role VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  description TEXT DEFAULT NULL,
  technologies JSON DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE
);

-- ----------------------------
-- 职位申请表
-- ----------------------------
CREATE TABLE applications (
  id INT PRIMARY KEY AUTO_INCREMENT,
  job_id INT NOT NULL,
  resume_id INT NOT NULL,
  user_id INT NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  job_title VARCHAR(100) NOT NULL,
  status ENUM('pending','screened','interview','hired','rejected') NOT NULL DEFAULT 'pending',
  reject_reason TEXT DEFAULT NULL,
  applied_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
  FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ----------------------------
-- 收藏表
-- ----------------------------
CREATE TABLE favorites (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  job_id INT NOT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
  UNIQUE KEY unique_favorite (user_id, job_id)
);

-- ----------------------------
-- 面试表
-- ----------------------------
CREATE TABLE interviews (
  id INT PRIMARY KEY AUTO_INCREMENT,
  application_id INT NOT NULL,
  job_id INT NOT NULL,
  user_id INT NOT NULL,
  resume_id INT NOT NULL,
  job_title VARCHAR(100) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  interviewer VARCHAR(50) NOT NULL,
  interview_time DATETIME NOT NULL,
  location VARCHAR(100) NOT NULL,
  method ENUM('online','offline') NOT NULL,
  result ENUM('pending','pass','fail') NOT NULL DEFAULT 'pending',
  notes TEXT DEFAULT NULL,
  notified TINYINT(1) DEFAULT 0,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE
);

-- ----------------------------
-- 通知表
-- ----------------------------
CREATE TABLE notifications (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  title VARCHAR(100) NOT NULL,
  content TEXT NOT NULL,
  type ENUM('interview','application','system') NOT NULL,
  is_read TINYINT(1) DEFAULT 0,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ----------------------------
-- 系统日志表
-- ----------------------------
CREATE TABLE system_logs (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT DEFAULT NULL,
  username VARCHAR(50) DEFAULT NULL,
  action VARCHAR(255) NOT NULL,
  ip VARCHAR(50) DEFAULT NULL,
  user_agent TEXT DEFAULT NULL,
  is_delete INT DEFAULT 0 COMMENT '0未删除 1已删除',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);