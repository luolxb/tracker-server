create table inform_config
(
    id          BIGINT(19) auto_increment
        primary key,
    title       VARCHAR(255) null,
    content     LONGTEXT(max) null,
    dept_id     BIGINT(19)   null,
    create_time BIGINT(19)   null,
    update_time BIGINT(19)   null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.inform_config (id, title, content, dept_id, create_time, update_time) VALUES (4, '用电安全', '<p>电动车禁止在室内充电</p>', 33, 1569826109402, null);