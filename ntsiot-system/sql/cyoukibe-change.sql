-- 2019-10-20 车辆信息增加‘排序序号’字段
ALTER TABLE `cyoubike`.`bike` ADD COLUMN `bike_seq` bigint(10) DEFAULT 0 COMMENT '排序序号' AFTER `unlock_mode`;