<div class="form-group row" data-path="${path}">
	<label class="col-md-3 control-label">
		${text}
	</label>
	<div class="col-md-9">
		<input type="text" name="id-${id}" class="form-control" <#if !write>disabled</#if> />
	</div>
</div>