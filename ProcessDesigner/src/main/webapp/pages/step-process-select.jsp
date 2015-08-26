<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN" ng-app="step1">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step-process-select.css" />" rel="stylesheet">
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
		<%--<s:iterator value="process_list" var="process_list">--%>
		<div class = "col-md-4" ng-repeat="node in thumbs|filter:search track by $index">
			<div class="gallery-item img-thumbnail">
				<div class="img-holder vertical-center" >
					<img src={{node.processImageUrl}} />
				</div>
				
				<p class="gallery-item-title">{{node.name}}</p>
				<div class="gallery-buttonGroup">
					<s:form action="step-process-select-submit">
					<input type="hidden" name="process_id" value="{{node.id}}"/>
					<div class="btn-group">
					<button class="btn btn-primary" type="submit">Custom</button>
					<button class="btn btn-success" type="button">Deploy</button>
					</div>
					</s:form>
				</div>
			</div>			
		</div>
			
		<%--</s:iterator>--%>
	</section>
		
	
	<script>
		var list = [];
		var obj={};
		<s:iterator value="process_list">
			obj = {};
			obj.processImageUrl="images/process/<s:property value="processImageUrl"/>";
			obj.name="<s:property value="name"/>"
			obj.id=<s:property value="id"/>
			list.push(obj);
		</s:iterator>	
	</script>
	<script src="<s:url value="js/step-process-select.js" />"></script>
</body>
</html>
