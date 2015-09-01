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
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><span style="color:#f9f9f9;font-weight:200;">SeGA</span> Workflow System</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Process Designer</a></li>
            <li><a href="#about">About</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
    
    <header class="container">

    </header>
    
    <section class="container login">
    	<div class="col-md-4 col-md-offset-1 login-container">
    		<img src="/images/user/expert.png" width="128px" height="128px"/>
        <div class="title"><h2>Business Expert</h2></div>  
    		<div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-process-select.png" width="28px" height="28px"/>
              Process Select
            </li>
            <li>
              <img src="/images/step_detail/step-custom-entity.png" width="28px" height="28px"/>
              Customize Entity
            </li>
            <li>
              <img src="/images/step_detail/step-custom-process.png" width="28px" height="28px"/>
              Customize Process
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="expert"></s:hidden>
          <button class="btn btn-default btn-block" type="submit">Login</button>
          </s:form>
        </div>
    	</div>
      <div class="col-md-4 col-md-offset-2 login-container">
        <img src="/images/user/developer.png" width="128px" height="128px"/>
        <div class="title"><h2>Developer</h2></div>  
        <div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-dbtemplate-edb-mapping.png" width="28px" height="28px"/>
              DB Template-EDB Mapping
            </li>
            <li>
              <img src="/images/step_detail/step-entity-edb-mapping.png" width="28px" height="28px"/>
              Entity-EDB Mapping
            </li>
            <li>
              <img src="/images/step_detail/step-bind-process.png" width="28px" height="28px"/>
              Service-Process Binding
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="developer"></s:hidden>
          <button class="btn btn-default btn-block">Login</button>
          </s:form>
        </div>
      </div>
    </section>
    
</body>
</html>