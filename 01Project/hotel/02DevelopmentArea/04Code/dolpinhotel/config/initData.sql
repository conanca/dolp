/* 插入初始记录 */
INSERT INTO SYSTEM_USER(ID, NUMBER, PASSWORD, NAME, GENDER, AGE, BIRTHDAY, PHONE) VALUES
(1,'1000','123','Admin','01',NULL,NULL,NULL),
(2,'1001','123','Jack','01',24,'2010-6-1','1234567890'),
(3,'1002','123','Kate','02',23,'2010-6-1','1122334455'),
(4,'1003','123','John','01',21,'2010-6-1','1234567890');

INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION ,ISORGARELA,ORGANIZATIONID) VALUES
(1,'系统管理员','拥有系统设置的权限',0,null),
(2,'普通用户','拥有全部业务功能的权限',0,null),
(3,'经理','拥有酒店设置的权限',0,null),
(4,'前台','拥有基本业务功能的权限',0,null),
(5,'财务人员',null,1,9);

INSERT INTO SYSTEM_USER_ROLE(ID ,USERID ,ROLEID) VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,3,4),
(5,4,4),
(6,4,5);

INSERT INTO SYSTEM_MENU(ID,NAME,URL,DESCRIPTION,LFT,RGT) VALUES
(1,'系统管理',NULL,NULL,1,20),
(2,'用户管理','system/user_manage.html',NULL,2,3),
(3,'角色管理','system/role_manage.html',NULL,4,5),
(4,'菜单管理','system/menu_manage.html',NULL,6,7),
(5,'权限管理','system/privilege_manage.html',NULL,8,9),
(6,'机构管理','system/organization_manage.html',NULL,10,11),
(7,'岗位管理','system/post_manage.html',NULL,12,13),
(8,'枚举管理','system/sysenum_mange.html',NULL,14,15),
(9,'系统参数管理','system/syspara_manage.html',NULL,16,17),
(10,'演示页面',NULL,NULL,21,40),
(11,'格式化文本框','demo1.html',NULL,22,23),
(12,'酒店设置',NULL,NULL,51,70),
(13,'房间类型设置','hotel/room_type_manage.html',NULL,52,53),
(14,'房间设置','hotel/room_manage.html',NULL,54,55),
(15,'经营管理',NULL,NULL,71,100),
(16,'可用房确认','hotel/available_room_check.html',NULL,72,73),
(17,'入住情况','hotel/room_occupancy_manage.html',NULL,74,75),
(18,'入住登记','hotel/check_in.html',NULL,76,77),
(19,'账单管理','hotel/bill_manage.html',NULL,78,79),
(20,'账单统计','hotel/bill_statistic.html',NULL,80,81);

INSERT INTO SYSTEM_ROLE_MENU(ID ,ROLEID ,MENUID) VALUES
(1,1,1),
(2,1,2),
(3,1,3),
(4,1,4),
(5,1,5),
(6,1,6),
(7,1,7),
(8,1,8),
(9,1,9),
(10,1,10),
(11,1,11),
(12,1,12),
(13,1,13),
(14,1,14),
(15,1,15),
(16,1,16),
(17,1,17),
(18,1,18),
(19,1,19),
(20,1,20),
(21,2,10),
(22,2,11),
(23,2,12),
(24,2,13),
(25,2,14),
(26,2,15),
(27,2,16),
(28,2,17),
(29,2,18),
(30,2,19),
(31,2,20),
(32,3,12),
(33,3,13),
(34,3,14),
(35,3,15),
(36,3,16),
(37,3,17),
(38,3,18),
(39,3,19),
(40,3,20),
(41,4,15),
(42,4,16),
(43,4,17),
(44,4,18),
(45,4,19),
(46,4,20);

INSERT INTO SYSTEM_ORGANIZATION(ID ,CODE ,NAME ,DESCRIPTION ,PARENTORGID) VALUES
(1,'0100','综合行政部','',0),
(2,'0200','人力资源部','',0),
(3,'0300','研发部','',0),
(4,'0400','实施部','',0),
(5,'0500','运营部','',0),
(6,'0401','实施1组','',4),
(7,'0402','实施2组','',4),
(8,'0301','产品组','',3),
(9,'0301','财务部','',0);

INSERT INTO SYSTEM_PRIVILEGE(ID ,NAME ,DESCRIPTION ,MENUID,METHODPATH) VALUES
(1,'查询',null,2,null),
(2,'删除/修改/查询',null,2,null),
(3,'查询',null,3,null),
(4,'删除/修改/查询',null,3,null);

INSERT INTO SYSTEM_ROLE_PRIVILEGE(ID,ROLEID,PRIVILEGEID) VALUES
(1,1,1),
(2,1,2),
(3,2,3),
(4,2,4);

INSERT INTO SYSTEM_SYSENUM(ID,NAME,DESCRIPTION) VALUES
(1,'certificateType','证件类型'), 
(2,'gender','性别');

INSERT INTO SYSTEM_SYSENUMITEM(ID,TEXT,VALUE,SYSENUMID ) VALUES
(1,'身份证','01',1),
(2,'护照','02',1),
(3,'军官证','03',1),
(4,'男','01',2),
(5,'女','02',2);

INSERT INTO DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(1, '标准间', 150.00, '标准两人间'),
(2, '商务间', 170.00, '大床房'),
(3, '豪华间', 200.00, '豪华大床房');

INSERT INTO SYSTEM_SYSPARA(ID, NAME, VALUE, DESCRIPTION) VALUES
(1, 'SystemName', 'AAAA', null);

INSERT INTO DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
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

INSERT INTO DOLPINHOTEL_BILL(ID, NUMBER, AMOUNT, DATE) VALUES
(1, 'aa', 3431.00, TIMESTAMP '2011-01-25 00:00:00.0'),
(2, 'bb', 3411.00, TIMESTAMP '2011-01-29 00:00:00.0'),
(4, 'c1', 1132.00, TIMESTAMP '2011-02-03 00:00:00.0'),
(5, 'c2', 3541.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(6, 'c3', 6411.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(7, 'c4', 3451.00, TIMESTAMP '2011-06-11 00:00:00.0'),
(8, 'c7', 2000.00, TIMESTAMP '2011-07-01 00:00:00.0');