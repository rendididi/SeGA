/**
 * Created by Administrator on 2015/7/28.
 */
$('#bTree').jstree({
    "plugins" : [ "search" ]
});
var to = false;
$('#btinput').keyup(function () {
    if(to) { clearTimeout(to); }
    to = setTimeout(function () {
        var v = $('#btinput').val();
        $('#bTree').jstree(true).search(v);
        if(v=="保障房申请"){
            $('#tplPanel').show();
        }
    }, 250);
});
function tplShow(){
    $('#tplPanel').show();
}

