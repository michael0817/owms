$().ready(function() {
    loadModuleType();
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/order/module/save",
		data : $('#moduleForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#moduleForm").validate({
		rules : {
            prefix : {
				required : true
			},
            url : {
                required : true
            }
		},
		messages : {
			prefix : {
				required : icon + "请输入前缀名"
			},
            url : {
                required : icon + "请上传模板"
            }
		}
	})
}
layui.use('upload', function () {
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#uploadModule', //绑定元素
        url: '/order/module/upload', //上传接口
        size: 1000,
        accept: 'file',
        exts: 'xls|xlsx',
        done: function (r) {
            layer.msg(r.msg);
            $('#url').val(r.fileName);
        },
        error: function (r) {
            layer.msg(r.msg);
        }
    });
});

function loadModuleType() {
    var html = "";
    $.ajax({
        url: '/common/dict/list/order_module',
        success: function (data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].value + '">' + data[i].name + '</option>'
            }
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight: 200
            });
        }
    });
}