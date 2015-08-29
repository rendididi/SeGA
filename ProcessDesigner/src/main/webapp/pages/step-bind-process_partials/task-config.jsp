<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" id="config-modal" aria-labelledby="myLargeModalLabel">
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
                    <li role="presentation"><a href="#service-tab" aria-controls="service" role="tab" data-toggle="tab">Sevice</a></li>

                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="RW-tab">1</div>
                    <div role="tabpanel" class="tab-pane" id="service-tab">
                        <%@include file="/pages/step-bind-process_partials/service-pane.jsp" %>
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