create table t_task_point
(
    id           BIGINT(19) auto_increment
        primary key,
    task_id      BIGINT(19)    null comment '任务id',
    point_id     BIGINT(19)    null comment '必到点id',
    point_name   VARCHAR(255)  null comment '必到点名称',
    point_remark VARCHAR(2000) null comment '必到点描述',
    longitude    VARCHAR(255)  null comment '经度',
    latitude     VARCHAR(255)  null comment '纬度'
)
    charset = utf8mb4;

INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (191, 32, 54, '吴泾派出所', null, '121.45034', '31.040682');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (192, 32, 55, '吴泾宝龙广场', null, '121.452124', '31.037105');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (193, 32, 63, '东川路红梅南路路口', null, '121.460153', '31.026228');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (194, 32, 62, '莲花南路东川路路口', null, '121.451016', '31.023798');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (195, 32, 61, '莲花南路', null, '121.448266', '31.030238');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (196, 32, 60, '莲花南路剑川路路口', null, '121.446112', '31.034931');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (197, 32, 64, '曹家塘路永德路路口', null, '121.445851', '31.03988');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (198, 32, 54, '吴泾派出所', null, '121.45034', '31.040682');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (214, 37, 85, 'test必到点02', null, '113.954409', '22.580841');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (215, 37, 86, 'test必到点03', null, '113.949115', '22.583927');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (250, 38, 90, '线路1-必到点1', null, '113.944102', '22.583815');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (251, 38, 93, '线路1-必到点2', null, '113.945286', '22.580691');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (252, 38, 94, '线路1-必到点3', null, '113.945161', '22.584459');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (256, 51, 112, 'test-11.1', null, '113.945172', '22.583974');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (257, 52, 113, '必到点001', null, '113.944212', '22.58444');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (258, 52, 114, '必到点002', null, '113.945162', '22.584554');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (259, 52, 115, '必到点003', null, '113.945924', '22.584499');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (260, 53, 118, '002', null, '113.94812', '22.585522');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (261, 53, 117, 'lxd001', null, '113.944102', '22.585953');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (262, 53, 119, '003', null, '113.944107', '22.583927');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (266, 54, 82, 'test必到点01', null, '113.949516', '22.579869');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (267, 54, 83, 'test必到点02', null, '113.950977', '22.581026');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (268, 54, 89, '西湖测试必到点', null, '123.403276', '41.750588');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (269, 55, 94, '线路1-必到点3', null, '113.945161', '22.584459');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (270, 55, 82, 'test必到点01', null, '113.949516', '22.579869');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (271, 55, 93, '线路1-必到点2', null, '113.945286', '22.580691');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (272, 55, 90, '线路1-必到点1', null, '113.944102', '22.583815');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (273, 55, 83, 'test必到点02', null, '113.950977', '22.581026');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (274, 56, 120, '凯达尔1号门', null, '113.945084', '22.58465');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (275, 56, 121, '凯达尔2号门', null, '113.945252', '22.584586');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (276, 56, 122, '凯达尔3号门', null, '113.944855', '22.584378');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (277, 57, 120, '凯达尔1号门', null, '113.945084', '22.58465');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (278, 57, 121, '凯达尔2号门', null, '113.945252', '22.584586');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (281, 58, 123, '二五', null, '113.944844', '22.584581');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (282, 58, 122, '凯达尔3号门', null, '113.944855', '22.584378');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (283, 58, 120, '凯达尔1号门', null, '113.945084', '22.58465');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (284, 58, 121, '凯达尔2号门', null, '113.945252', '22.584586');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (345, 34, 55, '吴泾宝龙广场', null, '121.452124', '31.037105');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (346, 34, 54, '吴泾派出所', null, '121.45034', '31.040682');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (347, 34, 56, '吴泾医院', null, '121.467621', '31.041761');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (348, 34, 57, '吴泾渡口及公园', null, '121.478581', '31.045728');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (349, 33, 54, '吴泾派出所', null, '121.45034', '31.040682');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (350, 33, 55, '吴泾宝龙广场', null, '121.452124', '31.037105');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (351, 33, 56, '吴泾医院', null, '121.467621', '31.041761');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (352, 33, 57, '吴泾渡口及公园', null, '121.478581', '31.045728');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (353, 59, 128, '凯达尔1', null, '113.945166', '22.584645');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (354, 59, 129, '凯达尔2', null, '113.945144', '22.584413');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (355, 60, 131, '南山分局002', null, '121.451869', '31.021897');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (356, 60, 130, '南山分局001', null, '121.451113', '31.023506');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (357, 60, 133, '南山分局004', null, '121.454589', '31.024568');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (358, 60, 132, '南山分局003', null, '121.453806', '31.02318');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (359, 61, 134, '凯达尔3', null, '113.945273', '22.584556');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (360, 61, 128, '凯达尔1', null, '113.945166', '22.584645');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (361, 61, 129, '凯达尔2', null, '113.945144', '22.584413');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (362, 62, 134, '凯达尔3', null, '113.945273', '22.584556');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (363, 62, 135, '凯达尔4', null, '113.909822', '22.574287');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (369, 63, 121, '凯达尔2号门', null, '113.945252', '22.584586');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (370, 63, 120, '凯达尔1号门', null, '113.945084', '22.58465');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (371, 63, 114, '必到点002', null, '113.945162', '22.584554');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (372, 64, 136, '测试1', null, '113.944903', '22.584576');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (373, 64, 137, '测试2', null, '113.945187', '22.584521');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (374, 65, 139, '西丽图片2', null, '113.945412', '22.584467');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (375, 65, 138, '西丽图片', null, '113.945255', '22.584658');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (376, 66, 140, '测试1223-1', null, '113.945673', '22.584716');
INSERT INTO cyoubike.t_task_point (id, task_id, point_id, point_name, point_remark, longitude, latitude) VALUES (377, 66, 141, '测试1223-2', null, '113.944337', '22.581487');