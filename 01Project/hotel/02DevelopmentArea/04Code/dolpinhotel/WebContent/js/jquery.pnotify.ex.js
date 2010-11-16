//jquery.pnotify.min.js的自定义扩展
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
var opts = {
	pnotify_addclass: "stack-bottomright",
	pnotify_stack: stack_bottomright
};
$.extend({
	addMessage : function(userData) {
		var infoMessages = userData.infoMessages;
		var warnMessages = userData.warnMessages;
		var errorMessages = userData.errorMessages;
		if (infoMessages) {
			opts.pnotify_title = "消息";
			$.each(infoMessages, function(k,infoMessage) {
				opts.pnotify_text = infoMessage;
				$.pnotify(opts);
	        });
		}
		if (warnMessages) {
			opts.pnotify_title = "警告";
			opts.pnotify_type = "error";
			opts.pnotify_error_icon = 'ui-icon-notice';
			$.each(warnMessages, function(k,warnMessage) {
				opts.pnotify_text = warnMessage;
				$.pnotify(opts);
	        });
		}
		if (errorMessages) {
			opts.pnotify_title = "错误";
			opts.pnotify_type = "error";
			$.each(errorMessages, function(k,errorMessage) {
				opts.pnotify_text = errorMessage;
				$.pnotify(opts);
	        });
		}
	},
	addMessageStr : function(infoMessage,warnMessage,errorMessage) {
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