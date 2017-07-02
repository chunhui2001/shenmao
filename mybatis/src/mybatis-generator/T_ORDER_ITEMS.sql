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

 Date: 07/02/2017 23:33:31 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `T_ORDER_ITEMS`
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_ITEMS`;
CREATE TABLE `T_ORDER_ITEMS` (
  `ORDER_ID` bigint(20) NOT NULL,
  `ORDER_ITEM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL,
  `PRODUCT_NAME` varchar(65) NOT NULL,
  `PRODUCT_UNIT` varchar(8) NOT NULL,
  `PRICE` decimal(10,2) NOT NULL,
  `COUNT` int(11) NOT NULL,
  PRIMARY KEY (`ORDER_ITEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
