/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.250
Source Server Version : 50719
Source Host           : 192.168.1.250:3306
Source Database       : autumn_auth

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-07-09 13:01:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `autumn_auth_resources`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_auth_resources`;
CREATE TABLE `autumn_auth_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `authority` varchar(100) NOT NULL,
  `remark` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_auth_resources_name` (`name`),
  KEY `ix_autumn_auth_resources_authority` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_auth_resources
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_role`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_role`;
CREATE TABLE `autumn_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `remark` text NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_role_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_role
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_role_authority`;
CREATE TABLE `autumn_role_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `resources_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_role_authority_role_id` (`role_id`),
  KEY `ix_autumn_role_authority_resources_id` (`resources_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_role_authority
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_user`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user`;
CREATE TABLE `autumn_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `nick_name` varchar(64) NOT NULL,
  `mobile_phone` varchar(20) NOT NULL,
  `mail_address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `head_image_path` varchar(255) NOT NULL,
  `is_account_non_expired` bit(1) NOT NULL,
  `status_message` varchar(255) NOT NULL,
  `is_account_non_locked` bit(1) NOT NULL,
  `is_credentials_non_expired` bit(1) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `gmt_delete` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_user_name` (`user_name`),
  KEY `ix_autumn_user_mobile_phone` (`mobile_phone`),
  KEY `ix_autumn_user_mail_address` (`mail_address`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user
-- ----------------------------
INSERT INTO `autumn_user` VALUES ('1', 'user', '测试', '13639089699', 'ycg-893@163.com', '$2a$10$M4MIpxBwky.eMR.EGWX7fe2YRT4Ww6kkbcn5NRKZ8Ehpdb4XQHxuG', ' ', '', ' ', '', '', '', '2018-04-12 16:13:43', '2018-04-12 16:13:46', '', null);

-- ----------------------------
-- Table structure for `autumn_user_authority`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user_authority`;
CREATE TABLE `autumn_user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `resources_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_authority_user_id` (`user_id`),
  KEY `ix_autumn_user_authority_resources_id` (`resources_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user_authority
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_user_login_binding`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user_login_binding`;
CREATE TABLE `autumn_user_login_binding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `client_type` varchar(20) NOT NULL,
  `client_equipment` varchar(255) NOT NULL,
  `login_token` varchar(255) NOT NULL,
  `login_token_validity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_login_binding_user_id` (`user_id`),
  KEY `ix_autumn_user_login_binding_client_type` (`client_type`),
  KEY `ix_autumn_user_login_binding_client_equipment` (`client_equipment`),
  KEY `ix_autumn_user_login_binding_login_token` (`login_token`),
  KEY `ix_autumn_user_login_binding_login_token_validity` (`login_token_validity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user_login_binding
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_user_login_logger`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user_login_logger`;
CREATE TABLE `autumn_user_login_logger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `login_ip_address` varchar(64) NOT NULL,
  `login_type` varchar(20) NOT NULL,
  `client_type` varchar(20) NOT NULL,
  `is_success` bit(1) NOT NULL,
  `status_message` varchar(255) NOT NULL,
  `client_info` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_login_logger_user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user_login_logger
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_user_login_provider`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user_login_provider`;
CREATE TABLE `autumn_user_login_provider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `login_provider` varchar(255) NOT NULL,
  `provider_key` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_login_provider_user_id` (`user_id`),
  KEY `ix_autumn_user_login_provider_login_provider` (`login_provider`),
  KEY `ix_autumn_user_login_provider_provider_key` (`provider_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user_login_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `autumn_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `autumn_user_role`;
CREATE TABLE `autumn_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_autumn_user_role_user_id` (`user_id`),
  KEY `ix_autumn_user_role_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of autumn_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` text,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` text,
  `authorities` text,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('autumn-oauth2-deom-client', 'autumn-oauth2-deom-client', 'secret', 'app', 'authorization_code,client_credentials,refresh_token,password,implicit', null, null, null, null, null, 'true');
