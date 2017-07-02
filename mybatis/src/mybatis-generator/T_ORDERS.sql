/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : shenmao

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 07/02/2017 23:33:24 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `T_ORDERS`
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDERS`;
CREATE TABLE `T_ORDERS` (
  `ORDER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `TOTAL_MONEY` decimal(10,0) NOT NULL,
  `CREATED_AT` datetime NOT NULL,
  `ORDER_STATUS` varchar(15) NOT NULL,
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
