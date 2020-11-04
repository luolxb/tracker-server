create table t_ma_user
(
    id          BIGINT(19) auto_increment
        primary key,
    name        VARCHAR(255)            null comment '用户名称',
    phone       VARCHAR(255) default '' null,
    openid      VARCHAR(255)            null,
    appid       VARCHAR(255)            null,
    dept_id     BIGINT(19)              null,
    user_type   INT(10)                 null,
    create_time BIGINT(19)              null,
    avatar      VARCHAR(255)            null
)
    charset = utf8mb4;

INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (699, '周卫卫', '13423763880', 'omB175eMdPV08RD3KoNu_1XqvjwY', 'wxc5a9768980576a73', 33, 0, 1571911534981, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (701, '谭琴', '13715143282', 'omB175SYOxMeJVJhCsy99tFhaPmU', 'wxc5a9768980576a73', 33, 0, 1571911646676, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (702, '测试', '13501869520', 'omB175YMrKj21b_T1BNDQjtQSUPo', 'wxc5a9768980576a73', 33, 0, 1571914123145, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (705, '信云', '15914012787', 'omB175RMTvAhbMJ12h5poYo2clLk', 'wxc5a9768980576a73', 33, 0, 1571997781723, 'http://images.cyoubike.com/oXj0b0frvEtltbRRZHnq7kEJMEVc.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (707, null, null, 'oXj0b0QCtn_dhqEqh0lIEx3y6kEw', 'wx7fd51c9e39e254eb', null, 1, 1572412572456, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (708, null, null, 'oXj0b0UyK2BXmYIk24ibDJM7aMuE', 'wx7fd51c9e39e254eb', null, 1, 1572416606686, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (709, null, null, 'oXj0b0STQvZ0otiyWPnuLfJKdBJw', 'wx7fd51c9e39e254eb', null, 1, 1572417296840, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (723, '吴凯平', '13657909078', 'omB175QKr61IAkmBZV4A43Ceex3Q', 'wxc5a9768980576a73', 33, 0, 1572941161030, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (724, null, null, 'oXj0b0WCFdxE8Ylu3lrfFolVRWug', 'wx7fd51c9e39e254eb', null, 1, 1572945770777, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (740, '何余', '13452155052', 'omB175QnELQBc0XceOxUm9Fi9xzQ', 'wxc5a9768980576a73', 33, 0, 1573639975090, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (741, 'aaa', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (742, 'bbb', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (743, 'ccc', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (744, 'a', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (745, 'b', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (746, 'c', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (747, 'aa', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (748, 'bb', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (749, 'cc', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (750, 'dd', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (751, 'ee', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (752, 'ff', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (753, 'gg', '', null, null, 33, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (754, null, null, 'omB175aAsgMHingNZZlqrTrbYhhA', 'wxc5a9768980576a73', null, 1, 1573782144076, 'http://images.cyoubike.com/omB175aAsgMHingNZZlqrTrbYhhA.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (757, '沈彦赫', '18641182137', 'omB175ax__hiJkGCpJJQpYBvK1PA', 'wxc5a9768980576a73', 33, 0, 1573817779539, 'http://images.cyoubike.com/omB175ax__hiJkGCpJJQpYBvK1PA.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (758, '袁泽祥', '13701866167', 'omB175apd8_8J-f7kOnV4lPrVCt0', 'wxc5a9768980576a73', 33, 0, 1573817816437, 'http://images.cyoubike.com/omB175apd8_8J-f7kOnV4lPrVCt0.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (759, '韩宝昆', '18321563426', 'oUkknv09FPhzy1W8lNnam4VdyM_I', 'wx7b2df33603ef50cf', 2, 0, 1573817816437, 'http://images.cyoubike.com/omB175apd8_8J-f7kOnV4lPrVCt0.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (764, '温先森', '13480924112', 'omB175XyUVQantCRu2-9xEhU6WBQ', 'wxc5a9768980576a73', 33, 0, 1574135186088, 'http://images.cyoubike.com/omB175XyUVQantCRu2-9xEhU6WBQ.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (765, null, null, 'oXj0b0frvEtltbRRZHnq7kEJMEVc', 'wx7fd51c9e39e254eb', null, 1, 1574215513527, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (766, null, null, 'omB175dJly0yr4CG_5Ro4weMZJ8k', 'wxc5a9768980576a73', null, 1, 1574246026790, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (767, '孙汾伟', '13643454625', 'omB175XA3nd66RGT4qKhKmtg0DmY', 'wxc5a9768980576a73', 33, 0, 1574246041550, 'http://images.cyoubike.com/omB175XA3nd66RGT4qKhKmtg0DmY.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (769, '王涛', '17612172512', 'oUocJ0bGfZ0Guo1XAz9B1G6QQHfc', 'wxb6298a80201aad5f', 2, 0, null, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (770, '刘伟', '15767543804', 'omB175T_LOzOSkPe5TYxSxwUlHC8', 'wxc5a9768980576a73', 33, 0, 1574646759521, 'http://images.cyoubike.com/omB175T_LOzOSkPe5TYxSxwUlHC8.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (771, null, null, 'oXj0b0ej1ioLUUoqgYIiDw0btP_I', 'wx7fd51c9e39e254eb', null, 1, 1575599214109, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (772, '智硕', '14774808276', 'omB175Zvzx3_Jupy_JLWL4JYKrDk', 'wxc5a9768980576a73', 33, 0, 1576202658766, 'http://images.cyoubike.com/omB175Zvzx3_Jupy_JLWL4JYKrDk.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (773, '孔亚涛', '15112463535', 'omB175Y6g80Y217ZAOCW8qKaZGMc', 'wxc5a9768980576a73', 33, 0, 1576202784379, 'http://images.cyoubike.com/omB175Y6g80Y217ZAOCW8qKaZGMc.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (776, '周君', '17620389488', 'omB175RWY4VLYMQZPxcCHBrtgOBI', 'wxc5a9768980576a73', 33, 0, 1576203820984, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (777, '双民', '13428727501', 'omB175Ru2ceqMtt322a7TvlT-V2g', 'wxc5a9768980576a73', 33, 0, 1576215689240, 'http://images.cyoubike.com/omB175Ru2ceqMtt322a7TvlT-V2g.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (778, '和明', '18818795524', 'omB175TtmWA8QwL4RgWzsGg8Sh2k', 'wxc5a9768980576a73', 33, 0, 1576215944654, 'http://images.cyoubike.com/omB175TtmWA8QwL4RgWzsGg8Sh2k.jpg');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (779, null, null, 'omB175VQ88Fpcd36EIZ3MYBKKrm0', 'wxc5a9768980576a73', null, 1, 1576490031814, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (780, '涂俊', '13410528170', 'omB175bpRmO4Imo6ODn8XTMqWozU', 'wxc5a9768980576a73', 33, 0, 1577088134973, 'http://images.cyoubike.com/');
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (781, null, null, 'oXj0b0cKYRPbJ_Hk-iu4RAoEn-HI', 'wx7fd51c9e39e254eb', null, 1, 1577359850938, null);
INSERT INTO cyoubike.t_ma_user (id, name, phone, openid, appid, dept_id, user_type, create_time, avatar) VALUES (782, null, null, 'omB175bt6ZT1D1J1MGnLGTUmUg74', 'wxc5a9768980576a73', null, 1, 1580815817776, 'http://images.cyoubike.com/omB175bt6ZT1D1J1MGnLGTUmUg74.jpg');