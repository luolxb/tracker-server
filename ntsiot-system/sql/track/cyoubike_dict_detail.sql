create table dict_detail
(
    id      BIGINT(19) auto_increment
        primary key,
    label   VARCHAR(255) not null comment '字典标签',
    value   VARCHAR(255) not null comment '字典值',
    sort    VARCHAR(255) null comment '排序',
    dict_id BIGINT(19)   null comment '字典id'
);

create index FK5tpkputc6d9nboxojdbgnpmyb
    on dict_detail (dict_id);

INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (1, '激活', 'true', '1', 1);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (2, '锁定', 'false', '2', 1);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (11, '正常', 'true', '1', 4);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (12, '停用', 'false', '2', 4);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (13, '正常', 'true', '1', 5);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (14, '停用', 'false', '2', 5);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (24, '临时', '0', '1', 6);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (25, '永久', '1', '2', 6);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (26, '已预约', '0', '1', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (27, '已关闭', '1', '2', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (28, '进行中', '2', '3', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (29, '临停中', '3', '4', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (30, '待开锁', '4', '5', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (31, '已失效', '5', '6', 7);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (32, '正常关闭', '0', '1', 8);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (33, '故障上报', '1', '2', 8);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (34, '临停超时', '2', '3', 8);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (35, '后台关闭', '3', '4', 8);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (36, '助力车', '0', '1', 9);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (37, '电动车', '1', '2', 9);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (38, '雨伞', '0', '1', 1119162515860684800);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (39, '雨伞', '1', '1', 1119162515860684800);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (40, '雨伞', '1', '1', 1119162515860684800);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (41, '是', '0', '1', 11);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (42, '否', '1', '2', 11);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (43, '单选', '1', '1', 12);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (44, '多选', '2', '2', 12);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (45, '输入框', '3', '3', 12);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (48, '雨伞', '1', '1', 10);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (49, '鞋套', '0', '2', 10);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (50, '椅子', '1', '3', 10);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (51, '最新资讯', '1', '1', 22);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (52, '新闻公告', '2', '2', 22);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (53, '待受理', '0', '0', 23);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (54, '已受理', '1', '1', 23);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (55, '开启', '0', '999', 24);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (56, '关闭', '1', '999', 24);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (57, '停留时间', '0', '1', 25);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (58, '上传图片', '1', '2', 25);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (59, '打卡签到', '2', '3', 25);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (60, '警车', '', '999', 9);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (61, '未受理', '0', '0', 27);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (62, '已受理', '1', '1', 27);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (63, '无需受理', '2', '2', 27);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (64, '无效举报', '3', '3', 27);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (71, '未巡检', '1', '999', 30);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (72, '正在巡检中', '0', '999', 30);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (74, '巡检完成', '2', '999', 30);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (79, '开启纠偏', 'false', '999', 31);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (82, '开启过滤', 'true', '999', 32);
INSERT INTO cyoubike.dict_detail (id, label, value, sort, dict_id) VALUES (83, '开启过滤', 'true', '999', 33);