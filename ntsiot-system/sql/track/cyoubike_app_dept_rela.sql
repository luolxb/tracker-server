create table app_dept_rela
(
    id      BIGINT(19) auto_increment comment 'id'
        primary key,
    app_id  BIGINT(19)        null comment '小程序编号',
    dept_id BIGINT(19)        null comment '辖区编号',
    type    INT(10) default 0 null comment '类型 0-小程序 1-公众号'
)
    charset = utf8mb4;

INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (5, 1, 2, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (7, 1, 20, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (8, 1, 23, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (12, 1, 38, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (13, 1, 37, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (14, 1, 8, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (15, 1, 6, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (20, 6, 29, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (22, 1, 5, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (31, 14, 1, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (39, 21, 52, 0);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (86, 68, 33, 1);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (89, 71, 33, 1);
INSERT INTO cyoubike.app_dept_rela (id, app_id, dept_id, type) VALUES (99, 1, 33, 0);