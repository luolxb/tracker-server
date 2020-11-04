create table job
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255) not null,
    enabled     BIT          not null,
    create_time DATETIME(19) null,
    sort        BIGINT(19)   not null,
    dept_id     BIGINT(19)   null
);

create index FKmvhj0rogastlctflsxf1d6k3i
    on job (dept_id);

INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (8, '人事专员', false, '2019-03-29 14:52:28', 3, 2);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (10, '产品经理', false, '2019-03-29 14:55:51', 4, 2);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (11, '全栈开发', true, '2019-03-31 13:39:30', 6, 2);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (12, '软件测试', true, '2019-03-31 13:39:43', 5, 2);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (33, '管理员', true, '2019-06-27 11:24:03', 999, 20);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (34, '塘湾派出所', true, '2019-07-03 10:00:03', 999, 23);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (35, '分局派出所', true, '2019-07-23 09:46:28', 999, 8);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (36, '小昆山派出所', true, '2019-07-23 10:07:22', 999, 29);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (37, '11', true, '2019-09-06 18:39:37', 999, 1);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (39, '岗位1', true, '2019-09-11 10:07:40', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (40, '岗位2', true, '2019-09-11 10:58:52', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (41, '闵行分局', true, '2019-09-11 17:36:26', 999, 7);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (43, 'test02', false, '2019-09-11 18:25:21', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (44, '测试岗位01', false, '2019-09-12 08:22:56', 999, 34);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (46, '洞泾派出所', true, '2019-09-17 09:04:35', 999, 6);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (47, '中山派出所', true, '2019-09-17 09:04:44', 999, 38);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (48, '车墩派出所', true, '2019-09-17 09:04:52', 999, 37);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (49, 'zhc测试job', false, '2019-09-17 11:21:35', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (52, '地铁站巡逻员', true, '2019-09-19 16:32:43', 999, 43);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (54, 'test岗位01', true, '2019-09-20 11:50:00', 999, 34);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (55, '测试岗位1', false, '2019-09-25 17:10:54', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (56, '测试岗位01', true, '2019-09-26 13:34:48', 999, 52);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (57, '测试岗位02', true, '2019-09-26 13:53:18', 999, 53);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (58, '测试', true, '2019-09-27 15:38:10', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (59, '测试4', true, '2019-09-29 09:50:48', 999, 33);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (60, '浦锦路管理员', true, '2019-10-09 15:20:27', 999, 5);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (61, 'liao2', true, '2019-10-10 15:11:15', 999, 62);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (62, '南山公安分局管理员', true, '2019-11-26 17:05:37', 999, 32);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (63, '测试', true, '2019-12-10 13:11:26', 999, 75);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (64, '岗亭', true, '2019-12-20 10:00:45', 999, 48);
INSERT INTO cyoubike.job (id, name, enabled, create_time, sort, dept_id) VALUES (65, '岗亭1', true, '2019-12-20 10:00:55', 999, 62);