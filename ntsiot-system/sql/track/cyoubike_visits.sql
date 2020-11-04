create table visits
(
    id          BIGINT(19) auto_increment
        primary key,
    create_time DATETIME(19) null,
    date        VARCHAR(255) null,
    ip_counts   BIGINT(19)   null,
    pv_counts   BIGINT(19)   null,
    week_day    VARCHAR(255) null,
    constraint UK_11aksgq87euk9bcyeesfs4vtp
        unique (date)
);

INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (106, '2019-06-21 18:14:23', '2019-06-21', 1, 9, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (107, '2019-06-24 17:54:50', '2019-06-24', 1, 5, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (108, '2019-06-25 10:35:06', '2019-06-25', 1, 9, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (109, '2019-06-26 17:51:08', '2019-06-26', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (110, '2019-06-27 10:49:43', '2019-06-27', 1, 14, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (111, '2019-06-28 11:25:42', '2019-06-28', 1, 4, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (112, '2019-07-01 11:55:46', '2019-07-01', 1, 12, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (113, '2019-07-02 18:09:43', '2019-07-02', 1, 9, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (114, '2019-07-03 18:13:14', '2019-07-03', 1, 2, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (115, '2019-07-04 09:32:16', '2019-07-04', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (116, '2019-07-05 09:30:00', '2019-07-05', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (117, '2019-07-09 20:50:14', '2019-07-09', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (118, '2019-07-10 14:41:16', '2019-07-10', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (119, '2019-07-12 20:03:02', '2019-07-12', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (120, '2019-07-13 15:27:56', '2019-07-13', 1, 1, 'Sat');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (121, '2019-07-15 10:04:21', '2019-07-15', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (122, '2019-07-16 10:20:48', '2019-07-16', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (123, '2019-07-17 10:43:14', '2019-07-17', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (124, '2019-07-18 12:47:53', '2019-07-18', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (125, '2019-07-22 15:47:11', '2019-07-22', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (126, '2019-07-24 10:28:51', '2019-07-24', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (127, '2019-07-29 19:29:05', '2019-07-29', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (128, '2019-07-30 16:54:13', '2019-07-30', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (129, '2019-07-31 09:41:48', '2019-07-31', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (130, '2019-08-01 09:33:21', '2019-08-01', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (131, '2019-08-02 19:14:34', '2019-08-02', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (132, '2019-08-05 18:24:59', '2019-08-05', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (133, '2019-08-06 18:00:42', '2019-08-06', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (134, '2019-08-07 18:14:18', '2019-08-07', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (135, '2019-08-08 17:38:12', '2019-08-08', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (136, '2019-08-09 09:07:26', '2019-08-09', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (137, '2019-08-14 10:31:57', '2019-08-14', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (138, '2019-08-15 10:02:07', '2019-08-15', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (139, '2019-08-17 18:32:27', '2019-08-17', 1, 1, 'Sat');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (140, '2019-08-20 10:50:02', '2019-08-20', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (141, '2019-08-21 17:23:04', '2019-08-21', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (142, '2019-08-22 10:19:13', '2019-08-22', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (143, '2019-08-23 11:21:50', '2019-08-23', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (144, '2019-08-24 10:38:48', '2019-08-24', 1, 1, 'Sat');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (145, '2019-08-27 09:40:02', '2019-08-27', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (146, '2019-08-28 09:58:52', '2019-08-28', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (147, '2019-08-29 18:40:12', '2019-08-29', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (148, '2019-09-02 13:14:26', '2019-09-02', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (149, '2019-09-03 16:56:53', '2019-09-03', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (150, '2019-09-06 09:20:45', '2019-09-06', 1, 1, 'Fri');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (151, '2019-09-09 14:54:29', '2019-09-09', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (152, '2019-09-10 10:08:03', '2019-09-10', 1, 1, 'Tue');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (153, '2019-09-11 10:08:47', '2019-09-11', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (154, '2019-09-12 10:36:24', '2019-09-12', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (155, '2019-09-16 10:22:03', '2019-09-16', 1, 1, 'Mon');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (156, '2019-09-18 09:48:20', '2019-09-18', 1, 1, 'Wed');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (157, '2019-09-19 17:54:16', '2019-09-19', 1, 1, 'Thu');
INSERT INTO cyoubike.visits (id, create_time, date, ip_counts, pv_counts, week_day) VALUES (158, '2019-09-20 11:34:13', '2019-09-20', 1, 1, 'Fri');