create table t_report_restrict_copy
(
    id          BIGINT(19) auto_increment comment '主键ID'
        primary key,
    content     VARCHAR(500)      null comment '举报须知内容',
    dept_id     BIGINT(19)        null comment '辖区id',
    template    INT(10) default 0 null comment '1-模板 0-不是模板',
    create_time BIGINT(19)        null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO cyoubike.t_report_restrict_copy (id, content, dept_id, template, create_time) VALUES (1, '1、 本平台受理[[name]]范围内的交通违法举报。
2、 举报的违法图片要求为3张、实时拍摄、不可修改。
3、 为确保举报信息的真实性，必须实名举报并提供有效的联系方式，公安机关会对其进行保密。
4、 严禁骑车、开车时进行拍摄。
5、 举报人须对举报违法行为的真实性负责，并配合公安机关开展后续调查。', 0, 1, 1575267653000);
INSERT INTO cyoubike.t_report_restrict_copy (id, content, dept_id, template, create_time) VALUES (2, '1、 本平台受理[[name]]范围内的交通违法举报。
2、 举报的违法图片要求为3张、实时拍摄、不可修改。
3、 为确保举报信息的真实性，必须实名举报并提供有效的联系方式，公安机关会对其进行保密。
4、 严禁骑车、开车时进行拍摄。', 1, 0, 1575272144464);