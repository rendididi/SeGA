<form class="form-panel f-cb">
			<div id="task-attrs">
				<div class="col-md-2">
					<div class="form-group">
                		<label for="taskName">name:</label>
                		<input type="text" class="form-control"  id="taskName">
           			</div>
            		<!-- <div class="form-group">
                		<label for="taskid">id:</label>
                		<input type="text" id="taskid" class="form-control" readonly>
            		</div> -->
				</div>
				<div class="col-md-2">
					<div class="form-group">
						<label for="taskid">id:</label>
						<input type="text" id="taskid" class="form-control" readonly>
					</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
                		<label for="taskdsp">description:</label>
                		<input type="text" id="taskdsp" class="form-control"></input>
            		</div>
				</div>
				<div class="col-md-2">
					<div class="form-group">
                		<label for="splitmode">SplitMode:</label>
                		<select class="form-control" id="splitMode">
                    		<option>--</option>
                    		<option>AND</option>
                    		<option>OR</option>
                    		<option>XOR</option>
                		</select>
            		</div>

				</div>
				

				<div class="col-md-2">
					<div class="form-group">
                		<label for="joinmode">JoinMode:</label>
                		<select class="form-control" id="joinMode">
                   			<option>--</option>
                    		<option>AND</option>
                    		<option>OR</option>
                    		<option>XOR</option>
                		</select>
            		</div>
				</div>

				<div class="col-md-2 syncPoint-block">
					<label class="syncPoint">
						<input type="checkbox" id="syncPoint" >Sync Point
					</label>					

				</div>
			</div>
			<div id="link-attrs">
				<div class="col-md-3">
					<div class="form-group">
                		<label for="linkId">id:</label>
                		<input type="text" class="form-control"  id="linkId" readonly>
            		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                		<label for="sourceId">sourceId:</label>
                		<input type="text" class="form-control"  id="sourceId" readonly>
            		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                		<label for="targetId">sourceId:</label>
                		<input type="text" class="form-control"  id="targetId" readonly>
            		</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
                		<label for="expression">Expression:</label>
                		<input type="text" class="form-control"  id="expression" >
           			</div>
				</div>
			</div>


		</form>