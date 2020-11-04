create table quartz_job
(
    id              BIGINT(19) auto_increment comment 'ID'
        primary key,
    bean_name       VARCHAR(255) null comment 'Spring Bean名称',
    cron_expression VARCHAR(255) null comment 'cron 表达式',
    is_pause        BIT          null comment '状态：1暂停、0启用',
    job_name        VARCHAR(255) null comment '任务名称',
    method_name     VARCHAR(255) null comment '方法名称',
    params          VARCHAR(255) null comment '参数',
    remark          VARCHAR(255) null comment '备注',
    update_time     DATETIME(19) null comment '创建或更新日期'
);

INSERT INTO cyoubike.quartz_job (id, bean_name, cron_expression, is_pause, job_name, method_name, params, remark, update_time) VALUES (1, 'visitsTask', '0 0 0 * * ?', false, '更新访客记录', 'run', null, '每日0点创建新的访客记录', '2019-01-08 14:53:31');
INSERT INTO cyoubike.quartz_job (id, bean_name, cron_expression, is_pause, job_name, method_name, params, remark, update_time) VALUES (2, 'testTask', '0/5 * * * * ?', true, '测试1', 'run1', 'test', '带参测试，多参使用json', '2019-01-13 14:20:50');
INSERT INTO cyoubike.quartz_job (id, bean_name, cron_expression, is_pause, job_name, method_name, params, remark, update_time) VALUES (3, 'testTask', '0/5 * * * * ?', true, '测试', 'run', '', '不带参测试', '2019-04-09 16:16:44');