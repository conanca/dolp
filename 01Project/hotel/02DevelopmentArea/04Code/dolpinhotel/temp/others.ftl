dao.create(${Domain}.class, true);

(X,'${Domain}管理','${htmlPath}/${Domain?lower_case}_manager.html',NULL,M,N);

(A,'查询',null,X,'${package}.module.${Domain}Module.getGridData'),
(B,'修改',null,X,'${package}.module.${Domain}Module.editRow');