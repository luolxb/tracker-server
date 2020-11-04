create table bike_icon
(
    id             BIGINT(19) auto_increment
        primary key,
    dept           BIGINT(19)    null comment '辖区',
    bike_type      VARCHAR(255)  null comment '车辆类型',
    bike_icon      VARCHAR(255)  null comment '车辆图标',
    remark         VARCHAR(1000) null comment '描述',
    show_real_line VARCHAR(10)   null,
    color          VARCHAR(10)   null
)
    charset = utf8mb4;

INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (1, 2, '助力车', 'http://images.cyoubike.com/17d8c206-0235-483c-a374-72f27cb6cc77.gif', '', 'true', '#0BD666');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (2, 2, '警车', 'http://images.cyoubike.com/083b37e4-f8ba-4251-b997-09b787df9ef8.png', '', null, null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (3, 34, '小型电动车', 'http://images.cyoubike.com/6bd6327b-95fe-4fbd-875a-9911b568f87a.png', 'test', null, null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (4, 33, '小型助力电动车', 'http://images.cyoubike.com/26044b9f-08d5-49e1-a42d-92dab519c024.png', '', 'true', '#004DF2');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (5, 33, '助力车', 'http://images.cyoubike.com/470caaf3-0679-49ed-b98d-3b80a6c62fbf.gif', '', 'true', '#F10707');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (6, 37, '助力车', 'http://images.cyoubike.com/c32f4204-0d6f-4d1e-a54f-5dcb3f07ed52.png', '', null, null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (7, 37, '存量车', 'http://images.cyoubike.com/e57d86a2-56c2-4e58-b545-7aaa1980a97a.png', '', null, null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (8, 20, '存量车', 'http://images.cyoubike.com/7947b444-1fe7-455d-abbf-429b38c28064.png', '', null, null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (9, 33, '测试', 'http://images.cyoubike.com/e87b17f2-2528-42c1-8a95-3d397cb109d0.gif', '', 'true', '#28F28F');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (10, 33, '测试1', 'http://images.cyoubike.com/c6be8509-a192-4fac-98e3-19b10cb3fec9.gif', '111', 'true', '#28F28F');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (11, 33, 'test', 'http://images.cyoubike.com/8785eecb-762f-4bf2-9667-6909223e35ab.gif', '', 'true', '#28F28F');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (12, 52, '助力车', 'http://images.cyoubike.com/499bc62d-6645-423d-aea2-f1820373a79b.gif', '', 'true', '#28F28F');
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (13, 52, '代步车', 'http://images.cyoubike.com/9653a9cc-d249-4a91-b621-f171ef53cf4b.png', '', 'true', null);
INSERT INTO cyoubike.bike_icon (id, dept, bike_type, bike_icon, remark, show_real_line, color) VALUES (14, 52, '新的', 'http://images.cyoubike.com/a1a1e48c-caf8-4c34-a571-39a40698cb58.gif', '', 'true', '#28F28F');