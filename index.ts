// 用户类型枚举
export enum UserRole {
  CANDIDATE = 'candidate', // 求职者
  HR = 'hr',               // HR
  ADMIN = 'admin'          // 管理员
}

// 简历状态枚举
export enum ResumeStatus {
  PENDING = 'pending',       // 待查看
  SCREENED = 'screened',     // 已初筛
  INTERVIEW = 'interview',   // 面试
  HIRED = 'hired',           // 录用
  REJECTED = 'rejected'      // 不匹配
}

// 面试方式
export enum InterviewMethod {
  ONLINE = 'online',     // 线上
  OFFLINE = 'offline'    // 线下
}

// 面试结果
export enum InterviewResult {
  PENDING = 'pending',   // 待定
  PASS = 'pass',         // 通过
  FAIL = 'fail'          // 不通过
}

// ==================== 用户相关 ====================
// 用户信息
export interface User {
  id: number
  username: string
  email: string
  phone: string
  role: UserRole  // 用户角色
  avatar?: string // 头像URL
  createdAt: string // 创建时间
  updatedAt: string // 更新时间
}

// 登录参数
export interface LoginParams {
  username: string
  password: string
  type?: 'account' | 'phone' // 账号密码或手机验证码
  code?: string // 验证码
}

// 注册参数
export interface RegisterParams {
  username: string
  password: string
  email: string
  phone: string
  role: UserRole  // 用户角色
  code?: string // 验证码
}

// 登录响应
export interface LoginResponse {
  token: string
  user: User
}

// ==================== 职位相关 ====================
// 职位信息
export interface Job {
  id: number
  title: string // 职位名称
  department: string // 部门
  location: string // 地点
  salaryMin: number // 最低薪资
  salaryMax: number // 最高薪资
  salaryDisplay: string // 显示的薪资文本
  education: string // 学历要求
  experience: string // 工作经验要求
  headcount: number // 招聘人数
  description: string // 岗位职责
  requirements: string // 任职要求
  benefits: string // 福利待遇
  status: 'active' | 'inactive' // 上架/下架
  viewCount: number // 浏览数
  applyCount: number // 申请数
  isHot: boolean // 是否热门
  createdAt: string
  updatedAt: string
}

// 创建职位参数
export interface JobQueryParams {
  keyword?: string // 搜索关键词
  department?: string // 部门
  location?: string // 地点
  salaryMin?: number // 最低薪资
  salaryMax?: number // 最高薪资
  education?: string // 学历要求
  experience?: string // 工作经验要求
  sortBy?: 'latest' | 'salary' | 'hot' // 排序方式
  page: number // 页码
  pageSize: number // 每页数量
}

// 职位列表响应
export interface JobListResponse {
  list: Job[] // 职位列表
  total: number // 总数
  page: number // 页码
  pageSize: number // 每页数量
}

// ==================== 简历相关 ====================
// 工作经历
export interface WorkExperience {
  company: string // 公司名称
  position: string // 职位名称
  startDate: string // 开始日期
  endDate?: string // 结束日期，当前工作可不填
  isCurrent: boolean // 是否当前工作
  description: string // 工作内容描述
}

// 教育经历
export interface Education {
  school: string // 学校名称
  major: string // 专业
  degree: string // 学位
  startDate: string // 开始日期
  endDate: string // 结束日期
}

// 项目经历
export interface ProjectExperience {
  name: string // 项目名称
  role: string // 在项目中的角色
  startDate: string // 开始日期
  endDate?: string // 结束日期，当前项目可不填
  description: string // 项目内容描述
  technologies: string[] // 使用的技术栈
}

// 简历信息
export interface Resume {
  id: number
  userId: number
  name: string // 姓名
  phone: string // 电话
  email: string // 邮箱
  age?: number // 年龄
  gender?: 'male' | 'female' // 性别
  education: string // 学历
  avatar?: string // 头像
  workExperience?: WorkExperience[] // 工作经历
  educationHistory?: Education[] // 教育经历
  projects?: ProjectExperience[] // 项目经历
  skills: string[] // 技能标签
  attachmentUrl?: string // 附件简历URL
  attachmentName?: string // 附件简历名称
  selfIntroduction?: string // 自我介绍
  createdAt: string
  updatedAt: string
}

// 创建简历参数
export interface CreateResumeParams {
  name: string
  phone: string
  email: string
  age?: number
  gender?: 'male' | 'female'
  education: string
  avatar?: string // 头像URL
  workExperience?: WorkExperience[] // 工作经历
  educationHistory?: Education[] // 教育经历
  projects?: ProjectExperience[] // 项目经历
  skills: string[]
  attachmentUrl?: string // 附件简历URL
  attachmentName?: string // 附件简历名称
  selfIntroduction?: string // 自我介绍
}

// ==================== 申请记录相关 ====================
// 申请记录信息
export interface Application {
  id: number
  jobId: number
  jobTitle: string
  resumeId: number // 使用的简历ID
  userId: number
  userName: string
  status: ResumeStatus
  appliedAt: string
  updatedAt: string
  job?: Job
  resume?: Resume // 可选，申请记录中可能包含职位和简历的详细信息
  rejectReason?: string // 拒绝原因
}

// 申请记录查询参数
export interface ApplicationQueryParams {
  userId?: number
  jobId?: number
  status?: ResumeStatus // 申请状态
  page: number
  pageSize: number
}

// ==================== 收藏相关 ====================
// 收藏信息
export interface Favorite {
  id: number
  userId: number
  jobId: number
  job: Job
  createdAt: string
}

// ==================== 面试相关 ====================
// 面试信息
export interface Interview {
  id: number
  applicationId: number
  jobId: number
  jobTitle: string
  userId: number
  userName: string
  resumeId: number
  interviewer: string // 面试官
  interviewTime: string // 面试时间
  location: string // 面试地点
  method: InterviewMethod // 面试方式
  result: InterviewResult // 面试结果
  notes?: string // 面试备注
  notified: boolean // 是否已发送通知
  createdAt: string
  updatedAt: string
}

// 创建面试参数
export interface CreateInterviewParams {
  applicationId: number // 申请记录ID
  interviewer: string // 面试官
  interviewTime: string // 面试时间
  location: string // 面试地点
  method: InterviewMethod // 面试方式
}

// ==================== 通知相关 ====================
// 通知信息
export interface Notification {
  id: number
  userId: number
  title: string
  content: string
  type: 'interview' | 'application' | 'system' // 通知类型
  isRead: boolean
  createdAt: string
}

// ==================== 系统管理相关 ====================
// 系统设置
export interface Department {
  id: number
  name: string
  code: string
  description?: string
  createdAt: string
  updatedAt: string
}

// 系统日志
export interface SystemLog {
  id: number
  userId: number
  username: string
  action: string
  ip: string
  userAgent: string
  createdAt: string
}

// 仪表盘统计数据
export interface DashboardStats {
  todayApplications: number // 今日申请数
  totalJobs: number // 总职位数
  pendingResumes: number // 待处理简历数
  pendingInterviews: number // 待安排面试数
  applicationTrend: Array<{
    date: string
    count: number
  }> // 近7天申请趋势
}

// ==================== 通用响应类型 ====================
// API响应格式
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

// 分页响应格式
export interface PageResponse<T> {
  list: T[]
  total: number
  page: number
  pageSize: number
}