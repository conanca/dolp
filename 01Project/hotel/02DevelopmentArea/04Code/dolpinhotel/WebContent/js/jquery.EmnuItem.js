//根据url提交给后台查询Item信息，获得的是JSON格式的map数据
function getItem(url){
	var Items;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url,function(response){
		Items = response;
	});
	return Items;
}

//根据系统枚举名称，获得它所有的枚举值
function getSysEmnuItem(SysEnumName){
	var url = 'system/sysEnum/getSysEnumOption/'+SysEnumName;
	return getItem(url);
}

//为该下拉列表框添加指定的Items作为option，此处的Items为JSON格式的map数据
(function($) {
	$.fn.addItems = function(Items) {
		var selectorid=this.selector;
		$.each(Items,function(text,value) {
			$(selectorid).append(new Option(text,value));
		});
	};
})(jQuery);

//为该下拉列表框添加指定系统枚举值作为option
(function($) {
$.fn.addSysEmnuItems = function(SysEnumName) {
	var selectorid=this.selector;
	var SysEnumItems = getSysEmnuItem(SysEnumName);
	$.each(SysEnumItems,function(text,value) {
		$(selectorid).append(new Option(text,value));
	});
};
})(jQuery);

//为JSON格式的map数据做键值互换
function swapJsonKV(str){
	var newStr = {};

	for (var key in str) {
	    var value = str[key];
	    newStr[value] = key;
	    delete str[key];
	}
	for (var key in newStr) {
	    var value = newStr[key];
	    str[key] = value;
	    delete newStr[key];
	}
	delete newStr;
}