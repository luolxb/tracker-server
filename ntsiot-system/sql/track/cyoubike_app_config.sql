create table app_config
(
    id          BIGINT(19) auto_increment comment 'id'
        primary key,
    app_id      VARCHAR(255)      null comment 'appId',
    secret      VARCHAR(255)      null,
    token       VARCHAR(255)      null,
    aes_key     VARCHAR(255)      null,
    is_default  VARCHAR(20)       null comment '是否默认',
    type        INT(10) default 0 null comment '类型 0-小程序 1-公众号',
    name        VARCHAR(255)      null comment '小程序或者公众号名字',
    create_time BIGINT(19)        null comment '创建时间'
)
    charset = utf8mb4;

INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (1, 'wxc5a9768980576a73', '13e56c5fe401228151dae7020ab3f8a2', 'mmp', '7V53YdRHlJ9jgEGlbAfhvNbKdcTpU9n7pjS2BBWnIsB', '0', 0, null, null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (6, 'wx7fd51c9e39e254eb1', '30dcc1ac5c8b7e4c295d1a1b4a1f9abf', 'mmp', '7V53YdRHlJ9jgEGlbAfhvNbKdcTpU9n7pjS2BBWnIsB', '1', 0, null, null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (7, '123', '30dcc1ac5c8b7e4c295d1a1b4a1f9abf', 'mmp', '7V53YdRHlJ9jgEGlbAfhvNbKdcTpU9n7pjS2BBWnIsB', '1', 0, null, null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (14, 'dfsdas', '30dcc1ac5c8b7e4c295d1a1b4a1f9abf', 'mmp', '7V53YdRHlJ9jgEGlbAfhvNbKdcTpU9n7pjS2BBWnIsB', '1', 0, null, null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (16, 'wxc5a9768980576a731', '13e56c5fe401228151dae7020ab3f8a2', 'mmp', '7V53YdRHlJ9jgEGlbAfhvNbKdcTpU9n7pjS2BBWnIsB', '1', 0, null, null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (17, 'wxee1c1e26121022f2', '3f233dadf1498964d1885e302c2231b7', null, null, null, 1, '摩融电池监测', null);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (68, 'wxee1c1e26121022f2', '3f233dadf1498964d1885e302c2231b7', null, null, null, 1, '新新新新的', 1574826190513);
INSERT INTO cyoubike.app_config (id, app_id, secret, token, aes_key, is_default, type, name, create_time) VALUES (71, 'wx621e065aae0e372c', '2a424731f258943d6782ad464f1504e8111', null, null, '1', 1, 'debug', 1574990321487);