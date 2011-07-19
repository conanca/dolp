/* 插入系统初始记录 */
INSERT INTO SYSTEM_USER(ID, NUMBER, PASSWORD, NAME, GENDER, AGE, BIRTHDAY, PHONE) VALUES
(1,'1000','123','Admin','01',NULL,NULL,NULL),
(2,'1001','123','Jack','01',24,'2010-6-1','1234567890'),
(3,'1002','123','Kate','02',23,'2010-6-1','1122334455'),
(4,'1003','123','John','01',21,'2010-6-1','1234567890');

INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION ,ISORGARELA,ORGANIZATIONID) VALUES
(1,'系统管理员','拥有系统管理的权限',false,null),
(2,'普通用户','拥有个人设置、消息管理等权限',false,null),
(3,'财务人员',null,true,9);

INSERT INTO SYSTEM_USER_ROLE(USERID ,ROLEID) VALUES
(1,1),
(1,2),
(1,3),
(2,2),
(2,3),
(3,2),
(4,2);

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
(10,'在线终端管理','system/client.html',NULL,18,19),
(11,'个人设置',NULL,NULL,201,300),
(12,'消息管理',NULL,NULL,202,250),
(13,'写新消息','system/messagebox_new.html',NULL,203,204),
(14,'收件箱','system/messagebox_inbox.html',NULL,205,206),
(15,'已发消息','system/messagebox_sent.html',NULL,207,208),
(16,'草稿','system/messagebox_draft.html',NULL,209,210),
(17,'修改密码','system/change_pwd.html',NULL,251,252),
(18,'演示页面',NULL,NULL,301,400),
(19,'格式化文本框','demo1.html',NULL,302,303);

INSERT INTO SYSTEM_ROLE_MENU(ROLEID ,MENUID) VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(2,11),
(2,12),
(2,13),
(2,14),
(2,15),
(2,16),
(2,17),
(2,18),
(2,19);

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
(1,'查询',null,10,'gs.dolp.system.module.ClientModule.getGridData'),
(2,'踢出',null,10,'gs.dolp.system.module.ClientModule.kickOff'),
(3,'查询',null,4,'gs.dolp.system.module.MenuModule.getGridData'),
(4,'修改',null,4,'gs.dolp.system.module.MenuModule.editRow'),
(5,'添加父菜单',null,4,'gs.dolp.system.module.MenuModule.addParentMenu'),
(6,'获取树节点(菜单管理)',null,4,'gs.dolp.system.module.MenuModule.getNodes'),
(7,'获取树节点(角色管理)',null,3,'gs.dolp.system.module.MenuModule.getPrivilegeNodesByRoleId'),
(8,'显示菜单',null,0,'gs.dolp.system.module.MenuModule.dispMenu'),
(9,'查询收件箱',null,14,'gs.dolp.system.module.MessageModule.getInboxGridData'),
(10,'查询发件箱',null,15,'gs.dolp.system.module.MessageModule.getSentboxGridData'),
(11,'查询草稿箱',null,16,'gs.dolp.system.module.MessageModule.getDraftboxGridData'),
(12,'发信',null,12,'gs.dolp.system.module.MessageModule.sendMessage'),
(13,'保存草稿',null,13,'gs.dolp.system.module.MessageModule.saveMessage'),
(14,'删除已收',null,14,'gs.dolp.system.module.MessageModule.deleteReceivedMessage'),
(15,'删除已发',null,15,'gs.dolp.system.module.MessageModule.deleteSentMessage'),
(16,'删除草稿',null,16,'gs.dolp.system.module.MessageModule.deleteDraftMessage'),
(18,'获取收件人账户编号',null,16,'gs.dolp.system.module.MessageModule.getReceiverUserNum'),
(19,'查询',null,6,'gs.dolp.system.module.OrganizationModule.getGridData'),
(20,'修改',null,6,'gs.dolp.system.module.OrganizationModule.editRow'),
(21,'获取树节点',null,6,'gs.dolp.system.module.OrganizationModule.getNodes'),
(22,'查询',null,5,'gs.dolp.system.module.PrivilegeModule.getGridData'),
(23,'修改',null,5,'gs.dolp.system.module.PrivilegeModule.editRow'),
(24,'查询',null,3,'gs.dolp.system.module.RoleModule.getGridData'),
(25,'修改',null,3,'gs.dolp.system.module.RoleModule.editRow'),
(26,'分配权限',null,3,'gs.dolp.system.module.RoleModule.assignPrivilege'),
(27,'获取ID-角色名的键值对',null,2,'gs.dolp.system.module.RoleModule.getAllRoleMap'),
(28,'获取指定用户岗位',null,3,'gs.dolp.system.module.RoleModule.getUserPostGridData'),
(29,'查询枚举',null,8,'gs.dolp.system.module.SysEnumModule.getSysEnumGridData'),
(30,'查询枚举细目',null,8,'gs.dolp.system.module.SysEnumModule.getSysEnumItemGridData'),
(31,'修改枚举',null,8,'gs.dolp.system.module.SysEnumModule.editSysEnum'),
(32,'修改枚举细目',null,8,'gs.dolp.system.module.SysEnumModule.editSysEnumItem'),
(33,'获取ID-枚举名的键值对',null,0,'gs.dolp.system.module.SysEnumModule.getSysEnumItemMap'),
(34,'查询',null,9,'gs.dolp.system.module.SysParaModule.getGridData'),
(35,'修改',null,9,'gs.dolp.system.module.SysParaModule.editRow'),
(36,'查询',null,2,'gs.dolp.system.module.UserModule.getGridData'),
(37,'获取新用户编号',null,2,'gs.dolp.system.module.UserModule.getNewUserNumber'),
(38,'修改',null,2,'gs.dolp.system.module.UserModule.editRow'),
(39,'分配角色',null,2,'gs.dolp.system.module.UserModule.assignRole'),
(40,'取该用户角色ID',null,2,'gs.dolp.system.module.UserModule.getCurrentRoleIDs'),
(41,'分配岗位',null,2,'gs.dolp.system.module.UserModule.assignPost'),
(42,'修改该用户密码',null,17,'gs.dolp.system.module.UserModule.changeCurrentUserPassword'),
(43,'修改用户密码',null,2,'gs.dolp.system.module.UserModule.changeUserPassword');

INSERT INTO SYSTEM_ROLE_PRIVILEGE(ROLEID,PRIVILEGEID) VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,19),
(1,20),
(1,21),
(1,22),
(1,23),
(1,24),
(1,25),
(1,26),
(1,27),
(1,28),
(1,29),
(1,30),
(1,31),
(1,32),
(1,34),
(1,35),
(1,36),
(1,37),
(1,38),
(1,39),
(1,40),
(1,41),
(1,43),
(2,9),
(2,10),
(2,11),
(2,12),
(2,13),
(2,14),
(2,15),
(2,16),
(2,17),
(2,18),
(2,42),
(2,8),
(2,33);

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

INSERT INTO SYSTEM_MESSAGE_RECEIVERUSER(MESSAGEID,USERID) VALUES
(1,1),
(2,1),
(3,1);

INSERT INTO SYSTEM_SYSPARA(ID, NAME, VALUE, DESCRIPTION) VALUES
(1, 'SystemName', 'AAAA', null),
(2, 'MaxRightValue', '1001', null),
(3, 'DefaultPassword', '123', null);