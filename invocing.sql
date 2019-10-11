/*
Navicat MySQL Data Transfer

Source Server         : 本地环境
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : invocing

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-10-04 15:43:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_gysinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_gysinfo`;
CREATE TABLE `tb_gysinfo` (
  `id` varchar(50) NOT NULL COMMENT '材料编号',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '客户编号',
  `name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `mobile` varchar(50) DEFAULT NULL COMMENT '电话/手机',
  `paper_name` varchar(100) DEFAULT NULL COMMENT '纸张名称',
  `specification` varchar(30) DEFAULT NULL COMMENT '纸张规格',
  `amount` varchar(10) DEFAULT NULL COMMENT '来料数量',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `user_signed` varchar(50) DEFAULT NULL COMMENT '签收人',
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_gysinfo
-- ----------------------------
INSERT INTO `tb_gysinfo` VALUES ('1001', '1004', '小宋', '1', '1230001', '1234561', '1457', '辉', '18341842036', '2019-10-03 21:49:32');
INSERT INTO `tb_gysinfo` VALUES ('1002', '国美电器', '苏宁', '南京', '123000', '123456', '1457', '辉', '18341842036', '2019-10-03 21:05:26');
INSERT INTO `tb_gysinfo` VALUES ('1003', '京东商城', '苏宁', '南京', '123000', '123456', '1457', '辉', '18341842036', '2019-10-03 21:05:30');

-- ----------------------------
-- Table structure for `tb_khinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_khinfo`;
CREATE TABLE `tb_khinfo` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '客户编号',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '客户姓名',
  `mobile` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话/手机',
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `pay_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '付款方式 现结、月付、季付',
  `remark` varchar(1200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `balance` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户信息表';

-- ----------------------------
-- Records of tb_khinfo
-- ----------------------------
INSERT INTO `tb_khinfo` VALUES ('1001', '小郑', '1', '1', '现结', '1', '1');
INSERT INTO `tb_khinfo` VALUES ('1002', '小王', '1', '1', '月结', '1', '1');
INSERT INTO `tb_khinfo` VALUES ('1003', '小陈', '1', '1', '季结', '1', '1');
INSERT INTO `tb_khinfo` VALUES ('1004', '小宋', '1', '1', '季结', '1', '1');
INSERT INTO `tb_khinfo` VALUES ('1005', '小小', '', '', '现结', '', '');

-- ----------------------------
-- Table structure for `tb_sell_main`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sell_main`;
CREATE TABLE `tb_sell_main` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '生产单编号',
  `customer_id` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '客户名称',
  `mobile` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话/手机',
  `pay_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '付款方式',
  `customer_remark` varchar(1200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注1',
  `farm` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '车间',
  `exposure_demand` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '晒版要求',
  `cpt_specification` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT 'CTP/菲林规格',
  `location` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '咬口位置',
  `exposure_directive` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '晒版指示',
  `chromatic_number` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '印刷色数',
  `print_method` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '印刷方式',
  `print_remark` varchar(1200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注2',
  `print_demand` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '印刷要求',
  `paper_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '纸张名称',
  `paper_specification` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '来料规格',
  `paper_amount` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '来料数量',
  `paper_remark` varchar(1200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注3',
  `amount` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印刷数量',
  `real_amount` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '印刷实数',
  `customer_demand` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户要求',
  `description` varchar(1200) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  `user_made` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '制单人',
  `customer_confirmed` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '客户确认',
  `user_confirmed` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '下单确认',
  `user_ended` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '机长',
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '制单时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='生产单表';

-- ----------------------------
-- Records of tb_sell_main
-- ----------------------------
INSERT INTO `tb_sell_main` VALUES ('1001', '1003', '小陈', '1', '', '', '台', '1800*1920', '简装', '452524', 'null', '1', '天地翻', '', '按样', '123000', '123456', 'null', '辉', 'null', 'null', 'null', 'null', '', '', '', '', '2019-10-04 11:33:50');
INSERT INTO `tb_sell_main` VALUES ('1002', '1004', '小宋', '1', '', '', '台', '1800*1920', '简装', '452524', '147728', '1', '天地翻', '', '看样', '123000', '123456', '1457', '辉', '', '', '', '', '', '', '', '', '2019-10-04 11:36:48');

-- ----------------------------
-- Table structure for `tb_spinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tb_spinfo`;
CREATE TABLE `tb_spinfo` (
  `id` varchar(50) NOT NULL COMMENT '入库编号',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '客户编号',
  `name` varchar(50) DEFAULT NULL COMMENT '客户姓名',
  `mobile` varchar(50) DEFAULT NULL COMMENT '电话/手机',
  `farm` varchar(100) DEFAULT NULL COMMENT '车间',
  `demand` varchar(255) DEFAULT NULL COMMENT '晒版要求',
  `specification` varchar(30) DEFAULT NULL COMMENT 'CTP/菲林规格',
  `location` varchar(100) DEFAULT NULL COMMENT '咬口位置',
  `directive` varchar(255) DEFAULT NULL COMMENT '晒版指示',
  `chromatic_number` varchar(30) DEFAULT NULL COMMENT '印刷色数',
  `method` varchar(30) DEFAULT NULL COMMENT '印刷要求',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `user_signed` varchar(50) DEFAULT NULL COMMENT '签收人',
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CPT/菲林入库信息表';

-- ----------------------------
-- Records of tb_spinfo
-- ----------------------------
INSERT INTO `tb_spinfo` VALUES ('2001', 'kh3', '小郑', '15757129711', '台', '33*20', '精装', '156498', '147687', '无', '单面印', '修改', 'z', '2019-10-03 14:51:52');
INSERT INTO `tb_spinfo` VALUES ('2002', '表', 'watch', '北京', '台', '20*20', '精装', '141', '827277', '无', '单面印', '', '', '2019-10-03 14:53:05');
INSERT INTO `tb_spinfo` VALUES ('2003', 'kh3', '小郑', '15757129711', '台', '1800*1920', '简装', '452524', '147728', '1', '天地翻', '', '', '2019-10-03 14:53:48');
INSERT INTO `tb_spinfo` VALUES ('2004', '手机', '米4', 'bei', 't', '15*10', '简装', '4527', '4524', '54', '国美电器', null, null, '2019-10-03 14:27:18');
INSERT INTO `tb_spinfo` VALUES ('2005', '笔记本', '笔记本电脑', '浙江', '台', '44*33', '精装', '4146', '654654987', '额', '京东商城', null, null, '2019-10-03 14:27:22');
INSERT INTO `tb_spinfo` VALUES ('2006', 'kh3', '小郑', '15757129711', '一车间', '普通', '默认', '左上', '无', '8色', '正背套', '备注', '郑卜毅', '2019-10-03 00:00:00');
INSERT INTO `tb_spinfo` VALUES ('2007', 'kh3', '小郑', '15757129711', '', '', '', '', '', '', '左右翻', '', '', '2019-10-03 00:00:00');
INSERT INTO `tb_spinfo` VALUES ('2008', 'kh2', '1', '1', '', '', '', '', '', '', '左右翻', '', '', '2019-10-03 14:44:29');

-- ----------------------------
-- Table structure for `tb_userlist`
-- ----------------------------
DROP TABLE IF EXISTS `tb_userlist`;
CREATE TABLE `tb_userlist` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `pass` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `quan` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of tb_userlist
-- ----------------------------
INSERT INTO `tb_userlist` VALUES ('宫帅辉', 'hui', '123456', '1');

-- ----------------------------
-- View structure for `v_sellview`
-- ----------------------------
DROP VIEW IF EXISTS `v_sellview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sellview` AS select `invocing`.`tb_sell_detail`.`sellID` AS `sellID`,`invocing`.`tb_spinfo`.`spname` AS `spname`,`invocing`.`tb_sell_detail`.`spid` AS `spid`,`invocing`.`tb_spinfo`.`gg` AS `gg`,`invocing`.`tb_sell_detail`.`dj` AS `dj`,`invocing`.`tb_sell_detail`.`sl` AS `sl`,`invocing`.`tb_sell_main`.`je` AS `je`,`invocing`.`tb_sell_main`.`khname` AS `khname`,`invocing`.`tb_sell_main`.`xsdate` AS `xsdate`,`invocing`.`tb_sell_main`.`czy` AS `czy`,`invocing`.`tb_sell_main`.`jsr` AS `jsr`,`invocing`.`tb_sell_main`.`jsfs` AS `jsfs` from ((`tb_sell_main` join `tb_sell_detail` on((`invocing`.`tb_sell_main`.`sellID` = `invocing`.`tb_sell_detail`.`sellID`))) join `tb_spinfo` on((`invocing`.`tb_sell_detail`.`spid` = `invocing`.`tb_spinfo`.`id`))) ;
