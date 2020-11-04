create table quartz_log
(
    id               BIGINT(19) auto_increment
        primary key,
    baen_name        VARCHAR(255) null,
    create_time      DATETIME(19) null,
    cron_expression  VARCHAR(255) null,
    exception_detail TEXT(65535)  null,
    is_success       BIT          null,
    job_name         VARCHAR(255) null,
    method_name      VARCHAR(255) null,
    params           VARCHAR(255) null,
    time             BIGINT(19)   null
);

INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (1, 'visitsTask', '2019-04-16 09:01:54', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 14229);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (2, 'visitsTask', '2019-04-17 09:19:43', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 230);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (3, 'visitsTask', '2019-04-18 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 103);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (4, 'visitsTask', '2019-04-19 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 248);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (5, 'visitsTask', '2019-04-20 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 284);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (6, 'visitsTask', '2019-04-21 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 176);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (7, 'visitsTask', '2019-04-23 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 153);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (8, 'visitsTask', '2019-04-24 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 201);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (9, 'visitsTask', '2019-04-25 00:00:00', '0 0 0 * * ?', null, true, '更新访客记录', 'run', null, 118);
INSERT INTO cyoubike.quartz_log (id, baen_name, create_time, cron_expression, exception_detail, is_success, job_name, method_name, params, time) VALUES (10, 'testTask', '2019-04-25 14:01:27', '0/5 * * * * ?', null, true, '测试', 'run', '', 3);