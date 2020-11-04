create table t_send_message
(
    id           BIGINT(19) auto_increment
        primary key,
    send_content VARCHAR(255) null,
    send_user    VARCHAR(255) null,
    send_phone   VARCHAR(255) null,
    create_time  VARCHAR(255) null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (1, '测试1111111', '呵呵', '18866665555', '2019-12-25 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (2, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (3, '测试22222', '哈哈', '18777778888', '2019-12-23 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (4, '测试22222', '哈哈', '18877778888', '2019-12-22 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (5, '测试22222', '哈哈', '18877778888', '2019-12-22 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (6, '测试22222', '哈哈', '18877778888', '2019-12-23 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (7, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (8, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (9, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (10, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (11, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (12, '测试22222', '哈哈', '18877778888', '2019-12-24 17:00:00');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (13, '今天天气贼差', 'll', '13714553315', '2019-12-27 13:56:46');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (14, 'aaaaaa', '11111', '111111', '2019-12-27 14:07:01');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (15, 'aaaaaa', '11111', '111111', '2019-12-27 14:07:22');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (16, null, '11111', '13714553315', '2019-12-27 14:24:28');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (17, '11111', '11111', '13714553315', '2019-12-27 14:24:42');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (18, '今天贼好', '谭琴', '13715143282', '2019-12-27 14:26:49');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (19, '今天贼好', '和明', '18818795524', '2019-12-27 14:26:49');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (20, '上山打老虎  gogogo', '谭琴', '13715143282', '2019-12-27 14:31:22');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (21, '上山打老虎  gogogo', '和明', '18818795524', '2019-12-27 14:31:22');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (22, '你瞅啥  ?', '谭琴', '13715143282', '2019-12-27 14:32:46');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (23, '你瞅啥  ?', '和明', '18818795524', '2019-12-27 14:32:46');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (24, '你瞅啥  ?', '谭琴', '13715143282', '2019-12-27 14:34:37');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (25, '你瞅啥  ?', '和明', '18818795524', '2019-12-27 14:34:39');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (26, '今天天气贼差 ?', '彭乘鹤', '18867368720', '2019-12-27 14:39:28');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (27, '今天贼好', '谭琴', '13715143282', '2019-12-31 10:29:57');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (28, '2020加油', '谭琴', '13715143282', '2019-12-31 10:50:50');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (29, '2020加油', '谭琴', '13715143282', '2019-12-31 10:51:04');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (30, '2020加油', '和明', '18818795524', '2019-12-31 10:52:52');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (31, '2020加油', '谭琴', '13715143282', '2019-12-31 10:52:52');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (32, '柔顺', '谭琴', '13715143282', '2019-12-31 11:03:28');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (33, '呜呜呜呜呜呜呜', '谭琴', '13715143282', '2019-12-31 11:10:27');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (34, '今天天气贼差', '和明', '18818795524', '2019-12-31 11:13:07');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (35, '今天天气贼差', '谭琴', '13715143282', '2019-12-31 11:13:22');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (36, '大家好啊啊', '谭琴', '13715143282', '2019-12-31 11:14:05');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (37, '今天天气贼差', '谭琴', '13715143282', '2019-12-31 11:23:43');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (38, '今天天气贼差', '谭琴', '13715143282', '2019-12-31 13:20:16');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (39, '上山打老虎', '谭琴', '13715143282', '2019-12-31 13:21:25');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (40, '今天贼好', '谭琴', '13715143282', '2019-12-31 13:43:50');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (41, '大家好！', '和明', '18818795524', '2019-12-31 17:51:50');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (42, '今天天气贼差', '周卫卫2', '13423763880', '2019-12-31 18:02:41');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (43, '大家好', '周卫卫2', '13423763880', '2019-12-31 18:03:19');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (44, '大家好', '周卫卫2', '13423763880', '2019-12-31 18:04:39');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (45, '大家好', '和明', '18818795524', '2019-12-31 18:04:39');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (46, '今天天气贼差', '周卫卫2', '13423763880', '2020-01-02 13:45:38');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (47, 'kakakakakakakaka', '谭琴', '13715143282', '2020-01-04 17:43:44');
INSERT INTO cyoubike.t_send_message (id, send_content, send_user, send_phone, create_time) VALUES (48, '今天贼好', 'debug', '13501869520', '2020-01-06 15:19:50');