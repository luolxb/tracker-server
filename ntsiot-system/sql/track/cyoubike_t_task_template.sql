create table t_task_template
(
    id          BIGINT(19) auto_increment
        primary key,
    task_name   VARCHAR(255)  null comment '任务名称',
    path        VARCHAR(2000) null comment '巡更轨迹',
    dept_id     BIGINT(19)    null comment '辖区',
    create_time BIGINT(19)    null,
    update_time BIGINT(19)    null
)
    charset = utf8mb4;

INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (32, '完整的巡逻任务', '', 2, 1566263169286, 1566263188505);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (33, '2dui', '', 2, 1566265562486, 1575386459509);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (34, '任务1', '', 2, 1568098363459, 1575298971523);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (37, 'test任务001', '', 34, 1568697751760, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (38, '线路1', '', 33, 1568699291077, 1572424036505);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (39, '地铁巡逻', '', 33, 1568884786366, 1568885072619);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (40, 'test巡更任务04', '', 33, 1568972328384, 1568972356965);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (44, '巡逻任务1', '', 6, 1570691897472, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (47, '157', '', 5, 1570844560799, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (48, '浦锦', '', 5, 1570848628265, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (51, '测试', '', 33, 1572588346713, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (52, '测试必到点', '', 33, 1573184345665, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (53, '必到点测试1125', '', 52, 1574648261916, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (54, '模板测试111', '', 33, 1574680412322, 1574680425975);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (55, '测试巡更新模版', '', 33, 1574755528802, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (56, '凯达尔任务', '', 33, 1574756189839, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (57, '十一月二十六', '', 33, 1574756615098, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (58, '二五', '', 33, 1574761850219, 1574993320353);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (59, '凯达尔', '', 52, 1575876119334, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (60, '南山分局', '', 32, 1576034865502, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (61, '留仙洞', '', 52, 1576141024810, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (62, '留仙洞测试', '', 52, 1576143376151, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (63, '2个上传图片任务', '', 33, 1576650613559, 1576658782573);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (64, '测试', '', 62, 1576819603209, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (65, '西丽图片任务', '', 33, 1576834333420, null);
INSERT INTO cyoubike.t_task_template (id, task_name, path, dept_id, create_time, update_time) VALUES (66, '巡更1223', '', 33, 1577094017625, null);