create table reservemask_owner_record
(
    id          BIGINT(19) auto_increment
        primary key,
    owner       VARCHAR(255)  null comment '房东',
    owner_phone VARCHAR(255)  null comment '房东电话',
    open_id     VARCHAR(255)  null,
    comite_id   BIGINT(19)    null comment '所属居委会',
    address     VARCHAR(2000) null comment '地址',
    create_time BIGINT(19)    null,
    update_time BIGINT(19)    null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (55, '六六六', '13714553315', null, 24, '西乡盐田新一村', 1581046749673, 1581046749673);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (56, '张三', '13501869520', null, 28, '大家就登记登记', 1581054174005, 1581051634574);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (57, '周卫卫', '13423763880', null, 32, '淅沥淅沥', 1581052240817, 1581052240817);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (58, '李四', '13501869520', null, 45, '是你那是你们', 1581052922184, 1581052922184);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (59, '这种', '13423763880', null, 45, '是结结实实', 1581053685506, 1581053685506);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (60, '尖叫鸡', '13423763880', null, 47, '京津冀', 1581054174005, 1581054174005);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (61, '张三', '13714553315', null, 49, '李四家旁边', 1581064918795, 1581064918795);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (62, '张三', '13714553315', null, 49, '李四家旁边', 1581065100329, 1581065100329);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (63, '王五', '13714553315', null, 49, '王麻子家旁边', 1581065176939, 1581065176939);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (64, '王麻子', '13714553315', null, 49, '赵一家旁边', 1581065805543, 1581065805543);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (65, '张四', '13714553315', null, 49, '李武家旁边', 1581066014680, 1581066014680);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (66, '彰武', '13714553315', null, 49, '狗六家旁边', 1581066266542, 1581066266542);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (67, '哈哈', '13715143282', null, 26, 'PK快快乐乐', 1581299634086, 1581299634086);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (68, '回来', '13715143282', null, 45, '管理局', 1581300464669, 1581300464669);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (69, '李太太', '1341383170', null, 32, '凯尔34栋', 1581302683853, 1581302683853);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (70, '红太太', '1341386170', 'omB175SYOxMeJVJhCsy99tFhaPmU', 32, '凯尔34栋', 1581312568447, 1581312568447);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (71, '绿太太', '155555555552', 'omBBBBBBBBBBBBBBBBBBBBBBBBB', 32, '凯尔36栋', 1581312592733, 1581312592733);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (72, '白太太', '155555555552', 'omB175eMdPV08RD3KoNu_1XqvjwY', 32, '凯尔36栋', 1581312707378, 1581312707378);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (73, '粉太太', '155555555558', null, 32, '南天36栋', 1581313887456, 1581313887456);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (74, '罗太太', '13413831702', null, 41, '宏达24栋', 1581315180055, 1581315180055);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (75, '胡姬花', '13410528170', null, 50, '多喝水斤斤计较', 1581315791950, 1581315791950);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (76, '起风了', '13715143282', 'omB175SYOxMeJVJhCsy99tFhaPmU', 45, '回家咯', 1581326167101, 1581326167101);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (77, '六六六', '13714553315', 'omB175bt6ZT1D1J1MGnLGTUmUg74', 49, '看看', 1581328488412, 1581328488412);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (78, '六六六', '13714553315', 'omB175bt6ZT1D1J1MGnLGTUmUg74', 49, '来来来', 1581328657182, 1581328657182);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (79, '六六六', '13714553315', 'omB175bt6ZT1D1J1MGnLGTUmUg74', 49, '哈哈哈', 1581329019617, 1581329019617);
INSERT INTO cyoubike.reservemask_owner_record (id, owner, owner_phone, open_id, comite_id, address, create_time, update_time) VALUES (80, '起风了', '13715143282', 'omB175SYOxMeJVJhCsy99tFhaPmU', 50, '空间', 1581335246762, 1581335246762);