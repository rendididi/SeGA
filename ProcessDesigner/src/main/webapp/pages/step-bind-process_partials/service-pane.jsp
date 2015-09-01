
    <label>
        <input  type="radio" name="service-radio" ng-value="true"  ng-model="serviceType" ng-checked="true">
        Web Service
    </label>



<ul class="list-group" ng-show="serviceType">
    <li class="list-group-item">
       <label>
           <input type="checkbox" ng-disabled="!serviceType">Web Service 1
       </label>
    </li>
</ul>
    <br>
        <label>
            <input type="radio" name="service-radio" ng-value="false"   ng-model="serviceType">
            Manual Service
        </label>

            <div class="well" ng-show="!serviceType">
                <h4><button class="btn btn-default" ng-disabled="serviceType" ng-init='serviceType="true"'>Auto Generating</button></h4>
                <h4>Exist Manual Service</h4>
                <ul class="list-group">
                    <li class="list-group-item">Manual Service 1</li>
                </ul>
            </div>


