create table gen_config
(
    id          BIGINT(19) auto_increment comment 'ID'
        primary key,
    author      VARCHAR(255) null comment '作者',
    cover       BIT          null comment '是否覆盖',
    module_name VARCHAR(255) null comment '模块名称',
    pack        VARCHAR(255) null comment '至于哪个包下',
    path        VARCHAR(255) null comment '前端代码生成的路径',
    api_path    VARCHAR(255) null
);

INSERT INTO cyoubike.gen_config (id, author, cover, module_name, pack, path, api_path) VALUES (1, 'jie', false, 'eladmin-system', 'com.zhengjie.modules.system', 'E:\\workspace\\com\\eladmin-qt\\src\\views\\system\\dictDetail', 'E:\\workspace\\com\\eladmin-qt\\src\\api');