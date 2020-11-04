create table question_option
(
    id          BIGINT(19) auto_increment comment 'id'
        primary key,
    question_id BIGINT(19)   null comment '题目id',
    option_id   VARCHAR(255) null comment '选项id',
    option_val  VARCHAR(255) null comment '选项值'
)
    collate = utf8_bin;

INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (17, 9, '1', '很安全(5分)');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (18, 9, '2', '比较安全（4分）');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (19, 9, '3', '感觉一般（3分）');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (20, 9, '4', '不太安全（2分）');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (21, 9, '5', '不安全（1分）');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (22, 10, '0', '很安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (23, 10, '1', '比较安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (24, 10, '2', '感觉一般');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (25, 10, '3', '不太安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (26, 10, '4', '不安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (27, 11, '5', '有明显进步');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (28, 11, '6', '有一些进步');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (29, 11, '7', '没有变化');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (30, 11, '8', '不太安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (31, 11, '9', '非常不安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (50, 16, '28', '非常满意');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (51, 16, '29', '比较满意');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (52, 16, '31', '一般');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (53, 16, '32', '不太满意');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (54, 16, '33', '非常不满意');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (55, 17, '0', '居住地周边环境安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (56, 17, '1', '机动车（电动车、自行车）安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (57, 17, '2', '人身安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (58, 17, '3', '家庭财务安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (59, 17, '4', '交通安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (60, 18, '5', '赌博问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (61, 18, '6', '抢劫抢夺问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (62, 18, '7', '入室盗窃问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (63, 18, '8', '打架斗殴问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (64, 18, '9', '外来人口犯罪问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (65, 18, '10', '公共场所失窃问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (66, 18, '11', '机动车（电动车、自行车）被盗问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (67, 18, '12', '行政执法问题');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (68, 20, '0', '有一些进步');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (69, 20, '1', '没有变化');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (70, 20, '2', '不太安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (71, 20, '3', '非常不安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (72, 21, '4', '比较安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (73, 21, '5', '一般');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (74, 21, '6', '不太安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (75, 21, '7', '非常不安全');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (83, 25, '1', '是');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (84, 25, '2', '否');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (94, 28, '0', '小花');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (95, 28, '1', '小草');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (96, 28, '2', '小树');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (97, 28, '3', '小叶');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (98, 29, '0', '哈哈');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (99, 29, '1', '呵呵');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (100, 29, '2', '嘿嘿');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (129, 33, '2', '33');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (130, 33, '3', '44');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (131, 34, '0', '111');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (132, 34, '1', '222');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (133, 34, '2', '333');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (134, 34, '3', '444');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (144, 23, '3', '生病');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (145, 23, '5', '问路求助');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (146, 23, '2', '遇到危险');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (147, 23, '4', '帮助别人');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (148, 23, '6', '其他');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (153, 32, '1', '2');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (154, 32, '2', '3');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (155, 32, '3', '4');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (156, 32, '1', '1');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (161, 22, '1', '否');
INSERT INTO cyoubike.question_option (id, question_id, option_id, option_val) VALUES (162, 22, '0', '是');