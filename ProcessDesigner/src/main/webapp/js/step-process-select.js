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