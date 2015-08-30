<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN" ng-app="app">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/task-list.css" />" rel="stylesheet">
	<script src="<s:url value="js/lib/angular/angular.js" />"></script>
</head>
<body ng-controller="AppController" ng-init="init()">
	<header>
		<%int step=0; %>
		<%@include file="/partials/navbar.jspf" %>
	</header>

	<!-- <section style="margin-top:100px">

	<s:set value="userType" var="user"/>
	<s:if test="%{#user=='expert'}">
	<a href="<s:url action='step-process-select'/>">Create New Process</a>
	</s:if>
	</section> -->

	<div class="container">
		<!-- 头部 -->
		<!-- <div class="g-header">
			<img src="images/process/process1.png" height="452" width="1180" class="backImg">
			<button class="btn btn-primary btn-header">szdfszdfszdfszdf</button>
		</div> -->

		<!-- 任务内容区 -->
		<div class="g-content">
			<img class="userImg" src="/images/user/<s:property value="userType"/>.png" height="90px" width="90px"/>
			<!-- 内容栏 -->
			<div class="m-activity">
				<h3>Activities
					<small class="totalNum">共<em> <s:property value="total" /> </em>个</small>
				</h3>
				<div class="btn-create">
						<a class="" href="#">
							<span class="glyphicon glyphicon-plus-sign addActivity" aria-hidden="true"></span>
							Create </a>
				</div>

				<ul class="list-unstyled" id="activityList">
					<li ng-repeat="activity in activities | orderBy: 'id' : true">
						<img class="activityImg" src="{{activity.step_image_url}}"/>
						<div class="info">
							<p class="header text-elli"></p>
							<p class="date">{{activity.date}}</p>
							<p class="decription">{{activity.step}}</p>
						</div>
					</li>

				</ul>
				<!-- 分页 -->
				 <nav>
				  <ul class="pagination flr">
				    <li>
				      <a href="#" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <li class="active"><a href="#">1</a></li>
				    <li><a href="#">2</a></li>
				    <li><a href="#">3</a></li>
				    <li><a href="#">4</a></li>
				    <li><a href="#">5</a></li>
				    <li>
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				  </ul>
				</nav>
			</div>

			<!-- 右侧栏 -->
			<div class="m-sidebar">
				<!-- 最近任务 -->
				<div class="latestTask">
					<h4 class="header">Latest Task</h4>
					<div class="list-unstyled" id="latestTaskList">
						<img class="activityImg" src="images/step_detail/step-bind-process.png">
						<div class="info">
							<p class="info-header">张三 </p>
							<p class="date">2015年3月27日 16:31</p>
							<p class="decription">详细描述详细描述详细描述详细描述详细描述详细</p>
						</div>
					</div>
				</div>

				<!-- 搜索栏 -->
				<div class="search">
				 	<h4>
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span class="header">Search</span>
				 	</h4>
				 	<div class="row">
					  <div class="col-lg-3">
					    <div class="input-group">
					      <input type="text" class="form-control" placeholder="Search for...">
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button">Go!</button>
					      </span>
					    </div><!-- /input-group -->
					  </div><!-- /.col-lg-3 -->
					</div>
				 </div>

				 <!-- 按图标筛选栏 -->
				 <div class="select">
				 	<ul class="list-unstyled" id="taskList">
						<li>
							<img class="activityImg" src="images/step_detail/step-dbtemplate-edb-mapping.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-bind-process.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-custom-process.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-custom-entity.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-publish.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-process-select.png">
							<p>任务名简称</p>
						</li>
						<li>
							<img class="activityImg" src="images/step_detail/step-entity-edb-mapping.png">
							<p>任务名简称</p>
						</li>
					</ul>
				 </div>

			</div>

		 </div>
		</div>
	</div>

	<script>
		var ActivityApp = angular.module('app',[]);

		ActivityApp.controller("AppController",function($scope){
			$scope.activities = [];

			$scope.init = function(){
				var activities = [];

				<s:iterator value="activities">
					var activity = {
						id: <s:property value="id"/>,
						step: '<s:property value="step"/>',
						date: '<s:date name="datetime"/>',
						step_image_url: "images/step_detail/<s:property value="step"/>.png"
					};

					activities.push(activity);
				</s:iterator>

				$scope.activities = activities;
			}
		});

	</script>
	<script src="<s:url value="js/task-list.js" />"></script>
</body>
</html>
