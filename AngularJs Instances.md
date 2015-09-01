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


重点在于：

1. 每个 radio 只会有一个 value ，而不像 checkbox 根据是否 checked 有两个 value；

2. 此处 radio 通过设置input标签原生的 value 属性没有效果，要使用 anglar 封装过的ng-value；

3. 调试中发现的问题，ng-model 的值不能还有‘-’，即 ng-model="radio-value" 会报错。
