<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN" ng-app="step1">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step1.css" />" rel="stylesheet">
	<script src="<s:url value="js/lib/angular/angular.js" />"></script>
</head>
<body ng-controller="step1_controller">
	<header>
		<%int step=1; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
	</header>
	<section class="filter container">
		 <div class="input-group">
            <input type="text" class="form-control" placeholder="input process name..." value="" ng-model="search">
	            <div class="input-group-btn">
            <button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
	</section>
	<section class="gallery container">
		<s:iterator value="process_list">
		<div class = "col-md-4" ng-repeat="node in thumbs|filter:search">
			<div class="gallery-item img-thumbnail">
				<div class="img-holder vertical-center" >
					<img src={{node.processImageUrl}} />
				</div>
				
				<p class="gallery-item-title">{{node.name}}</p>
				<div class="gallery-buttonGroup">
					<button class="btn btn-primary">编 辑</button>
					<button class="btn btn-success">部 署</button>
				</div>
			</div>			
		</div>
			
		</s:iterator>		
	</section>
		
	
	<script>
		var list = [];
		var obj={}
		<s:iterator value="process_list">
			obj.processImageUrl="images/process/<s:property value="processImageUrl"/>";
			obj.name="<s:property value="name"/>";
			list.push(obj);
		</s:iterator>	
	</script>
	<script src="<s:url value="js/step1.js" />"></script>
</body>
</html>
