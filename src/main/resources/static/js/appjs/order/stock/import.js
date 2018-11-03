$().ready(function() {
    $('#file').on('change',function(){
        var arr=$('#file').val().split('\\');
            var filename=arr[arr.length-1];
        $('#showFile').val(filename);
    });
    formSubmit();
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#importForm").validate({
        rules : {
            file : {
                required : true
            }
        },
        messages : {
            file : {
                required : icon + "请选择库存文件"
            }
        }
    })
}

function bindFileClick(){
    $('#file').click();
}

function formSubmit(){
    $('#submitBtn').on('click',function() {
        $("#importForm").validate();
        $("#importForm").ajaxSubmit(function (data) {
            if (data.code == 0) {
                parent.layer.alert(
                    "导入成功"+"<br/>"+
                    "导入失败的SKU：<br/>"+data.failedStockList+"<br/>"
                    , {area: ['600px', '320px']});
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }
        });
    });
}