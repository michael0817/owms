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
    document.getElementById("file").addEventListener("change", function(event) {
        let files = event.target.files;
        var count = files.length;
        if(count == 0){
            return;
        }
        if(count == 1){
            $('#showFile').val(files[0].webkitRelativePath);
        }else{
            $('#showFile').val(count+'个文件');
        }
    });
}

function formSubmit(){
    $('#submitBtn').on('click',function(){
        $("#importForm").validate();
        layer.confirm('导入日期为'+$('#createDate').val()+'的所有文件？', {
            btn : [ '确定', '取消' ]
        },function(){
            $("#importForm").ajaxSubmit(function(data){
                if (data.code == 0) {
                    parent.layer.alert(
                        "导入成功的文件：<br/>"+data.successList+"<br/>"+
                        "重复的订单：<br/>"+data.failedOrderList+"<br/>"+
                        "导入失败的文件：<br/>"+data.failedList+"<br/>"+
                        "文件名不匹配：<br/>"+data.ignoreList+"<br/>"
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
