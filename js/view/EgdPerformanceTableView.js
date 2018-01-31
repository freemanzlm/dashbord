EGD.View = Backbone.View.extend({
    el:"body",
    model:EGD.model.GetTable,
    initialize:function(){
        this.onload();   
        this.render();
    },
    // render:function(){
    //     this.$el.html(template("EGD_Datas",table))
    // },
    events:{
        "click .GMV_USD_sort":"topFive",
        "click .egd_set_sort":"problemLine",
        "click .egd_unset_sort":"highQualityLine"
    },
    onload:function(){
        var _this = this;
        this.model.fetch({
            url:"http://127.0.0.1:8080/api/getegdmessage",
            success:function(model,res){
                console.log(model);
                _this.model.set("data",res.data);
                table(res.data);
                _this.topFive();
            }
        })
    },
    topFive:function(){
        $(".GMV_USD_sort").parent().addClass("active").siblings().removeClass("active");
        var arr = this.model.toJSON().data;        
        // var newArr = arr.filter(function(v,i){
        //     if (v.)
        // })

        console.log(arr);
        var newArr = Arr = null;

        // for(var i = 1; i <=arr.length; i++ ){
        //     if(newArr.length <=5){
        //         if(arr[i].GMV_USD_share >= arr[ i-1 ].GMV_USD_share){
        //                  newArr.push(arr[i])                
        //          }
        //     }

           
            // console.log(arr[newArr.length].GMV_USD_share);
        // }

        // 筛选出交易额比重最高的前5个内容
       newArr = arr.sort(function(a,b){return a.GMV_USD_share - b.GMV_USD_share});
    
       Arr = newArr.reverse().slice(0,5);

        $('#example').DataTable().clear().rows.add(Arr).draw();

    },
    problemLine:function(){
      $(".egd_set_sort").parent().addClass("active").siblings().removeClass("active");
        var arr = this.model.toJSON().data;
       var newArr = arr.filter(function(v,i){
          return !(v.egd_set_day >0) && !(v.egd_set_day <=4) && !(v.PD_97<=v.egd_set_day)
      });
      console.log(newArr);
    //   $('#example').DataTable().destroy();
    // 初始化表格并添加数据
    $('#example').DataTable().clear().rows.add(newArr).draw()
    
    },
    highQualityLine:function(arg){
       $(".egd_unset_sort").parent().addClass("active").siblings().removeClass("active");              
        var arr = this.model.toJSON().data;
       var newArr = arr.filter(function(v,i){
           return (v.egd_set_day ==0||"")&&v.egd_4d_flag <= 4
       })
       $('#example').DataTable().clear().rows.add(newArr).draw()


    }
    // 其他几个也是一样
})

EGD.view = new EGD.View();