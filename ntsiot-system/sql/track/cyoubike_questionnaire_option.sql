create table questionnaire_option
(
    id               BIGINT(19) auto_increment comment 'id'
        primary key,
    questionnaire_id BIGINT(19) null comment '调查问卷id',
    question_id      BIGINT(19) null comment '问题id',
    sort             BIGINT(19) null comment '顺序'
)
    collate = utf8_bin;

INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (9, 3, 10, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (10, 3, 11, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (11, 3, 12, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (12, 3, 13, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (13, 3, 14, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (14, 3, 15, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (15, 3, 16, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (16, 3, 17, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (17, 3, 18, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (18, 3, 19, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (19, 4, 9, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (20, 4, 20, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (21, 4, 21, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (22, 5, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (23, 5, 23, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (24, 5, 24, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (25, 6, 25, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (26, 7, 28, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (27, 7, 29, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (28, 8, 28, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (29, 8, 33, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (30, 8, 34, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (31, 8, 29, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (32, 8, 32, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (33, 9, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (34, 9, 28, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (35, 9, 29, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (36, 10, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (37, 10, 24, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (38, 10, 32, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (39, 11, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (40, 12, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (41, 12, 23, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (42, 13, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (43, 13, 23, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (44, 13, 24, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (45, 14, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (46, 14, 34, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (47, 15, 22, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (48, 15, 23, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (49, 15, 24, null);
INSERT INTO cyoubike.questionnaire_option (id, questionnaire_id, question_id, sort) VALUES (50, 15, 28, null);