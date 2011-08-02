/* 插入hotel初始记录 */
INSERT INTO SYSTEM_ROLE(ID ,NAME ,DESCRIPTION ,ISORGARELA,ORGANIZATIONID) VALUES
(4,'酒店管理员','拥有酒店设置的权限',0,null),
(5,'前台','拥有基本业务功能的权限',0,null);

INSERT INTO SYSTEM_USER_ROLE(USERID ,ROLEID) VALUES
(1,4),
(1,5);

INSERT INTO SYSTEM_MENU(ID,NAME,URL,DESCRIPTION,LFT,RGT) VALUES
(20,'酒店设置',NULL,NULL,501,700),
(21,'房间类型设置','hotel/room_type_manage.html',NULL,502,503),
(22,'房间设置','hotel/room_manage.html',NULL,504,505),
(23,'经营管理',NULL,NULL,701,1000),
(24,'可用房确认','hotel/available_room_check.html',NULL,702,703),
(25,'入住情况','hotel/room_occupancy_manage.html',NULL,704,705),
(26,'入住登记','hotel/check_in.html',NULL,706,707),
(27,'账单管理','hotel/bill_manage.html',NULL,708,709),
(28,'账单统计','hotel/bill_statistic.html',NULL,710,711);

INSERT INTO SYSTEM_ROLE_MENU(ROLEID ,MENUID) VALUES
(4,20),
(4,21),
(4,22),
(5,23),
(5,24),
(5,25),
(5,26),
(5,27),
(5,28);

INSERT INTO SYSTEM_PRIVILEGE(ID ,NAME ,DESCRIPTION ,MENUID,METHODPATH) VALUES
(101,'查询',null,21,'com.dolplay.dolpinhotel.setup.RoomTypeModule.getGridData'),
(102,'修改',null,21,'com.dolplay.dolpinhotel.setup.RoomTypeModule.editRow'),
(103,'获取房间类型Map',null,20,'com.dolplay.dolpinhotel.setup.RoomTypeModule.getAllRoomTypeMap'),
(104,'查询',null,22,'com.dolplay.dolpinhotel.setup.RoomModule.getJqgridData'),
(105,'修改',null,22,'com.dolplay.dolpinhotel.setup.RoomModule.editRow'),
(106,'获取可用房间Map',null,22,'com.dolplay.dolpinhotel.setup.RoomModule.getAllAvailableRoomForSelectOption'),
(107,'所有房间Map',null,22,'com.dolplay.dolpinhotel.setup.RoomModule.getAllRoomForSelectOption'),
(108,'查询',null,24,'com.dolplay.dolpinhotel.management.AvailableRoomCheckModule.getGridData'),
(109,'查询',null,27,'com.dolplay.dolpinhotel.management.BillModule.getGridData'),
(110,'修改',null,27,'com.dolplay.dolpinhotel.management.BillModule.editRow'),
(111,'统计',null,28,'com.dolplay.dolpinhotel.management.BillModule.statisticBill'),
(112,'查询',null,25,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.getGridData'),
(113,'登记',null,26,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.saveRoomOccupancy'),
(114,'结帐',null,25,'com.dolplay.dolpinhotel.management.RoomOccupancyModule.checkOut'),
(115,'查询顾客信息',null,25,'com.dolplay.dolpinhotel.management.CustomerModule.getGridDataByRoomOccId');

INSERT INTO SYSTEM_ROLE_PRIVILEGE(ROLEID,PRIVILEGEID) VALUES
(2,101),
(2,102),
(2,103),
(2,104),
(2,105),
(2,106),
(2,107),
(2,108),
(2,109),
(2,110),
(2,111),
(2,112),
(2,113),
(2,114),
(2,115);

INSERT INTO DOLPINHOTEL_ROOMTYPE(ID, NAME, PRICE, DESCRIPTION) VALUES
(1, '标准间', 150.00, '标准两人间'),
(2, '商务间', 170.00, '大床房'),
(3, '豪华间', 200.00, '豪华大床房');

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