create table question
(
    id            BIGINT(19) auto_increment comment 'id'
        primary key,
    title         VARCHAR(1000) null comment '题目',
    question_type VARCHAR(10)   null comment '问题类型',
    is_show       VARCHAR(10)   null comment '是否展示',
    create_time   BIGINT(19)    null comment '创建时间',
    sort          BIGINT(19)    null comment '顺序',
    jurisdiction  BIGINT(19)    null comment '辖区'
)
    collate = utf8_bin;

INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (9, '您对居住地区是否感到安全？', '1', '0', 1561348475049, 0, 1);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (10, '您对居住地区是否感到安全？', '1', '0', 1561434509426, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (11, '最近一年来您所居住的地区治安状况与一年前比较是否有变化？', '1', '0', 1561434583136, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (12, '您怎么表述您居住区周边的公共场所的治安环境？', '1', '0', 1561434645907, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (13, '您能经常看见有警察或其他治安人员巡逻吗？', '1', '0', 1561434708088, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (14, '您怎么表述您居住区周边的交通道路秩序？', '1', '0', 1561434785160, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (15, '您对居住区防火状况的评价', '1', '0', 1561434841211, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (16, '您对居住地派出所的工作状况是否满意？', '1', '0', 1561434905901, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (17, '您对小区治安最关注的是那些问题？', '2', '0', 1561438633240, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (18, '您对小区治安最不满意的是那些方面？', '2', '0', 1561438766857, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (19, '您对我们的工作有什么好的建议？', '3', '0', 1561438788044, 0, 2);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (20, '最近一年来您所居住的地区治安状况与一年前比较是否有变化？', '1', '0', 1562159954974, 0, 1);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (21, '您怎么表述您居住区周边的公共场所的治安环境？', '1', '0', 1562160037043, 0, 1);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (22, '你是否见过巡逻车？', '1', '0', 1568958825921, 0, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (23, '你一般什么时候需要向周围警察求助？', '2', '0', 1568958899372, 0, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (24, '意见建议', '3', '0', 1568958928210, 0, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (25, '您对物业的服务是否满意', '1', '0', 1570678047973, 0, 6);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (28, '测试', '1', '0', 1570868364292, 0, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (29, '测试2', '1', '0', 1570868872258, 1, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (32, 'test', '2', '0', 1575007625020, 6, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (33, 'test1', '1', '0', 1575012389351, 0, 33);
INSERT INTO cyoubike.question (id, title, question_type, is_show, create_time, sort, jurisdiction) VALUES (34, 'trst2', '1', '0', 1575019979700, 0, 33);