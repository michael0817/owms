$().ready(function() {
    laydate({
        elem: '#createDate'
    });
    $('#createDate').val(laydate.now());
    bindFileUpload();
    $("#importForm").ajaxForm(function(data){
        if (data.code == 0) {
            parent.layer.alert(
                "导入成功的文件：<br/>"+data.successList+"<br/>"+
                "导入失败的订单：<br/>"+data.failedOrderList+"<br/>"+
                "导入失败的文件：<br/>"+data.failedList+"<br/>"+
                "忽略的文件：<br/>"+data.ignoreList+"<br/>"
            ,{area: ['800px', '600px']});
            parent.reLoad();
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);

        } else {
            parent.layer.alert(data.msg)
        }
    });
});

// $.validator.setDefaults({
// 	submitHandler : function() {
// 		save();
// 	}
// });

// function upload() {
// 	$.ajax({
// 		cache : true,
// 		type : "POST",
// 		url : "/order/order/upload",
// 		data : $('#importForm').serialize(),// 你的formid
// 		async : false,
// 		error : function(request) {
// 			parent.layer.alert("Connection error");
// 		},
// 		success : function(data) {
// 			if (data.code == 0) {
// 				parent.layer.msg("操作成功");
// 				parent.reLoad();
// 				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
// 				parent.layer.close(index);
//
// 			} else {
// 				parent.layer.alert(data.msg)
// 			}
//
// 		}
// 	});
// }

// function subimtBtn() {
//     var options  = {
//         url:'order/order/upload',
//         type:'post',
//         success:function(data)
//         {
//             alert(data);
//         }
//     };
//     $('#importForm').submit(function(){
//         $(this).ajaxSubmit(options);
//         return false;   //防止表单自动提交
//     });
//     //$("#fileForm").submit();  
// }

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
