create table icon
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255) null comment '名字',
    icon        VARCHAR(255) null comment '图标',
    dept_id     BIGINT(19)   null comment '辖区',
    create_time BIGINT(19)   null,
    update_time BIGINT(19)   null
)
    charset = utf8mb4;

INSERT INTO cyoubike.icon (id, name, icon, dept_id, create_time, update_time) VALUES (1, '24小时便利店', 'http://images.cyoubike.com/0a6d8be9-6b80-4207-9401-007e66b32a5b.jpg', 2, 1562984096271, null);
INSERT INTO cyoubike.icon (id, name, icon, dept_id, create_time, update_time) VALUES (2, '治安辖区', 'http://images.cyoubike.com/7020751f-d8d6-4bf3-8bb4-1a166a34dd50.jpg', 33, 1568962108640, null);
INSERT INTO cyoubike.icon (id, name, icon, dept_id, create_time, update_time) VALUES (3, '报刊亭', 'http://images.cyoubike.com/abc00747-b44e-40c5-8408-fadb761801da.png', 1, 1569241842630, null);