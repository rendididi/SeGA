<div class="row" data-path="${currentPath}" <#if !read>ng-init="entity.${currentPath}={}"</#if> >
	<label class="col-md-3 control-label">
		${text}
	</label>
	<div class="col-md-9">
		${children}
	</div>
</div>