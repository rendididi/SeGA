<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/login.css" />" rel="stylesheet">
</head>
<body class="login-bg">
    <nav class="navbar navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only"><!-- Toggle navigation -->导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><span style="color:#f9f9f9;font-weight:200;">住房保障公共服务平台</span></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">流程设计</a></li>
            <li><a href="<s:text name="taskviewer.url"/>">业务办理</a></li>
            <li><a href="#about">关于</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
    
    <header class="container">

    </header>
    
    <section class="container login">
    	<div class="col-md-4 col-md-offset-1 login-container">
    		<img src="/images/user/expert.png" width="128px" height="128px"/>
        <div class="title"><h2>业务专家</h2></div>  
    		<div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-process-select.png" width="28px" height="28px"/>
              流程选择
            </li>
            <li>
              <img src="/images/step_detail/step-custom-entity.png" width="28px" height="28px"/>
              自定义实体
            </li>
            <li>
              <img src="/images/step_detail/step-custom-process.png" width="28px" height="28px"/>
              自定义流程
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="expert"></s:hidden>
          <div class="login-city-box">
          	<img src="/images/mark.png" style="width:28px;height:28px;" />
          	<select class="form-control changeSelectBg">
          		<option>杭州</option>
          		<option>衢州</option>
          		<option>乌鲁木齐</option>
          	</select>
          </div>
          <button class="btn btn-default btn-block btn-bussiiness" type="submit">登录</button>
          </s:form>
        </div>
    	</div>
      <div class="col-md-4 col-md-offset-2 login-container">
        <img src="/images/user/developer.png" width="128px" height="128px"/>
        <div class="title"><h2>开发工程师</h2></div>  
        <div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-dbtemplate-edb-mapping.png" width="28px" height="28px"/>
              DB Template-EDB 映射
            </li>
            <li>
              <img src="/images/step_detail/step-entity-edb-mapping.png" width="28px" height="28px"/>
              EDB实体映射
            </li>
            <li>
              <img src="/images/step_detail/step-bind-process.png" width="28px" height="28px"/>
              服务进程绑定
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="developer"></s:hidden>
          <div class="login-city-box">
          	<img src="/images/mark.png" style="width:28px;height:28px;" />
          	<select class="form-control changeSelectBg">
          		<option>杭州</option>
          		<option>衢州</option>
          		<option>新疆</option>
          	</select>
          </div>
          <button class="btn btn-default btn-block ">登录</button>
          </s:form>
        </div>
      </div>
    </section>
    
</body>
</html>
