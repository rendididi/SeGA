<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN" >
<head>
  <%@include file="/partials/common_header.jspf" %>
  <meta charset="utf-8">
  <title>日志管理</title>
  <link href="<s:url value="css/task-list.css" />" rel="stylesheet">
  <link href="<s:url value="js/lib/bootstrap-3.3.5-dist/css/bootstrap-datetimepicker.css" />" rel="stylesheet">
  <script src="<s:url value="js/lib/bootstrap-3.3.5-dist/js/bootstrap-datetimepicker.js" />"></script>
  <style>
    .m-activity #activityList li{margin-top:0px;padding: 20px 20px 0px 15px; min-height: 70px;}
    .info{height:69px}
    .info p {margin-bottom:3px;}
  </style>
</head>
<body style="min-width:998px" >
  <header>
    <%int step=0; %>
    <%@include file="/partials/navbar.jspf" %>
  </header>
  <div class="container">
  <div class="row" id="user-area">
    <div class="col-sm-12">
      <img class="userImg" src="/images/step_detail/step-custom-entity.png" height="90px" width="90px"/>
    </div>
  </div>
  <br />
  <div class="row">
      <div class="col-sm-3">
          <!-- 右侧栏 -->
          <div class="m-sidebar" style="float:none">
            <!-- 时间选择器 -->
            <div class="latestTask">
              <h4 class="header"><!-- Latest Task -->时间筛选</h4>
              <div class="list-time">
                <div class="datepicker"></div>
              </div>
            </div>

            <!-- 搜索栏 -->
            <div class="search">
               <h4>
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                <span class="header"><!-- Search -->搜索</span>
               </h4>
               <div class="row">
                <div class="col-lg-3">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="输入关键字">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button"><!-- Go! -->确定</button>
                    </span>
                  </div><!-- /input-group -->
                </div><!-- /.col-lg-3 -->
              </div>
             </div>
          </div>
        </div>
        
        <div class="col-sm-9">
          <!-- 任务内容区 -->
          <div class="g-content">
            <!-- 内容栏 -->
            <div class="m-activity">
              <ul id="myTab" class="nav nav-tabs">
  				<li class="active"><a href="#tab1" data-toggle="tab">业务操作日志</a></li>
  				<li class=""><a href="#tab2" data-toggle="tab">数据处理日志</a></li>
  				<li class=""><a href="#tab3" data-toggle="tab">流程记录日志</a></li>
			  </ul>
			  <div id="myTabContent" class="tab-content" >
  				 <div class="tab-pane fade in active" id="tab1" >
    			 	<!-- 业务操作日志 -->
    			 	<ul class="list-unstyled list-unstyled-small" id="activityList">
    			 	 <s:iterator value="logs">
    			 	   <s:if test="type == '11'">
		                <li>
		                  <a class="btn-activity" href="">
		                    <img class="activityImg activityImg-small" src="/images/log-item.png"/>
		                    <div class="info">
		                      <p class="header text-elli">操作者：<s:property value="user.id" /></p>
		                      <p class="date">${date}</p>
		                      <p class="">操作类型： ${operationType}</p>
		                      <s:if test="content.length() > 60">
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content.substring(0,60)+'…'" /> </p>
		                      </s:if>
		                      <s:else>
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content" /> </p>
		                      </s:else>
		                    </div>
		                  </a>
		                </li>
		               </s:if>
		              </s:iterator>
		            </ul>
    			 </div>
    			 <div class="tab-pane fade in " id="tab2" >
    			 	<!-- 数据处理日志 -->
    			 	<ul class="list-unstyled list-unstyled-small" id="activityList">
    			 	 <s:iterator value="logs">
    			 	  <s:if test="type == '22'">
		                <li>
		                  <a class="btn-activity" href="">
		                    <img class="activityImg activityImg-small" src="/images/log-item.png"/>
		                    <div class="info">
		                      <p class="header text-elli">操作者：<s:property value="user.id" /></p>
		                      <p class="date">${date}</p>
		                      <p class="">操作类型： ${operationType}</p>
		                      <s:if test="content.length() > 60">
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content.substring(0,60)+'…'" /> </p>
		                      </s:if>
		                      <s:else>
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content" /> </p>
		                      </s:else>
		                    </div>
		                  </a>
		                </li>
		               </s:if>
		               </s:iterator>
		            </ul>
    			 </div>
    			 <div class="tab-pane fade" id="tab3" >
    			 	<!-- 流程记录日志 -->
    			 	<ul class="list-unstyled list-unstyled-small" id="activityList">
		             <s:iterator value="logs">
    			 	  <s:if test="type == '33'">
		                <li>
		                  <a class="btn-activity" href="">
		                    <img class="activityImg activityImg-small" src="/images/log-item.png"/>
		                    <div class="info">
		                      <p class="header text-elli">操作者：<s:property value="user.id" /></p>
		                      <p class="date">${date}</p>
		                      <p class="">操作类型： ${operationType}</p>
		                       <s:if test="content.length() > 60">
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content.substring(0,60)+'…'" /> </p>
		                      </s:if>
		                      <s:else>
		                      	<p class="content" title="<s:property value="content" />">操作内容： <s:property value="content" /> </p>
		                      </s:else>
		                    </div>
		                  </a>
		                </li>
		               </s:if>
		              </s:iterator>
		            </ul>
    			 </div>
    		  </div>
            </div>

            <!-- 分页 -->
            <!-- <div id="my-pagination">
            </div> -->
          </div>
      </div>
    </div>
  </div><!-- END of row  -->

</div> <!-- END of container -->

  <script>
  	$(function() {
	    $(".datepicker").datetimepicker({
	    	language : 'fr',weekStart : 1,todayBtn : 1,autoclose : 1,
	   	    todayHighlight : 1,startView : 2,minView : 2,forceParse : 0,
	    });
	});
  </script>
  <script>
  	$(function(){
  		setInterval("random()",100000);
  	});
  	function random(){
  		$.ajax({
  			type:"get",
  			url:"show-log-console.action",
  			success:function(text){
  				location.reload();
  			}
  		});
  	}
  </script>
  

 <script src="<s:url value="js/lib/bootstrap-paginator-1.0.2/build/bootstrap-paginator.min.js" />"></script>
</body>
</html>

