<fieldset class="artiform artifact" data-path="${currentPath}" <#if !read>ng-init="entity.${currentPath}={}"</#if> >
	<h3>
		${text}
	</h3>
	${children}
</fieldset>