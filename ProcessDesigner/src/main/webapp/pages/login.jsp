 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/login.css" />" rel="stylesheet">
</head>
    <%@include file="/partials/i18n.jsp" %>

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
          <a class="navbar-brand" href="#"><span style="color:#f9f9f9;font-weight:200;"><%=myResourceBundle.getString("projecttitle") %></span></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#"><%=myResourceBundle.getString("processdesigner") %></a></li>
            <li><a href="<s:text name="taskviewer.url"/>"><%=myResourceBundle.getString("taskviewer") %></a></li>
            <li><a href="#about"><%=myResourceBundle.getString("about") %></a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
    
    <header class="container">

    </header>
    
    <section class="container login">
    	<div class="col-md-4 col-md-offset-1 login-container">
    		<img src="/images/user/expert.png" width="128px" height="128px"/>
        <div class="title"><h2><%=myResourceBundle.getString("businessexpert") %></h2></div>  
    		<div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-process-select.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("processselect") %>
            </li>
            <li>
              <img src="/images/step_detail/step-custom-entity.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("customizeentity") %>
            </li>
            <li>
              <img src="/images/step_detail/step-custom-process.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("customizeprocess") %>
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="expert"></s:hidden>
          <div class="login-city-box">
          	<img src="/images/mark.png" style="width:28px;height:28px;" />
          	<select class="form-control changeSelectBg" name="processCity">
          		<option value="0571"><%=myResourceBundle.getString("hz") %></option>
          		<option value="0570"><%=myResourceBundle.getString("qz") %></option>
          		<option value="0991"><%=myResourceBundle.getString("xj") %></option>
          	</select>
          </div>
          <button class="btn btn-default btn-block btn-bussiiness" type="submit"><%=myResourceBundle.getString("login") %></button>
          </s:form>
        </div>
    	</div>
      <div class="col-md-4 col-md-offset-2 login-container">
        <img src="/images/user/developer.png" width="128px" height="128px"/>
        <div class="title"><h2><%=myResourceBundle.getString("developer") %></h2></div>  
        <div class="login-panel">
          <ul>
            <li>
              <img src="/images/step_detail/step-dbtemplate-edb-mapping.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("DBTemplate-EDBMapping") %>
            </li>
            <li>
              <img src="/images/step_detail/step-entity-edb-mapping.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("Entity-EDBMapping") %>
            </li>
            <li>
              <img src="/images/step_detail/step-bind-process.png" width="28px" height="28px"/>
              <%=myResourceBundle.getString("Service-ProcessBinding") %>
            </li>
          </ul>
          <s:form action="dologin">
          <s:hidden name="userType" value="developer"></s:hidden>
          <div class="login-city-box">
          	<img src="/images/mark.png" style="width:28px;height:28px;" />
          	<select class="form-control changeSelectBg" name="processCity">
          		<option value="0571"><%=myResourceBundle.getString("hz") %></option>
          		<option value="0570"><%=myResourceBundle.getString("qz") %></option>
          		<option value="0991"><%=myResourceBundle.getString("xj") %></option>
          	</select>
          </div>
          <button class="btn btn-default btn-block "><%=myResourceBundle.getString("login") %></button>
          </s:form>
        </div>
      </div>
    </section>
    
</body>
</html>
