var graph = new joint.dia.Graph;

paper = new joint.dia.Paper({
    el:$('#processViewer'),
    width:"100%",
    height:600,
    model:graph,
    gridSize:1,
//    	elementView: glpElementView,
    defaultLink: new joint.shapes.sega.Link({
        attrs:{
            '.connection-wrap':{display:"none"},
            '.link-tools':{display:'none'},
        }
    }),
    linkView: joint.shapes.sega.LinkView,
    interactive: false,
    validateConnection: function(cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
        return false;
    },
    validateMagnet: function(cellView, magnet) {
        return false;
    }
})


var resize = function(){
    paper.setDimensions(
        (paper.getContentBBox().x+paper.getContentBBox().width),
        (paper.getContentBBox().y+paper.getContentBBox().height)
    );

};

$(function(){
    graph.fromJSON(process_json);

    $(window).resize(resize);
    resize();
});

graph.on("change", resize);