create table t_check_point
(
    id           BIGINT(19) auto_increment
        primary key,
    longitude    VARCHAR(100) null,
    latitude     VARCHAR(100) null,
    name         VARCHAR(100) null,
    remark       TEXT(65535)  null,
    jurisdiction BIGINT(19)   null,
    create_time  BIGINT(19)   null,
    creator      BIGINT(19)   null,
    update_time  BIGINT(19)   null,
    updater      BIGINT(19)   null,
    scope        VARCHAR(255) null
)
    collate = utf8_bin;

INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (54, '121.45034', '31.040682', '吴泾派出所', null, 2, 1561450330764, 18, 1563175437729, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (55, '121.452124', '31.037105', '吴泾宝龙广场', null, 2, 1562933752943, 29, 1563175741083, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (56, '121.467621', '31.041761', '吴泾医院', null, 2, 1562933789906, 29, 1563175819328, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (57, '121.478581', '31.045728', '吴泾渡口及公园', null, 2, 1563175876104, 18, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (58, '121.466947', '31.043499', '永德路步行街', null, 2, 1563175950238, 18, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (60, '121.446112', '31.034931', '莲花南路剑川路路口', null, 2, 1563433629345, 30, 1566184343686, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (61, '121.448266', '31.030238', '莲花南路', null, 2, 1563433695084, 30, 1566184018522, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (62, '121.450999', '31.023775', '莲花南路东川路路口', null, 2, 1563433735568, 30, 1568077455256, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (63, '121.460094', '31.026226', '东川路红梅南路路口', null, 2, 1564549027929, 18, 1568077480234, 18, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (64, '121.445757', '31.039911', '曹家塘路永德路路口', null, 2, 1564549291498, 18, 1566471580445, 29, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (74, '121.451877', '31.023169', '12123', null, 1, 1566875399865, 1, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (75, '121.452542', '31.023243', '88888', null, 1, 1566875552347, 1, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (79, '123.435671', '41.755877', '九江', null, 1, 1566892790552, 1, null, null, '44');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (82, '113.949516', '22.579869', 'test必到点01', null, 33, 1568362644421, 318, 1570513376965, 318, '10');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (83, '113.950977', '22.581026', 'test必到点02', null, 33, 1568363223032, 318, null, null, '10');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (84, '113.944344', '22.580874', 'test必到点01', null, 34, 1568614829262, 320, null, null, '10');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (85, '113.954409', '22.580841', 'test必到点02', null, 34, 1568620915691, 320, null, null, '12');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (86, '113.949115', '22.583927', 'test必到点03', null, 34, 1568621281570, 320, null, null, '15');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (89, '123.403276', '41.750588', '西湖测试必到点', null, 33, 1568683608614, 316, 1569749562919, 343, '1000');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (90, '113.944102', '22.583815', '线路1-必到点1', null, 33, 1568691699023, 316, 1568773492638, 316, '20');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (93, '113.945286', '22.580691', '线路1-必到点2', null, 33, 1568697745609, 316, 1577167576186, 316, '20');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (94, '113.945161', '22.584459', '线路1-必到点3', null, 33, 1568697857737, 316, null, null, '20');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (95, '113.942852', '22.581214', '地铁巡逻01', null, 43, 1568884633689, 336, null, null, '100');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (96, '113.92619', '22.54897', '地铁巡逻02', null, 43, 1568884704087, 336, null, null, '55');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (99, '113.95244', '22.54088', '111111p', null, 1, 1569317797299, 1, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (103, '121.462044', '31.236403', '自然博物馆', null, 6, 1570679823427, 324, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (104, '121.510204', '31.029906', '洞泾', null, 6, 1570679992818, 324, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (107, '121.45361', '31.02336', '765', null, 5, 1570844412672, 355, 1570844470304, 355, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (108, '121.452821', '31.023401', '4688', null, 5, 1570844502643, 355, null, null, '80');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (109, '121.474017', '31.231119', '小区', null, 5, 1570848564361, 355, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (112, '113.945172', '22.583974', 'test-11.1', null, 33, 1572588293001, 348, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (113, '113.944212', '22.58444', '必到点001', null, 33, 1573184098225, 339, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (114, '113.945162', '22.584554', '必到点002', null, 33, 1573184146038, 339, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (115, '113.945924', '22.584499', '必到点003', null, 33, 1573184274798, 339, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (116, '113.93989', '22.54371', '111', null, 33, 1574399704166, 316, null, null, '10');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (117, '113.944102', '22.585953', 'lxd001', null, 52, 1574648104887, 360, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (118, '113.94812', '22.585522', '002', null, 52, 1574648160056, 360, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (119, '113.944107', '22.583927', '003', null, 52, 1574648199625, 360, null, null, '300');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (120, '113.945084', '22.58465', '凯达尔1号门', null, 33, 1574756021645, 348, 1574756488088, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (121, '113.945252', '22.584586', '凯达尔2号门', null, 33, 1574756064841, 348, 1575453664558, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (122, '113.944855', '22.584378', '凯达尔3号门', null, 33, 1574756150725, 348, 1575532940998, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (123, '113.944844', '22.584581', '二五', null, 33, 1574761829118, 348, 1575014027152, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (124, '113.944294', '22.5864', 'test1', null, 33, 1575352429232, 339, null, null, '100');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (125, '113.943944', '22.580655', 'test2', null, 33, 1575352541200, 339, null, null, '100');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (126, '113.94823', '22.581086', 'test3', null, 33, 1575352621452, 339, 1575353048089, 339, '100');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (127, '113.94096', '22.55504', 'test123', null, 33, 1575443743332, 339, null, null, '100');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (128, '113.945166', '22.584645', '凯达尔1', null, 52, 1575876050314, 363, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (129, '113.945144', '22.584413', '凯达尔2', null, 52, 1575876086996, 363, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (130, '121.451113', '31.023506', '南山分局001', null, 32, 1576034646598, 365, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (131, '121.451869', '31.021897', '南山分局002', null, 32, 1576034691776, 365, null, null, '20');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (132, '121.453806', '31.02318', '南山分局003', null, 32, 1576034760930, 365, null, null, '80');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (133, '121.454589', '31.024568', '南山分局004', null, 32, 1576034793910, 365, null, null, '50');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (134, '113.945273', '22.584556', '凯达尔3', null, 52, 1576140430832, 363, 1576140991520, 363, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (135, '113.909822', '22.574287', '凯达尔4', null, 52, 1576143343156, 363, 1576649242317, 316, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (136, '113.944903', '22.584576', '测试1', null, 62, 1576819552478, 416, 1576834202334, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (137, '113.945187', '22.584521', '测试2', null, 62, 1576819585962, 416, 1576834160033, 348, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (138, '113.945255', '22.584658', '西丽图片', null, 33, 1576834270912, 348, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (139, '113.945412', '22.584467', '西丽图片2', null, 33, 1576834308224, 348, null, null, '500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (140, '113.945673', '22.584716', '测试1223-1', null, 33, 1577093738667, 417, 1577095445319, 417, '1500');
INSERT INTO cyoubike.t_check_point (id, longitude, latitude, name, remark, jurisdiction, create_time, creator, update_time, updater, scope) VALUES (141, '113.944337', '22.581487', '测试1223-2', null, 33, 1577093959634, 417, 1577095324857, 417, '1500');