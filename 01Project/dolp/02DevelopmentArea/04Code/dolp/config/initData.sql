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
insert into SYSTEM_MENU values(1,'系统管理','','系统管理与设置',null,null);
insert into SYSTEM_MENU values(2,'用户管理','system/user_manage.jsp','系统管理与设置',null,1);
insert into SYSTEM_MENU values(3,'角色管理','system/role_manage.jsp','',null,1);
insert into SYSTEM_MENU values(4,'角色分配','system/role_assign.jsp','',null,1);
insert into SYSTEM_MENU values(5,'菜单管理','system/menu_manage.jsp','',null,1);
insert into SYSTEM_MENU values(6,'演示页面','','',null,null);
insert into SYSTEM_MENU values(7,'格式化文本框','demo1.jsp','',null,6);
insert into SYSTEM_MENU values(8,'酒店设置','','',null,null);
insert into SYSTEM_MENU values(9,'OOXX','1.jsp','',null,8);