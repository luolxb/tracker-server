create table t_account
(
    id           BIGINT(19) auto_increment comment '用户编号账目表编号'
        primary key,
    user_id      BIGINT(19)  null comment '用户编号',
    balance      DECIMAL(65) null comment '当前金额',
    total_expend DECIMAL(65) null comment '消费总金额'
)
    collate = utf8_bin;

