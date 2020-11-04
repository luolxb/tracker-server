create table t_bike_lock
(
    id           BIGINT(19) auto_increment
        primary key,
    bike_barcode VARCHAR(255) null,
    lock_barcode VARCHAR(255) null,
    is_del       VARCHAR(255) null,
    create_time  BIGINT(19)   null
)
    collate = utf8_bin;

INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (48, '105501009001', 'C28036149326', '1', 1563248583062);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (52, '105501009002', 'c37043794737', '1', 1566976935131);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (54, '105501009006', 'c37043797110', '1', 1567384666200);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (55, '105501009009', 'c37043775181', '1', 1567389577520);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (57, '11112222', '112013141516', '1', 1567560344210);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (59, '105501009024', 'c37043772568', '1', 1567735148082);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (69, 'test123456', '111713141516', '1', 1568872152831);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (85, '105501008561', 'c35035560659', '1', 1569208661384);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (104, '105501002003', 'c35035565542', '1', 1569240370065);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (105, '105501008548', 'c35035566102', '1', 1569257665381);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (106, '105501008566', 'c35035567308', '1', 1569257694041);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (126, '105501003001', 'c35035569312', '1', 1569400246443);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (127, '105501003002', 'c35035565591', '1', 1569401663052);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (130, '105504000001', '015191789325', '1', 1569485623182);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (131, '105504000002', '015191726885', '1', 1569485836325);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (132, '105504000003', '015191766313', '1', 1569486484197);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (135, '105501007001', '056789000010', '1', 1569666596636);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (143, '0000005', '000000000005', '1', 1569811238670);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (144, '105501007010', 'c35035566441', '1', 1570599938385);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (145, '105501007011', 'c35035564578', '1', 1570600409960);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (149, '22222', 'c35035566136', '1', 1571538242720);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (150, '333', 'c35035559529', '1', 1571542412969);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (151, 'ccc', 'c35035560741', '1', 1571543523557);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (157, '999', '020194100018', '1', 1571989236560);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (176, '105501008698', 'c35035569601', '1', 1572255423058);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (190, '105501008569', 'c35035568579', '1', 1572318546095);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (203, '105501008475', 'c35035568371', '1', 1573192998896);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (205, '105501008686', 'c37777777778', '1', 1573454588808);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (206, '038888888800', '038888888800', '1', 1573799264509);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (207, '105501009990', 'c38888888801', '1', 1573804973191);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (211, '105501009999', 'c38888888803', '1', 1574135525842);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (212, '105501008699', 'c35035568413', '1', 1575514770903);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (214, '105501008001', '000000000001', '1', 1575870877298);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (215, '105501008002', '011111111111', '1', 1575870908257);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (218, '105501008080', 'c35035565245', '1', 1575959163934);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (219, '105501008666', 'c35035568249', '1', 1575959825722);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (222, '105501008545', 'c35035568561', '1', 1576120366545);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (223, '105501008518', 'c37777777774', '1', 1576214378472);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (224, '105501008556', 'c37770000337', '1', 1576482131679);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (225, '105501008541', 'c37770000062', '1', 1576657817597);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (226, '105501008111', 'c37770000314', '1', 1576658404060);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (229, '105501000272', 'c37770000272', '1', 1576724572402);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (230, '105501000213', 'c37770000213', '1', 1576724602302);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (231, '105501008471', 'c37770000338', '1', 1577091280826);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (232, '105501008888', 'c37770000123', '1', 1577152880108);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (233, '105501008887', 'c37770000204', '1', 1577175933571);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (234, '105501008989', '490e782433e2', '1', 1577355806072);
INSERT INTO cyoubike.t_bike_lock (id, bike_barcode, lock_barcode, is_del, create_time) VALUES (235, '105501000243', 'c37770000243', '1', 1577416409067);