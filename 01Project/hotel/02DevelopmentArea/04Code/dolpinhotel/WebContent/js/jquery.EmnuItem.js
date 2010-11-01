$.extend({
	//根据url提交给后台查询Item信息，获得的是JSON格式的map数据
	getItem: function(url) {
		var Items = {};
		$.ajaxSetup({ async: false});//设为同步模式
		$.getJSON(url,function(response){
			Items = response;
		});
		return Items;
	},
	//根据系统枚举名称，获得它所有的枚举值
	getSysEmnuItem: function(SysEnumName) {
		var url = 'system/sysEnum/getSysEnumOption/'+SysEnumName;
		var Items = {};
		$.ajaxSetup({ async: false});//设为同步模式
		$.getJSON(url,function(response){
			Items = response;
		});
		return Items;
    },
	//为JSON格式的map数据做键值互换
    swapJSON: function(json) {
        var o = {};
        $.each(json, function(k, v) {
            o[v] = k;
        });
        return o;
    }
});

(function($) {
	//为该下拉列表框添加指定的Items作为option，此处的Items为JSON格式的map数据
	$.fn.addItems = function(Items) {
		var selectorid=this.selector;
		$.each(Items,function(text,value) {
			$(selectorid).append(new Option(text,value));
		});
	};
	//为该下拉列表框添加指定系统枚举值作为option
	$.fn.addSysEmnuItems = function(SysEnumName) {
		var selectorid=this.selector;
		var SysEnumItems = $.getSysEmnuItem(SysEnumName);
		$.each(SysEnumItems,function(text,value) {
			$(selectorid).append(new Option(text,value));
		});
	};
})(jQuery);