layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //订单列表展示
    var tableIns = table.render({
        elem: '#customerReprieveList',
        url: ctx + '/cus_reprieve/list?lossId=' + $("input[name='id']").val(),
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "customerReprieveListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'measure', title: "暂缓措施", align: "center"},
            {field: 'createDate', title: '创建时间', align: "center"},
            {field: 'updateDate', title: '更新时间', align: "center"},
            {title: '操作', minWidth:150, templet:'#customerReprieveListBar',fixed:"right",align:"center"}
        ]]
    });

    //头工具栏事件
    table.on('toolbar(customerReprieve)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateReprieveDialog();
                break;
            case "del":
                delSaleChance(checkStatus.data);
                break;
            case "liu":
                openLiuDialog();
                break;
        }
    });


    // /**
    //  * 行监听
    //  */
    table.on("tool(customerReprieve)", function (obj) {
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateReprieveDialog(obj.data.id);
        } else if (layEvent === "del") {
            layer.confirm('确定删除当前数据？', {icon: 3, title: "联系人管理"}, function () {
                $.post(ctx + "/cus_reprieve/delete", {ids: obj.data.id}, function (data) {
                    if (data.code == 200) {
                        layer.msg("操作成功！");
                        tableIns.reload();
                    } else {
                        layer.msg(data.msg, {icon: 5});
                    }
                });
            })
        }
    });

    function openLiuDialog(id) {
        var url = ctx + "/customer_loss/confirm?id=" + $("input[name='id']").val()
        var title = "添加联系人";
        if (id) {
            url = url + "&id=" + id;
            title = "更新联系人";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["700px", "560px"],
            maxmin: true,
            content: url
        });
    }

    //打开添加机会数据页面
    function openAddOrUpdateReprieveDialog(id) {
        var url = ctx + "/cus_reprieve/addOrUpdatePage?lossId=" + $("input[name='id']").val()
        var title = "添加联系人";
        if (id) {
            url = url + "&id=" + id;
            title = "更新联系人";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["700px", "560px"],
            maxmin: true,
            content: url
        });
    }


    // /**
    //  * 批量删除
    //  * @param datas
    //  */
    function delSaleChance(datas) {
        if (datas.length == 0) {
            layer.msg("请选择删除记录!", {icon: 5});
            return;
        }
        layer.confirm('确定删除选中的机会数据？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.close(index);
            var ids = "ids=";
            for (var i = 0; i < datas.length; i++) {
                if (i < datas.length - 1) {
                    ids = ids + datas[i].id + "&ids=";
                } else {
                    ids = ids + datas[i].id
                }
            }
            $.ajax({
                type: "post",
                url: ctx + "/cus_reprieve/delete",
                data: ids,
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        layer.msg("操作成功！");
                        tableIns.reload();

                    } else {
                        layer.msg(data.msg, {icon: 5});
                    }
                }
            })
        });
    }
});