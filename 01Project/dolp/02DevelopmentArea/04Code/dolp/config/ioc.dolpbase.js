var ioc = {

	// 系统参数
	prop : {
		type : "com.dolplay.dolpbase.common.domain.DolpProperties",
		args : [
			{
				"TestProp":"123",
				"SystemName":"AAAA1",
				"Environment":"dev"
			}
		]
	},

	// 消息附件文件的文件池
	attachmentPool : {
		type : 'org.nutz.filepool.NutFilePool',
		// 文件保存在服务器的路径,个数无限
		args : [ "/dolpbase/file/attachment", 0 ]
	},
	// 上传消息附件的Context
	uploadAttachmentContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ { refer : 'attachmentPool' } ],
		fields : {
			// 是否忽略空文件, 默认为 false
			ignoreNull : true,
			// 单个文件最大尺寸(大约的值，单位为字节，即 1048576 为 1M)
			maxFileSize : 10485760,
			// 正则表达式匹配可以支持的文件名
			nameFilter : '^(.+[.])(gif|jpg|png|bmp|zip|rar|doc|docx|xls|xlsx|ppt|pptx|txt|html|pdf)$'
		}
	},
	// 上传消息附件的适配器
	attachmentUpload : {
		type : 'org.nutz.mvc.upload.UploadAdaptor',
		singleton : false,
		args : [ { refer : 'uploadAttachmentContext' } ]
	}
};