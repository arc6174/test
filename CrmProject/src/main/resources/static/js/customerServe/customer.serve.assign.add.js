layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    // 初始化分配用户下拉框
    $.post(ctx+"/user/queryAllCustomerManager",function (res) {
        for (var i = 0; i < res.length; i++) {
            $("#assigner").append("<option value=\"" + res[i].id + "\">" + res[i].name + "</option>");
        }
        //重新渲染
        layui.form.render("select");
    });

    form.on("submit(addOrUpdateCustomerServe)", function (data) {
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //弹出loading
        $.post(ctx + "/customer_serve/saveOrUpdateCustomerServe", data.field, function (res) {
            if (res.code == 200) {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("操作成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                layer.msg(
                    res.msg, {
                        icon: 5
                    }
                );
            }
        });
        return false;
    });
});