/* 插入初始记录 */
INSERT INTO SYSTEM_USER(ID, NUMBER, PASSWORD, NAME, GENDER, AGE, BIRTHDAY, PHONE) VALUES
(1,'1000','123','Admin','01',NULL,NULL,NULL),
(2,'1001','123','Jack','01',24,'2010-6-1','1234567890'),
(3,'1002','123','Kate','02',23,'2010-6-1','1122334455'),
(4,'1003','123','John','01',21,'2010-6-1','1234567890');

INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION) VALUES
(1,'系统管理员','拥有系统设置的权限'),
(2,'普通用户','拥有全部业务功能的权限'),
(3,'经理','拥有酒店设置的权限'),
(4,'前台','拥有业务功能的权限');

INSERT INTO SYSTEM_USER_ROLE(ID ,USERID ,ROLEID) VALUES
(1,1,1),
(2,1,2),
(3,2,3),
(4,2,4),
(5,3,4);

INSERT INTO PUBLIC.DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(1, '标准间', 150.00, '标准两人间'),
(2, '商务间', 170.00, '大床房'),
(3, '豪华间', 200.00, '豪华大床房');

INSERT INTO PUBLIC.DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
(1, '101', 1, 0),
(2, '202', 2, 0),
(3, '103', 1, 0),
(4, '102', 1, 0),
(5, '201', 2, 0),
(6, '203', 2, 0),
(7, '301', 3, 0),
(8, '302', 3, 0),
(9, '303', 3, 0),
(10, '401', 1, 0),
(11, '402', 1, 0),
(12, '403', 1, 0);

INSERT INTO SYSTEM_SYSENUM(ID,NAME,DESCRIPTION) VALUES
(1,'certificateType','证件类型'), 
(2,'gender','性别');

INSERT INTO SYSTEM_SYSENUMITEM(ID,TEXT,VALUE,SYSENUMID ) VALUES
(1,'身份证','01',1),
(2,'护照','02',1),
(3,'军官证','03',1),
(4,'男','01',2),
(5,'女','02',2);

INSERT INTO SYSTEM_MENU(ID,NAME,URL,DESCRIPTION,LFT,RGT) VALUES
(1,'系统管理',NULL,NULL,1,20),
(2,'用户管理','system/user_manage.html',NULL,2,3),
(3,'角色管理','system/role_manage.html',NULL,4,5),
(4,'用户角色分配','system/role_assign.html',NULL,6,7),
(5,'菜单管理','system/menu_manage.html',NULL,8,9),
(6,'角色可见菜单分配','system/menu_assign.html',NULL,10,11),
(7,'枚举管理','system/sysenum_mange.html',NULL,12,13),
(8,'演示页面',NULL,NULL,21,40),
(9,'格式化文本框','demo1.html',NULL,22,23),
(10,'酒店设置',NULL,NULL,51,70),
(11,'房间类型设置','hotel/room_type_manage.html',NULL,52,53),
(12,'房间设置','hotel/room_manage.html',NULL,54,55),
(13,'经营管理',NULL,NULL,71,100),
(14,'可用房确认','hotel/available_room_check.html',NULL,72,73),
(15,'入住情况','hotel/room_occupancy_manage.html',NULL,74,75),
(16,'入住登记','hotel/check_in.html',NULL,76,77),
(17,'账单管理','hotel/bill_manage.html',NULL,78,79);