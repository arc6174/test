layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var tableIns = table.render({
        elem: '#LinkManList',
        url: ctx + '/linkman/list?cusId='+$("input[name='cusId']").val(),
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [5,10],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "LinkManListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "left"},
            {field: 'linkName', title: '联系人姓名', align: 'center'},
            {field: 'sex', title: '性别', align: 'center'},
            {field: 'zhiwei', title: '职位', align: 'center'},
            {field: 'officePhone', title: '办公电话', align: 'center'},
            {field: 'phone', title: '电话', align: 'center'},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作', minWidth:150, templet:'#linkmanListBar',fixed:"right",align:"center"}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function(){
        table.reload("LinkManListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                linkName: $("input[name='linkName']").val(),
                cusId: $("input[name='cusId']").val()
            }
        })
    });

    //头工具栏事件
    table.on('toolbar(linkMan)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateLinkManDialog();
                break;
            case "del":
                delSaleChance(checkStatus.data);
                break;
        }
    });


    // /**
    //  * 行监听
    //  */
    table.on("tool(linkMan)", function (obj) {
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateLinkManDialog(obj.data.id);
        }else if (layEvent === "del") {
            layer.confirm('确定删除当前数据？', {icon: 3, title: "联系人管理"}, function () {
                $.post(ctx + "/linkman/delete", {ids: obj.data.id}, function (data) {
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


    //打开添加机会数据页面
    function openAddOrUpdateLinkManDialog(id) {
        var url = ctx + "/linkman/addOrUpdatePage?cusId="+$("input[name='cusId']").val();
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
                url: ctx + "/linkman/delete",
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
