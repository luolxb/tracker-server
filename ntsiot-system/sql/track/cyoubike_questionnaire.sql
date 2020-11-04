create table questionnaire
(
    id           BIGINT(19) auto_increment comment 'id'
        primary key,
    title        VARCHAR(1000) null comment '题目',
    is_show      VARCHAR(10)   null comment '是否展示',
    create_time  BIGINT(19)    null comment '创建时间',
    jurisdiction BIGINT(19)    null comment '辖区'
)
    collate = utf8_bin;

INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (1, '12321312', '0', 1560428624043, 19);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (3, '吴泾问卷系统', '0', 1561445629315, 2);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (4, '总辖区问卷', '0', 1562160074150, 1);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (6, '社区生活调研', '0', 1570678087899, 6);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (9, '测测', '1', 1575020865361, 33);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (10, '测试', '0', 1575021336174, 33);
INSERT INTO cyoubike.questionnaire (id, title, is_show, create_time, jurisdiction) VALUES (11, '1', '1', 1575255164417, 33);