create table housing_tenant_record
(
    id            BIGINT(19) auto_increment
        primary key,
    tenant        VARCHAR(255) null comment '租客',
    tenant_phone  VARCHAR(255) null comment '租客电话',
    tenant_idcard VARCHAR(255) null comment '租客身份证号',
    house_id      BIGINT(19)   null comment '房屋id',
    create_time   BIGINT(19)   null,
    update_time   BIGINT(19)   null,
    constraint housing_tenant_record_ibfk_1
        foreign key (house_id) references housing_owner_record (id)
)
    collate = utf8mb4_unicode_ci;

create index fk_house_id
    on housing_tenant_record (house_id);

INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (1, '租客1', '1512341234', '123123123112331', 1, 1562833756728, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (2, '同方A', '110', '1111', 3, 1568698548352, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (3, '发改委', '120', '111', 4, 1568698795649, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (4, '花花', '15689352456', '15X963198562402353', 5, 1568772251962, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (5, 'fndndnd', '13554946464', '', 6, 1568772663385, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (6, '等你拿到你的呢', '13505181848', '', 6, 1568772663385, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (7, '嗯嗯你的', '13501869524', '', 7, 1568772737496, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (8, '代扣代缴等你呢', '13501869574', '', 7, 1568772737496, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (9, '顶焦度计到家', '', '', 8, 1568772808964, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (10, '等你到奶奶的', '', '', 8, 1568772808964, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (11, '艾卡阿阿', '', '', 9, 1568772991112, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (12, '八格牙路', '', '', 9, 1568772991112, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (13, '孩他爹', '', '', 10, 1568773028535, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (14, '还他妈', '', '', 10, 1568773028535, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (15, '大家都不懂你', '', '', 11, 1568773403795, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (16, '记得记得记得你', '', '', 11, 1568773403795, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (17, 'ratio', '', '', 12, 1568773545350, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (18, '侯任伟', '', '', 12, 1568773545350, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (19, '123', '123181302588', '370404199690537666', 17, 1570850174864, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (20, '胡姬花', '15618953241', '4561327869850580', 21, 1580720990474, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (21, '租户1', '15618905234567', '45870968523142', 22, 1580721284613, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (22, '租户2', '152369817564', '1376525800', 22, 1580721284613, null);
INSERT INTO cyoubike.housing_tenant_record (id, tenant, tenant_phone, tenant_idcard, house_id, create_time, update_time) VALUES (23, '租房媛', '1568975642314', '454646998555866', 23, 1580721583163, null);