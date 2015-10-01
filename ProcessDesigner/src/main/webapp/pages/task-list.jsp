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


  <div class="row" id="user-area">
    <div class="col-sm-12">
      <img class="userImg" src="/images/user/<s:property value="userType"/>.png" height="90px" width="90px"/>
    </div>
  </div>

  <div class="row">
      <div class="col-sm-8">
          <!-- 任务内容区 -->
          <div class="g-content">
            <!-- 内容栏 -->
            <div class="m-activity">
              <h3>Activities
                <small class="totalNum">共<em> <s:property value="total" /> </em>个</small>
              </h3>
              <s:set value="userType" name="userType"/>
              <s:if test="#userType=='expert'">
              <div class="btn-create">
                  <a class="" href="<s:url action='step-process-select'/>">
                    <span class="glyphicon glyphicon-plus-sign addActivity" aria-hidden="true"></span>
                    Create </a>
              </div>
              </s:if>
              <ul class="list-unstyled" id="activityList">
                <li ng-repeat="activity in activities">
                  <a class="btn-activity" href="{{activity.process_url}}">
                    <img class="activityImg" src="{{activity.step_image_url}}"/>
                    <div class="info">
                      <div class="btn-edit"><span class="glyphicon glyphicon-edit"></span></div>
                      <p class="header text-elli">{{activity.stepName}}</p>
                      <p class="date">{{activity.date}}</p>
                      <p class="decription">Template: {{activity.process.template}}</p>
                      <p class="name">Process: {{activity.process.name}}</p>
                    </div>
                  </a>
                </li>

              </ul>

            </div>

            <!-- 分页 -->
            <div id="my-pagination">

            </div>

          </div>
      </div>

      <div class="col-sm-4">
          <!-- 右侧栏 -->
          <div class="m-sidebar">
            <!-- 最近任务 -->
            <div class="latestTask">
              <h4 class="header">Latest Task</h4>
              <div ng-show="firstActivity.id" class="list-unstyled" id="latestTaskList">
                <img class="activityImg" src="{{firstActivity.step_image_url}}">
                <div class="info">
                  <p class="info-header">
                   {{firstActivity.stepName}}
                  </p>
                  <p class="date">{{firstActivity.date}}</p>
                  <p class="decription">Process: {{firstActivity.process.name}}</p>
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
                 <img class="activityImg" src="images/step_detail/step-process-select.png">
                 <p>Select Process Template</p>
               </li>
                <li>
                 <img class="activityImg" src="images/step_detail/step-custom-entity.png">
                 <p>Customize Entity</p>
               </li>
                <li>
                  <img class="activityImg" src="images/step_detail/step-dbtemplate-edb-mapping.png">
                  <p>Template DB-EDB Mapping</p>
                </li>
                <li>
                  <img class="activityImg" src="images/step_detail/step-entity-edb-mapping.png">
                  <p>Entity-EDB Mapping</p>
                </li>

                <div style="clear:both;"></div>
                <li>
                  <img class="activityImg" src="images/step_detail/step-custom-process.png">
                  <p>Customize Process</p>
                </li>
                <li>
                  <img class="activityImg" src="images/step_detail/step-bind-process.png">
                  <p>Service Binding</p>
                </li>

                <li>
                  <img class="activityImg" src="images/step_detail/step-publish.png">
                  <p>Publish</p>
                </li>


              </ul>
             </div>

          </div>
        </div>
    </div>
  </div><!-- END of row  -->

</div> <!-- END of container -->

  <script>
    var ActivityApp = angular.module('app',[]);

    ActivityApp.controller("AppController",function($scope){
      $scope.activities = [];

      $scope.init = function(){
        var activities = [], tepmplate_name, process_name, activity;

        <s:iterator value="activities">
          tepmplate_name = '<s:property value="process.template.name" />';
          process_name = '<s:property value="process.name" />';
          activity = {
            id: <s:property value="id"/>,
            step: '<s:property value="step"/>',
            stepName: '<s:property value="stepName"/>',
            date: '<s:date name="datetime"/>',
            step_image_url: "images/step_detail/<s:property value="step"/>.png",
            process_url: "<s:url action='select-task-process'/>?activityId=<s:property value='id'/>",
            process: {
              template: tepmplate_name == '' ? 'unnamed' : tepmplate_name,
              name: process_name == ''? 'unnamed': process_name
            }
          };

          activities.push(activity);
        </s:iterator>

        $scope.activities = activities;

        <s:if test="firstActivity">
        $scope.firstActivity = {
            id: '<s:property value="firstActivity.id"/>',
            step: '<s:property value="firstActivity.step" />',
            stepName: '<s:property value="firstActivity.stepName" />',
            date: '<s:date name="firstActivity.datetime" />',
            step_image_url: "images/step_detail/<s:property value="firstActivity.step" />.png",
            process: {
              template : '<s:property value="firstActivity.process.template.name"/>',
              name: '<s:property value="firstActivity.process.name"/>'
            }
        };
        </s:if>
      }
    });

    $(function(){
        var pageSize = <s:property value="pageSize"/>;
        var pages = <s:property value="totalPages"/>;

        if(pages>1){
          var options = {
              currentPage: <s:property value="page"/>,
              totalPages: pages,
              pageUrl: function(type, page, current){
                  return document.location.pathname+'?page='+page + '&pageSize='+pageSize;
              }
          }

          $('#my-pagination').bootstrapPaginator(options);
        }
    });
  </script>

  <script src="<s:url value="js/lib/bootstrap-paginator-1.0.2/build/bootstrap-paginator.min.js" />"></script>
  <script src="<s:url value="js/task-list.js" />"></script>
</body>
</html>
