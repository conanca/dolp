/* 与 */
insert into SYSTEM_USER values(1,'1000','123','Admin','男',null,null,null);
insert into SYSTEM_USER values(2,'1001','123','Jack','男',24,'2010-6-1','1234567890');
insert into SYSTEM_USER values(3,'1002','123','Kate','女',23,'2010-6-1','1122334455');
insert into SYSTEM_USER values(4,'1003','123','John','男',21,'2010-6-1','1234567890');
insert into SYSTEM_ROLE values(1,'系统管理员','拥有系统设置的权限');
insert into SYSTEM_ROLE values(2,'普通用户','拥有全部业务功能的权限');
insert into SYSTEM_ROLE values(3,'经理','拥有酒店设置的权限');
insert into SYSTEM_ROLE values(4,'前台','拥有业务功能的权限');
insert into SYSTEM_USER_ROLE values(1,1,1);
insert into SYSTEM_USER_ROLE values(2,1,2);
insert into SYSTEM_USER_ROLE values(3,2,3);
insert into SYSTEM_USER_ROLE values(4,2,4);
insert into SYSTEM_USER_ROLE values(5,3,4);
insert into SYSTEM_MENU values(1,'系统管理','','系统管理与设置',null,0);
insert into SYSTEM_MENU values(2,'用户管理','system/user_manage.jsp','用户信息设置',1,1);
insert into SYSTEM_MENU values(3,'角色管理','system/role_manage.jsp','角色信息设置',1,1);
insert into SYSTEM_MENU values(4,'角色分配','system/role_assign.jsp','用户角色分配',1,1);
insert into SYSTEM_MENU values(5,'菜单管理','system/menu_manage.jsp','菜单信息设置',1,1);
insert into SYSTEM_MENU values(6,'演示页面','','',null,0);
insert into SYSTEM_MENU values(7,'格式化文本框','demo1.jsp','',6,1);
insert into SYSTEM_MENU values(8,'酒店设置','','',null,0);
insert into SYSTEM_MENU values(9,'OOXX','1.jsp','',8,1);
INSERT INTO PUBLIC.DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(33, STRINGDECODE('\u6807\u51c6\u95f4'), 150.00, STRINGDECODE('123123123123\u5230\u5e95\u8303\u5fb7\u8428\u8fc7\u5206\u7684\u662f\u89c4\u8303')),
(34, STRINGDECODE('\u5546\u52a1\u95f4'), 170.00, STRINGDECODE('\u53d1\u751f\u7684\u5171\u540c\u594b\u6597\u8fd8\u6709')),
(35, STRINGDECODE('\u8c6a\u534e\u5546\u52a1\u95f4'), 200.00, STRINGDECODE('\u90fd\u5e02\u80a1\u4efd\u5927\u6982\u662f\u70ed\u821e '));
INSERT INTO PUBLIC.DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
(1, '101', 33, 0),
(2, '202', 34, 1),
(34, '103', 33, 1),
(35, '102', 33, 0),
(66, '201', 34, 0),
(67, '203', 34, 0),
(68, '301', 35, 0),
(69, '302', 35, 0),
(70, '303', 35, 0),
(71, '401', 33, 0),
(72, '402', 33, 1),
(73, '403', 33, 0);