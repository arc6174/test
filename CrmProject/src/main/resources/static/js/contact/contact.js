layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var tableIns = table.render({
        elem: '#contactList',
        url: ctx + '/contact/list?cusId='+$("input[name='cusId']").val(),
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [5,10],
        limit: 5,
        toolbar: "#toolbarDemo",
        id: "contactListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "left"},
            {field: 'contactTime', title: '记录时间',align:"center"},
            {field: 'address', title: '地点', align: 'center'},
            {field: 'overview', title: '概述', align: 'center'},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作', minWidth:150, templet:'#contactListBar',fixed:"right",align:"center"}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function(){
        table.reload("contactListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                address: $("input[name='address']").val(),
                cusId: $("input[name='cusId']").val()
            }
        })
    });

    //头工具栏事件
    table.on('toolbar(contact)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateContactDialog();
                break;
            case "del":
                delSaleChance(checkStatus.data);
                break;
        }
    });


    // /**
    //  * 行监听
    //  */
    table.on("tool(contact)", function (obj) {
        var layEvent = obj.event;
        if(layEvent === "edit") {
            openAddOrUpdateContactDialog(obj.data.id);
        }else if (layEvent === "del") {
            layer.confirm('确定删除当前数据？', {icon: 3, title: "联系人管理"}, function () {
                $.post(ctx + "/contact/delete", {ids: obj.data.id}, function (data) {
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
    function openAddOrUpdateContactDialog(id) {
        var url = ctx + "/contact/addOrUpdatePage?cusId="+$("input[name='cusId']").val();
        var title = "添加记录";
        if (id) {
            url = url + "&id=" + id;
            title = "更新记录";
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
                url: ctx + "/contact/delete",
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
