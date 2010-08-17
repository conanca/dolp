/**
 * Select Subcategory - A jQuery plugin for grabbing options of a select box using AJAX.
 * Tested in jQuery v1.3.2 or above
 *
 * http://nilambar.com.np
 *
 * Copyright (c) 2010 Nilambar Sharma
 * License: DWYW (Do Whatever You Want)
 * Version: 1.0
 */
/**
 * It is useful when you want to populate select list according to its parent select box's value.
 *
 * For example lets take a HTML markup as follows
 *
 *   <select name="category" id="category" size="1">
 *   <option value="-1">Select</option>
 *		  <option value="1" >Asia</option>
 *		  <option value="2">Europe</option>
 *	</select>
 *	<select name="subcategory" id="subcategory">
 *		  <option>Select</option>
 *	</select>
 *
 * Now include jQuery library along with the selectsubcategory(this) plugin
 *
 * Use the following snippet to initiate select box.
 *   $("#category").selectSubcategory({
			url: 'includes/getsubcategories.php',
			subcategoryid: 'subcategory'
		});
 *
 * Parameters here are:
 *
 * @url: 			url of the serverside file from where we want to get select options
  *		Default is 'getsubcategories.php' in the same directory
 * @subcategoryid:	id of the subcategory 
 *		Default is 'subcategory'
 *
 * JSON is used for sending data.
 *
 * In the server side, For example, PHP code:
 * getsubcategories.php
 * <?php
 *	if(isset($_GET['myid']))
 *	{
 *		$curid=$_GET['myid'];
 *		if($curid=='1')
 *		{
 *			echo '{Nepal=np,China=ch}';
 *		}
 *		else if($curid=='2')
 *		{
 *			echo '{Germany=gy,Denmark=dk}';
 *		}
 *		else
 *		{
 *			echo '{Select=-1}';
 *		}
 *		
 *	}
 *	?>
 *
 *
 *
 */(function($) {
$.fn.selectSubcategory = function(o) {
    o = $.extend({ url: "getsubcategories.php", subcategoryid:'subcategory'}, o || {});
	var selectorid=this.selector;
    return this.each(function() {
        var me = $(this), noop = function(){};
		me.change(function(){
			var datatosend='myid='+me.val();
			//alert(datatosend);
			$.ajax({
				   type: "GET",
				   url: o.url,
				   data: datatosend,
				   dataType: "json",
				   success:function(data){
					   $('#'+o.subcategoryid).find('option').remove().end();
					   $.each(data,function(text,value){
							var newopt=new Option(text,value);					
							$('#'+o.subcategoryid).append(newopt);
							});
					}				   
			});
		});
		
    });
};
})(jQuery);
