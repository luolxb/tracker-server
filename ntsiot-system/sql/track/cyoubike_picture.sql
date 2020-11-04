create table picture
(
    id          BIGINT(19) auto_increment comment 'ID'
        primary key,
    create_time DATETIME(19) null comment '上传日期',
    delete_url  VARCHAR(255) null comment '删除的URL',
    filename    VARCHAR(255) null comment '图片名称',
    height      VARCHAR(255) null comment '图片高度',
    size        VARCHAR(255) null comment '图片大小',
    url         VARCHAR(255) null comment '图片地址',
    username    VARCHAR(255) null comment '用户名称',
    width       VARCHAR(255) null comment '图片宽度'
);

