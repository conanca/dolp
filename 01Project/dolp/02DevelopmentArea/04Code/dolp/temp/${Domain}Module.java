package ${package}.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import ${package}.domain.${Domain};
import ${package}.service.${Domain}Service;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("${Domain?uncap_first}Module")
@At("/${requestPath}/${Domain?uncap_first}")
public class ${Domain}Module {
	private ${Domain}Service ${Domain?uncap_first}Service;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return ${Domain?uncap_first}Service.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") ${Domain} ${Domain?uncap_first}) {
		return ${Domain?uncap_first}Service.CUD${Domain}(oper, ids, ${Domain?uncap_first});
	}
}