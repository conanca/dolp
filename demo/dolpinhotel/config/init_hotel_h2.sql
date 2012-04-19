/* 插入hotel初始记录 */
INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION ,ISORGARELA,ORGANIZATIONID) VALUES
(1001,'酒店管理员','拥有酒店设置的权限',0,null),
(1002,'前台','拥有基本业务功能的权限',0,null);

INSERT INTO SYSTEM_USER_ROLE(USERID ,ROLEID) VALUES
(1,1001),
(1,1002),
(2,1002);

INSERT INTO SYSTEM_MENU(ID,NAME,URL,DESCRIPTION,LFT,RGT) VALUES
(1001,'酒店设置',NULL,NULL,501,700),
(1002,'房间类型设置','hotel/room_type_manage.html',NULL,502,503),
(1003,'房间设置','hotel/room_manage.html',NULL,504,505),
(1004,'经营管理',NULL,NULL,701,1000),
(1005,'可用房确认','hotel/available_room_check.html',NULL,702,703),
(1006,'入住情况','hotel/room_occupancy_manage.html',NULL,704,705),
(1007,'入住登记','hotel/check_in.html',NULL,706,707),
(1008,'账单管理','hotel/bill_manage.html',NULL,708,709),
(1009,'账单统计','hotel/bill_statistic.html',NULL,710,711);

INSERT INTO SYSTEM_ROLE_MENU(ROLEID ,MENUID) VALUES
(1001,1001),
(1001,1002),
(1001,1003),
(1002,1004),
(1002,1005),
(1002,1006),
(1002,1007),
(1002,1008),
(1002,1009);

INSERT INTO SYSTEM_PRIVILEGE(ID ,NAME ,DESCRIPTION ,MENUID,METHODPATH) VALUES
(1001,'查询',null,1002,'com.dolplay.dolpinhotel.setup.RoomTypeModule.getGridData'),
(1002,'修改',null,1002,'com.dolplay.dolpinhotel.setup.RoomTypeModule.editRow'),
(1003,'获取房间类型Map',null,1001,'com.dolplay.dolpinhotel.setup.RoomTypeModule.getAllRoomTypeMap'),
(1004,'查询',null,1003,'com.dolplay.dolpinhotel.setup.RoomModule.getJqgridData'),
(1005,'修改',null,1003,'com.dolplay.dolpinhotel.setup.RoomModule.editRow'),
(1006,'获取可用房间Map',null,1003,'com.dolplay.dolpinhotel.setup.RoomModule.getAllAvailableRoomForSelectOption'),
(1007,'所有房间Map',null,1003,'com.dolplay.dolpinhotel.setup.RoomModule.getAllRoomForSelectOption'),
(1008,'查询',null,1005,'com.dolplay.dolpinhotel.management.AvailableRoomCheckModule.getGridData'),
(1009,'查询',null,1008,'com.dolplay.dolpinhotel.management.BillModule.getGridData'),
(1010,'修改',null,1008,'com.dolplay.dolpinhotel.management.BillModule.editRow'),
(1011,'统计',null,1009,'com.dolplay.dolpinhotel.management.BillModule.statisticBill'),
(1012,'查询',null,1006,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.getGridData'),
(1013,'登记',null,1007,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.saveRoomOccupancy'),
(1014,'结帐',null,1006,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.checkOut'),
(1015,'查询顾客信息',null,1006,'com.dolplay.dolpinhotel.management.CustomerModule.getGridDataByRoomOccId');

INSERT INTO SYSTEM_ROLE_PRIVILEGE(ROLEID,PRIVILEGEID) VALUES
(1001,1001),
(1001,1002),
(1001,1003),
(1001,1004),
(1001,1005),
(1002,1006),
(1002,1007),
(1002,1008),
(1002,1009),
(1002,1010),
(1002,1011),
(1002,1012),
(1002,1013),
(1002,1014),
(1002,1015);

INSERT INTO DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(1, '标准间', 150.00, '标准两人间'),
(2, '商务间', 170.00, '大床房'),
(3, '豪华间', 200.00, '豪华大床房');

INSERT INTO DOLPINHOTEL_ROOM(ID, NUMBER, ROOMTYPEID, ISOCCUPANCY) VALUES
(1, '101', 1, false),
(2, '102', 1, false),
(3, '103', 1, false),
(4, '104', 1, false),
(5, '105', 1, false),
(6, '106', 1, false),
(7, '107', 1, false),
(8, '108', 1, false),
(9, '109', 1, false),
(10, '110', 1, false),
(11, '111', 1, false),
(12, '112', 1, false),
(13, '201', 2, false),
(14, '202', 2, false),
(15, '203', 2, false),
(16, '204', 2, false),
(17, '205', 2, false),
(18, '206', 2, false),
(19, '207', 2, false),
(20, '208', 2, false),
(21, '209', 2, false),
(22, '210', 2, false),
(23, '211', 2, false),
(24, '212', 2, false),
(25, '213', 2, false),
(26, '214', 2, false),
(27, '215', 2, false),
(28, '216', 2, false),
(29, '301', 3, false),
(30, '302', 3, false),
(31, '303', 3, false),
(32, '304', 3, false),
(33, '305', 3, false),
(34, '401', 1, false),
(35, '402', 1, false),
(36, '403', 1, false),
(37, '404', 1, false),
(38, '405', 1, false);

INSERT INTO DOLPINHOTEL_BILL(ID, NUMBER, AMOUNT, DATE) VALUES
(1, 'aa', 3431.00, TIMESTAMP '2011-01-25 00:00:00.0'),
(2, 'bb', 3411.00, TIMESTAMP '2011-01-29 00:00:00.0'),
(4, 'c1', 1132.00, TIMESTAMP '2011-02-03 00:00:00.0'),
(5, 'c2', 3541.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(6, 'c3', 6411.00, TIMESTAMP '2011-03-14 00:00:00.0'),
(7, 'c4', 3451.00, TIMESTAMP '2011-06-11 00:00:00.0'),
(8, 'c7', 2000.00, TIMESTAMP '2011-07-01 00:00:00.0');