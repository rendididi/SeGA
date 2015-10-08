<fieldset class="artiform artifact_n" data-path="${path}.${id}" ng-init="entity.${path}.${id}=[]" >
	
	<h3>
		<div class="text">
			${text}
		</div>
		<#if write>
		<div class="btn-legend btn-add" data-path="${path}.${id}" ng-click="addEntity('${pathForClick}.${id}')">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		</div>
		</#if>
	</h3>

	<fieldset class="artiform artifact_n_instance" data-path="${path}.${id}" ng-repeat="instance_${id} in entity.${path}.${id}">
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

</fieldset>