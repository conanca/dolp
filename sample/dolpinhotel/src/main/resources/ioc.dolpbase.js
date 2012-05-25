var ioc = {

	// 系统参数
	prop : {
		type : "com.dolplay.dolpbase.common.domain.DolpProperties",
		args : [
			{
				"TestProp":"123",
				"SYSTEM_SYSTEMNAME":"Dolpin Hotel",
				"SYSTEM_ENVIRONMENT":"dev",
				// 启动时初始化Dolp数据表
				"SYSTEM_INITDOLPTABLES_ONSTART":true,
				// 启动Dolp调度任务
				"SYSTEM_DOLPSCHEDULER_RUN":false,
				// 是否检查入口方法存在于权限表中
				"SYSTEM_ISCHECK_METHOD":true
			}
		]
	},

	// 消息附件文件的文件池
	attachmentPool : {
		type : 'org.nutz.filepool.NutFilePool',
		// 文件保存在服务器的路径,个数无限
		args : [ "~/dolpinhotel/file/attachment", 0 ]
	},
	// 上传消息附件的Context
	uploadAttachmentContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ { refer : 'attachmentPool' } ],
		fields : {
			// 忽略空文件,(入口函数File参数将得到null)
			ignoreNull : true,
			// 单个文件最大尺寸为10M
			maxFileSize : 10485760,
			// 支持的文件名
			nameFilter : '^(.+[.])(gif|jpg|png|bmp|zip|rar|doc|docx|xls|xlsx|ppt|pptx|txt|html|pdf)$'
		}
	},
	// 上传消息附件的适配器
	attachmentUpload : {
		type : 'org.nutz.mvc.upload.UploadAdaptor',
		singleton : false,
		args : [ { refer : 'uploadAttachmentContext' } ]
	},
	
	// 临时文件的文件池
	tempFilePool : {
		type : 'org.nutz.filepool.NutFilePool',
		// 文件保存在服务器的路径,最大个数1000
		args : [ "~/dolpinhotel/file/temp", 1000 ]
	},
	// 上传临时文件的Context
	tempFileContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ { refer : 'tempFilePool' } ],
		fields : {
			// 忽略空文件,(入口函数File参数将得到null)
			ignoreNull : true,
			// 单个文件最大尺寸为10M
			maxFileSize : 10485760,
			// 支持的文件名
			nameFilter : '^(.+[.])(gif|jpg|png|bmp|zip|rar|doc|docx|xls|xlsx|ppt|pptx|txt|html|pdf)$'
		}
	},
	// 上传临时文件的适配器
	tempFileUpload : {
		type : 'org.nutz.mvc.upload.UploadAdaptor',
		singleton : false,
		args : [ { refer : 'tempFileContext' } ]
	}
};