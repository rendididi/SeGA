 <%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="zh-CN">
<head>
<%@include file="/partials/common_header.jspf"%>
<link href="<s:url value="css/step-custom-entity.css" />" rel="stylesheet">
<link href="<s:url value="css/icomoon-font.css" />" rel="stylesheet">
<link rel="stylesheet"
	href="<s:url value="js/lib/jstree/themes/default/style.css" />" />
<script src="<s:url value="js/lib/jstree/jstree.js" />"></script>
<script src="<s:url value="js/lib/jstree/jstree.sega.js" />"></script>
</head>
<body>
<%@include file="/partials/i18n.jsp" %>
	<header>
		<%int step=2; %>
		<%@include file="/partials/navbar.jspf"%>
		<%@include file="/partials/step_detail.jspf"%>
	</header>
	<section class="container">
		<div id="entity_tree" class="col-lg-7"></div>

		<div class="col-lg-4 col-lg-offset-1 toolbox_container">
			<div class="toolbox" data-spy="affix" data-offset-top="400"
				data-offset-bottom="0">
				<div class="form-group">
					<label><!-- Name --><%=myResourceBundle.getString("Name") %></label>
					<div>
						<input type="text" class="form-control node-name" />
					</div>
				</div>

				<div class="form-group">
					<label><!-- Node --><%=myResourceBundle.getString("Node") %></label>
					<div>
						<select class="selectpicker form-control node-type"
							data-dropup-auto="false" data-hide-disabled="true">
							<option
								data-content="<span class='node_artifact node_icon'><!-- Entity -->实体</span>"
								value="artifact"><!-- Entity --><%=myResourceBundle.getString("Entity") %></option>
							<option
								data-content="<span class='node_artifact_n node_icon'><!-- Entity 1-n -->一对多实体</span>"
								value="artifact_n"><!-- Entity 1-n --><%=myResourceBundle.getString("Entity1-n") %></option>
							<option
								data-content="<span class='node_attribute node_icon'><!-- Attribute -->属性</span>"
								value="attribute"><!-- Attribute --><%=myResourceBundle.getString("Attribute") %></option>
							<option
								data-content="<span class='node_group node_icon'><!-- Group -->属性组</span>"
								value="group"><!-- Attribute Group --><%=myResourceBundle.getString("AttributeGroup") %></option>
							<option
								data-content="<span class='node_key node_icon'><!-- Key -->主键</span>"
								value="key"><!-- Key --><%=myResourceBundle.getString("Key") %></option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label><!-- Type --><%=myResourceBundle.getString("Type") %></label>
					<div>
						<select class="selectpicker form-control value-type"
							data-dropup-auto="false">
							<option value="auto"><%=myResourceBundle.getString("auto") %></option>
							<option value="number"><%=myResourceBundle.getString("numbertype") %></option>
							<optgroup label="<%=myResourceBundle.getString("string") %>">
								<option value="string-string"><%=myResourceBundle.getString("string-string") %></option>
								<option value="string-textarea"><%=myResourceBundle.getString("string-textarea") %></option>
							</optgroup>
							<option value="file"><%=myResourceBundle.getString("file") %></option>
						</select>
					</div>
				</div>

				<div class="form-group create">
					<label><!-- Create --><%=myResourceBundle.getString("Create") %> <span class="glyphicon glyphicon-plus"
						aria-hidden="true"></span>
					</label>
					<button type="button" class="btn btn-primary btn-md btn-block"
						data-type="artifact">
						<span class="icon-artifact"></span> <%=myResourceBundle.getString("Entity") %>
					</button>
					<button type="button" class="btn btn-primary btn-md btn-block"
						data-type="artifact_n">
						<span class="icon-artifact_n"> <span class="path1"></span><span
							class="path2"></span><span class="path3"></span><span
							class="path4"></span><span class="path5"></span><span
							class="path6"></span><span class="path7"></span><span
							class="path8"></span><span class="path9"></span><span
							class="path10"></span><span class="path11"></span><span
							class="path12"></span>
						</span> <%=myResourceBundle.getString("Entity1-n") %>
					</button>
					<button type="button" class="btn btn-primary btn-md btn-block"
						data-type="attribute">
						<span class="icon-attribute"></span> <%=myResourceBundle.getString("Attribute") %>
					</button>
					<button type="button" class="btn btn-primary btn-md btn-block"
						data-type="group">
						<span class="icon-group"></span> <%=myResourceBundle.getString("AttributeGroup") %>
					</button>
					<button type="button" class="btn btn-primary btn-md btn-block"
						data-type="key">
						<span class="icon-key"></span> <%=myResourceBundle.getString("Key") %>
					</button>
				</div>

				<div class="form-group">
					<label><!-- Delete --><%=myResourceBundle.getString("Delete") %> <span class="glyphicon glyphicon-minus"
						aria-hidden="true"></span>
					</label>
					<button type="button"
						class="btn btn-danger btn-md btn-block remove-btn">
						<%=myResourceBundle.getString("Delete") %></button>
				</div>

				<div class="form-group">
					<label><!-- Submit --><%=myResourceBundle.getString("submit") %> <span class="glyphicon glyphicon-ok"
						aria-hidden="true"></span>
					</label>
					<button type="button" class="btn btn-success btn-md btn-block"
						id="btn_viewjson"><!-- Submit --><%=myResourceBundle.getString("submit") %></button>
				</div>
			</div>
		</div>
		<!-- End if Toolbox-->
	</section>

	<div class="modal fade" id="modal_viewjson">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"><!-- Please Confirm Entity JSON to Submit --><%=myResourceBundle.getString("submit") %></h4>
				</div>
				<div class="modal-body json-modal-body">
					<p></p>
				</div>

				<div class="modal-footer">
					<s:form action="step-custom-entity-submit" id="form-submit">
						<s:hidden name="process.entityJSON" id="entityJSON"></s:hidden>
						<button type="button" class="btn btn-default" data-dismiss="modal"><!-- Close --><%=myResourceBundle.getString("Close") %></button>
						<button type="submit" class="btn btn-success"><!-- Confirm --><%=myResourceBundle.getString("ok") %></button>
					</s:form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<script type="text/javascript">
var entity_json = (<s:property value="process.entityJSON" escape="false"/>);
  </script>
	<script type="text/javascript" src="<s:url value="js/step-custom-entity.js" />"></script>
</body>
</html>
