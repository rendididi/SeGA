/**
 * Created by Administrator on 2015/7/27.
 */
require.config({
    paths: {
        geometry: '../js/vendor/geometry',
        vectorizer: '../js/vendor/vectorizer',
        jquery: '../js/vendor/jquery',
        lodash: '../js/vendor/lodash',
        backbone: '../js/vendor/backbone',
        joint: '../js/vendor/joint.all.clean'
    },
    shim: {
        vectorizer:{
            deps:['geometry'],
            exports:'V'
        },
        backbone: {
            //These script dependencies should be loaded before loading backbone.js.
            deps: ['lodash', 'jquery'],
            //Once loaded, use the global 'Backbone' as the module value.
            exports: 'Backbone'
        },
        joint: {
            deps: ['geometry', 'vectorizer', 'jquery', 'lodash', 'backbone'],
            exports: 'joint'
        },
        lodash: {
            exports: '_'
        }
    }
});

// Now we're ready to require JointJS and write our application code.
require(['vectorizer'], function(V) {

    alert(V)

   /* var graph = new joint.dia.Graph;
    var paper = new joint.dia.Paper({ width: 600, height: 400, model: graph });

    var elApp = document.getElementById('app');
    elApp.appendChild(paper.el);

    var rect = new joint.shapes.basic.Rect({
        position: { x: 50, y: 50 },
        size: { width: 100, height: 100 }
    });
    graph.addCell(rect);*/
});