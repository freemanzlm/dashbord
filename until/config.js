var EGD = EGD || {};
EGD.Model = {};
EGD.model = {};
EGD.View = {};
EGD.view = {};



function table(info){
    var lang = {
        "bProcessing" : true,
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "info": "",
        "sSearch": "搜索:",
        "sInfoPostFix": "",
        "aaSorting": [[5, 'desc']],
        "sEmptyTable": "没有符合条件的记录",   // 数据为空时显示的信息
        "sInfoEmpty": "", // 表格页脚显示多少条信息具体内容              
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页",
            "sJump": "跳转"
        }
    }
    $('#example').DataTable({
        "language":lang,
        "dom": 'tp',
        bFilter : true,
        bSort : true,
        bAutoWidth: false, 
        searching: false,
        data:info,
        bServerSide:false,
        sAjaxDataProp:"data",
        sPaginationType: 'full_numbers',
        "order": [[ 4, "asc" ]],
        // iDisplayLength: 6,
        // datasrc:"",
        "aLengthMenu": [5, 12],
        // initComplte:initComplte,//每刷新一次取消之前处理的状态
        // "deferRender": true,
        "bstatesave":true, 
        // "sAjaxSource":"http://127.0.0.1:8080/api/getegdmessage",
        "columns": [
            {"data":"ship_from"},
             {"data":'ship_to'}, 
             {"data":"SHPMT_MTHD"}, 
             {"data":"TXN_CNT_share",
             render:function(data, type, full, meta){
                if(data){
                    return full.TXN_CNT+" /<br>"+full.TXN_CNT_share
                }
                return "-"
             }
            }, 
             {"data":"GMV_USD",

                render:function(data, type, full, meta){
                    // console.log(data);
                    // if (type == "display") {
                    //     return "<a href=>"+data+"</a>";
                    // }
                    // if (type == 'sort') {
                    //     return data;
                    //     // if (data = 1) return 2;
                    //     // if (data = 2) return 1;
                    //     // return 1;
                    // }

                    // if () {
                    //     return 'filter';
                    // }
                    // console.log(data);
                    if(data){
                        return full.GMV_USD+" /<br>"+full.GMV_USD_share
                    }
                    return "-"
                }
            },
            {"data":"PD_97"},
            {"data":"egd_set_day",
                render:function(data){
                    if(data){
                        return data 
                    }
                    return "未设置"
                }
            },
            {"data":"egd_rate"},
            {   "data":null,
                "defaultContent":'<ul class="performance_analysis"></ul>',
                // render:function(data, type, full){
                //     return '<ul class="performance_analysis"></ul>'
                // }
            }
        ],

        // "aoColumns":["ship_from","ship_to","SHPMT_MTHD","TXN_CNT_share","GMV_USD_share","PD_97","egd_set_day","egd_rate"],
        "columnDefs": [
            // {
            //     "data":null
            // },
            // { "sWidth": "185px", "aTargets": [0,1,2,3,4,5,6,7,8] },
            {
             "aTargets": [ 0 ] ,
            //  "data":"ship_from",
            bSortable : true,
            bSearchable : false,
            // sClass: 'text-right',
            "sWidth": "85px",
            "sClass":"tb"
            }, 
            {
            "aTargets": [ 1 ] ,
            "sWidth": "115px", 
            "sClass":"tb"            
             }, 
            {
            "aTargets": [ 2 ] ,
            "sWidth": "130px",
            "sClass":"tb"            
            }, 
            {
            "aTargets": [ 3 ] ,
            "sWidth": "95px",
            }, 
            {
            "aTargets": [ 4 ] ,
            "sWidth": "95px"            
            },
            {
            "aTargets": [ 5 ] ,
            "sWidth": "137px",
            },
            {
            "aTargets": [ 6 ] ,
            "sWidth": "80px",
            },
            {
            "aTargets": [ 7 ] ,
            "sWidth": "108px", 
                "render":function(data,type,row){
                    if(data){
                        if(row.egd_rate<97){
                            return '<span style="color:red">'+data+'%</span>'
                        }else{
                            return data + "%"
                        }
                    }
                    return "-"
                }           
            },
            {
           targets:[8] ,
            "data":null,
            "sWidth": "215px", 
            "sClass":"analysis",
            "render":function(data, type, row){
                        var content1 = content2 = content3 = content4 =content5 = "";

                         if(row.egd_set_day ==0||""){
                            if(row.PD_97 <= 4){
                                content3 = '<li style="color:#00b050;font-weight:800" >可设置EGD='+ row.PD_97 +'天</li>';
                            }else{
                                content2 = "<li>当前海外仓未能有效覆盖</li>"
                                           +'<li><a href="javascript:;">点击查看可以有效覆盖的海外仓</a></li>'
                            }
                        }else if (row.egd_set_day > 0){
                            if(row.PD_97 <= 4 && row.PD_97 <= row.egd_set_day){
                                content1 = "<span>表现良好,继续加油！</span>";
                            }else{
                                    if (row.PD_97 <= 4 && row.PD_97 > row.egd_set_day){
                                        content3 = '<li style="color:#00b050;font-weight:800" >可设置EGD='+ row.PD_97 +'天</li>';
                                    }
                                    if (row.PD_97 > 4){
                                        content2 = "<li>当前海外仓未能有效覆盖</li>"
                                                     +'<li><a href="javascript:;">点击查看可以有效覆盖的海外仓</a></li>'
                                    }
                                    if( row.WH_AP_time > 1 ){
                                        content4 = '<li>请注意将出仓时间控制在1天内，目前' + row.WH_AP_time + '天</li>'
                                    }
                                    if (row.PD_97 - row.egd_set_day >=2){
                                        content5 = "<li>请合理设置EGD，避免影响账户表现</li>"
                                    }
                           
                            }
                        }

                        return content1?content1:content1+'<ul class="performance_analysis">' + content2 + content3 + content4 + content5 + '</ul>'
                }           
            },
            
        ],
    });
}