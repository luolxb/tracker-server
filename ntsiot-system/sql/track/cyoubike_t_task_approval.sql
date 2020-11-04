create table t_task_approval
(
    id          BIGINT(19) auto_increment
        primary key,
    task_id     BIGINT(19)    null comment '任务id',
    score       BIGINT(19)    null comment '分数',
    remark      VARCHAR(2000) null comment '描述',
    create_time BIGINT(19)    null
)
    charset = utf8mb4;

INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (1, 1, 1, '不及格', 1562935286785);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (2, 2, 100, '', 1562935328928);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (3, 3, 11, '', 1562935379222);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (4, 4, 333, '', 1562935816921);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (5, 23, 1100, '', 1562984760527);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (6, 523, 3, '任务没做完，偷懒。', 1567589267711);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (7, 571, 2, '未完成全部必到点巡更（测试用）', 1568700036490);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (8, 579, 1, '', 1568774006681);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (9, 604, 100, '', 1568886063821);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (10, 654, 1, '', 1569201293175);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (11, 775, 1, '', 1569833392132);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (12, 860, 60, '', 1570516257028);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (13, 861, 70, '', 1570516860102);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (14, 898, 70, '', 1570789633008);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (15, 906, 10, '', 1570845316618);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (16, 911, 10, '', 1570848998852);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (17, 905, 99, '测试用', 1570850534926);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (18, 1390, 90, '', 1574756096161);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (19, 1268, 11, '', 1574846070945);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (20, 1498, 12, '', 1575267802089);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (21, 1803, 100, '', 1577095770611);
INSERT INTO cyoubike.t_task_approval (id, task_id, score, remark, create_time) VALUES (22, 1802, 1, '', 1577095794887);