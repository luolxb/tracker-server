create table bike_jurisdiction_rela
(
    id              BIGINT(19) auto_increment comment '编号'
        primary key,
    jurisdiction_id BIGINT(19) null comment '辖区编号',
    bike_id         BIGINT(19) null comment '车辆编号'
)
    collate = utf8_bin;

