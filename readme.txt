������js��css�ļ������޸�

jquery.ui.datepicker-zh-CN.js �޸�����
/* Chinese initialisation for the jQuery UI date picker plugin. */
/* Written by Cloudream (cloudream@gmail.com). */
jQuery(function($){
	$.datepicker.regional['zh-CN'] = {
		changeMonth: true,
		changeYear: true,
		showOn: 'both',
		buttonImage: 'images/commons/calendar.gif',
		closeText: '�ر�',
		prevText: '&#x3c;����',
		nextText: '����&#x3e;',
		currentText: '����',
		monthNames: ['һ��','����','����','����','����','����',
		'����','����','����','ʮ��','ʮһ��','ʮ����'],
		monthNamesShort: ['һ��','����','����','����','����','����',
		'����','����','����','ʮ��','ʮһ��','ʮ����'],
		dayNames: ['������','����һ','���ڶ�','������','������','������','������'],
		dayNamesShort: ['����','��һ','�ܶ�','����','����','����','����'],
		dayNamesMin: ['��','һ','��','��','��','��','��'],
		weekHeader: '��',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '��'};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});



jquery.formFill.js 124��

styleElementName: 'none',	// object | none


jquery-ui-1.8.2.custom.css 439��

.ui-datepicker select.ui-datepicker-month-year {width: 60%;}
.ui-datepicker select.ui-datepicker-month, 
.ui-datepicker select.ui-datepicker-year { width: 30%;}

jquery.meio.mask.js 144��

'date-us'			: { mask : '19/39/9999' },
'date-cn'			: { mask : '9999-19-39' },