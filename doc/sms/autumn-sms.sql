/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.250
Source Server Version : 50719
Source Host           : 192.168.1.250:3306
Source Database       : autumn-sms

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-07-09 12:59:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app_info`
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(50) NOT NULL,
  `servide_name` varchar(50) NOT NULL,
  `service_key` varchar(64) NOT NULL,
  `sort_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `remark` text,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_app_service_id` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_info
-- ----------------------------
INSERT INTO `app_info` VALUES ('3', 'service-demo', '贵农网', '03e477560fd64ada93c1fbebbe9821e3', '1', '3', '', '2018-01-12 17:42:13', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_info` VALUES ('4', 'A02', '供销好买网', 'c7b1a691563449999736da42d7b40f42', '2', '2', '', '2018-01-12 19:40:53', '2018-01-12 19:40:55', '', null);
INSERT INTO `app_info` VALUES ('5', 'gyb-integral-app', '积分商城APP', 'SJGSHDAGJSDHJKG', '1', '2', '', '2018-01-13 15:43:13', '2018-01-13 15:43:13', '', null);
INSERT INTO `app_info` VALUES ('6', 'gyb-integral-manager', '积分商城商家', 'SJGSHDAGJS234DHJKG', '1', '2', '', '2018-01-13 15:43:13', '2018-01-13 15:43:13', '', null);
INSERT INTO `app_info` VALUES ('7', 'autumn-member', '会员中心', 'SJGSHh3246234DHJKG', '1', '2', '', '2018-01-13 15:43:13', '2018-01-13 15:43:13', '', null);
INSERT INTO `app_info` VALUES ('8', 'gyb-integral-center', '贵阳银行积分中心', 'dfsdafsdaf', '1', '2', ' ', '2018-01-14 14:45:24', '2018-01-14 14:45:27', '', null);
INSERT INTO `app_info` VALUES ('9', 'autumn-log', '贵农网股金系统后台管理', '2c701a87662440fe832f8be5aa35fbc0', '4', '3', 'string11145', '2018-02-08 16:00:36', '2018-02-08 16:44:15', '', '2018-02-08 16:44:20');

-- ----------------------------
-- Table structure for `app_template`
-- ----------------------------
DROP TABLE IF EXISTS `app_template`;
CREATE TABLE `app_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` bigint(20) NOT NULL,
  `template_id` bigint(20) NOT NULL,
  `sort_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `remark` text,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_app_template_app_id` (`app_id`),
  KEY `ix_app_template_template_id` (`template_id`),
  KEY `ix_app_template_sort` (`sort_id`),
  CONSTRAINT `fk_app_template_info` FOREIGN KEY (`app_id`) REFERENCES `app_info` (`id`),
  CONSTRAINT `fk_app_template_st` FOREIGN KEY (`template_id`) REFERENCES `standard_template` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_template
-- ----------------------------
INSERT INTO `app_template` VALUES ('3', '3', '3', '1', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('4', '3', '4', '2', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('5', '3', '5', '3', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('6', '3', '6', '4', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('7', '3', '7', '5', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('8', '3', '8', '5', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('9', '3', '9', '6', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('10', '3', '10', '7', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('11', '3', '11', '8', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('12', '3', '12', '9', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('13', '3', '13', '10', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('14', '3', '14', '10', '3', 'ss', '2018-01-12 19:59:59', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('15', '4', '15', '1', '2', '', '2018-01-12 20:37:07', '2018-01-12 20:37:10', '', null);
INSERT INTO `app_template` VALUES ('16', '3', '16', '1', '3', '', '2018-01-12 20:38:28', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('17', '4', '16', '1', '2', '', '2018-01-12 20:38:54', '2018-01-12 20:38:56', '', null);
INSERT INTO `app_template` VALUES ('18', '3', '17', '1', '3', '', '2018-01-12 20:38:28', '2018-06-11 11:04:29', '', null);
INSERT INTO `app_template` VALUES ('19', '4', '17', '1', '2', '', '2018-01-12 20:38:28', '2018-01-12 20:38:31', '', null);
INSERT INTO `app_template` VALUES ('20', '4', '18', '2', '2', '', '2018-01-12 20:38:28', '2018-01-12 20:38:31', '', null);
INSERT INTO `app_template` VALUES ('21', '4', '3', '2', '2', '', '2018-01-13 13:03:22', '2018-01-13 13:03:25', '', null);
INSERT INTO `app_template` VALUES ('22', '4', '4', '2', '2', '', '2018-01-13 13:03:22', '2018-01-13 13:03:25', '', null);
INSERT INTO `app_template` VALUES ('23', '4', '5', '3', '2', '', '2018-01-13 13:03:22', '2018-01-13 13:03:25', '', null);
INSERT INTO `app_template` VALUES ('24', '4', '7', '3', '2', '', '2018-01-13 13:03:22', '2018-01-13 13:03:25', '', null);
INSERT INTO `app_template` VALUES ('25', '4', '8', '3', '2', '', '2018-01-13 13:03:22', '2018-01-13 13:03:25', '', null);
INSERT INTO `app_template` VALUES ('26', '5', '3', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('27', '5', '4', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('28', '5', '5', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('29', '5', '6', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('30', '5', '7', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('31', '5', '8', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('32', '5', '9', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('33', '5', '10', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('34', '5', '10', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('35', '5', '11', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('36', '5', '12', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('37', '5', '13', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('38', '5', '14', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('39', '5', '16', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('40', '5', '17', '5', '2', '积分商城APP', '2018-01-13 15:59:06', '2018-01-13 15:59:06', '', null);
INSERT INTO `app_template` VALUES ('41', '6', '3', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('42', '6', '4', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('43', '6', '5', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('44', '6', '6', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('45', '6', '7', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('46', '6', '8', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('47', '6', '9', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('48', '6', '10', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('49', '6', '10', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('50', '6', '11', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('51', '6', '12', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('52', '6', '13', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('53', '6', '14', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('54', '6', '16', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('55', '6', '17', '5', '2', '积分商城商家', '2018-01-13 16:00:34', '2018-01-13 16:00:34', '', null);
INSERT INTO `app_template` VALUES ('56', '7', '3', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('57', '7', '4', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('58', '7', '5', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('59', '7', '6', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('60', '7', '7', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('61', '7', '8', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('62', '7', '9', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('63', '7', '10', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('64', '7', '10', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('65', '7', '11', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('66', '7', '12', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('67', '7', '13', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('68', '7', '14', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('69', '7', '16', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('70', '7', '17', '5', '2', '会员中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('89', '8', '3', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('90', '8', '4', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('91', '8', '5', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('92', '8', '6', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('93', '8', '7', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('94', '8', '8', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('95', '8', '9', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('96', '8', '10', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('97', '8', '10', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('98', '8', '11', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('99', '8', '12', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('100', '8', '13', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('101', '8', '14', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('102', '8', '16', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);
INSERT INTO `app_template` VALUES ('103', '8', '17', '5', '2', '贵阳银行积分中心', '2018-01-13 16:01:48', '2018-01-13 16:01:48', '', null);

-- ----------------------------
-- Table structure for `app_template_platform`
-- ----------------------------
DROP TABLE IF EXISTS `app_template_platform`;
CREATE TABLE `app_template_platform` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auto_sort_id` int(11) NOT NULL,
  `app_template_id` bigint(20) NOT NULL,
  `platform_template_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_app_template_platform_sort` (`auto_sort_id`),
  KEY `ix_app_template_platform_template` (`app_template_id`),
  KEY `ix_app_template_platformd_platform` (`platform_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_template_platform
-- ----------------------------
INSERT INTO `app_template_platform` VALUES ('4', '2', '4', '4');
INSERT INTO `app_template_platform` VALUES ('5', '3', '5', '5');
INSERT INTO `app_template_platform` VALUES ('6', '4', '6', '6');
INSERT INTO `app_template_platform` VALUES ('7', '5', '7', '7');
INSERT INTO `app_template_platform` VALUES ('8', '6', '8', '8');
INSERT INTO `app_template_platform` VALUES ('9', '7', '9', '9');
INSERT INTO `app_template_platform` VALUES ('10', '8', '10', '10');
INSERT INTO `app_template_platform` VALUES ('11', '8', '11', '11');
INSERT INTO `app_template_platform` VALUES ('12', '8', '12', '12');
INSERT INTO `app_template_platform` VALUES ('13', '9', '13', '13');
INSERT INTO `app_template_platform` VALUES ('14', '9', '14', '14');
INSERT INTO `app_template_platform` VALUES ('15', '9', '16', '15');
INSERT INTO `app_template_platform` VALUES ('16', '6', '18', '16');
INSERT INTO `app_template_platform` VALUES ('17', '5', '21', '17');
INSERT INTO `app_template_platform` VALUES ('18', '5', '26', '3');
INSERT INTO `app_template_platform` VALUES ('19', '5', '27', '4');
INSERT INTO `app_template_platform` VALUES ('20', '5', '28', '5');
INSERT INTO `app_template_platform` VALUES ('21', '5', '29', '6');
INSERT INTO `app_template_platform` VALUES ('22', '5', '30', '7');
INSERT INTO `app_template_platform` VALUES ('23', '5', '31', '8');
INSERT INTO `app_template_platform` VALUES ('24', '5', '32', '9');
INSERT INTO `app_template_platform` VALUES ('25', '5', '33', '10');
INSERT INTO `app_template_platform` VALUES ('26', '5', '35', '11');
INSERT INTO `app_template_platform` VALUES ('27', '5', '36', '12');
INSERT INTO `app_template_platform` VALUES ('28', '5', '37', '13');
INSERT INTO `app_template_platform` VALUES ('29', '5', '38', '14');
INSERT INTO `app_template_platform` VALUES ('30', '5', '39', '15');
INSERT INTO `app_template_platform` VALUES ('31', '5', '40', '16');
INSERT INTO `app_template_platform` VALUES ('32', '5', '41', '3');
INSERT INTO `app_template_platform` VALUES ('33', '5', '42', '4');
INSERT INTO `app_template_platform` VALUES ('34', '5', '43', '5');
INSERT INTO `app_template_platform` VALUES ('35', '5', '44', '6');
INSERT INTO `app_template_platform` VALUES ('36', '5', '45', '7');
INSERT INTO `app_template_platform` VALUES ('37', '5', '46', '8');
INSERT INTO `app_template_platform` VALUES ('38', '5', '47', '9');
INSERT INTO `app_template_platform` VALUES ('39', '5', '48', '10');
INSERT INTO `app_template_platform` VALUES ('40', '5', '50', '11');
INSERT INTO `app_template_platform` VALUES ('41', '5', '51', '12');
INSERT INTO `app_template_platform` VALUES ('42', '5', '52', '13');
INSERT INTO `app_template_platform` VALUES ('43', '5', '53', '14');
INSERT INTO `app_template_platform` VALUES ('44', '5', '54', '15');
INSERT INTO `app_template_platform` VALUES ('45', '5', '55', '16');
INSERT INTO `app_template_platform` VALUES ('46', '5', '56', '3');
INSERT INTO `app_template_platform` VALUES ('47', '5', '57', '4');
INSERT INTO `app_template_platform` VALUES ('48', '5', '58', '5');
INSERT INTO `app_template_platform` VALUES ('49', '5', '59', '6');
INSERT INTO `app_template_platform` VALUES ('50', '5', '60', '7');
INSERT INTO `app_template_platform` VALUES ('51', '5', '61', '8');
INSERT INTO `app_template_platform` VALUES ('52', '5', '62', '9');
INSERT INTO `app_template_platform` VALUES ('53', '5', '64', '10');
INSERT INTO `app_template_platform` VALUES ('54', '5', '65', '11');
INSERT INTO `app_template_platform` VALUES ('55', '5', '66', '12');
INSERT INTO `app_template_platform` VALUES ('56', '5', '67', '13');
INSERT INTO `app_template_platform` VALUES ('57', '5', '68', '14');
INSERT INTO `app_template_platform` VALUES ('58', '5', '69', '15');
INSERT INTO `app_template_platform` VALUES ('59', '5', '70', '16');
INSERT INTO `app_template_platform` VALUES ('60', '5', '89', '3');
INSERT INTO `app_template_platform` VALUES ('61', '5', '90', '4');
INSERT INTO `app_template_platform` VALUES ('62', '5', '91', '5');
INSERT INTO `app_template_platform` VALUES ('63', '5', '92', '6');
INSERT INTO `app_template_platform` VALUES ('64', '5', '93', '7');
INSERT INTO `app_template_platform` VALUES ('65', '5', '94', '8');
INSERT INTO `app_template_platform` VALUES ('66', '5', '95', '9');
INSERT INTO `app_template_platform` VALUES ('67', '5', '96', '10');
INSERT INTO `app_template_platform` VALUES ('68', '5', '97', '11');
INSERT INTO `app_template_platform` VALUES ('69', '5', '98', '12');
INSERT INTO `app_template_platform` VALUES ('70', '5', '99', '13');
INSERT INTO `app_template_platform` VALUES ('71', '5', '100', '14');
INSERT INTO `app_template_platform` VALUES ('72', '5', '101', '15');
INSERT INTO `app_template_platform` VALUES ('73', '5', '102', '16');
INSERT INTO `app_template_platform` VALUES ('75', '1', '3', '3');

-- ----------------------------
-- Table structure for `platform_info`
-- ----------------------------
DROP TABLE IF EXISTS `platform_info`;
CREATE TABLE `platform_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `config_Info` text NOT NULL,
  `remark` text,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_platform_info_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platform_info
-- ----------------------------
INSERT INTO `platform_info` VALUES ('3', 'juhe', '聚合', '2', '{\"key\":\"2a274be7a12b5e50861d6443b051755e\",\"check_url\":\"http://v.juhe.cn/sms/black\",\"url\":\"http://v.juhe.cn/sms/send\"}', '', '2018-01-12 18:08:11', '2018-01-12 18:08:11', '', null);
INSERT INTO `platform_info` VALUES ('4', 'aliyun', '阿里云', '2', '{\"key\":\"23859675\",\"app_secret\":\"4fc3317b9314f25e8ce94955a6e15420\",\"url\":\"http://sms.market.alicloudapi.com\"}', '', '2018-01-12 18:09:04', '2018-01-12 18:09:04', '', null);
INSERT INTO `platform_info` VALUES ('5', '1212', '1212', '3', 'rer', 'rere', '2018-06-08 11:27:26', '2018-06-08 11:30:46', '', '2018-06-08 11:30:51');

-- ----------------------------
-- Table structure for `platform_template`
-- ----------------------------
DROP TABLE IF EXISTS `platform_template`;
CREATE TABLE `platform_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `platform_id` bigint(20) NOT NULL,
  `standard_template_id` bigint(20) NOT NULL,
  `sign_name` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `remark` text,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_platform_template_code` (`code`),
  KEY `ix_platform_template_name` (`name`),
  KEY `ix_platform_template_platform` (`platform_id`),
  KEY `ix_platform_template_standard` (`standard_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platform_template
-- ----------------------------
INSERT INTO `platform_template` VALUES ('3', '32370', '短信验证', '3', '3', '贵农网', '2', '', '2018-01-12 19:57:03', '2018-06-27 16:01:39', '', null);
INSERT INTO `platform_template` VALUES ('4', '31003', '银行卡绑定', '3', '4', '贵农网', '2', '', '2018-01-12 19:57:03', '2018-01-12 19:57:08', '', null);
INSERT INTO `platform_template` VALUES ('5', '37501', '银行卡绑定失败', '3', '5', '贵农网', '2', '', '2018-01-12 20:18:43', '2018-01-12 20:18:47', '', null);
INSERT INTO `platform_template` VALUES ('6', '11470', '创建订单', '3', '6', '贵农网', '2', '', '2018-01-12 20:18:48', '2018-01-12 20:18:51', '', null);
INSERT INTO `platform_template` VALUES ('7', '31007', '支付验证', '3', '7', '贵农网', '2', '', '2018-01-12 20:18:55', '2018-01-12 20:18:56', '', null);
INSERT INTO `platform_template` VALUES ('8', '37503', '支付失败', '3', '8', '贵农网', '2', '', '2018-01-12 20:18:58', '2018-01-12 20:19:00', '', null);
INSERT INTO `platform_template` VALUES ('9', '37503', '支付成功', '3', '9', '贵农网', '2', '', '2018-01-12 20:19:03', '2018-01-12 20:19:04', '', null);
INSERT INTO `platform_template` VALUES ('10', '11473', '物流配送', '3', '10', '贵农网', '2', '', '2018-01-12 20:19:06', '2018-01-12 20:19:08', '', null);
INSERT INTO `platform_template` VALUES ('11', '11474', '退款成功通知', '3', '11', '贵农网', '2', '', '2018-01-12 20:19:10', '2018-01-12 20:19:13', '', null);
INSERT INTO `platform_template` VALUES ('12', '11475', '重置密码', '3', '12', '贵农网', '2', '', '2018-01-12 20:19:14', '2018-01-12 20:19:16', '', null);
INSERT INTO `platform_template` VALUES ('13', '11476', '店铺审核通过', '3', '13', '贵农网', '2', '', '2018-01-12 20:19:18', '2018-01-12 20:19:20', '', null);
INSERT INTO `platform_template` VALUES ('14', '11477', '店铺开通', '3', '14', '贵农网', '2', '', '2018-01-12 20:19:22', '2018-01-12 20:19:25', '', null);
INSERT INTO `platform_template` VALUES ('15', '24139', '发货提醒(商家)', '3', '16', '贵农网', '2', '', '2018-01-12 20:19:29', '2018-01-12 20:19:31', '', null);
INSERT INTO `platform_template` VALUES ('16', '37508', '退款申请通知(商家)', '3', '17', '贵农网', '2', '', '2018-01-12 20:19:35', '2018-01-12 20:19:32', '', null);
INSERT INTO `platform_template` VALUES ('17', 'SMS_81505018', '注册验证码', '4', '3', '吕凌', '2', '', '2018-01-12 21:24:07', '2018-01-12 21:24:10', '', null);
INSERT INTO `platform_template` VALUES ('18', '12121', '测试模板', '3', '6', '测试', '1', '测试测试', '2018-02-08 10:21:34', '2018-06-08 14:59:01', '', '2018-06-14 15:36:41');
INSERT INTO `platform_template` VALUES ('19', '1', '212', '3', '3', '', '1', '12', '2018-06-14 15:36:27', '2018-06-14 15:36:27', '', '2018-06-14 15:36:34');

-- ----------------------------
-- Table structure for `platform_template_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `platform_template_parameter`;
CREATE TABLE `platform_template_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL,
  `auto_sort_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `friendly_name` varchar(50) NOT NULL,
  `standard_parameter_name` varchar(50) NOT NULL,
  `is_must` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_platform_template_parameter_template` (`template_id`),
  KEY `ix_platform_template_parameter_sort_id` (`auto_sort_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platform_template_parameter
-- ----------------------------
INSERT INTO `platform_template_parameter` VALUES ('3', '3', '1', '#checkCode#', '验证码', 'checkCode', '');
INSERT INTO `platform_template_parameter` VALUES ('4', '4', '1', '#checkCode#', '验证码', 'checkCode', '');
INSERT INTO `platform_template_parameter` VALUES ('5', '4', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('6', '6', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('7', '6', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('8', '7', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('9', '7', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('10', '7', '1', '#checkCode#', '验证码', 'checkCode', '');
INSERT INTO `platform_template_parameter` VALUES ('11', '8', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('12', '9', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('13', '9', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('14', '9', '1', '#Total#', '支付金额', 'Total', '');
INSERT INTO `platform_template_parameter` VALUES ('15', '10', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('16', '10', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('17', '10', '1', '#shippingNumber#', '物流单号', 'shippingNumber', '');
INSERT INTO `platform_template_parameter` VALUES ('18', '10', '1', '#shippingCompany#', '物流公司名称', 'shippingCompany', '');
INSERT INTO `platform_template_parameter` VALUES ('19', '11', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('20', '11', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('21', '11', '1', '#RefundMoney#', '退款金额', 'RefundMoney', '');
INSERT INTO `platform_template_parameter` VALUES ('22', '12', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('23', '13', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('24', '13', '1', '#shopName#', '店铺名称', 'shopName', '');
INSERT INTO `platform_template_parameter` VALUES ('25', '14', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('26', '14', '1', '#shopName#', '店铺名称', 'shopName', '');
INSERT INTO `platform_template_parameter` VALUES ('27', '15', '1', '#userName#', '用户名称', 'userName', '');
INSERT INTO `platform_template_parameter` VALUES ('28', '16', '1', '#orderId#', '订单编号', 'orderId', '');
INSERT INTO `platform_template_parameter` VALUES ('29', '16', '1', '#refundMoney#', '退款金额', 'refundMoney', '');
INSERT INTO `platform_template_parameter` VALUES ('30', '17', '1', 'no', '订单编号', 'checkCode', '');

-- ----------------------------
-- Table structure for `standard_template`
-- ----------------------------
DROP TABLE IF EXISTS `standard_template`;
CREATE TABLE `standard_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `is_restrict` bit(1) NOT NULL,
  `restrict_seconds` int(11) DEFAULT NULL,
  `is_verification_code` bit(1) NOT NULL,
  `verification_code_type` int(11) DEFAULT NULL,
  `verification_effective_seconds` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `remark` text,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_standard_template_code` (`code`),
  KEY `ix_standard_template_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of standard_template
-- ----------------------------
INSERT INTO `standard_template` VALUES ('3', '01', '短信验证', '', '60', '', '99', null, '2', '', '2018-01-12 18:19:36', '2018-01-12 18:19:36', '', null);
INSERT INTO `standard_template` VALUES ('4', '003', '银行卡绑定', '', '0', '', null, null, '2', '', '2018-01-12 18:26:27', '2018-01-12 18:26:27', '', null);
INSERT INTO `standard_template` VALUES ('5', '004', '银行卡绑定失败', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('6', '005', '创建订单', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('7', '006', '支付验证', '', '0', '', '99', null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('8', '007', '支付失败', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('9', '008', '支付成功', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('10', '009', '物流配送', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('11', '010', '退款成功通知', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('12', '011', '重置密码', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('13', '012', '店铺审核通过', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('14', '013', '店铺开通', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('15', '014', '新订单提醒(商家)', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('16', '001', '发货提醒(商家)', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('17', '002', '退款申请通知(商家)', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('18', '015', '退款成功(商家)', '', '0', '', null, null, '2', '', '2018-01-12 18:27:26', '2018-01-12 18:27:29', '', null);
INSERT INTO `standard_template` VALUES ('19', '120', '模板1', '', '120', '', '99', '120', '3', '模板1', '2018-02-07 11:26:28', '2018-02-07 14:07:36', '', '2018-02-07 14:07:47');

-- ----------------------------
-- Table structure for `standard_template_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `standard_template_parameter`;
CREATE TABLE `standard_template_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` bigint(20) NOT NULL,
  `auto_sort_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `friendly_name` varchar(50) NOT NULL,
  `is_must` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_standard_template_parameter_template` (`template_id`),
  KEY `ix_standard_template_parameter_sort` (`auto_sort_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of standard_template_parameter
-- ----------------------------
INSERT INTO `standard_template_parameter` VALUES ('1', '3', '0', 'checkCode', '验证码', '');
INSERT INTO `standard_template_parameter` VALUES ('2', '3', '0', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('3', '3', '0', 'operation', '操作', '');
INSERT INTO `standard_template_parameter` VALUES ('4', '4', '1', 'checkCode', '验证码', '');
INSERT INTO `standard_template_parameter` VALUES ('5', '4', '1', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('6', '6', '1', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('7', '6', '1', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('8', '7', '2', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('9', '7', '2', 'checkCode', '验证码', '');
INSERT INTO `standard_template_parameter` VALUES ('10', '7', '2', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('11', '8', '3', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('12', '9', '4', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('13', '9', '4', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('14', '9', '4', 'Total', '支付金额', '');
INSERT INTO `standard_template_parameter` VALUES ('15', '10', '5', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('16', '10', '5', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('17', '10', '5', 'shippingCompany', '物流公司名称', '');
INSERT INTO `standard_template_parameter` VALUES ('18', '10', '5', 'shippingNumber', '物流单号', '');
INSERT INTO `standard_template_parameter` VALUES ('19', '11', '6', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('20', '11', '6', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('21', '11', '6', 'RefundMoney', '退款金额', '');
INSERT INTO `standard_template_parameter` VALUES ('22', '12', '7', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('23', '13', '7', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('24', '13', '7', 'shopName', '店铺名称', '');
INSERT INTO `standard_template_parameter` VALUES ('25', '14', '7', 'userName', '用户名称', '');
INSERT INTO `standard_template_parameter` VALUES ('26', '14', '7', 'shopName', '店铺名称', '');
INSERT INTO `standard_template_parameter` VALUES ('27', '15', '8', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('28', '16', '8', 'name', '商家名称', '');
INSERT INTO `standard_template_parameter` VALUES ('29', '16', '8', 'Total', '付款金额', '');
INSERT INTO `standard_template_parameter` VALUES ('30', '16', '8', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('31', '17', '9', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('32', '17', '9', 'RefundMoney', '退款金额', '');
INSERT INTO `standard_template_parameter` VALUES ('33', '18', '9', 'orderId', '订单编号', '');
INSERT INTO `standard_template_parameter` VALUES ('34', '18', '9', 'RefundMoney', '退款金额', '');

-- ----------------------------
-- View structure for `view_app_platform_template`
-- ----------------------------
DROP VIEW IF EXISTS `view_app_platform_template`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dev`@`%` SQL SECURITY DEFINER VIEW `view_app_platform_template` AS select `a`.`id` AS `id`,`a`.`app_id` AS `app_id`,`a`.`template_id` AS `template_id`,`a`.`sort_id` AS `sort_id`,`b`.`auto_sort_id` AS `auto_sort_id`,`a`.`status` AS `status`,`a`.`remark` AS `remark`,`a`.`gmt_create` AS `gmt_create`,`a`.`gmt_modified` AS `gmt_modified`,`a`.`is_delete` AS `is_delete`,`a`.`gmt_delete` AS `gmt_delete`,`d`.`code` AS `platform_code`,`d`.`name` AS `platform_name`,`d`.`config_Info` AS `platform_config_info`,`d`.`status` AS `platform_status`,`d`.`is_delete` AS `platform_is_delete`,`c`.`id` AS `platform_template_id`,`c`.`code` AS `platform_template_code`,`c`.`name` AS `platform_template_name`,`c`.`sign_name` AS `platform_template_sign_name`,`c`.`status` AS `platform_template_status`,`c`.`is_delete` AS `platform_template_is_delete` from (((`app_template` `a` join `app_template_platform` `b` on((`b`.`app_template_id` = `a`.`id`))) join `platform_template` `c` on((`b`.`platform_template_id` = `c`.`id`))) join `platform_info` `d` on((`c`.`platform_id` = `d`.`id`))) ;

-- ----------------------------
-- View structure for `view_app_platform_template_parameter`
-- ----------------------------
DROP VIEW IF EXISTS `view_app_platform_template_parameter`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dev`@`%` SQL SECURITY DEFINER VIEW `view_app_platform_template_parameter` AS select `a`.`id` AS `id`,`a`.`app_id` AS `app_id`,`a`.`template_id` AS `template_id`,`a`.`sort_id` AS `sort_id`,`b`.`auto_sort_id` AS `auto_sort_id`,`a`.`status` AS `status`,`a`.`remark` AS `remark`,`a`.`gmt_create` AS `gmt_create`,`a`.`gmt_modified` AS `gmt_modified`,`a`.`is_delete` AS `is_delete`,`a`.`gmt_delete` AS `gmt_delete`,`d`.`status` AS `platform_status`,`d`.`is_delete` AS `platform_is_delete`,`c`.`id` AS `platform_template_id`,`c`.`status` AS `platform_template_status`,`c`.`is_delete` AS `platform_template_is_delete`,`e`.`auto_sort_id` AS `platform_template_parameter_auto_sort_id`,`e`.`name` AS `platform_template_parameter_name`,`e`.`friendly_name` AS `platform_template_parameter_friendly_name`,`e`.`standard_parameter_name` AS `platform_template_parameter_standard_name`,`e`.`is_must` AS `platform_template_parameter_is_must` from ((((`app_template` `a` join `app_template_platform` `b` on((`b`.`app_template_id` = `a`.`id`))) join `platform_template` `c` on((`b`.`platform_template_id` = `c`.`id`))) join `platform_info` `d` on((`c`.`platform_id` = `d`.`id`))) join `platform_template_parameter` `e` on((`c`.`id` = `e`.`template_id`))) ;

-- ----------------------------
-- View structure for `view_app_standard_template`
-- ----------------------------
DROP VIEW IF EXISTS `view_app_standard_template`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dev`@`%` SQL SECURITY DEFINER VIEW `view_app_standard_template` AS select `a`.`id` AS `id`,`a`.`app_id` AS `app_id`,`a`.`template_id` AS `template_id`,`a`.`sort_id` AS `sort_id`,`a`.`status` AS `status`,`a`.`is_delete` AS `is_delete`,`a`.`remark` AS `remark`,`a`.`gmt_create` AS `gmt_create`,`a`.`gmt_modified` AS `gmt_modified`,`a`.`gmt_delete` AS `gmt_delete`,`b`.`code` AS `template_code`,`b`.`name` AS `template_name`,`b`.`status` AS `template_status`,`b`.`is_delete` AS `template_is_delete`,`b`.`is_restrict` AS `is_restrict`,`b`.`restrict_seconds` AS `restrict_seconds`,`b`.`is_verification_code` AS `is_verification_code`,`b`.`verification_code_type` AS `verification_code_type` from (`app_template` `a` join `standard_template` `b` on((`a`.`template_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `view_app_standard_template_parameter`
-- ----------------------------
DROP VIEW IF EXISTS `view_app_standard_template_parameter`;
CREATE ALGORITHM=UNDEFINED DEFINER=`dev`@`%` SQL SECURITY DEFINER VIEW `view_app_standard_template_parameter` AS select `a`.`id` AS `id`,`a`.`app_id` AS `app_id`,`a`.`template_id` AS `template_id`,`a`.`sort_id` AS `sort_id`,`a`.`status` AS `status`,`a`.`is_delete` AS `is_delete`,`a`.`remark` AS `remark`,`a`.`gmt_create` AS `gmt_create`,`a`.`gmt_modified` AS `gmt_modified`,`a`.`gmt_delete` AS `gmt_delete`,`b`.`status` AS `template_status`,`b`.`is_delete` AS `template_is_delete`,`c`.`auto_sort_id` AS `template_parameter_sort_id`,`c`.`name` AS `template_parameter_name`,`c`.`friendly_name` AS `template_parameter_friendly_name`,`c`.`is_must` AS `template_parameter_is_must` from ((`app_template` `a` join `standard_template` `b` on((`a`.`template_id` = `b`.`id`))) join `standard_template_parameter` `c` on((`c`.`template_id` = `b`.`id`))) ;
