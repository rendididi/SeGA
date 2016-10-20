<form class="form-panel f-cb" id="task-attrs">
	<div class="container">
		<div class="col-md-2">
			<div class="form-group">
        		<label for="taskName">名称:</label>
        		<input type="text" class="form-control"  id="taskName">
   			</div>
    		<!-- <div class="form-group">
        		<label for="taskid">id:</label>
        		<input type="text" id="taskid" class="form-control" readonly>
    		</div> -->
		</div>
		<div class="col-md-2">
			<div class="form-group">
        		<label for="taskdsp">描述:</label>
        		<input type="text" id="taskdsp" class="form-control"></input>
    		</div>
		</div>
		<div class="col-md-2">
			<div class="form-group">
        		<label for="splitmode">分离模式:</label>
        		<select class="form-control" id="splitMode">
            		<option>--</option>
            		<option>且</option>
            		<option>或</option>
            		<option>异或</option>
        		</select>
    		</div>

		</div>
		

		<div class="col-md-2">
			<div class="form-group">
        		<label for="joinmode">聚合模式:</label>
        		<select class="form-control" id="joinMode">
           			<option>--</option>
            		<option>且</option>
            		<option>或</option>
            		<option>异或</option>
        		</select>
    		</div>
		</div>

	</div>
</form>

<form class="form-panel f-cb" id="link-attrs">
	<div class="container">
		<div class="col-md-3">
			<div class="form-group">
        		<label for="linkId">id:</label>
        		<input type="text" class="form-control"  id="linkId" readonly>
    		</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
        		<label for="sourceId">资源Id:</label>
        		<input type="text" class="form-control"  id="sourceId" readonly>
    		</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
        		<label for="targetId">资源Id:</label>
        		<input type="text" class="form-control"  id="targetId" readonly>
    		</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
        		<label for="expression">表达式:</label>
        		<input type="text" class="form-control"  id="expression" >
   			</div>
		</div>
	</div>


</form>