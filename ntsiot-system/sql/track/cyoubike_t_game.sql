create table t_game
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255)      not null comment '游戏名称',
    url         VARCHAR(255)      not null comment '游戏地址',
    num_order   BIGINT(19)        not null comment '序号',
    status      INT(10) default 0 not null comment '状态  0-显示 -1-不显示',
    cover_url   VARCHAR(255)      not null comment '封面图片地址',
    app_id      VARCHAR(255)      null comment '小程序的APPID',
    type        INT(10) default 0 not null comment ' 游戏类型 0-小程序 1-H5',
    create_time BIGINT(19)        null comment '创建时间',
    update_time BIGINT(19)        null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_game (id, name, url, num_order, status, cover_url, app_id, type, create_time, update_time) VALUES (47, '百度', 'www.baidu.com', 0, 0, 'http://images.cyoubike.com/7-20191205154939.jpeg', '', 1, 1575532188798, null);