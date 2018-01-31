;(function () {
    $("th").css({
        "height": "60px",
        "cursor": "pointer"
    });
    // $("th").off().on("click", function () {});
    $("#example_length").css({"display":"none"});
    var lang = {
            "bProcessing" : true,
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "info": "",
            "sEmptyTable": "没有符合条件的记录",   // 数据为空时显示的信息
            "sInfoEmpty": "", // 表格页脚显示多少条信息具体内容                                            
            "oPaginate": {
                "sFirst": "第一页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "最后一页",
            },
    }

    var dt = $('#example').dataTable({
        // language : {
        // "sProcessing": "处理中...",
        // "sLengthMenu": "每页 _MENU_ 项",
        // "sZeroRecords": "没有匹配结果",
        // "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        // "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
        // "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        // "sInfoPostFix": "",
        // "sSearch": "搜索:",
        // // "sUrl": "",
        // "sLoadingRecords": "载入中...",
        // "sInfoThousands": ",",
        // "oPaginate": {
        //     "sFirst": "首页",
        //     "sPrevious": "上页",
        //     "sNext": "下页",
        //     "sLast": "末页",
        //     "sJump": "跳转"
        // }},

        "language":lang,

        columnDefs: [{
            "targets": 'nosort',  //列的样式名
            "orderable": false    //包含上样式名‘nosort’的禁止排序
        }],

        // 'ordering': false,
        bautoWidth: false, 
        searching: false,
        "aLengthMenu": [5, 12],
        bFilter : true,
        bsort:false,
        // "sAjaxSource": "../../json/transactions.json" ,
    });

    // var dt = $('#example').DataTable();
    // $("#example_length").hide();
    // $("#example_paginate").hide();

    // initcomplete:function(){

    $(".GMV_USD_sort a").on("click", function () {
        alert(1);
        //         "aoColumnDefs": [
        //   { "aDataSort": [ 0, 1 ], "aTargets": [ 0 ] },
        //   { "aDataSort": [ 1, 0 ], "aTargets": [ 1 ] },
        //   { "aDataSort": [ 2, 3, 4 ], "aTargets": [ 2 ] }
        // ]

        // 清除所有数据
        $('#example').dataTable().fnClearTable()

        // dt.api({
        //     aoColumnDefs: {
        //         "bSortable": true,
        //         "aTargets": [3],
        //         "asSorting": ["asc"],
        //         "aTargets": [3]
        //     }
        // })
        // dt.aoColumnDefs=[
        // ]


        // dt.order([
        //     [3, "asc"]
        // ]).draw();
        $("#example_paginate").hide();

    })
    // }
    $("#en").on("click", function () {
        $("#example_paginate").show();

    })


})(jQuery)