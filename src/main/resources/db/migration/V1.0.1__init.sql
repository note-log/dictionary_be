CREATE TABLE user
(
    id       BIGINT       not null primary key auto_increment COMMENT 'user id',
    username VARCHAR(128) not null COMMENT '学号',
    password VARCHAR(128) not null COMMENT '密码',
    email VARCHAR(128) not null COMMENT '电子邮箱',
    phone VARCHAR(11) not null COMMENT '手机号',
    is_audit BOOLEAN not null COMMENT '是否通过审核' DEFAULT false,
    is_admin BOOLEAN not null COMMENT '是否是管理员' DEFAULT false,
    is_banned BOOLEAN not null COMMENT '是否禁用' DEFAULT false,
    is_deleted BOOLEAN not null COMMENT '是否删除' DEFAULT false,
    login_time INTEGER not null COMMENT '登录次数' DEFAULT 0,
    last_login_timestamp BIGINT COMMENT '最后一次登录时间' DEFAULT 0,
    major VARCHAR(40) COMMENT '专业',
    clazz VARCHAR(60) COMMENT '班级',
    enrollment_year VARCHAR(4) COMMENT '入校年份',
    graduate_year VARCHAR(4) COMMENT '毕业年份',
    company VARCHAR(100) COMMENT '就业单位',
    city VARCHAR(10) COMMENT '所在城市'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_username ON user (username);