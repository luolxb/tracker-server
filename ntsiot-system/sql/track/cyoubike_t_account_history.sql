create table t_account_history
(
    id             BIGINT(19) auto_increment
        primary key,
    account_id     BIGINT(19)   null comment '账号编号',
    balance        DECIMAL(56)  null comment '账户余额',
    balance_amount DECIMAL(56)  null comment '余额支付',
    wexin_amount   DECIMAL(56)  null,
    weixin_no      VARCHAR(255) null comment '微信流水号',
    create_time    BIGINT(19)   null,
    type           SMALLINT(5)  null comment '类型'
)
    collate = utf8_bin;

