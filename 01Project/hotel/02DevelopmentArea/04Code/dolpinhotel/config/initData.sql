/* 插入初始记录 */
INSERT INTO SYSTEM_USER(ID, NUMBER, PASSWORD, NAME, GENDER, AGE, BIRTHDAY, PHONE) VALUES
(1,'1000','123','Admin','01',NULL,NULL,NULL),
(2,'1001','123','Jack','01',24,'2010-6-1','1234567890'),
(3,'1002','123','Kate','02',23,'2010-6-1','1122334455'),
(4,'1003','123','John','01',21,'2010-6-1','1234567890');

INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION ,ISORGARELA,ORGANIZATIONID) VALUES
(1,'系统管理员','拥有系统管理的权限',0,null),
(2,'普通用户','拥有个人设置、消息管理等权限',0,null),
(3,'酒店管理员','拥有酒店设置的权限',0,null),
(4,'前台','拥有基本业务功能的权限',0,null),
(5,'财务人员',null,1,9);

INSERT INTO SYSTEM_USER_ROLE(ID ,USERID ,ROLEID) VALUES
(1,1,1),
(2,1,2),
(3,1,3),
(4,1,4),
(5,1,5),
(6,2,2),
(7,2,3),
(8,2,4),
(9,3,2),
(10,3,4),
(11,4,2);

INSERT INTO SYSTEM_MENU(ID,NAME,URL,DESCRIPTION,LFT,RGT) VALUES
(1,'系统管理',NULL,NULL,1,200),
(2,'用户管理','system/user_manage.html',NULL,2,3),
(3,'角色管理','system/role_manage.html',NULL,4,5),
(4,'菜单管理','system/menu_manage.html',NULL,6,7),
(5,'权限管理','system/privilege_manage.html',NULL,8,9),
(6,'机构管理','system/organization_manage.html',NULL,10,11),
(7,'岗位管理','system/post_manage.html',NULL,12,13),
(8,'枚举管理','system/sysenum_mange.html',NULL,14,15),
(9,'系统参数管理','system/syspara_manage.html',NULL,16,17),
(10,'消息管理',NULL,NULL,201,300),
(11,'写新消息','system/messagebox_new.html',NULL,202,203),
(12,'收件箱','system/messagebox_inbox.html',NULL,204,205),
(13,'已发消息','system/messagebox_sent.html',NULL,206,207),
(14,'草稿','system/messagebox_draft.html',NULL,208,209),
(15,'演示页面',NULL,NULL,301,400),
(16,'格式化文本框','demo1.html',NULL,302,303),
(17,'酒店设置',NULL,NULL,501,700),
(18,'房间类型设置','hotel/room_type_manage.html',NULL,502,503),
(19,'房间设置','hotel/room_manage.html',NULL,504,505),
(20,'经营管理',NULL,NULL,701,1000),
(21,'可用房确认','hotel/available_room_check.html',NULL,702,703),
(22,'入住情况','hotel/room_occupancy_manage.html',NULL,704,705),
(23,'入住登记','hotel/check_in.html',NULL,706,707),
(24,'账单管理','hotel/bill_manage.html',NULL,708,709),
(25,'账单统计','hotel/bill_statistic.html',NULL,710,711);

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
(10,2,10),
(11,2,11),
(12,2,12),
(13,2,13),
(14,2,14),
(15,2,15),
(16,2,16),
(17,3,17),
(18,3,18),
(19,3,19),
(20,4,20),
(21,4,21),
(22,4,22),
(23,4,23),
(24,4,24),
(25,4,25);

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

INSERT INTO SYSTEM_MESSAGE(ID,SENDERUSERID,DATE,TITLE,CONTENT,STATE ) VALUES
(1,1,TIMESTAMP '2011-01-25 00:00:00.0','Test title1','test content','1'),
(2,2,TIMESTAMP '2011-01-25 00:00:00.0','Test title2','test content','1'),
(3,2,TIMESTAMP '2011-01-25 00:00:00.0','Test title222','test content','1');

INSERT INTO SYSTEM_MESSAGE_RECEIVERUSER(ID,MESSAGEID,USERID,ISREAD) VALUES
(1,1,1,1),
(2,2,1,1),
(3,3,1,1);

INSERT INTO DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(1, '标准间', 150.00, '标准两人间'),
(2, '商务间', 170.00, '大床房'),
(3, '豪华间', 200.00, '豪华大床房');

INSERT INTO SYSTEM_SYSPARA(ID, NAME, VALUE, DESCRIPTION) VALUES
(1, 'SystemName', 'AAAA', null);

INSERT INTO DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
(1, '101', 1, 0),
(2, '102', 1, 0),
(3, '103', 1, 0),
(4, '104', 1, 0),
(5, '105', 1, 0),
(6, '106', 1, 0),
(7, '107', 1, 0),
(8, '108', 1, 0),
(9, '109', 1, 0),
(10, '110', 1, 0),
(11, '111', 1, 0),
(12, '112', 1, 0),
(13, '201', 2, 0),
(14, '202', 2, 0),
(15, '203', 2, 0),
(16, '204', 2, 0),
(17, '205', 2, 0),
(18, '206', 2, 0),
(19, '207', 2, 0),
(20, '208', 2, 0),
(21, '209', 2, 0),
(22, '210', 2, 0),
(23, '211', 2, 0),
(24, '212', 2, 0),
(25, '213', 2, 0),
(26, '214', 2, 0),
(27, '215', 2, 0),
(28, '216', 2, 0),
(29, '301', 3, 0),
(30, '302', 3, 0),
(31, '303', 3, 0),
(32, '304', 3, 0),
(33, '305', 3, 0),
(34, '401', 1, 0),
(35, '402', 1, 0),
(36, '403', 1, 0),
(37, '404', 1, 0),
(38, '405', 1, 0);

INSERT INTO DOLPINHOTEL_BILL(ID, NUMBER, AMOUNT, DATE) VALUES
(1, 'aa', 3431.00, TIMESTAMP '2011-01-25 00:00:00.0'),
(2, 'bb', 3411.00, TIMESTAMP '2011-01-29 00:00:00.0'),
(4, 'c1', 1132.00, TIMESTAMP '2011-02-03 00:00:00.0'),
(5, 'c2', 3541.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(6, 'c3', 6411.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(7, 'c4', 3451.00, TIMESTAMP '2011-06-11 00:00:00.0'),
(8, 'c7', 2000.00, TIMESTAMP '2011-07-01 00:00:00.0');