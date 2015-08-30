<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/task-list.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%int step=0; %>
		<%@include file="/partials/navbar.jspf" %>
	</header>

	<section style="margin-top:100px">
	
	<s:set value="userType" var="user"/>
	<s:if test="%{#user=='expert'}">
	<a href="<s:url action='step-process-select'/>">Create New Process</a>
	</s:if>
	</section>

	<section>
		<%--<s:iterator value="process_list" var="process_list">--%>
		
			
		<%--</s:iterator>--%>
	</section>
	
	<script src="<s:url value="js/task-list.js" />"></script>
</body>
</html>
