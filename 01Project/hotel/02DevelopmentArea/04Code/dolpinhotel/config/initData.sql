/* 插入初始记录 */
INSERT INTO SYSTEM_USER(ID, NUMBER, PASSWORD, NAME, GENDER, AGE, BIRTHDAY, PHONE) VALUES
(1,'1000','123','Admin','男',NULL,NULL,NULL),
(2,'1001','123','Jack','男',24,'2010-6-1','1234567890'),
(3,'1002','123','Kate','女',23,'2010-6-1','1122334455'),
(4,'1003','123','John','男',21,'2010-6-1','1234567890');

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
(33, '标准间', 150.00, '标准两人间'),
(34, '商务间', 170.00, '大床房'),
(35, '豪华间', 200.00, '豪华大床房');

INSERT INTO PUBLIC.DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
(1, '101', 33, 0),
(2, '202', 34, 0),
(34, '103', 33, 0),
(35, '102', 33, 0),
(66, '201', 34, 0),
(67, '203', 34, 0),
(68, '301', 35, 0),
(69, '302', 35, 0),
(70, '303', 35, 0),
(71, '401', 33, 0),
(72, '402', 33, 0),
(73, '403', 33, 0);