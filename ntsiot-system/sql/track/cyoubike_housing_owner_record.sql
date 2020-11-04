create table housing_owner_record
(
    id          BIGINT(19) auto_increment
        primary key,
    owner       VARCHAR(255)  null comment '房东',
    owner_phone VARCHAR(255)  null comment '房东电话',
    dept_id     BIGINT(19)    null comment '所属辖区',
    address     VARCHAR(2000) null comment '地址',
    create_time BIGINT(19)    null,
    update_time BIGINT(19)    null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (1, '房东1', '151121341234', 2, 'xxxx', 1562833756728, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (2, '房东2', '151121341234', 2, 'xxxx2', 1562833756728, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (3, 'zzm', '15104017491', 2, '三好街', 1568698548261, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (4, 'zzm', '15104017490', 2, '和平区', 1568698795562, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (5, 'zzm', '15104017490', 2, '东川路555号', 1568772251959, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (6, '顶焦度计顶焦度计', '13501869520', 23, '反馈反馈开发看风景', 1568772663383, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (7, '觉得解放军', '13501869520', 23, '大家记得记得你的呢', 1568772737481, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (8, '放假就到家', '13501869520', 23, '大家放假到奶奶的呢', 1568772808962, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (9, '大哥他弟', '15201937616', 23, '哈偷摸iFeng4573号', 1568772991110, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (10, '大哥仿佛', '15201937616', 23, '合团哦哟45哦', 1568773028533, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (11, 'zzm', '15104017490', 2, '嗯嗯你的呢', 1568773403793, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (12, '哈图墨王', '15201937616', 2, '爬梯子要进去还没收', 1568773545348, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (13, '房东13', '15201937616', 2, 'xxxx1', 1568773403793, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (14, '房东13', '15201937616', 2, 'xxxx2', 1568773403793, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (15, '房东131', '15201937616', 34, 'xxxx3', 1568773403793, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (16, '刘测试', '13048992376', 33, '深圳南山凯达尔整栋', 1570778066238, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (17, '吕', '15201937616', 5, '上海市浦东新区', 1570850174862, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (18, '哈哈哈', '13714553315', 2, 'TTF', 1571911444023, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (19, '六六六', '13714553315', 70, '深圳宝安区西乡盐田', 1576481501808, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (20, '小米', '13715143282', 33, '南山', 1576563204808, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (21, 'tujun', '13410528170', 33, '风风光光哈哈哈', 1580720990465, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (22, 'tujun', '13410528170', 33, '风风光光哈哈哈', 1580721284605, null);
INSERT INTO cyoubike.housing_owner_record (id, owner, owner_phone, dept_id, address, create_time, update_time) VALUES (23, 'tujun', '13410528170', 33, '深圳西丽极光', 1580721583154, null);