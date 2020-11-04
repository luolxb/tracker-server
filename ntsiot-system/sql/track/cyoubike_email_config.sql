create table email_config
(
    id        BIGINT(19) auto_increment comment 'ID'
        primary key,
    from_user VARCHAR(255) null comment '收件人',
    host      VARCHAR(255) null comment '邮件服务器SMTP地址',
    pass      VARCHAR(255) null comment '密码',
    port      VARCHAR(255) null comment '端口',
    user      VARCHAR(255) null comment '发件者用户名'
);

