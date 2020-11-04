create table comite
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255) null comment '居委会名称',
    dept_id     BIGINT(19)   null comment '所属辖区',
    create_time BIGINT(19)   null,
    update_time BIGINT(19)   null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (24, '永丰村委会', 33, 1580799698133, 1580807959472);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (26, '龙华居委会', 33, 1580800136364, 1580800136364);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (27, '罗湖居委会', 33, 1580807829510, 1580807829510);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (28, '南山居委会', 33, 1580807829510, 1580992680497);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (32, '西丽居委会', 33, 1580911798112, 1580911798112);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (41, '成安居委会', 33, 1580993371235, 1580993371235);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (42, '平湖居委会', 33, 1581043438828, 1581046540363);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (45, '留仙洞居委会', 33, 1581052431618, 1581052431618);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (46, '南山区直属居委会', 32, 1581053975376, 1581053975376);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (47, '吴泾居委会', 2, 1581054089328, 1581054089328);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (48, '闵行居委会', 7, 1581058788689, 1581058788689);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (49, '西游居委会', 70, 1581058866668, 1581058866668);
INSERT INTO cyoubike.comite (id, name, dept_id, create_time, update_time) VALUES (50, '大学城居委会', 33, 1581315317520, 1581315317520);