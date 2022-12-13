/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : rental_car

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 13/12/2022 16:56:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '轿车、SUV等',
  `brand` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌',
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '型号',
  `color` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `in_price` double NULL DEFAULT NULL COMMENT '购买价格',
  `out_price` double NULL DEFAULT NULL COMMENT '出租价格',
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆所在地点',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆描述',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车辆图片',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '1：未出租；2：已出租',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (73, '桂C 88888', '轿车', '宝马', '320Li', '红色', 329888, 400, '桂林', '操控好', 'upload/car/1669875014753.png', 1);
INSERT INTO `car` VALUES (74, '桂B 66666', 'MPV', '别克', 'GL8', '棕色', 266666, 344, '柳州', '空间大，适用于商务', 'upload/car/1669875285296.png', 1);
INSERT INTO `car` VALUES (75, '桂C 22222', '轿车', '大众', '宝来', '白色', 88000, 120, '桂林', '适合年轻人', 'upload/car/1669875534814.png', 2);
INSERT INTO `car` VALUES (76, '桂C 55555', '轿车', '大众', '迈腾', '黑色', 186666, 240, '桂林', '配置高', 'upload/car/1669875601809.png', 1);
INSERT INTO `car` VALUES (77, '桂A 33333', '轿车', '大众', '速腾', '白色', 136777, 180, '南宁', '好开', 'upload/car/1669875665991.png', 2);
INSERT INTO `car` VALUES (78, '桂A 56666', 'SUV', '现代', 'IX35', '白色', 158888, 260, '南宁', '空间大', 'upload/car/1669875745372.png', 2);
INSERT INTO `car` VALUES (79, '桂A 88888', 'SUV', '英菲尼迪', 'QX50', '灰色', 246666, 370, '南宁', '好开', 'upload/car/1669875812567.png', 1);
INSERT INTO `car` VALUES (81, '桂A 55667', '轿车', '大众', '速腾', '白色', 138888, 130, '南宁', '好开省油', 'upload/car/1670251863307.png', 1);
INSERT INTO `car` VALUES (82, '桂A 77766', '轿车', '大众', '宝来', '棕色', 88888, 140, '南宁', '好开', 'upload/car/1670823906556.png', 1);

-- ----------------------------
-- Table structure for checks
-- ----------------------------
DROP TABLE IF EXISTS `checks`;
CREATE TABLE `checks`  (
  `check_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检查单号',
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `time` datetime NULL DEFAULT NULL COMMENT '检查时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `pay` double NULL DEFAULT NULL COMMENT '赔付金额',
  PRIMARY KEY (`check_id`) USING BTREE,
  INDEX `toIndent`(`order_id`) USING BTREE,
  CONSTRAINT `toIndent` FOREIGN KEY (`order_id`) REFERENCES `indent` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of checks
-- ----------------------------
INSERT INTO `checks` VALUES ('I20221205T221950732', 'I20221205T221946070', '2022-12-05 22:19:51', '剐蹭', 200);
INSERT INTO `checks` VALUES ('I20221206T133323486', 'I20221206T133142289', '2022-12-06 13:33:23', '无', 0);
INSERT INTO `checks` VALUES ('I20221206T133609613', 'I20221206T133604438', '2022-12-06 13:36:10', '剐蹭', 100);
INSERT INTO `checks` VALUES ('I20221206T133826952', 'I20221206T133821544', '2022-12-06 13:38:27', '无', 0);
INSERT INTO `checks` VALUES ('I20221206T134111083', 'I20221206T133948248', '2022-12-06 13:41:11', '轮胎有问题', 500);
INSERT INTO `checks` VALUES ('I20221212T154323372', 'I20221206T140104992', '2022-12-12 15:43:23', NULL, NULL);
INSERT INTO `checks` VALUES ('I20221212T195245217', 'I20221206T135206888', '2022-12-12 19:52:45', '无', 0);

-- ----------------------------
-- Table structure for indent
-- ----------------------------
DROP TABLE IF EXISTS `indent`;
CREATE TABLE `indent`  (
  `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车牌号',
  `identity` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `price` double NULL DEFAULT NULL COMMENT '出租价格',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '订单状态 1:租赁中 3:还车检查 3:完成',
  `start_time` datetime NULL DEFAULT NULL COMMENT '起租时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `toID`(`identity`) USING BTREE,
  CONSTRAINT `toID` FOREIGN KEY (`identity`) REFERENCES `user` (`identity`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of indent
-- ----------------------------
INSERT INTO `indent` VALUES ('I20221205T221946070', '桂C 22222', '879825199909298762', 800, 3, '2022-12-01 09:00:00', '2022-12-05 09:00:00', '2022-12-01 22:19:46');
INSERT INTO `indent` VALUES ('I20221206T133142289', '桂C 55555', '879825199909298762', 1440, 3, '2022-12-01 09:00:00', '2022-12-04 09:00:00', '2022-12-01 13:31:42');
INSERT INTO `indent` VALUES ('I20221206T133604438', '桂A 56666', '879825199909298762', 620, 3, '2022-12-06 09:00:00', '2022-12-08 09:00:00', '2022-12-06 13:36:04');
INSERT INTO `indent` VALUES ('I20221206T133821544', '桂C 55555', '879825199909298762', 480, 3, '2022-12-08 09:00:00', '2022-12-10 09:00:00', '2022-12-06 13:38:22');
INSERT INTO `indent` VALUES ('I20221206T133948248', '桂A 55667', '879825199909298762', 2320, 3, '2022-11-23 09:00:00', '2022-11-26 09:00:00', '2022-11-23 13:39:48');
INSERT INTO `indent` VALUES ('I20221206T135206888', '桂C 88888', '450881200009189122', 2800, 3, '2022-12-06 09:00:00', '2022-12-09 09:00:00', '2022-12-06 13:52:07');
INSERT INTO `indent` VALUES ('I20221206T135906183', '桂C 22222', '450881200009189122', 240, 4, '2022-12-20 09:00:00', '2022-12-22 09:00:00', '2022-12-06 13:59:06');
INSERT INTO `indent` VALUES ('I20221206T140104992', '桂A 56666', '879825199909298762', 520, 2, '2022-12-06 09:00:00', '2022-12-08 09:00:00', '2022-12-06 14:01:05');
INSERT INTO `indent` VALUES ('I20221212T154409059', '桂C 22222', '879825199909298762', 480, 1, '2022-12-12 09:00:00', '2022-12-16 09:00:00', '2022-12-12 15:44:09');
INSERT INTO `indent` VALUES ('I20221212T195825047', '桂A 33333', '450881200009189122', 720, 1, '2022-12-12 09:00:00', '2022-12-16 09:00:00', '2022-12-12 19:58:25');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `identity` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` tinyint(1) NULL DEFAULT NULL COMMENT '0：超级管理员；1：管理员；2：普通用户',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `identity`(`identity`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '18607825529', '男', '450889200208279124', 'upload/Icon/1670250847742.jpg', NULL, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMCIsImV4cCI6MTY3MTAwNjEwNiwidXNlcm5hbWUiOiJhZG1pbiJ9.1q53Vgk2qLsRXAukkb534n0HISEbid4tNGxIAGXgy68');
INSERT INTO `user` VALUES (2, 'lucy', '670b14728ad9902aecba32e22fa4f6bd', '露西', '18629572862', '女', '401234199812010192', NULL, 1, NULL);
INSERT INTO `user` VALUES (45, 'Owens', '670b14728ad9902aecba32e22fa4f6bd', '苏云熙', '13121817012', '女', '771987200102079972', NULL, 2, NULL);
INSERT INTO `user` VALUES (49, 'Foster', '670b14728ad9902aecba32e22fa4f6bd', '酷酷', '19897217349', '女', '450881200912849812', NULL, 2, NULL);
INSERT INTO `user` VALUES (51, 'Hernandez', '670b14728ad9902aecba32e22fa4f6bd', '龙震南', '15342884086', '男', '450881200009204176', NULL, 2, NULL);
INSERT INTO `user` VALUES (75, 'zeka', '670b14728ad9902aecba32e22fa4f6bd', 'zeka', '13788764093', '男', '450897201208128764', NULL, 2, NULL);
INSERT INTO `user` VALUES (78, 'guilike', '670b14728ad9902aecba32e22fa4f6bd', '运运', '19834829374', '男', '450992200209149872', NULL, 2, NULL);
INSERT INTO `user` VALUES (79, 'iplkcc', '670b14728ad9902aecba32e22fa4f6bd', '桂桂', '19283918293', '男', '879825199909298762', 'upload/Icon/1669899344526.jpg', 2, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMiIsImV4cCI6MTY3MDkxNzI3OSwidXNlcm5hbWUiOiJpcGxrY2MifQ.ZcRU2h4f8V69FA47C967XIwapbeRrgB1IXSDL6cBq_c');
INSERT INTO `user` VALUES (80, 'yeki', '670b14728ad9902aecba32e22fa4f6bd', '叶七', '13981209389', '女', '450881200009189122', 'upload/Icon/1670826374297.jpg', 2, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMiIsImV4cCI6MTY3MDkzMjMzMiwidXNlcm5hbWUiOiJ5ZWtpIn0.xE3lbUed4-qNXlJ7q0aAG5_Pc-uvIVKruz0bOWoAN1c');
INSERT INTO `user` VALUES (81, 'fafa', 'ef3cd4e707ec0268a020cc4f214daaa8', '花花', '13981920389', '女', '334923200110109128', NULL, 2, NULL);
INSERT INTO `user` VALUES (82, 'gugu', '670b14728ad9902aecba32e22fa4f6bd', '咕咕', '19823475734', '女', '341378200204188321', NULL, 2, 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiMiIsImV4cCI6MTY3MDQ3NjQ4NywidXNlcm5hbWUiOiJndWd1In0.LyXu_2PGjNdD3qS7gj6Ij1kVq--0QvvgjXebKorHqrM');

SET FOREIGN_KEY_CHECKS = 1;
