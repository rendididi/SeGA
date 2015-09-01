1. 通过 radio 控制节点的显示，以及表单控件的disable属性。

```html

<lable>
    <input type="radio" ng-value="true" name="radio-test" ng-model="radioValue" />    
    启用
</lable>
<lable>
    <input type="radio" ng-value="false" name="radio-test" ng-model="radioValue"/>
    禁用
</lable>
<input type="text" ng-disabled="!radioValue" ng-model="text" ng-init="text='hello world'">
<p ng-show="radioValue">
    {{text}}
</p>
 
```

