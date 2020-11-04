create table role
(
    id          BIGINT(19) auto_increment comment 'ID'
        primary key,
    create_time DATETIME(19) null comment '创建日期',
    name        VARCHAR(255) not null comment '名称',
    remark      VARCHAR(255) null comment '备注',
    data_scope  VARCHAR(255) null
);

INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (1, '2018-11-23 11:04:37', '管理员', '系统所有权', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (2, '2018-11-23 13:09:06', '普通用户', '用于测试菜单与权限', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (3, '2019-05-17 10:47:44', '吴泾辖区管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (4, '2019-06-13 09:36:46', 'cej测试角色', 'cej测试角色', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (6, '2019-06-13 16:56:41', '浦锦路辖区管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (7, '2019-06-13 17:20:30', 'gp测试角色', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (8, '2019-07-02 15:33:46', '三方系统角色', '', '本级');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (9, '2019-07-03 16:19:09', '梅陇辖区管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (10, '2019-07-23 09:47:24', '小昆山派出所管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (11, '2019-07-23 10:07:45', '松江分局派出所管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (12, '2019-09-03 11:34:35', '梅陇辖区第三方接口', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (16, '2019-09-11 10:04:10', '西丽派出所管理员-自定义1', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (18, '2019-09-11 17:37:57', '闵行分局管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (20, '2019-09-11 19:50:14', 'test角色01', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (21, '2019-09-11 19:51:00', 'test角色02', '', '本级');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (34, '2019-09-17 09:05:15', '中山派出所管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (35, '2019-09-17 09:06:14', '车墩派出所管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (36, '2019-09-17 09:06:47', '洞泾派出所管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (37, '2019-09-17 11:17:38', '范德萨发', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (41, '2019-09-17 11:22:32', 'debug', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (47, '2019-09-19 15:26:31', '西丽派出所巡检监督员', '负责监督巡检员工作内容', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (49, '2019-09-20 10:57:32', '周卫卫测试角色', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (58, '2019-09-25 15:16:38', '西丽街道办', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (59, '2019-09-25 15:17:13', '西丽纸质委员会', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (60, '2019-09-25 15:55:32', '11111', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (66, '2019-09-26 16:47:44', ' 昌', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (73, '2019-09-27 15:01:40', 'test1', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (74, '2019-09-27 15:03:15', 'test2', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (76, '2019-09-27 15:08:10', '9999', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (77, '2019-09-27 15:08:27', '8888', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (78, '2019-09-27 15:09:44', '普工职员', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (79, '2019-09-27 15:11:09', '65', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (82, '2019-09-27 15:18:47', '100', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (84, '2019-09-27 15:26:05', '90', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (86, '2019-09-27 16:56:28', '801', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (88, '2019-09-27 17:55:08', '小小', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (90, '2019-09-27 18:03:18', '步步', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (92, '2019-09-27 19:00:23', '虎', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (93, '2019-09-27 19:19:28', '虎虎', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (94, '2019-09-29 09:04:38', '角色1', '', '全部');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (101, '2019-10-26 10:19:14', '统计功能', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (102, '2019-11-12 17:20:57', '超级管理员', '超级管理员角色-拥有所有权限和菜单', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (103, '2019-11-26 17:06:35', '南山分局管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (104, '2019-12-04 18:02:17', '第三方api管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (105, '2019-12-09 09:39:23', 'xxx', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (106, '2019-12-09 14:08:58', '留仙洞管理员', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (107, '2019-12-09 16:36:19', '测试0000', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (108, '2019-12-09 16:36:57', '测试1111', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (110, '2019-12-20 09:51:51', '岗亭', '', '自定义');
INSERT INTO cyoubike.role (id, create_time, name, remark, data_scope) VALUES (111, '2019-12-20 09:54:02', '岗亭1', '', '自定义');