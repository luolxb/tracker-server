create table t_video
(
    id          BIGINT(19) auto_increment
        primary key,
    title       VARCHAR(255) not null comment '视频标题',
    url         VARCHAR(255) not null comment '视频链接地址',
    dept_id     BIGINT(19)   null,
    dept_name   VARCHAR(255) null,
    start_time  BIGINT(19)   not null comment '生效起始时间',
    end_time    BIGINT(19)   not null comment '生效结束时间',
    create_time BIGINT(19)   null comment '创建时间',
    update_time BIGINT(19)   null comment '修改时间'
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (25, '测试3-5', 'https://wepyjs.github.io/wepy-docs/2.x/#/base/event', 33, '西丽派出所', 1574362800000, 1574370000000, 1574401304920, null);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (57, 'cs', 'http://images.cyoubike.com/2.mp4', 33, '西丽派出所', 1574784000000, 1574812800000, 1574678852492, 1574821464919);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (62, 'a', 'a', 52, '留仙洞派出所', 1573056000000, 1573142400000, 1574737481820, null);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (70, '测试2121', 'http://images.cyoubike.com/mda-jkdrp2wjyayyimj7-20191126170131.mp4', 33, '西丽派出所', 1574611200000, 1574784000000, 1574758953721, 1574831666927);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (71, '南山分局的视频', 'https://v-cdn.zjol.com.cn/277001.mp4', 32, '深圳南山分局', 1574956800000, 1575039600000, 1574759924928, 1574992511329);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (73, '测试', 'http://images.cyoubike.com/1-20191202145619.mp4', 33, '西丽派出所', 1575216000000, 1575216000000, 1575269797346, 1575352404147);
INSERT INTO cyoubike.t_video (id, title, url, dept_id, dept_name, start_time, end_time, create_time, update_time) VALUES (74, 'test', 'http://images.cyoubike.com/100-20191203135337.mp4', 33, '西丽派出所', 1575302400000, 1575388800000, 1575352433534, 1575511899112);