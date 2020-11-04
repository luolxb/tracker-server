create table verification_code
(
    id          BIGINT(19) auto_increment comment 'ID'
        primary key,
    code        VARCHAR(255) null comment '验证码',
    create_time DATETIME(19) null comment '创建日期',
    status      BIT          null comment '状态：1有效、0过期',
    type        VARCHAR(255) null comment '验证码类型：email或者短信',
    value       VARCHAR(255) null comment '接收邮箱或者手机号码',
    scenes      VARCHAR(255) null comment '业务名称：如重置邮箱、重置密码等'
);

