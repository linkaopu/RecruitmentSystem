-- 关闭外键约束，清空原有数据
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE system_logs;
TRUNCATE TABLE notifications;
TRUNCATE TABLE interviews;
TRUNCATE TABLE favorites;
TRUNCATE TABLE applications;
TRUNCATE TABLE project_experiences;
TRUNCATE TABLE education_history;
TRUNCATE TABLE work_experiences;
TRUNCATE TABLE resumes;
TRUNCATE TABLE jobs;
TRUNCATE TABLE departments;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS=1;

-- ===================== 1. 用户表 users =====================
INSERT INTO users (username, password, email, phone, role, avatar, is_delete) VALUES
('admin', '123456', 'admin@qq.com', '13800000001', 'admin', 'https://picsum.photos/200/200', 0),
('hr_zhang', '123456', 'hr@company.com', '13800000002', 'hr', 'https://picsum.photos/200/201', 0),
('jobseeker01', '123456', 'zhangsan@163.com', '13800000011', 'candidate', 'https://picsum.photos/200/202', 0),
('jobseeker02', '123456', 'lisi@163.com', '13800000012', 'candidate', 'https://picsum.photos/200/203', 0),
('jobseeker03', '123456', 'wangwu@163.com', '13800000013', 'candidate', 'https://picsum.photos/200/204', 0);

-- ===================== 2. 部门表 departments =====================
INSERT INTO departments (name, code, description, is_delete) VALUES
('技术部', 'TECH', '负责软件开发、系统维护', 0),
('产品部', 'PROD', '负责产品规划、需求设计', 0),
('运营部', 'OP', '负责市场推广、用户运营', 0),
('人事部', 'HR', '负责招聘、员工管理', 0);

-- ===================== 3. 职位表 jobs =====================
INSERT INTO jobs (title, department, location, salary_min, salary_max, salary_display, education, experience, headcount, description, requirements, benefits, status, view_count, apply_count, is_hot, is_delete) VALUES
('前端开发工程师', '技术部', '北京', 15000, 30000, '15K-30K', '本科', '3-5年', 2, '负责公司Web前端开发、维护、优化', '熟练掌握Vue3/Element Plus，有企业项目经验', '五险一金、年终奖、弹性工作制', 'active', 120, 18, 1, 0),
('后端Java工程师', '技术部', '上海', 18000, 35000, '18K-35K', '本科', '3-5年', 3, '负责后端接口开发、数据库设计、服务优化', 'Java基础扎实，SpringBoot/SpringCloud', '包三餐、定期涨薪、股票期权', 'active', 100, 15, 1, 0),
('产品经理', '产品部', '深圳', 12000, 25000, '12K-25K', '本科', '2-3年', 1, '负责产品规划、需求文档、项目跟进', '有互联网产品经验，沟通能力强', '双休、带薪年假、晋升空间大', 'active', 80, 9, 0, 0),
('运营专员', '运营部', '广州', 7000, 12000, '7K-12K', '大专', '1年以内', 2, '负责内容运营、活动策划、用户增长', '会写文案，熟悉新媒体平台', '轻松氛围、下午茶、团建', 'active', 60, 7, 0, 0);

-- ===================== 4. 简历表 resumes =====================
INSERT INTO resumes (user_id, name, phone, email, age, gender, education, avatar, skills, self_introduction, is_delete) VALUES
(3, '张三', '13800000011', 'zhangsan@163.com', 24, 'male', '本科', 'https://picsum.photos/200/202', '["Vue3","JavaScript","CSS","ElementPlus"]', '热爱前端，2年前端开发经验，学习能力强', 0),
(4, '李四', '13800000012', 'lisi@163.com', 25, 'male', '本科', 'https://picsum.photos/200/203', '["Java","SpringBoot","MySQL","Redis"]', '3年后端经验，专注高并发系统开发', 0),
(5, '王五', '13800000013', 'wangwu@163.com', 23, 'female', '本科', 'https://picsum.photos/200/204', '["产品设计","Axure","Xmind","需求分析"]', '细心负责，擅长产品规划与需求梳理', 0);

-- ===================== 5. 工作经历表 work_experiences =====================
INSERT INTO work_experiences (resume_id, company, position, start_date, end_date, is_current, description, is_delete) VALUES
(1, '星辰科技有限公司', '前端开发工程师', '2022-07-01', '2024-06-30', 0, '负责企业官网、后台管理系统前端开发与优化', 0),
(2, '云数信息科技', 'Java后端开发', '2021-09-01', NULL, 1, '负责微服务接口开发、数据库维护、接口性能调优', 0),
(3, '创新互联网工场', '产品助理', '2023-07-01', NULL, 1, '协助产品经理完成需求文档、原型设计、项目跟进', 0);

-- ===================== 6. 教育经历表 education_history =====================
INSERT INTO education_history (resume_id, school, major, degree, start_date, end_date, is_delete) VALUES
(1, '北京理工大学', '计算机科学与技术', '本科', '2019-09-01', '2023-06-30', 0),
(2, '上海交通大学', '软件工程', '本科', '2018-09-01', '2022-06-30', 0),
(3, '深圳大学', '产品设计', '本科', '2019-09-01', '2023-06-30', 0);

-- ===================== 7. 项目经历表 project_experiences =====================
INSERT INTO project_experiences (resume_id, name, role, start_date, end_date, description, technologies, is_delete) VALUES
(1, '企业招聘管理系统', '前端负责人', '2023-01-01', '2023-12-01', '基于Vue3+Element Plus开发招聘系统前端，实现职位浏览、简历投递、个人中心等功能', '["Vue3","ElementPlus","Vite","Pinia"]', 0),
(2, '电商微服务交易平台', '后端开发', '2022-03-01', '2023-10-01', '负责订单、支付、用户模块接口开发，优化数据库查询性能', '["Java","SpringBoot","MySQL","Redis"]', 0);

-- ===================== 8. 职位申请表 applications =====================
INSERT INTO applications (job_id, resume_id, user_id, user_name, job_title, status, reject_reason, is_delete) VALUES
(1, 1, 3, '张三', '前端开发工程师', 'interview', NULL, 0),
(2, 2, 4, '李四', '后端Java工程师', 'screened', NULL, 0),
(3, 3, 5, '王五', '产品经理', 'pending', NULL, 0);

-- ===================== 9. 收藏表 favorites =====================
INSERT INTO favorites (user_id, job_id, is_delete) VALUES
(3, 1, 0),
(3, 2, 0),
(4, 2, 0),
(5, 3, 0);

-- ===================== 10. 面试表 interviews =====================
INSERT INTO interviews (application_id, job_id, user_id, resume_id, job_title, user_name, interviewer, interview_time, location, method, result, notes, notified, is_delete) VALUES
(1, 1, 3, 1, '前端开发工程师', '张三', '张HR', '2025-12-20 14:00:00', '腾讯会议线上房间', 'online', 'pending', '技术面+项目经历提问', 0, 0);

-- ===================== 11. 通知表 notifications =====================
INSERT INTO notifications (user_id, title, content, type, is_read, is_delete) VALUES
(3, '面试通知', '恭喜你已进入【前端开发工程师】岗位面试环节，请准时参加', 'interview', 0, 0),
(3, '申请状态更新', '你的简历已被HR查看，正在筛选中', 'application', 0, 0);

-- ===================== 12. 系统日志表 system_logs =====================
INSERT INTO system_logs (user_id, username, action, ip, user_agent, is_delete) VALUES
(1, 'admin', '管理员登录系统，进入后台管理', '127.0.0.1', 'Mozilla/5.0', 0),
(2, 'hr_zhang', 'HR查看前端岗位简历列表', '127.0.0.1', 'Mozilla/5.0', 0);