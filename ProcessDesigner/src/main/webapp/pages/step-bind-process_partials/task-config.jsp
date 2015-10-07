<div class="modal modal-bind fade bs-example-modal-lg" tabindex="-1" role="dialog" id="config-modal" aria-labelledby="myLargeModalLabel"  >
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#RW-tab" aria-controls="RW" role="tab" data-toggle="tab">Read and Write</a></li>
                    <li role="presentation"><a id="action-title" href="#service-tab" aria-controls="service" role="tab" data-toggle="tab">Sevice</a></li>

                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="RW-tab">
                        <div id="entity_tree"></div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="service-tab">
                        <div class="web-service">
                            <h2>Restful Service Url</h2>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">http://</span>
                                <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1">
                            </div>
                            <h2>Restful Service Parameters</h2>
                            <div class=input-group ws-params">
                            </div>              
                        </div>
                        <div class="human-task">
                            <h2>Application Interface</h2>
                            <div class="check on">
                                <span class="glyphicon glyphicon-ok check-box"></span>
                                <span class="check-text">Auto Generate</span>
                                <input type="checkbox" checked="checked" />
                            </div>
                        </div>

                        <div class="sync-point">
                            <h2>Entity-EDB Sync Point</h2>
                            <div class="check on">
                                <span class="glyphicon glyphicon-ok check-box"></span>
                                <span class="check-text">Sync</span>
                                <input type="checkbox" checked="checked" />
                            </div>
                        </div>

                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <!--<button type="button" class="btn btn-primary">Save changes</button>-->
            </div>


        </div>
    </div>
</div>