    <div class="modal fade" id="read_write_modal" tabindex="-1" role="dialog" aria-labelledby="read_write_modal" ng-app="dataModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">数据</h4>
                </div>
                <div class="modal-body data-modal" ng-controller="entityController">
        <script id="listItem" type="text/ng-template">
        <li class="list-group-item" ng-repeat="item in data">
            <h3 ng-if="item.type=='artifact'||item.type=='artifact_n'">{{item.name}}</h3>
            <div ng-if="!item.type||item.type=='key'||item.type=='group'">
                    {{item.name}}
                    <div class="data-label-group">
                        <label>
                            <input type="checkbox" ng-binding='item.read' ng-model='item.read' ng-true-value="true" ng-false-value="false" ng-checked="{{item.read}}">Read
                        </label>
                        <label>
                            <input type="checkbox" ng-binding="item.write" ng-model='item.write' ng-true-value="true" ng-false-value="false">Write
                        </label>
                    </div>

            </div>
            <ul class="list-group" ng-if="item.type=='artifact'||item.type=='artifact_n'" ng-include="'listItem'" ng-init="data=item.children"></ul>

        </li>
        </script>
                    <ul class="list-group" ng-include="'listItem'"></ul>
                    <%--<ul>--%>
                        <%--<li ng-repeat="item in data">{{item.name}}</li>--%>
                    <%--</ul>--%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">确定</button>
                </div>
            </div>
        </div>
    </div>

