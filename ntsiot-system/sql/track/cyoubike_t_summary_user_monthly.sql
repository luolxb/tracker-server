create table t_summary_user_monthly
(
    id                    BIGINT(19) auto_increment
        primary key,
    user_id               BIGINT(19)                              not null comment '人员id',
    dept_id               BIGINT(19)                              not null comment '辖区id',
    summary_month         INT(10)                                 not null comment '年月',
    username              VARCHAR(256)                            null comment '人员姓名',
    dept_name             VARCHAR(256)                            null comment '辖区名称',
    riding_miles          DOUBLE(22, 0) default 0                 null comment '本辖区骑行里程',
    riding_duration       DOUBLE(22, 0) default 0                 null comment '本辖区骑行时长(单位秒)',
    clock_in_system_count INT(10)       default 0                 null comment '系统要求打卡次数',
    clock_in_actual_count INT(10)       default 0                 null comment '实际打卡次数',
    image_system_count    INT(10)       default 0                 null comment '系统要求上传图片数',
    image_actual_count    INT(10)       default 0                 null comment '实际上传图片数',
    create_time           DATETIME(19)  default CURRENT_TIMESTAMP null,
    remark                VARCHAR(1024)                           null,
    constraint AK_summary_user_monthly_key_2
        unique (dept_id, user_id, summary_month)
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1190, 764, 33, 201911, '温先森', '西丽派出所', 499, 321.308, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1191, 762, 33, 201911, '六六六', '西丽派出所', 853, 5345.5779999999995, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1192, 763, 33, 201911, '周君', '西丽派出所', 14, 95.254, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1193, 33, 2, 201907, '', '吴泾派出所', 0, 46.293000000000006, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1194, 767, 33, 201911, '孙汾伟', '西丽派出所', 0, 53.466, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1195, 766, 33, 201911, 'ceshi', '西丽派出所', 23, 3188.6539999999995, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1196, 740, 33, 201909, '何余', '西丽派出所', 0, 0, 16, 0, 24, 2, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1197, 245, 37, 201909, '', '车墩派出所', 0, 775.654, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1198, 245, 2, 201909, '', '吴泾派出所', 0, 1259.748, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1199, 503, 2, 201909, '', '吴泾派出所', 0, 460.253, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1200, 12, 2, 201907, '', '吴泾派出所', 0, 1028.74, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1201, 638, 2, 201909, '', '吴泾派出所', 0, 36.421, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1202, 618, 33, 201910, '', '西丽派出所', 0, 174.332, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1203, 11, 2, 201907, '', '吴泾派出所', 0, 1303865.768, 3, 0, 3, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1204, 347, 33, 201909, '', '西丽派出所', 1084, 13095.638999999996, 118, 0, 177, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1205, 512, 33, 201909, '刘南昌', '西丽派出所', 0, 0, 60, 0, 90, 0, '2019-11-30 13:39:15', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1206, 729, 33, 201909, '六六六', '西丽派出所', 0, 0, 4, 0, 6, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1207, 636, 2, 201909, '', '吴泾派出所', 0, 95.27799999999999, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1208, 30, 52, 201907, '', '留仙洞派出所', 1011, 30.374, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1209, 665, 33, 201909, '', '西丽派出所', 252, 3013.171, 128, 7, 192, 14, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1210, 617, 2, 201909, '', '吴泾派出所', 0, 20.584, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1211, 701, 33, 201909, '谭琴', '西丽派出所', 0, 0, 1, 0, 2, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1212, 638, 33, 201910, '', '西丽派出所', 860, 13964.565999999997, 1, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1213, 618, 2, 201909, '', '吴泾派出所', 0, 3470.7440000000006, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1214, 702, 33, 201909, '测试', '西丽派出所', 0, 0, 62, 1, 93, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1215, 619, 2, 201909, '', '吴泾派出所', 0, 1762.435, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1216, 476, 33, 201909, '', '西丽派出所', 0, 163.802, 116, 0, 174, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1217, 457, 2, 201909, '', '吴泾派出所', 0, 123.761, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1218, 700, 33, 201910, '', '西丽派出所', 0, 119.937, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1219, 681, 2, 201910, '', '吴泾派出所', 196, 797.292, 0, 0, 6, 3, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1220, 700, 33, 201911, '', '西丽派出所', 0, 238.12800000000001, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1221, 604, 33, 201909, '', '西丽派出所', 0, 3705.4669999999996, 40, 0, 60, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1222, 739, 33, 201909, '六六六', '西丽派出所', 0, 0, 4, 0, 6, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1223, 634, 2, 201909, '', '吴泾派出所', 0, 80.461, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1224, 665, 33, 201910, '', '西丽派出所', 24944660, 38458.05699999999, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1225, 678, 2, 201910, '', '吴泾派出所', 1, 101.501, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1226, 702, 33, 201911, '测试', '西丽派出所', 0, 253.165, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1227, 11, 2, 201908, '', '吴泾派出所', 0, 601191.4879999999, 0, 0, 3, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1228, 11, 2, 201909, '', '吴泾派出所', 0, 39790.392, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1229, 581, 33, 201910, '', '西丽派出所', 0, 968.068, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1230, 30, 48, 201907, '', '西丽地铁站岗亭', 250, 1778.767, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1231, 740, 33, 201910, '何余', '西丽派出所', 0, 0, 4, 0, 0, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1232, 740, 33, 201911, '何余', '西丽派出所', 8, 1002.6429999999999, 64, 0, 7, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1233, 618, 2, 201910, '', '吴泾派出所', 0, 2848.902, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1234, 704, 33, 201911, '彭乘鹤', '西丽派出所', 40, 211.662, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1235, 463, 33, 201909, '', '西丽派出所', 49871688, 70071.09600000002, 60, 0, 90, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1236, 457, 37, 201909, '', '车墩派出所', 0, 308.142, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1237, 1, 2, 201908, '测试123', '吴泾派出所', 0, 0, 0, 0, 39, 0, '2019-11-30 13:39:15', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1238, 11, 37, 201909, '', '车墩派出所', 0, 25080.647, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1239, 1, 2, 201907, '测试123', '吴泾派出所', 0, 0, 39, 0, 39, 0, '2019-11-30 13:39:15', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1240, 46, 2, 201907, '', '吴泾派出所', 0, 3636.727, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1241, 46, 2, 201908, '', '吴泾派出所', 0, 91153.30999999998, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1242, 720, 33, 201911, '', '西丽派出所', 119, 208.822, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1243, 46, 2, 201909, '', '吴泾派出所', 0, 21103.280999999995, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1244, 762, 33, 201909, '六六六', '西丽派出所', 0, 0, 82, 0, 123, 7, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1245, 604, 33, 201910, '', '西丽派出所', 594, 2867.143, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1246, 712, 33, 201909, '六六六', '西丽派出所', 0, 0, 22, 9, 33, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1247, 770, 33, 201910, '刘伟', '西丽派出所', 0, 0, 1, 0, 0, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1248, 581, 33, 201909, '', '西丽派出所', 4134, 124298.62000000002, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1249, 582, 33, 201909, '', '西丽派出所', 0, 18468.771, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1250, 699, 33, 201909, '周卫卫2', '西丽派出所', 0, 0, 51, 0, 77, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1251, 739, 33, 201911, '', '西丽派出所', 16, 114.985, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1252, 424, 2, 201909, '', '吴泾派出所', 0, 337640.718, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1253, 423, 2, 201909, '', '吴泾派出所', 0, 391.41400000000004, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1254, 770, 33, 201911, '刘伟', '西丽派出所', 63, 161.153, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1255, 30, 62, 201907, '', '西丽地铁站岗亭1', 1055, 17.267, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1256, 687, 5, 201910, '吕师傅', '浦锦路派出所', 0, 0, 10, 0, 39, 0, '2019-11-30 13:39:15', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1257, 463, 2, 201909, '', '吴泾派出所', 1333, 20000.91099999999, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1258, 701, 33, 201911, '谭琴', '西丽派出所', 35228, 134747.398, 184, 91, 11, 8, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1259, 686, 33, 201910, '', '西丽派出所', 51, 19838.238, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1260, 634, 33, 201909, '', '西丽派出所', 0, 133.794, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1261, 654, 33, 201909, '', '西丽派出所', 0, 992.24, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1262, 689, 5, 201910, '王卫', '浦锦路派出所', 0, 0, 3, 0, 18, 0, '2019-11-30 13:39:15', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1263, 701, 33, 201910, '谭琴', '西丽派出所', 5148, 15715.652999999998, 10, 6, 2, 3, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1264, 679, 33, 201910, '', '西丽派出所', 0, 302.08399999999995, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1265, 666, 33, 201910, '', '西丽派出所', 0, 1854.3280000000002, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1266, 502, 2, 201909, '', '吴泾派出所', 0, 1362.22, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1267, 666, 2, 201910, '', '吴泾派出所', 0, 425.35900000000004, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1268, 30, 2, 201909, '', '吴泾派出所', 0, 3324.4410000000003, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1269, 655, 2, 201909, '', '吴泾派出所', 37401861, 11114.167, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1270, 770, 33, 201909, '刘伟', '西丽派出所', 0, 0, 2, 0, 3, 2, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1271, 347, 2, 201908, '', '吴泾派出所', 0, 663699.5070000001, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1272, 712, 33, 201910, '', '西丽派出所', 0, 5.504, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1273, 347, 2, 201909, '', '吴泾派出所', 0, 578.514, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1274, 30, 2, 201907, '', '吴泾派出所', 0, 1389.772, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1275, 705, 33, 201911, '信云', '西丽派出所', 0, 6540.085999999999, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1276, 638, 33, 201909, '', '西丽派出所', 925, 6638.4349999999995, 32, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1277, 756, 33, 201909, '六六六', '西丽派出所', 0, 0, 2, 0, 3, 0, '2019-11-30 13:39:16', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1278, 699, 33, 201910, '周卫卫2', '西丽派出所', 239, 453.35, 8, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1279, 457, 33, 201909, '', '西丽派出所', 1214, 14572.643999999993, 116, 0, 174, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1280, 725, 33, 201911, '', '西丽派出所', 397, 789.994, 0, 0, 0, 0, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1281, 699, 33, 201911, '周卫卫2', '西丽派出所', 2644, 10230.440999999999, 72, 5, 42, 7, '2019-11-30 13:38:29', null);
INSERT INTO cyoubike.t_summary_user_monthly (id, user_id, dept_id, summary_month, username, dept_name, riding_miles, riding_duration, clock_in_system_count, clock_in_actual_count, image_system_count, image_actual_count, create_time, remark) VALUES (1282, 761, 33, 201909, '六六六测试', '西丽派出所', 0, 0, 4, 0, 6, 0, '2019-11-30 13:39:16', null);