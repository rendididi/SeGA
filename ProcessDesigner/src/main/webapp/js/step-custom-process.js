;var step4=(function(){
	// init
	var globleData="";

	var graph = new joint.dia.Graph;
	var current_selected='';//current selected cell

	var paper = new joint.dia.Paper({
    	el:$('#draw'),
    	width:"100%",
    	height:600,
    	model:graph,
    	gridSize:1,
//    	elementView: glpElementView,
    	defaultLink: new joint.shapes.sega.Link(),
    	linkView: joint.shapes.sega.LinkView,
    	interactive: {"vertexAdd": false},
    	validateConnection: function(cellViewS, magnetS, cellViewT, magnetT, end, linkView) {
            // Prevent loop linking
            
            return (magnetS !== magnetT);
    	}
	});
	
	graph.fromJSON(process_json);
	
  var resize = function(){
    paper.setDimensions(
      Math.max(document.body.clientWidth, paper.getContentBBox().x+paper.getContentBBox().width+600), 
      Math.max(document.body.clientHeight - $("#draw").offset().top,paper.getContentBBox().y+paper.getContentBBox().height+450)
    );
    $(".paper-container").height(document.body.clientHeight - $(".paper-container").offset().top);
  };

  $(function(){
    paper.setOrigin($(".step-title2").offset().left, 0);
    $(window).resize(resize);
    resize();
  });

	graph.on("change", resize);
	
	var taskTool=document.getElementById("taskShape");
	var startTool = document.getElementById("startShape");
	var endTool = document.getElementById("endShape");
	taskTool.ondragend=dragEvent;
	startTool.ondragend=dragEvent;
	endTool.ondragend=dragEvent;

	function dragEvent(ev){

		var x=ev.pageX-$("#draw").offset().left- paper.options.origin.x + $("#draw").scrollLeft();
  	var y=ev.pageY-$("#draw").offset().top-paper.options.origin.y + $("#draw").scrollTop();
  	var newCell='';
  	var elements = graph.getElements();
  	var startNum=0;
  	var endNum=0;

  	for(var i=0;i<elements.length;i++){
  		if(elements[i].prop("type")=="sega.Start"){
  			startNum++
  		}else if(elements[i].prop("type")=="sega.End"){
  			endNum++;
  		}
  	}

  	switch(ev.target.attributes.id.value){
  		case "taskShape":
  			newCell= new joint.shapes.sega.Task({
      			position : {x: x-75,y: y-30}
 				});
 				break;
 			case "startShape":
 				if(startNum==0){
 					newCell= new joint.shapes.sega.Start({
      				position : {x: x-17,y: y-17}
 					});
 				}else{
 					alert("Start Node is exist");
 				}
  			
 				break;
 			case "endShape":

 					newCell= new joint.shapes.sega.End({
      				position : {x: x-17,y: y-17}
 					});

 				break;

  	}
  	graph.addCell(newCell);
	}

	$(document).keyup(function(e){
  	if(e.keyCode==46&&current_selected!=''){
  		current_selected.model.remove();
  		current='';
  		$('#task-attrs').removeClass("active");
  		$('#link-attrs').removeClass("active");
  	}
  })


	/*click event*/
	paper.on('cell:pointerdown',function(evt,x,y){
    	if(current_selected!=""){
        	current_selected.unhighlight();
    	}
    	evt.highlight();
    	current_selected=evt;;
    if(evt.model.isLink()){
        $('#link-attrs').addClass("active");
        $('#task-attrs').removeClass("active");
        $('#linkId').val(evt.model.id);
        $('#sourceId').val(evt.model.attributes.source.id);
        $('#targetId').val(evt.model.attributes.target.id);


    }else if(evt.model.prop("type")=="sega.Task"){

        $('#taskid').val(evt.model.id);
        $('#link-attrs').removeClass("active");
        $('#task-attrs').addClass("active");
        $('#taskName').val(evt.model.attributes.attrs['.label'].text);
/*        $('#splitMode').children("option").each(function(){
            if($(this).text()=='XOR'){
                this.selected="selected"
            }
        });*/
        $('#taskdsp').val(evt.model.attr("data").description);
        $('#splitMode').children("option").each(function(){
            if($(this).text()==evt.model.attr("data").splitemode){
                this.selected="selected"
            }
        });
        $('#joinMode').children("option").each(function(){
            if($(this).text()==evt.model.attr("data").jointmode){
                this.selected="selected"
            }
        })

    }
  /*  alert(JSON.stringify(evt.model.toJSON()))*/
  })

	paper.on('blank:pointerdown',function(evt,x,y){
		if(current_selected!=""){
			current_selected.unhighlight();
			current_selected='';
			$('#task-attrs').removeClass("active");
			$('#link-attrs').removeClass("active");
		}
	})


  $(".form-panel").bind("mousedown",function(event){
    event.stopPropagation();
  });

	/*set attrs*/

  var setTaskAttr = function(){
    current_selected.model.setText($('#taskName').val());
    current_selected.model.attr("data").name=$('#taskName').val();
    //console.log(current_selected.model.attr("data/splitemode",$('#splitMode').val()));
    $('#joinMode').val();
    current_selected.model.attr("data/jointmode",$('#joinMode').val());
    current_selected.model.attr("data/description",$('#taskdsp').val());
  };

	$('#taskName').bind("blur focusout change keyup paste", setTaskAttr);
	$('#splitMode').bind("blur focusout change keyup paste", setTaskAttr);
	$('#joinMode').bind("blur focusout change keyup paste", setTaskAttr);
	$('#taskdsp').bind("blur focusout change keyup paste", setTaskAttr);

  var setListAttr = function(){
    current_selected.model.prop("expression",$("#expression").val());
  };

	$("#expression").bind("blur focusout change keyup paste", setListAttr);




	$("button#btn_viewjson,button#btn-navbar-submit").on("click",function(e){
		var json = JSON.stringify(graph.toJSON());
		var svg = paper.exportSvg();
		$("#modal_viewjson .modal-body p").html(json);
		$("#modal_viewjson input#processJSON").val(json);
		$("#modal_viewjson input#input-svg").val(svg);
		$("#modal_viewjson").modal();
	});
	function getcc(){
		return current_selected;
	}
	return {
		current_selected:getcc
	}
})();