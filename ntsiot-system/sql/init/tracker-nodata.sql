/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.1-m11 : Database - cyoubike
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cyoubike` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cyoubike`;

/*Table structure for table `app_config` */

DROP TABLE IF EXISTS `app_config`;

CREATE TABLE `app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` varchar(255) DEFAULT NULL COMMENT 'appId',
  `secret` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `aes_key` varchar(255) DEFAULT NULL,
  `is_default` varchar(20) DEFAULT NULL COMMENT '是否默认',
  `type` int(11) DEFAULT '0' COMMENT '类型 0-小程序 1-公众号',
  `name` varchar(255) DEFAULT NULL COMMENT '小程序或者公众号名字',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='小程序配置表';

/*Table structure for table `app_dept_rela` */

DROP TABLE IF EXISTS `app_dept_rela`;

CREATE TABLE `app_dept_rela` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` bigint(20) DEFAULT NULL COMMENT '小程序编号',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区编号',
  `type` int(10) DEFAULT '0' COMMENT '类型 0-小程序 1-公众号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='小程序配置和辖区关系表';

/*Table structure for table `app_module` */

DROP TABLE IF EXISTS `app_module`;

CREATE TABLE `app_module` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '模块名',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `remark` varchar(1000) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='小程序模块表';

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `is_repay` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `article_number` */

DROP TABLE IF EXISTS `article_number`;

CREATE TABLE `article_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `loan_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `bike` */

DROP TABLE IF EXISTS `bike`;

CREATE TABLE `bike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `bike_barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '车辆条码',
  `lock_barcode` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '智能锁条码',
  `jurisdiction_id` bigint(20) DEFAULT NULL COMMENT '所属辖区',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `updater` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者',
  `register_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '鉴权码',
  `iccid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'sim卡iccid号',
  `status` bigint(10) DEFAULT NULL COMMENT '车辆状态',
  `user` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '骑行人',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '车辆类型',
  `bike_icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `unlock_mode` int(255) DEFAULT '2' COMMENT '1-无需开锁；2-扫码开锁；',
  `bike_seq` bigint(10) DEFAULT '0' COMMENT '排序序号',
  `show_real_line` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `color` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='车辆表';

/*Table structure for table `bike_icon` */

DROP TABLE IF EXISTS `bike_icon`;

CREATE TABLE `bike_icon` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dept` bigint(10) DEFAULT NULL COMMENT '辖区',
  `bike_type` varchar(255) DEFAULT NULL COMMENT '车辆类型',
  `bike_icon` varchar(255) DEFAULT NULL COMMENT '车辆图标',
  `remark` varchar(1000) DEFAULT NULL COMMENT '描述',
  `show_real_line` varchar(10) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `bike_jurisdiction_rela` */

DROP TABLE IF EXISTS `bike_jurisdiction_rela`;

CREATE TABLE `bike_jurisdiction_rela` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `jurisdiction_id` bigint(20) DEFAULT NULL COMMENT '辖区编号',
  `bike_id` bigint(20) DEFAULT NULL COMMENT '车辆编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='车辆辖区关系表';

/*Table structure for table `bike_license` */

DROP TABLE IF EXISTS `bike_license`;

CREATE TABLE `bike_license` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `telephone` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '所属辖区',
  `type` bigint(20) DEFAULT NULL COMMENT '授权类型 1：永久；0：临时',
  `start_time` bigint(20) DEFAULT NULL COMMENT '授权开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '授权结束时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `creator` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `updater` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='车辆授权';

/*Table structure for table `borrowing_returning` */

DROP TABLE IF EXISTS `borrowing_returning`;

CREATE TABLE `borrowing_returning` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `get_time` bigint(20) DEFAULT NULL,
  `repay_time` bigint(20) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `ma_open_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `comite` */

DROP TABLE IF EXISTS `comite`;

CREATE TABLE `comite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '居委会名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属辖区',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='居委会信息';

/*Table structure for table `common_problem` */

DROP TABLE IF EXISTS `common_problem`;

CREATE TABLE `common_problem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `content` text COLLATE utf8_bin COMMENT '内容',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '辖区',
  `is_visible` bigint(20) DEFAULT NULL COMMENT '是否可见',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='常见问题';

/*Table structure for table `customer_service` */

DROP TABLE IF EXISTS `customer_service`;

CREATE TABLE `customer_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标记 1：正常  2：删除',
  `help_phone` varchar(20) DEFAULT NULL COMMENT '救援电话',
  `booking_line` varchar(20) DEFAULT NULL COMMENT '预约电话',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `dept` */

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `pid` bigint(20) NOT NULL COMMENT '上级部门',
  `create_time` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `number` longtext,
  `code` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `bike_icon` varchar(255) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `aes_key` varchar(255) DEFAULT NULL,
  `city_phone` varchar(255) DEFAULT NULL,
  `dept_phone` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `check_license` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `dept_modules` */

DROP TABLE IF EXISTS `dept_modules`;

CREATE TABLE `dept_modules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(20) DEFAULT NULL,
  `app_module_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='辖区、小程序模块关系表';

/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(125) DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(64) DEFAULT NULL COMMENT '型号',
  `device_no` varchar(125) DEFAULT NULL COMMENT '设备号(IMEI)或者(IMEI,SIM)',
  `device_logo` varchar(125) DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `password` varchar(32) DEFAULT '123456' COMMENT '设备的默认密码为''123456''',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用 1：启用；2：禁用',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '是否删除1：否；2：是',
  `leave_factory_time` date DEFAULT NULL COMMENT '出厂时间',
  `install_time` date DEFAULT NULL COMMENT '安装时间',
  `platform_expires_time` date DEFAULT NULL COMMENT '平台到期',
  `user_expires_time` date DEFAULT NULL COMMENT '用户到期',
  `status` tinyint(1) DEFAULT '1' COMMENT '设备状态 1：离线 2.在线',
  `version` varchar(32) DEFAULT NULL COMMENT '版本号',
  `location` varchar(125) DEFAULT NULL COMMENT '位置 ',
  `gps_date` date DEFAULT NULL COMMENT '定位时间',
  `card_sim` varchar(125) DEFAULT NULL COMMENT '装在设备内的手机卡号',
  `car_no` varchar(64) DEFAULT NULL COMMENT '车牌号',
  `speed_alert` float(11,2) DEFAULT NULL COMMENT '超速警报',
  `speed_time` float(11,2) DEFAULT NULL COMMENT '超速持续时间',
  `high_temperature_alert` float(11,2) DEFAULT NULL COMMENT '高温报警值',
  `low_temperature_alert` float(11,2) DEFAULT NULL COMMENT '低温报警值',
  `icc_id` varchar(64) DEFAULT NULL COMMENT 'iccid',
  `car_no_colour` varchar(16) DEFAULT NULL COMMENT '车牌颜色',
  `frame_no` varchar(64) DEFAULT NULL COMMENT '车架号',
  `engine_no` varchar(64) DEFAULT NULL COMMENT '发动机号',
  `car_type` varchar(64) DEFAULT NULL COMMENT '车辆型号',
  `activation_code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `gender` tinyint(1) DEFAULT '1' COMMENT '司机性别',
  `type` varchar(64) DEFAULT NULL COMMENT '设备型号',
  `car_made_country` varchar(64) DEFAULT NULL COMMENT '汽车制造国',
  `car_init_time` datetime DEFAULT NULL COMMENT '车辆生产日期',
  `last_on_line_time` datetime DEFAULT NULL COMMENT '最后在线时间',
  `activation` tinyint(1) DEFAULT '1' COMMENT '设备激活 1：未激活 2.已激活',
  `activation_time` datetime DEFAULT NULL COMMENT '激活时间',
  `contact_person` varchar(64) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(16) DEFAULT NULL COMMENT '用户电话',
  `rest_time` datetime DEFAULT NULL COMMENT '静止时间',
  PRIMARY KEY (`id`),
  KEY `index_device_no` (`device_no`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `device_alert` */

DROP TABLE IF EXISTS `device_alert`;

CREATE TABLE `device_alert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_no` bigint(20) DEFAULT NULL COMMENT '设备编号',
  `alert_type` varchar(32) DEFAULT NULL COMMENT '报警类型，与new_dict 字典表code对应',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标记 1：正常 2：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `status` tinyint(1) DEFAULT '1' COMMENT '报警状态 1：报警 2：取消报警',
  `longitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(64) DEFAULT NULL COMMENT '纬度',
  `course` varchar(16) DEFAULT NULL COMMENT '方向',
  `speed` varchar(16) DEFAULT NULL COMMENT '速度',
  `time` datetime DEFAULT NULL COMMENT '信号时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `device_fence` */

DROP TABLE IF EXISTS `device_fence`;

CREATE TABLE `device_fence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备id',
  `fence_id` bigint(20) DEFAULT NULL COMMENT '围栏id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `dict_detail` */

DROP TABLE IF EXISTS `dict_detail`;

CREATE TABLE `dict_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `sort` varchar(255) DEFAULT NULL COMMENT '排序',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `email_config` */

DROP TABLE IF EXISTS `email_config`;

CREATE TABLE `email_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `from_user` varchar(255) DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) DEFAULT NULL COMMENT '密码',
  `port` varchar(255) DEFAULT NULL COMMENT '端口',
  `user` varchar(255) DEFAULT NULL COMMENT '发件者用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `evaluation` */

DROP TABLE IF EXISTS `evaluation`;

CREATE TABLE `evaluation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionnaire_id` bigint(20) DEFAULT NULL COMMENT '问卷id',
  `question_id` bigint(20) DEFAULT NULL COMMENT '问题id',
  `answer` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '答案',
  `respondents` bigint(20) DEFAULT NULL COMMENT '回答人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='满意度评价';

/*Table structure for table `fence` */

DROP TABLE IF EXISTS `fence`;

CREATE TABLE `fence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `coordinate` varchar(6000) COLLATE utf8_bin DEFAULT NULL COMMENT '围栏坐标',
  `user_id` bigint(20) DEFAULT NULL COMMENT '所属用户id',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `remark` text COLLATE utf8_bin COMMENT '说明',
  `creator` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '类型 与new_dic 关联',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '状态 与new_dic 关联',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='电子围栏表';

/*Table structure for table `gen_config` */

DROP TABLE IF EXISTS `gen_config`;

CREATE TABLE `gen_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `cover` bit(1) DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `gis_service` */

DROP TABLE IF EXISTS `gis_service`;

CREATE TABLE `gis_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT 'gis服务名称',
  `type` varchar(64) DEFAULT NULL COMMENT '兴趣点类型',
  `latitude` varchar(64) DEFAULT NULL COMMENT '经度',
  `longitude` varchar(64) DEFAULT NULL COMMENT '纬度',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `housing_owner_record` */

DROP TABLE IF EXISTS `housing_owner_record`;

CREATE TABLE `housing_owner_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房东',
  `owner_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房东电话',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属辖区',
  `address` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房屋房东备案';

/*Table structure for table `housing_tenant_record` */

DROP TABLE IF EXISTS `housing_tenant_record`;

CREATE TABLE `housing_tenant_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客',
  `tenant_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客电话',
  `tenant_idcard` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客身份证号',
  `house_id` bigint(20) DEFAULT NULL COMMENT '房屋id',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_house_id` (`house_id`) USING BTREE,
  CONSTRAINT `fk_house_id` FOREIGN KEY (`house_id`) REFERENCES `housing_owner_record` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房屋租客备案';

/*Table structure for table `icon` */

DROP TABLE IF EXISTS `icon`;

CREATE TABLE `icon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='图标类别管理';

/*Table structure for table `illegal_report` */

DROP TABLE IF EXISTS `illegal_report`;

CREATE TABLE `illegal_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `telephone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `point` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `deal_time` bigint(20) DEFAULT NULL,
  `remark` varchar(3000) COLLATE utf8_bin DEFAULT NULL,
  `jurisdiction_name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `inform_config` */

DROP TABLE IF EXISTS `inform_config`;

CREATE TABLE `inform_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `dept_id` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房屋出租人义务告知';

/*Table structure for table `internal_information` */

DROP TABLE IF EXISTS `internal_information`;

CREATE TABLE `internal_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `id_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `sort` bigint(20) NOT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKmvhj0rogastlctflsxf1d6k3i` (`dept_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `jurisdiction_configuration` */

DROP TABLE IF EXISTS `jurisdiction_configuration`;

CREATE TABLE `jurisdiction_configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logo_url` varchar(255) DEFAULT NULL,
  `is_vehicle_license` varchar(255) DEFAULT NULL,
  `is_virtual` varchar(255) DEFAULT NULL,
  `is_parking_restrictions` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `bike_Icon1` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '电动车图标',
  `bike_Icon2` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '助力车图标',
  `other_day` bigint(10) DEFAULT NULL COMMENT '前几天',
  `start_time` varchar(20) DEFAULT NULL COMMENT '查询开始时间',
  `next_day` bigint(10) DEFAULT NULL COMMENT '后几天',
  `end_time` varchar(20) DEFAULT NULL COMMENT '查询结束时间',
  `stop_marker1` varchar(255) DEFAULT NULL COMMENT '停留点（30分钟以内）',
  `stop_marker2` varchar(255) DEFAULT NULL COMMENT '停留点（30~60分钟以内）',
  `stop_marker3` varchar(255) DEFAULT NULL COMMENT '停留点（60分钟以外）',
  `show_real_line` varchar(10) DEFAULT '1' COMMENT '是否显示小尾巴',
  `color` varchar(10) DEFAULT NULL COMMENT '小尾巴颜色',
  `security_guard` varchar(1000) DEFAULT NULL COMMENT '安全守护自定义',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `loan_count` */

DROP TABLE IF EXISTS `loan_count`;

CREATE TABLE `loan_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `borrowingReturningId` bigint(20) DEFAULT NULL,
  `articleId` bigint(20) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `giveNumber` varchar(255) DEFAULT NULL,
  `giveTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `exception_detail` text,
  `log_type` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` longtext,
  `request_ip` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_username` (`username`),
  KEY `index_desc` (`description`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `pid` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `sort` bigint(20) NOT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `hidden` varchar(1) DEFAULT NULL COMMENT '是否隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `mileage_statistics` */

DROP TABLE IF EXISTS `mileage_statistics`;

CREATE TABLE `mileage_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_no` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `mileage` double(10,2) DEFAULT NULL COMMENT '里程(公里)',
  `speed` int(10) DEFAULT NULL COMMENT '超速(次)',
  `stop` int(10) DEFAULT NULL COMMENT '停留(次)',
  `fence_in` double(10,2) DEFAULT NULL COMMENT '围栏内里程',
  `fence_out` double(10,2) DEFAULT NULL COMMENT '围栏外里程',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '删除标记  1:正常  2：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='里程统计';

/*Table structure for table `new_dict` */

DROP TABLE IF EXISTS `new_dict`;

CREATE TABLE `new_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL COMMENT '字典code',
  `value` varchar(64) DEFAULT NULL COMMENT '值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `remark` varbinary(255) DEFAULT NULL COMMENT '备注',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父节点',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用 1：启用；2：禁用',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '是否删除1：否；2：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


insert  into `new_dict`(`id`,`code`,`value`,`description`,`remark`,`p_id`,`create_time`,`update_time`,`create_by`,`update_by`,`enabled`,`del_flag`) values 
(1,'custom_type_01','客户类型',NULL,NULL,0,'2020-04-30 13:32:41',NULL,'admin',NULL,1,1),
(2,'custom_type_001','4S店集团',NULL,NULL,1,'2020-04-30 13:33:52',NULL,'admin',NULL,1,1),
(3,'custom_type_002','经销商',NULL,NULL,1,'2020-04-30 13:34:27',NULL,'admin',NULL,1,1),
(4,'custom_type_003','4S店',NULL,NULL,1,'2020-04-30 13:35:26',NULL,'admin',NULL,1,1),
(5,'custom_type_004','用户',NULL,NULL,1,'2020-04-30 13:35:51',NULL,'admin',NULL,1,1),
(6,'custom_type_005','使用者',NULL,NULL,1,'2020-04-30 13:36:09',NULL,'admin',NULL,1,1),
(7,'device_type_01','型号',NULL,NULL,0,'2020-05-06 06:39:18',NULL,'admin',NULL,1,1),
(8,'device_type_001','DA107',NULL,NULL,7,'2020-05-06 06:40:39',NULL,'admin',NULL,1,1),
(9,'device_type_002','DA106',NULL,NULL,7,'2020-05-06 06:40:41',NULL,'admin',NULL,1,1),
(10,'device_type_003','DA105',NULL,NULL,7,'2020-05-06 06:40:42',NULL,'admin',NULL,1,1),
(11,'alert_type_01','设备报警类型',NULL,NULL,0,'2020-05-07 06:59:53',NULL,'admin',NULL,1,1),
(12,'alert_type_001','SOS劫警',NULL,NULL,11,'2020-05-07 07:00:51',NULL,'admin',NULL,1,1),
(13,'alert_type_002','超速报警',NULL,NULL,11,'2020-05-07 07:01:28',NULL,'admin',NULL,1,1),
(14,'alert_type_003','OBD故障报警',NULL,NULL,11,'2020-05-07 07:02:25',NULL,'admin',NULL,1,1),
(15,'alert_type_004','出围栏报警',NULL,NULL,11,'2020-05-07 07:02:27',NULL,'admin',NULL,1,1),
(16,'alert_type_005','震动报警',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(17,'alert_type_006','车况异常',NULL,NULL,11,'2020-05-07 07:03:23',NULL,'admin',NULL,1,1),
(18,'alert_type_007','入围栏报警',NULL,NULL,11,'2020-05-07 07:04:00',NULL,'admin',NULL,1,1),
(19,'alert_type_008','位移报警',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(20,'alert_type_009','伪基站',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(21,'alert_type_0010','断主电报警',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(22,'alert_type_0011','4S救援',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(23,'alert_type_0012','BMS故障报警',NULL,NULL,11,'2020-05-07 07:02:58',NULL,'admin',NULL,1,1),
(24,'fence_type_01','围栏类型',NULL,NULL,0,'2020-05-26 10:36:18',NULL,'admin',NULL,1,1),
(25,'fence_type_001','仓库',NULL,NULL,24,'2020-05-26 10:40:14',NULL,'admin',NULL,1,1),
(26,'fence_type_002','公司企业',NULL,NULL,24,'2020-05-26 10:40:50',NULL,'admin',NULL,1,1),
(27,'fence_type_003','工厂',NULL,NULL,24,'2020-05-26 10:41:07',NULL,'admin',NULL,1,1),
(28,'fence_type_004','口岸',NULL,NULL,24,'2020-05-26 10:41:19',NULL,'admin',NULL,1,1),
(29,'fence_type_005','收费站',NULL,NULL,24,'2020-05-26 10:41:41',NULL,'admin',NULL,1,1),
(30,'fence_type_006','码头',NULL,NULL,24,'2020-05-26 10:41:53',NULL,'admin',NULL,1,1),
(31,'fence_type_007','物流园',NULL,NULL,24,'2020-05-26 10:42:13',NULL,'admin',NULL,1,1),
(32,'fence_type_008','跟踪点',NULL,NULL,24,'2020-05-26 10:42:32',NULL,'admin',NULL,1,1),
(33,'fence_type_009','网点',NULL,NULL,24,'2020-05-26 10:42:44',NULL,'admin',NULL,1,1),
(34,'fence_type_0010','其他',NULL,NULL,24,'2020-05-26 10:42:55',NULL,'admin',NULL,1,1),
(35,'fence_type_0011','付费围栏',NULL,NULL,24,'2020-05-26 10:43:18',NULL,'admin',NULL,1,1),
(36,'fence_status_01','围栏状态',NULL,NULL,0,'2020-05-26 10:44:14',NULL,'admin',NULL,1,1),
(37,'fence_status_001','出界',NULL,NULL,36,'2020-05-26 10:45:14',NULL,'admin',NULL,1,1),
(38,'fence_status_002','入界',NULL,NULL,36,'2020-05-26 10:45:34',NULL,'admin',NULL,1,1),
(39,'fence_status_003','双向',NULL,NULL,36,'2020-05-26 10:45:52',NULL,'admin',NULL,1,1),
(40,'fence_status_004','关闭',NULL,NULL,36,'2020-05-26 10:46:00',NULL,'admin',NULL,1,1),
(41,'gis_service_01','GIS服务',NULL,NULL,0,'2020-05-26 15:07:35',NULL,'admin',NULL,1,1),
(42,'gis_service_001','摄像头',NULL,NULL,41,'2020-05-26 15:09:15',NULL,'admin',NULL,1,1),
(43,'gis_service_002','岗亭',NULL,NULL,41,'2020-05-26 15:13:39',NULL,'admin',NULL,1,1),
(44,'gis_service_003','报警点',NULL,NULL,41,'2020-05-26 15:13:39',NULL,'admin',NULL,1,1),
(45,'gis_service_004','仓库',NULL,NULL,41,'2020-05-26 15:13:39',NULL,'admin',NULL,1,1),
(46,'gis_service_005','服饰穿戴',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(47,'gis_service_006','公司企业',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(48,'gis_service_007','交通旅游',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(49,'gis_service_008','金融服务',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(50,'gis_service_009','商场百货',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(51,'gis_service_0010','生活服务',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(52,'gis_service_0011','文化教育',NULL,NULL,41,'2020-05-26 15:13:40',NULL,'admin',NULL,1,1),
(53,'gis_service_0012','餐饮美食',NULL,NULL,41,'2020-05-26 15:13:41',NULL,'admin',NULL,1,1),
(54,'gis_service_0013','休闲娱乐',NULL,NULL,41,'2020-05-26 15:13:41',NULL,'admin',NULL,1,1),
(55,'gis_service_0014','医疗保健',NULL,NULL,41,'2020-05-26 15:13:41',NULL,'admin',NULL,1,1),
(56,'gis_service_0015','政府公务',NULL,NULL,41,'2020-05-26 15:13:41',NULL,'admin',NULL,1,1),
(57,'device_type_004','DA1008',NULL,NULL,7,'2020-06-10 09:47:53',NULL,'admin',NULL,1,1);

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(3000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '新闻标题',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '新闻类型',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin COMMENT '新闻内容',
  `is_visible` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否展示',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '辖区',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `thumb_media_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公众号文章media_id',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '公众号文章原文链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新闻表';

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单id',
  `status` bigint(20) DEFAULT NULL COMMENT '订单状态 参照字典 order_status',
  `user_id` bigint(10) DEFAULT NULL COMMENT '使用用户',
  `bike_barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '车辆编号',
  `start_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `start_lng` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始经度',
  `start_lat` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始纬度',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `end_lng` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '结束经度',
  `end_lat` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '结束纬度',
  `mile` double(20,5) DEFAULT NULL COMMENT '订单里程',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '辖区编号',
  `close_source` bigint(10) DEFAULT NULL COMMENT '结束来源 ',
  `order_amount` double(10,2) DEFAULT NULL COMMENT '订单金额',
  `compensation_amount` double(10,2) DEFAULT NULL COMMENT '补偿金额',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `unlock_mode` int(255) DEFAULT '2' COMMENT '1-无需开锁；2-扫码开锁；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `alias` varchar(255) DEFAULT NULL COMMENT '别名',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `pid` int(11) NOT NULL COMMENT '上级权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '上传日期',
  `delete_url` varchar(255) DEFAULT NULL COMMENT '删除的URL',
  `filename` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `height` varchar(255) DEFAULT NULL COMMENT '图片高度',
  `size` varchar(255) DEFAULT NULL COMMENT '图片大小',
  `url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `width` varchar(255) DEFAULT NULL COMMENT '图片宽度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qiniu_config` */

DROP TABLE IF EXISTS `qiniu_config`;

CREATE TABLE `qiniu_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `access_key` text COMMENT 'accessKey',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
  `host` varchar(255) NOT NULL COMMENT '外链域名',
  `secret_key` text COMMENT 'secretKey',
  `type` varchar(255) DEFAULT NULL COMMENT '空间类型',
  `zone` varchar(255) DEFAULT NULL COMMENT '机房（华东，华南，华北，北美）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `qiniu_content` */

DROP TABLE IF EXISTS `qiniu_content`;

CREATE TABLE `qiniu_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `size` varchar(255) DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
  `update_time` datetime DEFAULT NULL COMMENT '上传或同步的时间',
  `url` varchar(255) DEFAULT NULL COMMENT '文件url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `quartz_job` */

DROP TABLE IF EXISTS `quartz_job`;

CREATE TABLE `quartz_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'Spring Bean名称',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron 表达式',
  `is_pause` bit(1) DEFAULT NULL COMMENT '状态：1暂停、0启用',
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名称',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT NULL COMMENT '创建或更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `quartz_log` */

DROP TABLE IF EXISTS `quartz_log`;

CREATE TABLE `quartz_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baen_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `exception_detail` text,
  `is_success` bit(1) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '题目',
  `question_type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '问题类型',
  `is_show` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '是否展示',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `sort` bigint(10) DEFAULT NULL COMMENT '顺序',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '辖区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='题库';

/*Table structure for table `question_option` */

DROP TABLE IF EXISTS `question_option`;

CREATE TABLE `question_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `question_id` bigint(20) DEFAULT NULL COMMENT '题目id',
  `option_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项id',
  `option_val` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='题库选项';

/*Table structure for table `questionnaire` */

DROP TABLE IF EXISTS `questionnaire`;

CREATE TABLE `questionnaire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '题目',
  `is_show` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '是否展示',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `jurisdiction` bigint(10) DEFAULT NULL COMMENT '辖区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问卷调查';

/*Table structure for table `questionnaire_option` */

DROP TABLE IF EXISTS `questionnaire_option`;

CREATE TABLE `questionnaire_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionnaire_id` bigint(20) DEFAULT NULL COMMENT '调查问卷id',
  `question_id` bigint(20) DEFAULT NULL COMMENT '问题id',
  `sort` bigint(20) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='调查问卷题库';

/*Table structure for table `reference_point` */

DROP TABLE IF EXISTS `reference_point`;

CREATE TABLE `reference_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `icon_type` bigint(20) DEFAULT NULL COMMENT '图标类别',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区',
  `remark` varchar(2000) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='参考点';

/*Table structure for table `reservemask_owner_record` */

DROP TABLE IF EXISTS `reservemask_owner_record`;

CREATE TABLE `reservemask_owner_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `owner` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房东',
  `owner_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房东电话',
  `open_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `comite_id` bigint(20) DEFAULT NULL COMMENT '所属居委会',
  `address` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='口罩预约房东记录';

/*Table structure for table `reservemask_tenant_record` */

DROP TABLE IF EXISTS `reservemask_tenant_record`;

CREATE TABLE `reservemask_tenant_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客',
  `tenant_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客电话',
  `tenant_idcard` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租客身份证号',
  `tenant_idcard_img` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证图片地址',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '房东id',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_owner_id` (`owner_id`) USING BTREE,
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `reservemask_owner_record` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='口罩预约租客信息';

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `data_scope` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `roles_depts` */

DROP TABLE IF EXISTS `roles_depts`;

CREATE TABLE `roles_depts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `roles_depts_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `roles_depts_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `roles_menus` */

DROP TABLE IF EXISTS `roles_menus`;

CREATE TABLE `roles_menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE,
  KEY `menu_id` (`menu_id`) USING BTREE,
  CONSTRAINT `roles_menus_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `roles_menus_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `roles_permissions` */

DROP TABLE IF EXISTS `roles_permissions`;

CREATE TABLE `roles_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKboeuhl31go7wer3bpy6so7exi` (`permission_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `roles_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `roles_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `shuffling_figure` */

DROP TABLE IF EXISTS `shuffling_figure`;

CREATE TABLE `shuffling_figure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_enable` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `jurisdiction` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url_link` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `sys_date` */

DROP TABLE IF EXISTS `sys_date`;

CREATE TABLE `sys_date` (
  `year` varchar(20) DEFAULT NULL COMMENT '年',
  `month` varchar(20) DEFAULT NULL COMMENT '月',
  `day` varchar(20) DEFAULT NULL COMMENT '日',
  `week` varchar(20) DEFAULT NULL COMMENT '周',
  `quarter` varchar(20) DEFAULT NULL COMMENT '季度',
  `ms` bigint(20) NOT NULL COMMENT '毫秒',
  PRIMARY KEY (`ms`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '用户编号账目表编号',
  `user_id` bigint(30) DEFAULT NULL COMMENT '用户编号',
  `balance` decimal(65,0) DEFAULT NULL COMMENT '当前金额',
  `total_expend` decimal(65,0) DEFAULT NULL COMMENT '消费总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_account_history` */

DROP TABLE IF EXISTS `t_account_history`;

CREATE TABLE `t_account_history` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(30) DEFAULT NULL COMMENT '账号编号',
  `balance` decimal(56,0) DEFAULT NULL COMMENT '账户余额',
  `balance_amount` decimal(56,0) DEFAULT NULL COMMENT '余额支付',
  `wexin_amount` decimal(56,0) DEFAULT NULL,
  `weixin_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '微信流水号',
  `create_time` bigint(30) DEFAULT NULL,
  `type` smallint(10) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_bike_lock` */

DROP TABLE IF EXISTS `t_bike_lock`;

CREATE TABLE `t_bike_lock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bike_barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `lock_barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_del` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_bike_warn` */

DROP TABLE IF EXISTS `t_bike_warn`;

CREATE TABLE `t_bike_warn` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `bike_barcode` varchar(255) DEFAULT NULL COMMENT '车辆条码',
  `lock_barcode` varchar(255) DEFAULT NULL COMMENT '智能锁条码',
  `dept_id` bigint(30) DEFAULT NULL,
  `alarm_id` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  `time` bigint(20) DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_check_point` */

DROP TABLE IF EXISTS `t_check_point`;

CREATE TABLE `t_check_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `longitude` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `latitude` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_check_point_task` */

DROP TABLE IF EXISTS `t_check_point_task`;

CREATE TABLE `t_check_point_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `create_time` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `check_point_id` bigint(20) DEFAULT NULL,
  `target` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `start_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `end_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_perform` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `upload_img_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_game` */

DROP TABLE IF EXISTS `t_game`;

CREATE TABLE `t_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏名称',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '游戏地址',
  `num_order` bigint(20) NOT NULL COMMENT '序号',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态  0-显示 -1-不显示',
  `cover_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '封面图片地址',
  `app_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '小程序的APPID',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT ' 游戏类型 0-小程序 1-H5',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_lock` */

DROP TABLE IF EXISTS `t_lock`;

CREATE TABLE `t_lock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lock_barcode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sim_iccid_no` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mac_address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `authorization_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `creator` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `updater` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_use` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_ma_user` */

DROP TABLE IF EXISTS `t_ma_user`;

CREATE TABLE `t_ma_user` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `phone` varchar(255) DEFAULT '',
  `openid` varchar(255) DEFAULT NULL,
  `appid` varchar(255) DEFAULT NULL,
  `dept_id` bigint(30) DEFAULT NULL,
  `user_type` int(10) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `type_id` varchar(255) DEFAULT NULL COMMENT '消息类型的id(task_id)',
  `type` varchar(255) DEFAULT NULL COMMENT '消息类型',
  `user_id` bigint(30) DEFAULT NULL COMMENT '消息接收人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `is_read` tinyint(5) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_message_template` */

DROP TABLE IF EXISTS `t_message_template`;

CREATE TABLE `t_message_template` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `message_title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message_content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_picture` */

DROP TABLE IF EXISTS `t_picture`;

CREATE TABLE `t_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pk` bigint(20) DEFAULT NULL,
  `path` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_real_check_point` */

DROP TABLE IF EXISTS `t_real_check_point`;

CREATE TABLE `t_real_check_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_task_id` bigint(30) DEFAULT NULL COMMENT '任务id',
  `longitude` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `latitude` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `scope` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sort` bigint(20) DEFAULT NULL,
  `point_id` bigint(20) DEFAULT NULL,
  `task_instance_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_real_check_point_task` */

DROP TABLE IF EXISTS `t_real_check_point_task`;

CREATE TABLE `t_real_check_point_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `create_time` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `real_point_id` bigint(20) DEFAULT NULL,
  `target` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `start_time` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `end_time` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `is_perform` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `upload_img_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_over` tinyint(5) DEFAULT NULL COMMENT '是否完成任务',
  `longitude` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `latitude` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `task_state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `over_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_real_task` */

DROP TABLE IF EXISTS `t_real_task`;

CREATE TABLE `t_real_task` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `task_temp_id` bigint(30) DEFAULT NULL COMMENT '任务模板ID',
  `yyyymmdd` varchar(255) DEFAULT NULL COMMENT '任务生成的日期',
  `status` varchar(10) DEFAULT NULL COMMENT '任务完成状态',
  `begin_time` bigint(20) DEFAULT NULL COMMENT '任务开启时间',
  `over_time` bigint(20) DEFAULT NULL COMMENT '任务结束时间',
  `sys_patrol_start_time` varchar(255) DEFAULT NULL COMMENT '系统巡更开始时间',
  `sys_patrol_end_time` varchar(255) DEFAULT NULL COMMENT '系统巡更结束时间',
  `path` varchar(255) DEFAULT NULL COMMENT '巡更轨迹',
  `start_up` varchar(10) DEFAULT NULL COMMENT '是否启动（0:启动，1：关闭）',
  `patrolman_id` bigint(30) DEFAULT NULL COMMENT '巡更人员id',
  `patrolman` varchar(255) DEFAULT NULL COMMENT '巡更人员名称',
  `phone` varchar(255) DEFAULT NULL COMMENT '巡更人员电话',
  `dept_id` bigint(30) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `lock_barcode` varchar(255) DEFAULT NULL COMMENT '车锁编号',
  `is_approval` varchar(255) DEFAULT NULL COMMENT '是否审核',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `instance_id` bigint(20) DEFAULT '0' COMMENT '实例id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_recharge_record` */

DROP TABLE IF EXISTS `t_recharge_record`;

CREATE TABLE `t_recharge_record` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `out_recharge_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商户充值记录编号',
  `user_id` bigint(30) DEFAULT NULL COMMENT '用户编号',
  `history_id` bigint(30) DEFAULT NULL COMMENT '流水编号',
  `charges_count` decimal(56,0) DEFAULT NULL COMMENT '充值金额',
  `largess_count` decimal(56,0) DEFAULT NULL COMMENT '赠送金额',
  `actual_count` decimal(56,0) DEFAULT NULL COMMENT '实际金额',
  `charges_time` bigint(20) DEFAULT NULL COMMENT '充值时间',
  `status` int(10) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_report_restrict` */

DROP TABLE IF EXISTS `t_report_restrict`;

CREATE TABLE `t_report_restrict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '举报须知内容',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区id',
  `template` int(2) DEFAULT '0' COMMENT '1-模板 0-不是模板',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_report_restrict_copy` */

DROP TABLE IF EXISTS `t_report_restrict_copy`;

CREATE TABLE `t_report_restrict_copy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '举报须知内容',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区id',
  `template` int(2) DEFAULT '0' COMMENT '1-模板 0-不是模板',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_send_message` */

DROP TABLE IF EXISTS `t_send_message`;

CREATE TABLE `t_send_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `send_content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `send_user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `send_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_summary_car_daily` */

DROP TABLE IF EXISTS `t_summary_car_daily`;

CREATE TABLE `t_summary_car_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bike_barcode` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '车牌号',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_date` date NOT NULL COMMENT '日期',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `alarm_count` int(11) DEFAULT '0' COMMENT '越界报警次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_car_daily_key_2` (`dept_id`,`bike_barcode`,`summary_date`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区车辆每日统计表(辖区的每个车每天一条记录)';

/*Table structure for table `t_summary_car_monthly` */

DROP TABLE IF EXISTS `t_summary_car_monthly`;

CREATE TABLE `t_summary_car_monthly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bike_barcode` bigint(20) NOT NULL COMMENT '车牌号',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_month` int(8) NOT NULL COMMENT '年月',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `alarm_count` int(11) DEFAULT '0' COMMENT '越界报警次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_car_monthly_key_2` (`dept_id`,`bike_barcode`,`summary_month`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区车辆每月统计表(辖区的每个车每月一条记录)';

/*Table structure for table `t_summary_dept_daily` */

DROP TABLE IF EXISTS `t_summary_dept_daily`;

CREATE TABLE `t_summary_dept_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_date` date NOT NULL COMMENT '日期',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `self_riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `self_riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `self_patrol_count` int(11) DEFAULT '0' COMMENT '本辖区巡逻人次(订单数量)',
  `self_task_count` int(11) DEFAULT '0' COMMENT '本辖区总任务数量',
  `self_finish_task_count` int(11) DEFAULT '0' COMMENT '本辖区完成任务数量',
  `self_car_used_rate` double DEFAULT '0' COMMENT '本辖区车辆使用率(小于1的实际比率)',
  `whole_riding_miles` double DEFAULT '0' COMMENT '本辖区及下级辖区骑行里程',
  `whole_riding_duration` double DEFAULT '0' COMMENT '本辖区及下级辖区骑行时长(单位秒)',
  `whole_patrol_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区巡逻人次',
  `whole_task_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区总任务数量',
  `whole_finish_task_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区完成任务数量',
  `whole_car_used_rate` double DEFAULT '0' COMMENT '本辖区及下级辖区车辆使用率',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_dept_daily_key_2` (`dept_id`,`summary_date`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区日统计表(每个辖区每天一条记录)';

/*Table structure for table `t_summary_dept_monthly` */

DROP TABLE IF EXISTS `t_summary_dept_monthly`;

CREATE TABLE `t_summary_dept_monthly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_month` int(8) NOT NULL COMMENT '年月',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `self_riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `self_riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `self_patrol_count` int(11) DEFAULT '0' COMMENT '本辖区巡逻人次(订单数量)',
  `self_task_count` int(11) DEFAULT '0' COMMENT '本辖区总任务数量',
  `self_finish_task_count` int(11) DEFAULT '0' COMMENT '本辖区完成任务数量',
  `self_car_used_rate` double DEFAULT '0' COMMENT '本辖区车辆使用率(小于1的实际比率)',
  `whole_riding_miles` double DEFAULT '0' COMMENT '本辖区及下级辖区骑行里程',
  `whole_riding_duration` double DEFAULT '0' COMMENT '本辖区及下级辖区骑行时长(单位秒)',
  `whole_patrol_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区巡逻人次',
  `whole_task_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区总任务数量',
  `whole_finish_task_count` int(11) DEFAULT '0' COMMENT '本辖区及下级辖区完成任务数量',
  `whole_car_used_rate` double DEFAULT '0' COMMENT '本辖区及下级辖区车辆使用率',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_dept_monthly_key_2` (`dept_id`,`summary_month`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='增加辖区月统计表(每个辖区每月一条记录)';

/*Table structure for table `t_summary_point_daily` */

DROP TABLE IF EXISTS `t_summary_point_daily`;

CREATE TABLE `t_summary_point_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `point_id` bigint(20) NOT NULL COMMENT '必到点id',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_date` date NOT NULL COMMENT '日期',
  `point_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '必到点名称',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `clock_in_system_count` int(11) DEFAULT '0' COMMENT '系统要求打卡次数',
  `clock_in_actual_count` int(11) DEFAULT '0' COMMENT '实际打卡次数',
  `image_system_count` int(11) DEFAULT '0' COMMENT '系统要求上传图片数',
  `image_actual_count` int(11) DEFAULT '0' COMMENT '实际上传图片数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_point_daily_key_2` (`dept_id`,`point_id`,`summary_date`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区必到点每日统计表(辖区的每个必到点每天一条记录)';

/*Table structure for table `t_summary_point_monthly` */

DROP TABLE IF EXISTS `t_summary_point_monthly`;

CREATE TABLE `t_summary_point_monthly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `point_id` bigint(20) NOT NULL COMMENT '必到点id',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_month` int(8) NOT NULL COMMENT '年月',
  `point_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '必到点名称',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `clock_in_system_count` int(11) DEFAULT '0' COMMENT '系统要求打卡次数',
  `clock_in_actual_count` int(11) DEFAULT '0' COMMENT '实际打卡次数',
  `image_system_count` int(11) DEFAULT '0' COMMENT '系统要求上传图片数',
  `image_actual_count` int(11) DEFAULT '0' COMMENT '实际上传图片数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_point_monthly_key_2` (`dept_id`,`point_id`,`summary_month`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区必到点每月统计表(辖区的每个必到点每月一条记录)';

/*Table structure for table `t_summary_user_daily` */

DROP TABLE IF EXISTS `t_summary_user_daily`;

CREATE TABLE `t_summary_user_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '人员id',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_date` date NOT NULL COMMENT '日期',
  `username` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员姓名',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `clock_in_system_count` int(11) DEFAULT '0' COMMENT '系统要求打卡次数',
  `clock_in_actual_count` int(11) DEFAULT '0' COMMENT '实际打卡次数',
  `image_system_count` int(11) DEFAULT '0' COMMENT '系统要求上传图片数',
  `image_actual_count` int(11) DEFAULT '0' COMMENT '实际上传图片数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_user_daily_key_2` (`dept_id`,`user_id`,`summary_date`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区人员每日统计表(辖区的每个人每天一条记录)';

/*Table structure for table `t_summary_user_monthly` */

DROP TABLE IF EXISTS `t_summary_user_monthly`;

CREATE TABLE `t_summary_user_monthly` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '人员id',
  `dept_id` bigint(20) NOT NULL COMMENT '辖区id',
  `summary_month` int(8) NOT NULL COMMENT '年月',
  `username` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员姓名',
  `dept_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '辖区名称',
  `riding_miles` double DEFAULT '0' COMMENT '本辖区骑行里程',
  `riding_duration` double DEFAULT '0' COMMENT '本辖区骑行时长(单位秒)',
  `clock_in_system_count` int(11) DEFAULT '0' COMMENT '系统要求打卡次数',
  `clock_in_actual_count` int(11) DEFAULT '0' COMMENT '实际打卡次数',
  `image_system_count` int(11) DEFAULT '0' COMMENT '系统要求上传图片数',
  `image_actual_count` int(11) DEFAULT '0' COMMENT '实际上传图片数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_summary_user_monthly_key_2` (`dept_id`,`user_id`,`summary_month`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='辖区人员每月统计表(辖区的每个人每月一条记录)';

/*Table structure for table `t_task_approval` */

DROP TABLE IF EXISTS `t_task_approval`;

CREATE TABLE `t_task_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `score` bigint(10) DEFAULT NULL COMMENT '分数',
  `remark` varchar(2000) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='巡更任务审核';

/*Table structure for table `t_task_instance` */

DROP TABLE IF EXISTS `t_task_instance`;

CREATE TABLE `t_task_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) DEFAULT NULL COMMENT '模板名称',
  `task_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `path` varchar(2000) DEFAULT NULL COMMENT '巡更轨迹',
  `start_up` varchar(20) DEFAULT NULL COMMENT '是否启动',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区',
  `remark` varchar(2000) DEFAULT NULL COMMENT '描述',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL COMMENT '模板ID',
  `repeat_time` varchar(255) DEFAULT NULL COMMENT '重复类型',
  `sys_patrol_start_time` varchar(50) DEFAULT NULL COMMENT '系统巡更开始时间',
  `sys_patrol_end_time` varchar(50) DEFAULT NULL COMMENT '系统巡更结束时间',
  `exec_time` bigint(20) DEFAULT NULL COMMENT '提前下发时间',
  `active_date_start` varchar(255) DEFAULT NULL COMMENT '生效日期',
  `active_date_end` varchar(255) DEFAULT NULL COMMENT '失效日期',
  `is_del` int(255) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='智能轨迹任务配置';

/*Table structure for table `t_task_instance_point` */

DROP TABLE IF EXISTS `t_task_instance_point`;

CREATE TABLE `t_task_instance_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `instance_id` bigint(20) DEFAULT NULL COMMENT '实例id',
  `point_type` int(11) DEFAULT '1' COMMENT '1-默认，2-临时必到点',
  `point_id` bigint(20) DEFAULT NULL COMMENT '必到点id',
  `create_time` bigint(20) NOT NULL,
  `remark` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `longitude` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '纬度',
  `point_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_task_patrolman` */

DROP TABLE IF EXISTS `t_task_patrolman`;

CREATE TABLE `t_task_patrolman` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `patrolman_id` bigint(20) DEFAULT NULL COMMENT '巡更人员id',
  `patrolman` varchar(255) DEFAULT NULL COMMENT '巡更人员名称',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='巡更人员表';

/*Table structure for table `t_task_point` */

DROP TABLE IF EXISTS `t_task_point`;

CREATE TABLE `t_task_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务id',
  `point_id` bigint(20) DEFAULT NULL COMMENT '必到点id',
  `point_name` varchar(255) DEFAULT NULL COMMENT '必到点名称',
  `point_remark` varchar(2000) DEFAULT NULL COMMENT '必到点描述',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='巡更必到点';

/*Table structure for table `t_task_template` */

DROP TABLE IF EXISTS `t_task_template`;

CREATE TABLE `t_task_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `path` varchar(2000) DEFAULT NULL COMMENT '巡更轨迹',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '辖区',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='智能轨迹任务配置';

/*Table structure for table `t_utilization` */

DROP TABLE IF EXISTS `t_utilization`;

CREATE TABLE `t_utilization` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(100) DEFAULT NULL,
  `time` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日期',
  `utilization` double(100,2) DEFAULT NULL COMMENT '使用率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_video` */

DROP TABLE IF EXISTS `t_video`;

CREATE TABLE `t_video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频标题',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频链接地址',
  `dept_id` bigint(20) DEFAULT NULL,
  `dept_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_time` bigint(20) NOT NULL COMMENT '生效起始时间',
  `end_time` bigint(20) NOT NULL COMMENT '生效结束时间',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Table structure for table `t_warn_type` */

DROP TABLE IF EXISTS `t_warn_type`;

CREATE TABLE `t_warn_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_id` int(11) NOT NULL,
  `alarm_type_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `mode_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `top_up` */

DROP TABLE IF EXISTS `top_up`;

CREATE TABLE `top_up` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `money` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `jurisdiction` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `enabled` bigint(20) DEFAULT '1' COMMENT '状态：1启用、0禁用',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `last_password_reset_time` datetime DEFAULT NULL COMMENT '最后修改密码的日期',
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contact_person` varchar(64) DEFAULT NULL COMMENT '联系人',
  `icon` varchar(255) DEFAULT NULL COMMENT '头像',
  `p_id` bigint(20) DEFAULT NULL COMMENT '上级客户ID',
  `nick_name` varchar(255) NOT NULL COMMENT '昵称/客户名称',
  `mp_open_id` varchar(255) DEFAULT NULL COMMENT '公众号open id',
  `ma_open_id` varchar(255) DEFAULT NULL COMMENT '小程序open id',
  `union_id` varchar(255) DEFAULT NULL COMMENT '用户唯一标识符',
  `app_id` varchar(255) DEFAULT NULL,
  `custom_type_id` bigint(20) DEFAULT NULL COMMENT '客户类型ID（数据字典new__dict）',
  `del_flag` int(1) DEFAULT '1' COMMENT '删除标记  1:正常，2删除',
  PRIMARY KEY (`id`,`nick_name`),
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

insert  into `user`(`id`,`avatar`,`create_time`,`email`,`enabled`,`password`,`username`,`last_password_reset_time`,`phone`,`address`,`contact_person`,`icon`,`p_id`,`nick_name`,`mp_open_id`,`ma_open_id`,`union_id`,`app_id`,`custom_type_id`,`del_flag`) values 
(1,'https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg','2018-08-23 09:11:56','admin@eladmin.net',1,'e10adc3949ba59abbe56e057f20f883e','admin','2020-05-19 14:46:26','string111','string','string',NULL,0,'admin',NULL,NULL,NULL,NULL,2,1);


/*Table structure for table `user_device` */

DROP TABLE IF EXISTS `user_device`;

CREATE TABLE `user_device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `device_id` bigint(20) DEFAULT NULL COMMENT '设备ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `verification_code` */

DROP TABLE IF EXISTS `verification_code`;

CREATE TABLE `verification_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(255) DEFAULT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `status` bit(1) DEFAULT NULL COMMENT '状态：1有效、0过期',
  `type` varchar(255) DEFAULT NULL COMMENT '验证码类型：email或者短信',
  `value` varchar(255) DEFAULT NULL COMMENT '接收邮箱或者手机号码',
  `scenes` varchar(255) DEFAULT NULL COMMENT '业务名称：如重置邮箱、重置密码等',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `virtual_pile` */

DROP TABLE IF EXISTS `virtual_pile`;

CREATE TABLE `virtual_pile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `longitude` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '经度',
  `latitude` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '纬度',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `scope` bigint(20) DEFAULT NULL COMMENT '范围',
  `remark` text COLLATE utf8_bin COMMENT '说明',
  `jurisdiction` bigint(20) DEFAULT NULL COMMENT '辖区编号',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `updater` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='虚拟桩表';

/*Table structure for table `visits` */

DROP TABLE IF EXISTS `visits`;

CREATE TABLE `visits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `ip_counts` bigint(20) DEFAULT NULL,
  `pv_counts` bigint(20) DEFAULT NULL,
  `week_day` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_11aksgq87euk9bcyeesfs4vtp` (`date`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Table structure for table `work_diary` */

DROP TABLE IF EXISTS `work_diary`;

CREATE TABLE `work_diary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8_bin,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `point` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `open_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
