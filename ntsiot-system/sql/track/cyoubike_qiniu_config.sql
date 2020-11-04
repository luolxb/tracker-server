create table qiniu_config
(
    id         BIGINT(19) auto_increment comment 'ID'
        primary key,
    access_key TEXT(65535)  null comment 'accessKey',
    bucket     VARCHAR(255) null comment 'Bucket 识别符',
    host       VARCHAR(255) not null comment '外链域名',
    secret_key TEXT(65535)  null comment 'secretKey',
    type       VARCHAR(255) null comment '空间类型',
    zone       VARCHAR(255) null comment '机房（华东，华南，华北，北美）'
);

INSERT INTO cyoubike.qiniu_config (id, access_key, bucket, host, secret_key, type, zone) VALUES (1, 'tObp3kCEGJ2Lvf7EWt7oGifrATth-P-QI6aMH9HT', 'images', 'http://images.cyoubike.com', 'j4EpTqXwCbp2MoPzNsR6LdI_Kae9p69rskEPvfDK', '公开', '华东');