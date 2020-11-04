create table t_message_template
(
    id              BIGINT(19) auto_increment
        primary key,
    message_title   VARCHAR(255) null,
    message_content VARCHAR(255) null,
    create_time     VARCHAR(255) null,
    update_time     VARCHAR(255) null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (4, '今天1', '今天贼好', '2019-12-25 15:28:44', '2019-12-25 15:38:11');
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (5, '今天天气', '今天天气贼差', '2019-12-25 15:30:12', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (6, '狗扎', '汪汪汪', '2019-12-25 15:30:27', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (7, '托马斯', '呜呜呜呜呜呜呜', '2019-12-25 15:30:41', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (8, '海飞丝', '柔顺', '2019-12-25 15:30:57', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (9, '飘柔', '丝滑', '2019-12-25 15:31:11', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (10, '12345', '上山打老虎', '2019-12-25 15:31:31', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (11, '晚间播报', '你瞅啥', '2019-12-25 15:31:57', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (12, '来学粤语', '哈撒给', '2019-12-25 15:32:13', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (13, '死亡如风', '常伴吾身', '2019-12-25 15:32:25', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (14, '亚索', '亚索亚索亚索亚索', '2019-12-25 15:32:40', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (16, '111', '222', '2019-12-25 15:42:26', null);
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (17, '停机公告', '停机公告1', '2019-12-25 16:07:33', '2019-12-25 16:07:46');
INSERT INTO cyoubike.t_message_template (id, message_title, message_content, create_time, update_time) VALUES (18, '测试', '大家好！', '2019-12-30 18:27:56', null);