layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        elem: '#customerLossList',
        url : ctx+'/customer_loss/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerLossListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'cusNo', title: '客户编号',align:"center"},
            {field: 'cusName', title: '客户名称',align:"center"},
            {field: 'cusManager', title: '客户经理',align:"center"},
            {field: 'lastOrderTime', title: '最后下单时间',align:"center"},
            {field: 'lossReason', title: '流失原因',align:"center"},
            {field: 'confirmLossTime', title: '确认流失时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#op"}
        ]]
    });

    $(".search_btn").on("click",function(){
        table.reload("customerLossListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                cusNo: $("input[name='cusNo']").val(),  //用户名
                cusName: $("input[name='cusName']").val(),  //邮箱
                state: $("#state").val()  //手机号
            }
        })
    });

    /**
     * 行监听
     */
    table.on("tool(customerLosses)", function (obj) {
        var layEvent = obj.event;
        if (layEvent === "info") {
            openCustomerLossInfoDialog(obj.data.id);
        } else if (layEvent === "add") {
            openCustomerLossDialog(obj.data.id);
        }
    });

    function openCustomerLossDialog(sid) {
        var url = ctx + "/customer_loss/info2?id="+sid;
        var title = "流失管理-暂缓管理";
        layui.layer.open({
            title: title,
            type: 2,
            area: ["900px", "700px"],
            maxmin: true,
            content: url
        });
    }

    function openCustomerLossInfoDialog(sid) {
        var url = ctx + "/customer_loss/info?id="+sid;
        var title = "流失管理-详情查看";
        layui.layer.open({
            title: title,
            type: 2,
            area: ["900px", "700px"],
            maxmin: true,
            content: url
        });
    }

});