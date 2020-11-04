create table bike_license
(
    id           BIGINT(19) auto_increment comment '编号'
        primary key,
    telephone    VARCHAR(30)  null comment '联系电话',
    jurisdiction BIGINT(19)   null comment '所属辖区',
    type         BIGINT(19)   null comment '授权类型 1：永久；0：临时',
    start_time   BIGINT(19)   null comment '授权开始时间',
    end_time     BIGINT(19)   null comment '授权结束时间',
    create_time  BIGINT(19)   null comment '创建时间',
    update_time  BIGINT(19)   null comment '修改时间',
    creator      VARCHAR(255) null comment '创建者',
    updater      VARCHAR(255) null comment '修改者'
)
    collate = utf8_bin;

INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (25, '13501869520', 2, 1, null, null, 1561604202638, null, 'wujing', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (26, '13643454625', 2, 1, null, null, 1562833756728, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (29, '15104017490', 2, 1, null, null, 1565768480575, null, 'xierui', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (30, '15914012787', 2, 1, null, null, 1566560147603, null, 'zhc', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (31, '13252853505', 2, 1, null, null, 1566560694193, null, 'zhc', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (32, '13501869520', 25, 1, null, null, 1567384726638, null, 'wujing', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (33, '13428727501', 33, 1, null, null, 1567390896162, 1568601734935, 'wujing', 'lijie');
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (38, '13714553315', 2, 1, null, null, 1568185174231, 1575386545391, 'admin', 'xierui');
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (40, '13452155052', 33, 1, null, null, 1568625296217, 1570775071959, 'admin', 'heyu1');
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (43, '15914012787', 33, 1, null, null, 1568690680296, null, 'heyu1', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (45, '18559226282', 33, 1, null, null, 1568794352539, null, 'liunanchang', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (46, '18641182137', 33, 1, null, null, 1568795289515, null, 'heyu1', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (47, '13423455432', 33, 0, 1568822400000, 1569427200000, 1568887111724, null, 'wayne', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (49, '13423763880', 33, 1, null, null, 1568887260735, 1568944419669, 'wayne', 'wayne');
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (50, '13620903683', 33, 1, null, null, 1568958787813, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (51, '13714553315', 33, 1, null, null, 1568961781911, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (56, '13452155052', 2, 1, null, null, 1569226630384, null, 'wujing', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (58, '13501869520', 37, 1, null, null, 1569258190003, null, 'chedun', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (59, '13452155052', 37, 1, null, null, 1569258229842, null, 'chedun', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (60, '13715143282', 33, 1, null, null, 1569492572956, 1570518177545, 'wayne', 'tanqin');
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (61, '18867368720', 33, 0, 1567267200000, 1568476800000, 1569575458932, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (62, '18867368720', 33, 1, null, null, 1569575615779, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (67, '13048992376', 33, 1, null, null, 1570606022117, null, 'liupiyi02', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (69, '13501869520', 6, 0, 1570550400000, 1570809600000, 1570613794669, null, 'dongjing', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (71, '15118086963', 33, 1, null, null, 1573782217764, null, 'tanqin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (72, '13480924112', 33, 1, null, null, 1574131323073, null, 'tanqin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (73, '13643454625', 33, 1, null, null, 1574245932436, null, 'heyu1', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (74, '13501869520', 33, 1, null, null, 1574246057209, null, 'heyu1', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (75, '17620389488', 33, 1, null, null, 1574316830194, null, 'tanqin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (76, '15767543804', 33, 1, null, null, 1575013098136, null, 'wayne', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (78, '13715143282', 32, 1, null, null, 1575949801264, null, 'tanqin07', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (79, '13715143282', 52, 1, null, null, 1575961656124, null, 'tanqin05', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (80, '14774808276', 33, 1, null, null, 1576202923109, null, 'tanqin06', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (81, '15112463535', 33, 1, null, null, 1576202936892, null, 'tanqin06', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (82, '13428727501', 33, 1, null, null, 1576215141263, null, 'tanqin06', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (83, '18818795524', 33, 1, null, null, 1576215483686, null, 'tanqin06', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (84, '13701866167', 33, 1, null, null, 1576724868736, null, 'wayne', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (85, '13715143282', 48, 1, null, null, 1576807402878, null, 'tanqin09', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (86, '13715143282', 62, 1, null, null, 1576807455617, null, 'tanqin12', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (87, '13410528170', 33, 1, null, null, 1577090646276, null, 'admin', null);
INSERT INTO cyoubike.bike_license (id, telephone, jurisdiction, type, start_time, end_time, create_time, update_time, creator, updater) VALUES (88, '13715143282', 2, 1, null, null, 1577416617071, null, 'qintan03', null);