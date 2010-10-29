(function($) {
$.fn.addSysEmnuItem = function(SysEnumName) {
	var selectorid=this.selector;
	var url = 'system/sysEnum/getSysEnumOption/'+SysEnumName;
	var SysEnumItems;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url,function(response){
		SysEnumItems = response;
	});
	$.each(SysEnumItems,function(text,value) {
		$(selectorid).append(new Option(text,value));
	});
};
})(jQuery);