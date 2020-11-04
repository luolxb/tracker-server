create table roles_depts
(
    id      BIGINT(19) auto_increment
        primary key,
    role_id BIGINT(19) null,
    dept_id BIGINT(19) null,
    constraint roles_depts_ibfk_1
        foreign key (role_id) references role (id),
    constraint roles_depts_ibfk_2
        foreign key (dept_id) references dept (id)
);

create index FK7qg6itn5ajdoa9h9o78v9ksur
    on roles_depts (dept_id);

create index role_id
    on roles_depts (role_id);

INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (1, 2, 7);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (2, 3, 2);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (5, 6, 5);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (7, 4, 2);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (12, 10, 29);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (14, 12, 20);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (24, 18, 7);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (42, 34, 38);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (43, 35, 37);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (44, 36, 6);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (47, 16, 33);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (64, 11, 8);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (70, 47, 33);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (83, 7, 23);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (84, 9, 20);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (156, 101, 32);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (157, 101, 46);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (158, 101, 33);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (159, 101, 5);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (160, 101, 7);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (161, 101, 41);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (162, 101, 23);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (163, 101, 48);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (164, 101, 6);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (165, 101, 39);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (166, 101, 25);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (167, 101, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (168, 101, 8);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (169, 101, 38);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (170, 101, 1);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (171, 101, 20);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (172, 101, 37);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (173, 101, 44);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (174, 101, 62);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (175, 101, 2);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (220, 102, 32);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (221, 102, 46);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (222, 102, 33);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (223, 102, 5);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (224, 102, 72);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (225, 102, 7);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (226, 102, 73);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (227, 102, 23);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (228, 102, 48);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (229, 102, 41);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (230, 102, 6);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (231, 102, 39);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (232, 102, 25);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (233, 102, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (234, 102, 8);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (235, 102, 38);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (236, 102, 20);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (237, 102, 37);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (238, 102, 1);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (239, 102, 62);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (240, 102, 44);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (241, 102, 2);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (242, 103, 32);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (243, 103, 75);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (244, 103, 33);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (245, 103, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (246, 103, 76);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (247, 103, 73);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (248, 103, 48);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (249, 103, 62);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (250, 103, 74);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (251, 104, 25);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (252, 104, 2);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (253, 105, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (254, 105, 73);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (255, 105, 74);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (260, 106, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (261, 60, 52);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (263, 108, 77);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (265, 107, 25);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (266, 110, 48);
INSERT INTO cyoubike.roles_depts (id, role_id, dept_id) VALUES (267, 111, 62);