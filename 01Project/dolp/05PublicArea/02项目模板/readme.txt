对以下js和css文件做过修改
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
jquery.ui.datepicker-zh-CN.js 修改如下
/* Chinese initialisation for the jQuery UI date picker plugin. */
/* Written by Cloudream (cloudream@gmail.com). */
jQuery(function($){
	$.datepicker.regional['zh-CN'] = {
		changeMonth: true,
		changeYear: true,
		showOn: 'both',
		buttonImage: 'images/commons/calendar.gif',
		closeText: '关闭',
		prevText: '&#x3c;上月',
		nextText: '下月&#x3e;',
		currentText: '今天',
		monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '年'};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
jquery-ui-1.8.2.custom.css 439行

.ui-datepicker select.ui-datepicker-month-year {width: 60%;}
.ui-datepicker select.ui-datepicker-month, 
.ui-datepicker select.ui-datepicker-year { width: 40%;}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
jquery.meio.mask.js 144行

'date-us'			: { mask : '19/39/9999' },
'date-cn'			: { mask : '9999-19-39' },
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

jquery.selectsubcategory.js 82-84行
				   success:function(data){
					   $('#'+o.subcategoryid).find('option').remove().end();
					   $.each(data,function(text,value){
							var newopt='<option value="'+value+'">'+text+'</option>';					
							$('#'+o.subcategoryid).append(newopt);
					   });
					}

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
jquery.pnotify.default.css增加：
/*----------------------------------*/

/* Custom styled notice CSS */

/* This one is done through code, to show how it is done. Look down
			   at the stack_bottomright variable in the JavaScript below. */
.ui-pnotify.stack-bottomright {
	/* These are just CSS default values to reset the pnotify CSS. */
	right: auto;
	top: auto;
}