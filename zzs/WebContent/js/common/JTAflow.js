/**
 * Created by Administrator on 2015/7/21.
 */
;(function(){
    function JTAflow(){
        var Process ={
            _id:"",
            _name:'',
            _description:'',
            read:[],
            write:[],
            syncPoint:false,
            joinMode:'',
            spliteMode:'',
            set name(name){
                this._name=name;
            },
            get name(){
                return this._name;
            },
            set id(id){

            },
            get id(){
                return this._id;
            },
            set description(dsp){
                this._description=dsp;
            },
            get description(){
                return this._description;
            },
            toJson:function(){

            },
            fromJson:function(){

            }
        };
    }

})()
