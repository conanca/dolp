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

INSERT INTO SYSTEM_USER_ROLE(ID ,USERID ,ROLEID) VALUES
(1,1,1),
(2,1,2),
(3,1,3),
(4,2,2),
(5,2,3),
(6,3,2),
(7,4,2);

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
(11,2,11),
(12,2,12),
(13,2,13),
(14,2,14),
(15,2,15),
(16,2,16),
(17,2,17),
(18,2,18),
(19,2,19);

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

INSERT INTO SYSTEM_SYSPARA(ID, NAME, VALUE, DESCRIPTION) VALUES
(1, 'SystemName', 'AAAA', null),
(2, 'MaxRightValue', '1001', null),
(3, 'DefaultPassword', '123', null);