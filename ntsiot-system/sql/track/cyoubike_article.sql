create table article
(
    id           BIGINT(19) auto_increment
        primary key,
    name         VARCHAR(255) null,
    jurisdiction BIGINT(19)   null,
    create_time  BIGINT(19)   null,
    is_repay     VARCHAR(255) null
)
    collate = utf8_bin;

INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (4, '雨伞', 2, 1561448045401, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (5, 'mac笔记本电脑', 20, 1562909540250, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (6, '雨伞', 20, 1562909566235, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (7, 'mac笔记本电脑', 2, 1562909910271, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (8, '台式电脑', 2, 1568614823264, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (10, '笔', 33, 1568711996955, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (11, '一次性雨衣', 33, 1568949771825, '0');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (12, '药品', 33, 1568949819193, '0');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (13, '充电宝', 33, 1568950218120, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (16, '灭火器', 33, 1569826968111, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (17, '伞', 33, 1570784177856, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (18, '笔', 33, 1570784216994, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (19, '衣服', 33, 1570784463966, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (20, '文具', 33, 1570784776558, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (21, '手电筒', 33, 1570784812927, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (22, '西瓜', 33, 1570842837259, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (23, '哈密瓜', 33, 1570842898337, '1');
INSERT INTO cyoubike.article (id, name, jurisdiction, create_time, is_repay) VALUES (24, '椅子', 33, 1570849462244, '1');