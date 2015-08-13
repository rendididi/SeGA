<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step1.css" />" rel="stylesheet">
</head>
<body>
	<header>
		<%int step=1; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
	</header>
	<section class="filter container">
		 <div class="input-group">
            <input type="text" class="form-control" placeholder="input process name..." value="">
	            <div class="input-group-btn">
            <button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
	</section>
	<section class="gallery container">
		
	</section>
	
</body>
</html>
