/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100407
 Source Host           : 127.0.0.1:3306
 Source Schema         : template

 Target Server Type    : MySQL
 Target Server Version : 100407
 File Encoding         : 65001

 Date: 16/08/2022 22:13:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父ID[默认0]',
  `type` int NOT NULL DEFAULT 0 COMMENT '类型(0:菜单[默认] 1:菜单项 2:按钮)',
  `sort` int NOT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `permission_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `cache` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否缓存(0:否 1:是)',
  `components` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统管理', 0, 0, 1, 'setting', NULL, 'system', '/system', b'0', '@layout/index.vue', '2022-08-16 22:00:05', '2022-08-16 22:00:07');
INSERT INTO `sys_permission` VALUES (2, '用户管理', 1, 1, 2, 'document', 'user:list', 'user', '/system/user', b'0', '@views/system/user.vue', '2022-08-16 22:01:48', '2022-08-16 22:01:49');
INSERT INTO `sys_permission` VALUES (3, '用户新增', 2, 2, 2, 'document', 'user:insert', NULL, NULL, b'0', NULL, '2022-08-16 22:03:04', '2022-08-16 22:03:06');
INSERT INTO `sys_permission` VALUES (4, '用户删除', 2, 2, 2, 'document', 'user:delete', NULL, NULL, b'0', NULL, '2022-08-16 22:04:06', '2022-08-16 22:04:08');
INSERT INTO `sys_permission` VALUES (5, '用户更新', 2, 2, 2, 'document', 'user:update', NULL, NULL, b'0', NULL, '2022-08-16 22:04:48', '2022-08-16 22:04:51');
INSERT INTO `sys_permission` VALUES (6, '角色管理', 1, 1, 3, 'document', 'role:list', 'role', '/system/role', b'0', '@views/system/role.vue', '2022-08-16 22:06:04', '2022-08-16 22:06:06');
INSERT INTO `sys_permission` VALUES (7, '角色新增', 6, 2, 3, 'document', 'role:insert', NULL, NULL, b'0', NULL, '2022-08-16 22:06:52', '2022-08-16 22:06:55');
INSERT INTO `sys_permission` VALUES (8, '角色删除', 6, 2, 3, 'document', 'role:delete', NULL, NULL, b'0', NULL, '2022-08-16 22:07:26', '2022-08-16 22:07:29');
INSERT INTO `sys_permission` VALUES (9, '角色更新', 6, 2, 3, 'document', 'role:update', NULL, NULL, b'0', NULL, '2022-08-16 22:07:59', '2022-08-16 22:08:02');
INSERT INTO `sys_permission` VALUES (10, '菜单管理', 1, 1, 4, 'document', 'menu:list', 'menu', '/system/menu', b'0', '@views/system/menu.vue', '2022-08-16 22:08:59', '2022-08-16 22:09:01');
INSERT INTO `sys_permission` VALUES (11, '菜单新增', 10, 2, 4, 'document', 'menu:insert', NULL, NULL, b'0', NULL, '2022-08-16 22:09:33', '2022-08-16 22:09:34');
INSERT INTO `sys_permission` VALUES (12, '菜单删除', 10, 2, 4, 'document', 'menu:delete', NULL, NULL, b'0', NULL, '2022-08-16 22:10:08', '2022-08-16 22:10:10');
INSERT INTO `sys_permission` VALUES (13, '菜单更新', 10, 2, 4, 'document', 'menu:update', NULL, NULL, b'0', NULL, '2022-08-16 22:10:35', '2022-08-16 22:10:37');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `illustrate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '系统管理员，拥有最高权限。', '2022-08-16 21:57:10', '2022-08-16 21:57:12');
INSERT INTO `sys_role` VALUES (2, 'test', '拥有测试权限。', '2022-08-16 21:57:27', '2022-08-16 21:57:29');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色权限ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1, 2);
INSERT INTO `sys_role_permission` VALUES (3, 1, 3);
INSERT INTO `sys_role_permission` VALUES (4, 1, 4);
INSERT INTO `sys_role_permission` VALUES (5, 1, 5);
INSERT INTO `sys_role_permission` VALUES (6, 1, 6);
INSERT INTO `sys_role_permission` VALUES (7, 1, 7);
INSERT INTO `sys_role_permission` VALUES (8, 1, 8);
INSERT INTO `sys_role_permission` VALUES (9, 1, 9);
INSERT INTO `sys_role_permission` VALUES (10, 1, 10);
INSERT INTO `sys_role_permission` VALUES (11, 1, 11);
INSERT INTO `sys_role_permission` VALUES (12, 1, 12);
INSERT INTO `sys_role_permission` VALUES (13, 1, 13);
INSERT INTO `sys_role_permission` VALUES (14, 2, 1);
INSERT INTO `sys_role_permission` VALUES (15, 2, 2);
INSERT INTO `sys_role_permission` VALUES (16, 2, 6);
INSERT INTO `sys_role_permission` VALUES (17, 2, 10);
INSERT INTO `sys_role_permission` VALUES (18, 2, 11);
INSERT INTO `sys_role_permission` VALUES (19, 2, 12);
INSERT INTO `sys_role_permission` VALUES (20, 2, 13);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `gender` int NULL DEFAULT 0 COMMENT '用户性别(0:男(默认) 1:女 2:保密)',
  `Introduction` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `avatar_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '账户状态(0:不可用 1:可用(默认))',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', 'hetongxue', '何同学', '15226523413', '15226523413@163.com', 0, '暂无说明', '.....', '1', '2022-08-16 21:56:50', '2022-08-16 21:56:52');
INSERT INTO `sys_user` VALUES (2, 'test', '123456', 'test', '测试人员', '15664585916', '15664585916@163.com', 1, '暂无说明', '.....', '1', '2022-08-16 21:58:33', '2022-08-16 21:58:35');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
