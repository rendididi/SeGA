<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step3.css" />" rel="stylesheet">

</head>
<body>
  <header>
  	<%int step=3; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>
 </body>
 </html>