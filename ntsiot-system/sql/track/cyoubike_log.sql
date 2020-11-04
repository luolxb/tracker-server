create table log
(
    id               BIGINT(19) auto_increment
        primary key,
    create_time      DATETIME(19) null,
    description      VARCHAR(255) null,
    exception_detail TEXT(65535)  null,
    log_type         VARCHAR(255) null,
    method           VARCHAR(255) null,
    params           LONGTEXT(max) null,
    request_ip       VARCHAR(255) null,
    time             BIGINT(19)   null,
    username         VARCHAR(255) null
);

