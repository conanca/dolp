//jquery.pnotify.min.js的自定义扩展
$.extend({
	addMessage : function(userData) {
		var infoMessage = userData.infoMessage;
		var warnMessage = userData.warnMessage;
		var errorMessage = userData.errorMessage;
		var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
		var opts = {
				pnotify_addclass: "stack-bottomright",
				pnotify_stack: stack_bottomright
		};
		
		if (infoMessage) {
			opts.pnotify_title = "消息";
			opts.pnotify_text = infoMessage;
			$.pnotify(opts);
		}
		if (warnMessage) {
			opts.pnotify_title = "警告";
			opts.pnotify_text = warnMessage;
			opts.pnotify_type = "error";
			pnotify_error_icon: 'ui-icon-notice';
			$.pnotify(opts);
		}
		if (errorMessage) {
			opts.pnotify_title = "错误";
			opts.pnotify_text = errorMessage;
			opts.pnotify_type = "error";
			$.pnotify(opts);
		}
	},
	addMessageStr : function(infoMessage,warnMessage,errorMessage) {
		var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
		var opts = {
				pnotify_addclass: "stack-bottomright",
				pnotify_stack: stack_bottomright
		};
		
		if (infoMessage) {
			opts.pnotify_title = "消息";
			opts.pnotify_text = infoMessage;
			$.pnotify(opts);
		}
		if (warnMessage) {
			opts.pnotify_title = "警告";
			opts.pnotify_text = warnMessage;
			opts.pnotify_type = "error";
			pnotify_error_icon: 'ui-icon-notice';
			$.pnotify(opts);
		}
		if (errorMessage) {
			opts.pnotify_title = "错误";
			opts.pnotify_text = errorMessage;
			opts.pnotify_type = "error";
			$.pnotify(opts);
		}
	}
});