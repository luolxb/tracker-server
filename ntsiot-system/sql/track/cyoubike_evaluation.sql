create table evaluation
(
    id               BIGINT(19) auto_increment comment 'id'
        primary key,
    questionnaire_id BIGINT(19)   null comment '问卷id',
    question_id      BIGINT(19)   null comment '问题id',
    answer           VARCHAR(255) null comment '答案',
    respondents      BIGINT(19)   null comment '回答人',
    create_time      BIGINT(19)   null comment '创建时间'
)
    collate = utf8_bin;

INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (49, 3, 10, '1', 30, 1562309667532);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (50, 3, 11, '8', 30, 1562309667535);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (51, 3, 12, '13', 30, 1562309667536);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (52, 3, 13, '17', 30, 1562309667537);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (53, 3, 14, '22', 30, 1562309667538);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (54, 3, 15, '27', 30, 1562309667539);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (55, 3, 17, '3', 30, 1562309667542);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (56, 3, 16, '33', 30, 1562309667543);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (57, 3, 18, '8', 30, 1562309667545);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (58, 3, 19, '不懂你当年你的', 30, 1562309667546);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (59, 3, 10, '3', 230, 1562309876882);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (60, 3, 11, '7', 230, 1562309876884);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (61, 3, 12, '12', 230, 1562309876885);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (62, 3, 13, '16', 230, 1562309876886);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (63, 3, 14, '21', 230, 1562309876887);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (64, 3, 15, '25', 230, 1562309876888);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (65, 3, 16, '31', 230, 1562309876890);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (66, 3, 17, '2', 230, 1562309876891);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (67, 3, 18, '10, 8', 230, 1562309876892);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (68, 3, 19, '还是说好的
', 230, 1562309876893);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (69, 3, 10, '0', 463, 1567645051809);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (70, 3, 11, '5', 463, 1567645051812);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (71, 3, 12, '10', 463, 1567645051813);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (72, 3, 13, '15', 463, 1567645051814);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (73, 3, 14, '19', 463, 1567645051815);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (74, 3, 15, '24', 463, 1567645051816);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (75, 3, 16, '28', 463, 1567645051818);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (76, 3, 17, '0', 463, 1567645051819);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (77, 3, 18, '5', 463, 1567645051820);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (78, 3, 10, '0', 463, 1568087151701);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (79, 3, 18, '5', 463, 1568087151703);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (80, 3, 17, '0', 463, 1568087151704);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (81, 3, 16, '28', 463, 1568087151706);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (82, 3, 15, '23', 463, 1568087151707);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (83, 3, 14, '18', 463, 1568087151708);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (84, 3, 13, '15', 463, 1568087151709);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (85, 3, 12, '10', 463, 1568087151710);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (86, 3, 11, '5', 463, 1568087151711);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (87, 3, 10, '0', 17, 1568106718572);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (88, 3, 11, '5', 17, 1568106718574);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (89, 3, 12, '10', 17, 1568106718575);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (90, 3, 13, '15', 17, 1568106718576);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (91, 3, 14, '18', 17, 1568106718577);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (92, 3, 15, '23', 17, 1568106718578);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (93, 3, 16, '29', 17, 1568106718579);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (94, 3, 17, '0, 1', 17, 1568106718580);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (95, 3, 18, '5', 17, 1568106718581);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (96, 3, 10, '0', 17, 1568179495119);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (97, 3, 11, '5', 17, 1568179495120);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (98, 3, 12, '10', 17, 1568179495122);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (99, 3, 13, '16', 17, 1568179495123);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (100, 3, 14, '18', 17, 1568179495124);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (101, 3, 15, '23', 17, 1568179495125);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (102, 3, 16, '28', 17, 1568179495126);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (103, 3, 17, '0, 1', 17, 1568179495127);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (104, 3, 18, '5', 17, 1568179495128);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (105, 3, 10, '0', 17, 1568179855332);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (106, 3, 11, '5', 17, 1568179855333);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (107, 3, 12, '10', 17, 1568179855334);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (108, 3, 13, '15', 17, 1568179855335);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (109, 3, 14, '18', 17, 1568179855336);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (110, 3, 15, '23', 17, 1568179855337);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (111, 3, 16, '28', 17, 1568179855338);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (112, 3, 17, '0, 2', 17, 1568179855339);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (113, 3, 18, '5, 8, 7', 17, 1568179855340);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (114, 3, 11, '7', 476, 1568257473988);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (115, 3, 10, '2', 476, 1568257473990);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (116, 3, 12, '12', 476, 1568257473991);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (117, 3, 13, '16', 476, 1568257473992);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (118, 3, 14, '20', 476, 1568257473993);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (119, 3, 15, '25', 476, 1568257473994);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (120, 3, 16, '31', 476, 1568257473996);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (121, 3, 17, '2', 476, 1568257473997);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (122, 3, 18, '9, 8', 476, 1568257473998);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (123, 3, 19, '无', 476, 1568257473999);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (124, 3, 10, '4', 476, 1568257950253);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (125, 3, 11, '9', 476, 1568257950255);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (126, 3, 12, '14', 476, 1568257950255);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (127, 3, 13, '17', 476, 1568257950256);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (128, 3, 14, '22', 476, 1568257950257);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (129, 3, 15, '27', 476, 1568257950258);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (130, 3, 16, '33', 476, 1568257950259);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (131, 3, 17, '4', 476, 1568257950260);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (132, 3, 18, '12', 476, 1568257950261);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (133, 3, 19, '测试', 476, 1568257950262);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (134, 5, 22, '0', 608, 1568959787706);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (135, 5, 23, '4, 3, 2', 608, 1568959787708);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (136, 5, 24, '很好', 608, 1568959787709);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (137, 5, 22, '1', 608, 1568959814490);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (138, 5, 23, '2, 3', 608, 1568959814492);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (139, 5, 24, '不错', 608, 1568959814493);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (140, 6, 25, '1', 245, 1570678114164);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (141, 5, 22, '0', 638, 1570781268243);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (142, 5, 23, '2', 638, 1570781268245);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (143, 5, 24, '很好', 638, 1570781268246);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (144, 6, 25, '1', 684, 1570868064112);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (145, 5, 22, '1', 638, 1570869019855);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (146, 5, 23, '2', 638, 1570869019856);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (147, 5, 22, '0', 701, 1575020286102);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (148, 5, 23, '2', 701, 1575020286111);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (149, 5, 24, '没有', 701, 1575020286118);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (150, 5, 22, '0', 699, 1575020712874);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (151, 5, 23, '3, 4, 5', 699, 1575020712884);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (152, 5, 24, '哈哈哈', 699, 1575020712892);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (153, 10, 22, '0', 699, 1575021601546);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (154, 10, 24, '还真是', 699, 1575021601558);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (155, 10, 32, '1, 2', 699, 1575021601565);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (156, 10, 22, '0', 701, 1576563167994);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (157, 10, 24, '没有', 701, 1576563167999);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (158, 10, 32, '2', 701, 1576563168000);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (159, 10, 22, '0', 780, 1577155979512);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (160, 10, 24, '测试', 780, 1577155979521);
INSERT INTO cyoubike.evaluation (id, questionnaire_id, question_id, answer, respondents, create_time) VALUES (161, 10, 32, '2, 1', 780, 1577155979528);