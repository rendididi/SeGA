 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.sega.ProcessDesigner.util.Constant"%>

<html lang="zh-CN" ng-app="step1">
<head>
	<%@include file="/partials/common_header.jspf" %>
	<link href="<s:url value="css/step-process-select.css" />" rel="stylesheet">
	<script src="<s:url value="js/lib/angular/angular.js" />"></script>
</head>
<body ng-controller="step1_controller">
<%@include file="/partials/i18n.jsp" %>
	<header>
		<%int step=1; %>
		<%@include file="/partials/navbar.jspf" %>
		<%@include file="/partials/step_detail.jspf" %>
		<select class="form-control template-select" style="width:15%;position:absolute;top:204px;left:410px"><!-- 386px -->
	        	<option value="" <s:if test="processType==null">selected</s:if>><%=myResourceBundle.getString("all") %></option>
	        	<option value="房源" <s:if test="processType.contains('房源')">selected</s:if>><%=myResourceBundle.getString("Propertymanagement") %></option>
	        	<option value="申请" <s:if test="processType.contains('申请')">selected</s:if>><%=myResourceBundle.getString("Qualificationsmanagement") %></option>
	        	<option value="配租" <s:if test="processType.contains('配租')">selected</s:if>><%=myResourceBundle.getString("rentmanagement") %></option>
	        	<option value="缴交" <s:if test="processType.contains('缴交')">selected</s:if>><%=myResourceBundle.getString("Rentpayment") %></option>
	    </select>
	</header>
	<section class="filter container">
		 <div class="input-group">
            <input type="text" class="form-control seacrhProcess" placeholder="输入流程名称" value="" ng-model="search">
	        <div class="input-group-btn">
            	<button type="submit" class="btn btn-info btn-process-select"><span class="glyphicon glyphicon-search"></span></button>
            </div>
        </div>
	</section>
	<section class="gallery container">
		<%--<s:iterator value="process_list" var="process_list">--%>
		<div class = "col-md-4" ng-repeat="node in thumbs|filter:search track by $index">
			<div class="gallery-item img-thumbnail">
				<div class="img-holder vertical-center" >
					<img src={{node.processImageUrl}} />
					{{node.processImageUrl}}
				</div>
				
				<p class
				="gallery-item-title">{{node.name}}</p>
				<div class="gallery-buttonGroup">
					<s:form action="step-process-select-submit">
						<input type="hidden" name="process_id" value="{{node.id}}"/>
						
						<div class="btn-group">
							<button class="btn btn-primary btn-custom-process" type="button"><!-- Custom --><%=myResourceBundle.getString("Custom") %></button>
							<button class="btn btn-success" type="button"><!-- Deploy --><%=myResourceBundle.getString("Deploy") %></button>
						</div>
					</s:form>
				</div>
			</div>			
		</div>
			
		<%--</s:iterator>--%>
	</section>
		
	<section>
		<div class="modal fade" id="process-modal">
			<s:form action="step-process-select-submit" id="form-submit">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title"><!-- Process Information --><%=myResourceBundle.getString("ProcessInformation") %></h4>
					</div>
					<div class="modal-body">
						<h2><!-- Name --><%=myResourceBundle.getString("Name") %></h2>
						<input type="text" name="process.name" class="form-control" placeholder="" aria-describedby="sizing-addon1"/>
						<h2><!-- Description --><%=myResourceBundle.getString("Description") %></h2>
						<textarea readOnly name="process.description" class="form-control" placeholder="" aria-describedby="sizing-addon1" rows=4></textarea>
					</div>
					<div class="modal-footer">
							<input type="hidden" name="process_id" value=""/>
							<button type="button" class="btn btn-success" id="btn-submit"><!-- Submit --><%=myResourceBundle.getString("ok") %></button>

					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
			</s:form>
		</div><!-- /.modal -->

	</section>
	<script>
		$(".template-select").change(function(){
			location.href="step-process-select?processType="+$(".template-select option:selected").attr("value");
			
		})
	</script>
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
