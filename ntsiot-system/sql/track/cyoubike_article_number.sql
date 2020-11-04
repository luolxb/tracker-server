create table article_number
(
    id          BIGINT(19) auto_increment
        primary key,
    total       VARCHAR(255) null,
    loan_number VARCHAR(255) null,
    create_time VARCHAR(255) null,
    article_id  BIGINT(19)   null
)
    collate = utf8_bin;

INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (6, '10000', '314', '1561448045404', 4);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (7, '100', '0', '1562909540253', 5);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (8, '100', '0', '1562909566237', 6);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (9, '100', '22', '1562909910273', 7);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (10, '100', '50', '1568614823266', 8);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (11, '99', '92', '1568698576006', 9);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (12, '0', '0', '1568711996957', 10);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (13, '50', '11', '1568949771827', 11);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (14, '50', '11', '1568949819195', 12);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (16, '22', '0', '1568950383061', 14);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (17, '1', '-2', '1569316593023', 15);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (18, '19', '1', '1569826968116', 16);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (19, '10', '0', '1570784177858', 17);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (20, '90', '3', '1570784216996', 18);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (21, '80', '0', '1570784463967', 19);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (22, '122', '0', '1570784776560', 20);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (23, '0', '0', '1570784812928', 21);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (24, '100', '0', '1570842837261', 22);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (25, '100', '0', '1570842898338', 23);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (26, '90', '2', '1570849462253', 24);
INSERT INTO cyoubike.article_number (id, total, loan_number, create_time, article_id) VALUES (27, '10', '0', '1570862194825', 25);