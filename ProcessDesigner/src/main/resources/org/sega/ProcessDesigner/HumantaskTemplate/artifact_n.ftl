<fieldset class="artiform artifact_n" data-path="${path}.${id}" <#if !read>ng-init="entity.${path}.${id}=[]"</#if> >
	
	<h3>
		<div class="text">
			${text}
		</div>
		<#if write>
		<div class="btn-legend btn-add" data-path="${path}.${id}" ng-click="entity.${path}.${id}.push({})">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		</div>
		</#if>
	</h3>

	<fieldset class="artiform artifact_n_instance" data-path="${path}.${id}" ng-repeat="instance_${id} in entity.${path}.${id} track by $index">
			<h3>
				<div class="text">
					NEW ${text}
				</div>
				<div class="btn-legend btn-remove" ng-click="entity.${currentPathWithoutLastIndex}.splice($index,1)">
					<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				</div>
			</h3>
			${children}
	</fieldset>

</fieldset>