create table virtual_pile
(
    id           BIGINT(19) auto_increment comment '编号'
        primary key,
    longitude    VARCHAR(100) null comment '经度',
    latitude     VARCHAR(100) null comment '纬度',
    name         VARCHAR(100) null comment '名称',
    scope        BIGINT(19)   null comment '范围',
    remark       TEXT(65535)  null comment '说明',
    jurisdiction BIGINT(19)   null comment '辖区编号',
    create_time  BIGINT(19)   null comment '创建时间',
    creator      VARCHAR(100) null comment '创建者',
    update_time  BIGINT(19)   null comment '更新时间',
    updater      VARCHAR(100) null comment '更新者'
)
    collate = utf8_bin;

INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (50, '121.449251', '31.040479', '派出所停车点', 50, '派出所1', 2, 1561340349422, '1', 1561715917474, '30');
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (53, '121.45568', '31.249545', '上海站', 33, null, 2, 1566472238583, '29', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (54, '123.435077', '41.76132', '青年', 44, null, 2, 1566475175523, '29', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (55, '121.452526', '31.023155', '111111', null, null, 1, 1566875374988, '1', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (56, '123.432905', '41.756017', '世贸', 33, null, 2, 1566897895422, '29', 1566898144011, '29');
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (57, '123.442475', '41.755056', '吾爱立交', 55, null, 2, 1566898210643, '29', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (58, '123.452968', '41.744266', '七星公馆', 33, null, 2, 1566898762764, '29', 1566898817360, '29');
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (59, '123.422431', '41.764238', 'EPM', 44, null, 1, 1566900439684, '1', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (60, '123.401323', '41.771884', '六医院', 44, null, 2, 1566902618669, '1', 1566902672270, '1');
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (61, '123.431481', '41.764226', '党校', 44, null, 2, 1566985731720, '29', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (62, '113.943412', '22.58094', 'test虚拟桩01', 15, '测试', 34, 1568614528564, '320', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (63, '113.950432', '22.584616', 'test虚拟桩02', 100, null, 34, 1568614647457, '320', 1568623852563, '323');
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (68, '123.426493', '41.763547', 'rns虚拟桩', 100000, null, 2, 1568863703510, '30', null, null);
INSERT INTO cyoubike.virtual_pile (id, longitude, latitude, name, scope, remark, jurisdiction, create_time, creator, update_time, updater) VALUES (120, '113.944812', '22.584492', '测试', 500, null, 33, 1576484782162, '398', null, null);