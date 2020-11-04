create table api_for_push_message_copy
(
    id         INT(10) auto_increment comment '主键'
        primary key,
    c_dest_url VARCHAR(255) null comment '第三方接口',
    c_desc     VARCHAR(255) not null comment '备注',
    n_valid    INT(10)      null comment '0: 无效，1：有效'
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.api_for_push_message_copy (id, c_dest_url, c_desc, n_valid) VALUES (1, 'http://localhost:8000/test', '测试url', 1);