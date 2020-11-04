create table reference_point
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255)  null comment '名称',
    icon_type   BIGINT(19)    null comment '图标类别',
    longitude   VARCHAR(255)  null comment '经度',
    latitude    VARCHAR(255)  null comment '纬度',
    dept_id     BIGINT(19)    null comment '辖区',
    remark      VARCHAR(2000) null comment '描述',
    create_time BIGINT(19)    null,
    update_time BIGINT(19)    null
)
    charset = utf8mb4;

INSERT INTO cyoubike.reference_point (id, name, icon_type, longitude, latitude, dept_id, remark, create_time, update_time) VALUES (1, '123132', 1, '121.426431', '31.059197', 2, '', 1562984110288, 1564583312463);
INSERT INTO cyoubike.reference_point (id, name, icon_type, longitude, latitude, dept_id, remark, create_time, update_time) VALUES (2, '地铁站参考点', 2, '113.944105', '22.581177', 33, '', 1568962161882, null);
INSERT INTO cyoubike.reference_point (id, name, icon_type, longitude, latitude, dept_id, remark, create_time, update_time) VALUES (3, '留仙洞地铁口报刊亭1', 3, '113.944248', '22.580681', 1, '', 1569241896056, null);