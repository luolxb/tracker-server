create table top_up
(
    id           BIGINT(19) auto_increment
        primary key,
    money        VARCHAR(255) null,
    jurisdiction BIGINT(19)   null,
    create_time  BIGINT(19)   null
)
    collate = utf8_bin;

INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (18, '100', null, 1568614755624);
INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (19, '"''', 2, 1568685392230);
INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (20, '10', null, 1568946716348);
INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (21, '11', null, 1568946881646);
INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (22, '12', null, 1569220316246);
INSERT INTO cyoubike.top_up (id, money, jurisdiction, create_time) VALUES (23, '0.01', null, 1569294082512);