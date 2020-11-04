create table app_module
(
    id          BIGINT(19) auto_increment comment 'id'
        primary key,
    name        VARCHAR(255)  null comment '模块名',
    alias       VARCHAR(255)  null comment '别名',
    remark      VARCHAR(1000) null comment '描述',
    create_time BIGINT(19)    null comment '创建时间'
)
    charset = utf8mb4;

INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (9, 'aqsh', '安全守护', '12', 1562564632955);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (10, 'smdj', '物品领用登记', '', 1562564643684);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (11, 'nbxxsb', '实有人口采集', '', 1562564651976);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (12, 'yjbj', '一键报警', '', 1562564660149);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (13, 'cjwt', '常见问题', '', 1562564674576);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (14, 'yysmdj', '预约上门登记', '', 1562564697639);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (15, 'zngj', '智能轨迹', '', 1562564706086);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (16, 'wfjb', '违法举报', '', 1562564714537);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (17, 'mydcp', '满意度测评', '', 1562564731200);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (18, 'fwyy', '服务预约', '', 1562564738402);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (19, 'smkc', '扫码开锁', '', 1562564748837);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (20, 'gzrzsb', '工作日志上报', '', 1562564766732);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (21, 'xxtz', '消息通知', '', 1562564774618);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (22, 'cjwt', '常见问题', '', 1562564782059);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (24, 'fdba', '房东备案', '房东备案', 1568625339410);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (27, 'phb', '排行榜', '', 1573442195564);
INSERT INTO cyoubike.app_module (id, name, alias, remark, create_time) VALUES (29, 'kzyy', '口罩预约', '', 1581163924902);