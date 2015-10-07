<fieldset class="artiform artifact_n" data-path="${path}">
	<template>
		<fieldset class="artiform artifact_n_instance" data-path="${path}">
			<script>
$(function(){
	var root = $("fieldset.artifact_n_instance[data-path='${path}']");
	root.find(".btn-remove").on("click", function(){
		root.remove();
	});
});
			</script>
			<h3>
				<div class="text">
					NEW ${text}
				</div>
				<div class="btn-legend btn-remove">
					<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				</div>
			</h3>
			${children}
		</fieldset>
	</template>
	<script>

$(function(){
	var root = $("fieldset.artifact_n[data-path='${path}']");
	var template = root.children("template").html();
	root.find(".btn-add").on("click", function(){
		var t = template.slice();
		var index = root.children("fieldset.artifact_n_instance").length;
		t = t.replace(/index_${id}/g, ""+index);
		root.append(t);
	});
	if(sega.FormInit){
		sega.FormInit.push(
			{	path:"${path}",
				addFunc: function(index){
					var t = template.slice();
					t.replace(/index_${id}/g, ""+index);
					root.append(t);
				}
			});
	}
});
	</script>
	<h3>
		<div class="text">
			${text}
		</div>
		<#if write>
		<div class="btn-legend btn-add">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		</div>
		</#if>
	</h3>
</fieldset>