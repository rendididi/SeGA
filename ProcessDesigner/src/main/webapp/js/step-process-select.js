var step1App = angular.module('step1',[]);

step1App.controller("step1_controller",function($scope){
	$scope.thumbs=list;
});

var onCustomClick = function(){
	var process_id = $(this).parents("form").find("input[name='process_id']").val();
	var template_name = $(this).parents(".gallery-item").find(".gallery-item-title").text();
	//console.log
	$("#process-modal").find("input[name=process_id]").val(process_id);
	$("#process-modal").find("input[name='process.name']").val(template_name);
	if(template_name.indexOf('申请') > 0){
		$("#process-modal").find("textarea[name='process.description']").val("为各保障性住房资格的申请与审核，实现全市统一规范、统一编号规则的受理及资格审核流程，根据政府公平、公正、公开的办事要求，实现基于流程的业务审批过程并可查询、可跟踪，实现资格审核过程所需的公示、审查、审核等，为后续摇号、选房等功能提供支持，保障资格管理便于审核部门掌握申请家庭房产、车辆等情况。");
	}else if(template_name.indexOf('房源') > 0){
		$("#process-modal").find("textarea[name='process.description']").val("为各保障性住房，提供房源基本信息（包括房源的地段、朝向、房间信息、配套设备信息等）的采集、审核入库、房源状态调整等功能。");
	}else if(template_name.indexOf('配租') > 0){
		$("#process-modal").find("textarea[name='process.description']").val("为各保障性住房通过资格审核完成后实现房屋分配管理，按照统一的标准规范进行配给。实现按照保障形式分别进行租赁登记、办理入住手续，为后续的收缴租金或发放租赁补贴等管理提供数据及业务支撑。");
	}else if(template_name.indexOf('缴交') > 0){
		$("#process-modal").find("textarea[name='process.description']").val("对公共租赁住房实物配租家庭的租金管理实行租补分离政策。实现按照市场平均租金标准、楼层、朝向等因素确认市场租金，租金收缴、催缴，生成银行代扣租金，租金补贴发放、停发，以及相关查询统计功能。");
	}
	
	$("#process-modal").on("shown.bs.modal" ,function(){
		$("#process-modal").find("input[name='process.name']").focus();
		$("#process-modal").find("input[name='process.name']").select();
	});
	$("#process-modal").modal();
	
};

var onSubmitClick = function(){
	$("#form-submit").submit();
}

$(function(){
	$(".btn-custom-process").click(onCustomClick);
	$("#btn-submit").click(onSubmitClick);
});