create table about_us
(
    id          BIGINT(19) auto_increment
        primary key,
    title       VARCHAR(255) null comment '标题',
    content     LONGTEXT(max) null comment '内容',
    is_visible  VARCHAR(255) null comment '是否显示',
    dept_id     BIGINT(19)   null comment '辖区',
    create_time BIGINT(19)   null,
    update_time BIGINT(19)   null
)
    charset = utf8mb4;

INSERT INTO cyoubike.about_us (id, title, content, is_visible, dept_id, create_time, update_time) VALUES (1, '"''', '<p>测试</p>', '0', 2, 1568166063079, null);
INSERT INTO cyoubike.about_us (id, title, content, is_visible, dept_id, create_time, update_time) VALUES (2, '西游西游', '<p>西游西游</p><p>西游西游</p><p>西游西游</p><p>西游西游</p><p>西游西游</p><p>西游西游</p><p>西游西游</p><p><b></b><i></i><u></u><sub></sub><sup></sup><strike></strike><br></p>', '0', 33, 1568961436829, null);