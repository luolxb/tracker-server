create table users_roles
(
    id      BIGINT(19) auto_increment
        primary key,
    user_id BIGINT(19) null comment '用户ID',
    role_id BIGINT(19) null comment '角色ID',
    constraint users_roles_ibfk_1
        foreign key (user_id) references user (id),
    constraint users_roles_ibfk_2
        foreign key (role_id) references role (id)
);

create index FKq4eq273l04bpu4efj0jd0jb98
    on users_roles (role_id);

create index user_id
    on users_roles (user_id);

INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (51, 30, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (55, 33, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (60, 186, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (63, 205, 7);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (64, 64, 9);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (70, 314, 10);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (72, 18, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (74, 312, 12);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (95, 319, 18);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (125, 318, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (137, 326, 35);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (138, 327, 34);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (158, 325, 11);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (160, 29, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (163, 332, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (164, 333, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (174, 323, 9);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (188, 334, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (189, 337, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (193, 338, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (211, 324, 36);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (216, 339, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (217, 341, 2);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (233, 344, 47);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (252, 342, 60);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (279, 316, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (282, 340, 6);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (283, 340, 4);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (284, 340, 7);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (285, 340, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (286, 340, 2);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (287, 340, 9);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (300, 348, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (315, 345, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (322, 353, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (323, 354, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (324, 355, 6);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (333, 1, 101);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (334, 1, 1);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (341, 358, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (343, 359, 102);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (344, 360, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (346, 361, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (347, 362, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (349, 364, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (350, 365, 103);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (371, 386, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (372, 387, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (373, 388, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (374, 389, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (375, 390, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (376, 391, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (377, 392, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (378, 393, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (379, 394, 101);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (380, 395, 101);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (381, 396, 101);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (382, 397, 101);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (384, 398, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (386, 400, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (387, 401, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (388, 402, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (389, 403, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2127, 404, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2128, 405, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2129, 28, 7);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2130, 406, 104);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2132, 357, 3);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2136, 356, 2);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2138, 363, 106);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2139, 399, 103);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2140, 407, 106);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2142, 408, 2);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2143, 409, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2144, 410, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2145, 411, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2146, 412, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2147, 413, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2148, 317, 47);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2150, 414, 47);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2151, 415, 110);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2152, 416, 111);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2155, 417, 16);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2156, 418, 6);
INSERT INTO cyoubike.users_roles (id, user_id, role_id) VALUES (2160, 419, 16);