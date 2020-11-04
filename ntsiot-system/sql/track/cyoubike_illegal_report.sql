create table illegal_report
(
    id                BIGINT(19) auto_increment
        primary key,
    content           VARCHAR(255)  null,
    name              VARCHAR(255)  null,
    address           VARCHAR(255)  null,
    telephone         VARCHAR(255)  null,
    create_time       BIGINT(19)    null,
    jurisdiction      BIGINT(19)    null,
    type              VARCHAR(255)  null,
    point             VARCHAR(255)  null,
    user_name         VARCHAR(20)   null,
    deal_time         BIGINT(19)    null,
    remark            VARCHAR(3000) null,
    jurisdiction_name VARCHAR(20)   null
)
    collate = utf8_bin;

INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (11, '测试', '测试', '测试', '13252853505', 1561448096326, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (12, '测试', '测试', '测试', '13252853505', 1561448845399, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (13, '测试', '测试', '测试', '13252853505', 1561449002884, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (14, '测试', '测试', '测试', '13252853505', 1561449096452, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (15, '测试过海
关', '测试', '测试', '13252853505', 1561449199917, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (16, '测试', '高鹏', '测试', '13252853505', 1561449939829, 2, '0', '123.423387,41.760785', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (17, '测试', '测试', '测试', '13252853505', 1561449980239, 2, '0', '123.420465,41.760755', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (18, '测试', '测试', '测试', '13252853505', 1561450163596, 2, '0', '123.422925,41.760788', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (19, '测试', '测试', '测试', '', 1561450284276, 2, '1', '123.420465,41.760755', 'admin', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (20, 'nxnxnxn', '', 'jxnxndj', '', 1561519620423, 2, '0', '121.448858,31.025119', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (21, '外面下雨，回不了家', '花花', '仙境', '15201937616', 1561543126840, 2, '3', '', 'wujing', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (22, '异常', '花花', '仙境', '15201937616', 1561601970218, 2, '1', '', 'wujing', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (23, '测试一下', '李四', '浦东新区', '18645231230', 1561725550838, 2, '0', '121.61319732666016,31.239656448364258', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (24, ' bbhxcbmkjb
', '', 'jkjhvbkgccbmmnbbv', '', 1561965998247, 2, '0', '', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (25, '红包', '哈哈', '辽宁省沈阳市和平区南三好街96号', '15140096454', 1561982721818, 2, '1', '123.426611328125,41.76319905598958', 'admin', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (26, '测试', '', '辽宁省沈阳市和平区三好街', '', 1562072321486, 2, '0', '123.42656412760417,41.76345296223958', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (27, '测试', '', '辽宁省沈阳市和平区三好街', '', 1562073733637, 2, '0', '123.42686604817709,41.76327365451389', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (28, '123123123', '213123', '辽宁省沈阳市和平区十一纬路76号', '15111111111', 1562074367642, 2, '0', '123.42056,41.78978', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (29, '111', '', '辽宁省沈阳市和平区三好街', '', 1562074566031, 2, '0', '123.42686604817709,41.76327365451389', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (30, '测试', '啊', '辽宁省沈阳市和平区南三好街96号', '13252853505', 1562120671096, 2, '0', '123.426611328125,41.76319905598958', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (31, '楚河汉界你吧', '', '韩国奋斗的地方干活', '', 1562745070182, 2, '0', '', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (32, '哈哈哈', '', '上海市闵行区东川路555号', '', 1562833425397, 20, '0', '121.45351616753472,31.023237575954862', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (33, '哈哈哈', '', '上海市闵行区东川路555号', '', 1562833426421, 20, '0', '121.45351616753472,31.023237575954862', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (34, 'fdsaf', '', '上海市闵行区东川路555号', '', 1562833454651, 2, '0', '121.452849,31.023834', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (35, '发放大', '', '上海市闵行区东川路555号', '', 1562833765313, 2, '0', '121.452849,31.023834', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (36, '范德萨发', '', '上海市闵行区东川路555号', '', 1562833819428, 2, '0', '121.452849,31.023834', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (37, 'f''d''sa范德萨发', '', '上海市闵行区东川路555号', '', 1562833868577, 2, '0', '121.452849,31.023834', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (38, '出门吃口饭看开点', '', '上海市闵行区东川路555号', '', 1562895898471, 2, '0', '121.45348117404514,31.023113606770835', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (39, '继续难兄难弟那你
', '', '上海市闵行区东川路555号', '', 1562895920743, 20, '0', '121.45348117404514,31.023113606770835', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (40, '，', '', '上海市闵行区东川路555号', '', 1562899341281, 2, '0', '121.45346327039931,31.02320773654514', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (41, 'fdsafd ', '', '上海市闵行区东川路555号', '', 1562902555021, 2, '0', '121.452849,31.023834', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (42, '记得你都没得满满的', '', '上海市闵行区东川路555号', '', 1562903787239, 20, '0', '121.45391082763672,31.02351188659668', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (43, '1', '', '上海市闵行区东川路555号', '', 1562908049059, 20, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (44, '1', '', '上海市闵行区东川路555号', '', 1562908050508, 20, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (45, '111', '', '上海市闵行区东川路555号', '', 1562908476666, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (46, '111', '', '上海市闵行区东川路555号', '', 1562908476806, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (47, '111', '', '上海市闵行区东川路555号', '', 1562908476945, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (48, '111', '', '上海市闵行区东川路555号', '', 1562908477025, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (49, '111', '', '上海市闵行区东川路555号', '', 1562908477245, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (50, '111', '', '上海市闵行区东川路555号', '', 1562908477444, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (51, '111', '', '上海市闵行区东川路555号', '', 1562908477605, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (52, '111', '', '上海市闵行区东川路555号', '', 1562908477764, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (53, '111', '', '上海市闵行区东川路555号', '', 1562908477924, 2, '0', '121.45341254340278,31.023198784722222', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (54, '111', '', '上海市闵行区东川路555号', '', 1562908478086, 2, '3', '121.45341254340278,31.023198784722222', 'zhc', 1565834613749, 'fsadfas ', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (55, '111', '', '上海市闵行区东川路555号', '', 1562908478266, 2, '1', '121.45341254340278,31.023198784722222', 'zhc', 1565834602186, 'asfasd ', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (56, '111', '', '上海市闵行区东川路555号', '', 1562908478424, 2, '2', '121.45341254340278,31.023198784722222', 'wujing', 1565705764332, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (57, '111', '', '上海市闵行区东川路555号', '', 1562908478585, 2, '1', '121.45341254340278,31.023198784722222', 'wujing', 1565705699666, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (58, '111', '', '上海市闵行区东川路555号', '', 1562908478765, 2, '1', '121.45341254340278,31.023198784722222', 'xierui', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (59, 'h''j''k''h''j''h和接口还结婚看', '', '上海市闵行区东川路555号', '13501869520', 1563435120744, 23, '1', '121.452849,31.023834', 'tangwan', null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (60, '三十多少钱', '', '广东省深圳市南山区同沙路32号', '', 1568096497760, 2, '0', '113.94550618489583,22.58415961371528', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (61, '有人乱扔垃圾', '', '上海市闵行区东川路555号', '', 1568106657971, 2, '0', '121.45370768229166,31.023548448350695', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (62, '写点什么吧！！！！！', '何11111', '广东省深圳市南山区同沙路30-4号', '13111111111', 1568181932219, 2, '1', '113.94559000651041,22.584517957899305', 'admin', 1568182003458, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (63, '测试', 'lijie', '广东省深圳市南山区留仙大道', '13666666666', 1568257682144, 2, '0', '113.94718933105469,22.58049201965332', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (64, 'ceshi', '', '广东省深圳市南山区同沙路30-4号', '', 1568280607475, 2, '0', '113.94554138183594,22.584409713745117', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (65, '。。', '', '广西省', '', 1568280722597, 20, '0', '113.94554138183594,22.584409713745117', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (66, '刘测试', '刘测试', '广东省深圳市南山区', '13048992376', 1568707598032, 2, '0', '113.94550618489583,22.58415961371528', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (67, '刘测试', '刘测试', '广东省深圳市南山区', '13048992376', 1568707602981, 2, '3', '113.94550618489583,22.58415961371528', 'test123123', 1568975072150, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (68, '开发人员测试使用，请勿处理', '', '广东省深圳市南山区同沙路', '', 1568777815862, 2, '1', '113.94568115234375,22.584691297743056', 'admin', 1568777875941, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (69, '啊啊啊', '', '广东省深圳市南山区同沙路', '', 1568777914938, 2, '3', '113.94568115234375,22.584691297743056', 'admin', 1568777936254, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (70, '从那次拒绝❌！你说褥我也偷偷告诉他你要我不去你家找不来人心都没有人能够给人以安全感就是你不可能在别人面前表现出了你', '春节放假', '广东省深圳市南山区同沙路30-4号', '15011112222', 1568876555237, 33, '3', '113.94551849365234,22.58458709716797', 'wayne', 1568943596084, '乱举报', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (71, '有人偷东西', '雷锋', '广东省深圳市南山区同沙路30-4号', '13344556677', 1568944541881, 2, '1', '113.94553602430555,22.584414876302084', 'test123123', 1568974877872, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (72, '有人偷东西', '雷锋', '广东省深圳市南山区同沙路30-4号', '13311223344', 1568944585826, 33, '1', '113.94553602430555,22.584414876302084', 'wayne', 1568944623001, '请详细描述一下', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (73, '有人卖发票', '雷锋', '广东省深圳市南山区同沙路30-4号', '13344556677', 1568944674125, 33, '2', '113.94553602430555,22.584414876302084', 'wayne', 1568944698112, '暂不处理，请向税务部门举报', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (74, '举报举报，有摩的出没', '莫迪', '广东省深圳市南山区同沙路30-4号', '13344445555', 1568945008996, 33, '2', '113.94553602430555,22.584414876302084', 'wayne', 1568945263715, '请举报给城管部门', null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (75, '道路不通', '海昏侯', '广东省深圳市南山区同沙路30-4号', '13422223333', 1568945050671, 33, '1', '113.94553602430555,22.584414876302084', 'admin', 1569241347354, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (76, '就是举报', '', '广东省深圳市南山区同沙路30-4号', '', 1569309917373, 2, '0', '113.94568115234375,22.584633246527776', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (77, 'gggg', '', '广东省深圳市南山区桃园路2号', '', 1569310727931, 2, '0', '113.93041,22.53332', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (78, 'h哈哈哈', '', '广东省深圳市南山区桃园路2号', '', 1569311017374, 2, '0', '113.93041,22.53332', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (79, 'jjj', '', '广东省深圳市南山区桃园路2号', '', 1569311247134, 2, '0', '113.93041,22.53332', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (80, '一样', '', '广东省深圳市南山区同沙路30-4号', '', 1569311287247, 2, '0', '113.94568115234375,22.584633246527776', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (81, 'ggggg11', '', '广东省深圳市南山区桃园路2号', '', 1569314128000, 2, '0', '113.93041,22.53332', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (84, '我要举报你', '', '广东省深圳市南山区同沙路168号', '', 1569314490386, 33, '3', '113.9450669289,22.5842412953', 'hytest', 1569315007911, '瞎搞', '西丽派出所');
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (85, '再次举报你', '', '广东省深圳市南山区同沙路168号', '', 1569315070583, 33, '0', '113.9450669289,22.5842412953', null, null, null, '西丽派出所');
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (86, '测试', '李四', '广东省深圳市南山区同沙路168号', '13100000000', 1569403993866, 33, '0', '113.9450669289,22.5842412953', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (87, '测试', '哈哈', '广东省深圳市南山区同沙路168号', '13500001111', 1569404655004, 33, '0', '113.9450669289,22.5842412953', null, null, null, null);
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (88, '路边有非法售卖野生动物', '', '广东省深圳市南山区同沙路168号', '', 1570777992870, 33, '1', '113.94503,22.58432', 'liupiyi02', 1570778011735, null, '西丽派出所');
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (89, '测试', '测试', '广东省深圳市南山区同沙路30-4号', '13500002222', 1570780565273, 2, '1', '113.94555609809028,22.584516059027777', 'xierui', 1575393724428, '1234', '吴泾派出所');
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (90, '有人打架斗殴', '测试用户', '广东省深圳市南山区同沙路30-4号', '13369852356', 1577156713485, 33, '1', '113.94597954644097,22.58457763671875', 'tujun', 1577156739472, null, '西丽派出所');
INSERT INTO cyoubike.illegal_report (id, content, name, address, telephone, create_time, jurisdiction, type, point, user_name, deal_time, remark, jurisdiction_name) VALUES (91, '发货回家
', '', '广东省深圳市南山区同沙路30-4号', '', 1577157233324, 33, '0', '113.94597954644097,22.58457763671875', null, null, null, '西丽派出所');