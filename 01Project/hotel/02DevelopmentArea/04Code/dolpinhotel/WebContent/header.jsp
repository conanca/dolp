<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<h1><b>当前用户</b></h1>
<div align="right">
	当前用户：${logonUser.name}
	<a href="system/user/logout">用户登出</a>
</div>