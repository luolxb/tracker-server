create table dict
(
    id     BIGINT(19) auto_increment
        primary key,
    name   VARCHAR(255) not null comment '字典名称',
    remark VARCHAR(255) null comment '描述'
);

INSERT INTO cyoubike.dict (id, name, remark) VALUES (1, 'user_status', '用户状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (4, 'dept_status', '部门状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (5, 'job_status', '岗位状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (6, 'license_status', '授权类型');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (7, 'order_status', '订单状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (8, 'source_status', '结束状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (9, 'bike_type', '车辆类型');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (10, 'article_manage', '物品管理');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (11, 'trueOrFalse', '是否类型');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (12, 'question_type', '问题类型');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (22, 'news_type', '新闻公告类型');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (23, 'accept_status', '受理状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (24, 'check_license', '授权认证');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (25, 'task_type', '任务类别');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (27, 'deal_status', '违法举报处理状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (29, 'test11111', '测试1');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (30, '巡检状态', 'wayne-测试');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (31, 'rectify_status', '纠偏状态');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (32, 'rectify_lnglat_filter', '纠偏时过滤经纬度');
INSERT INTO cyoubike.dict (id, name, remark) VALUES (33, 'rectify_lockstate_filter', '纠偏时过滤锁状态');