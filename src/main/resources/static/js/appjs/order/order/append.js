$().ready(function() {
    laydate({
        elem: '#createDate',
        done: function(){
            $('#importDate').text($('#createDate').val());
        }
    });
    $('#createDate').val(laydate.now());
    bindFileUpload();
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
                required : icon + "请选择订单文件夹"
            }
        }
    })
}

function bindFileClick(){
    $('#file').click();
}

function bindFileUpload(){
    $('#file').on("change", function(event) {
        var names = $('#file').val().split('\\');
        $('#showFile').val(names[names.length-1]);
    });
}

function formSubmit(){
    $('#submitBtn').on('click',function(){
        layer.confirm('导入日期为'+$('#createDate').val()+'的所有文件？', {
            btn : [ '确定', '取消' ]
        },function(){
            $("#importForm").ajaxSubmit(function(data){
                if (data.code == 0) {
                    parent.layer.alert(
                        "导入成功的文件：<br/>"+data.successList+"<br/>"+
                        "导入失败的订单：<br/>"+data.failedOrderList+"<br/>"+
                        "导入失败的文件：<br/>"+data.failedList+"<br/>"+
                        "忽略的文件：<br/>"+data.ignoreList+"<br/>"
                        ,{area: ['800px', '480px']});
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    parent.layer.alert(data.msg)
                }
            });
        });
    });


}
