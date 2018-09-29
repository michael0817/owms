$().ready(function() {
    laydate({
        elem: '#createDate'
    });
    $('#createDate').val(laydate.now());
});

// $.validator.setDefaults({
// 	submitHandler : function() {
// 		save();
// 	}
// });

// function save() {
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

function loadModuleList() {
    var html = "";
    $.ajax({
        url: '/order/module/listAll',
        async: false,
        success: function (data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
            	if(data[i].moduleType == '1'){
                    html += '<option value="' + data[i].moduleId + '">' + data[i].url + '</option>'
                }
            }
            $("#moduleList").append(html);
        }
    });


}