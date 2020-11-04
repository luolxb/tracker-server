create table t_recharge_record
(
    id              BIGINT(19) auto_increment comment '记录编号'
        primary key,
    out_recharge_id VARCHAR(255) null comment '商户充值记录编号',
    user_id         BIGINT(19)   null comment '用户编号',
    history_id      BIGINT(19)   null comment '流水编号',
    charges_count   DECIMAL(56)  null comment '充值金额',
    largess_count   DECIMAL(56)  null comment '赠送金额',
    actual_count    DECIMAL(56)  null comment '实际金额',
    charges_time    BIGINT(19)   null comment '充值时间',
    status          INT(10)      null comment '状态'
)
    collate = utf8_bin;

INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (7, '2019091711033264014', 476, null, 0, null, 0, null, 0);
INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (8, '2019091713550556856', 476, null, 0, null, 0, null, 2);
INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (9, '2019091713583383339', 476, null, 0, null, 0, null, 2);
INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (10, '2019091717175725999', 476, null, 0, null, 0, null, 2);
INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (11, '2019091914460613544', 604, null, 0, null, 0, null, 0);
INSERT INTO cyoubike.t_recharge_record (id, out_recharge_id, user_id, history_id, charges_count, largess_count, actual_count, charges_time, status) VALUES (12, '2019092313593693881', 634, null, 0, null, 0, null, 0);