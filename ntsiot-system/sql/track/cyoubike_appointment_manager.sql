create table appointment_manager
(
    id           BIGINT(19) auto_increment
        primary key,
    username     VARCHAR(255) null comment '姓名',
    phone        VARCHAR(50)  null comment '电话',
    jurisdiction BIGINT(19)   null comment '辖区',
    switch       INT(10)      null comment '短信开关,1:开启，0关闭',
    period INT (10) default 0 null comment '短信发送周期,0:每天，1：每周，2：每月',
    create_time  BIGINT(19)   null comment '创建时间',
    update_time  BIGINT(19)   null comment '修改时间'
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.appointment_manager (id, username, phone, jurisdiction, switch, period, create_time, update_time) VALUES (1, '韩宝昆', '18321563426', 25, 0, 1, 15614300152871231, 1581066305791);
INSERT INTO cyoubike.appointment_manager (id, username, phone, jurisdiction, switch, period, create_time, update_time) VALUES (2, '王涛', '17612172512', 2, 0, 1, 15614300152871231, 1572623921572);
INSERT INTO cyoubike.appointment_manager (id, username, phone, jurisdiction, switch, period, create_time, update_time) VALUES (4, '李朝阳', '18640561230', 2, 0, 2, 1572448061500, 1581066297672);