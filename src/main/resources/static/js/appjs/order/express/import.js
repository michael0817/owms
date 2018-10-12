$().ready(function() {
    laydate({
        elem: '#createDate'
    });
    $('#createDate').val(laydate.now());
    $('#file').on('change',function(){
        var arr=$('#file').val().split('\\');
        var filename=arr[arr.length-1];
        $('#showFile').val(filename);
    });
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
                required : icon + "请选择运单文件"
            }
        }
    })
}

function bindFileClick(){
    $('#file').click();
}