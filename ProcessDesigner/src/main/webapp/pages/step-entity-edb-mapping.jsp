﻿ <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step-entity-edb-mapping.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
	<link href="<s:url value="css/icomoon-font2.css" />" rel="stylesheet">
	<link rel="stylesheet" href="<s:url value="js/lib/jstree/themes/default/style.css" />" />

</head>
<body>
<%@include file="/partials/i18n.jsp" %>
  <header>
  	<%int step=4; %>
  	<%@include file="/partials/navbar.jspf" %>
  	<%@include file="/partials/step_detail.jspf" %>
  </header>



  <section class="container" id="mappingbox">
  	<div id="entity_tree" class="col-md-4">
  		
  	</div>
  	<div id="db_tables" class="col-md-4 col-md-offset-4">
  	</div>
  </section>

  <!--
  <section class="" id="mapping_panel">
    <div class="container vertical-center">
      <div class="col-md-5" id="entity_path">
        <span></span>
      </div>
      <div class="col-md-2 btn-group" id="btn-confirm">
        <button class="btn btn-success btn-block">Confirm 确认</button>
      </div>
      <div class="col-md-5" id="db_path">
        <span></span>
      </div>
    </div>
  </section>
  -->
  <section>
    <div class="modal fade" id="mapping-option-modal">
      
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          </div>
          <div class="modal-body">
            <h4><!-- Entity Mapping Function -->实体映射功能</h4>
              <select class="selectpicker form-control function_entity"
                data-dropup-auto="false" data-hide-disabled="true">
                <option value="default"><!-- Default -->默认</option>
                <option value="datetime_yy_mm_dd">日期(年-月-日)</option>
                <option value="localtion_dist_rd">地址(区-街道)</option>
              </select>
            <h4><!-- Database Mapping Function -->数据库映射功能</h4>
              <select class="selectpicker form-control function_db"
                data-dropup-auto="false" data-hide-disabled="true">
                <option value="default"><!-- Default -->默认</option>
                <option value="datetime_yy_mm_dd">日期(年-月-日)</option>
                <option value="localtion_dist_rd">地址(区-街道)</option>
              </select>
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-block btn-success" id="btn-modal-submit"><!-- Add Rule -->添加规则</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
      
    </div><!-- /.modal -->

  </section>
  
  <section>
    <s:form action="step-entity-edb-mapping-submit" id="form-submit">
      <s:hidden name="process.EDmappingJSON" id="hidden-ruleJSON"></s:hidden>
      <s:hidden name="process.entityJSON" id="hidden-entityJSON"></s:hidden>
    </s:form>
  </section>
  
  <script type="text/javascript">
var entity_json = <s:property value="process.entityJSON" escape="false"/>;
var database_json = <s:property value="process.databaseJSON" escape="false"/>;
var ed_rules = <s:property value="process.EDmappingJSON" escape="false"/>;
  </script>
  <script type="text/javascript" src="<s:url value="js/lib/d3/d3.min.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/lib/jstree/jstree.sega.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.js" />"></script>
  <script type="text/javascript" src="<s:url value="js/step-entity-edb-mapping.mappingtool.js" />"></script>

</body>
</html>

