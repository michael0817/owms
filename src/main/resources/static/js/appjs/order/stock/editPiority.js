$().ready(function() {
    $.ajax({
            url: '/order/stock/getPiority',
            type: 'POST',
            async: false,
            dataType:"json",
            contentType:"application/json",
            success: function (data) {
                $.each(data,function(i,v){
                    $('#piority').append('<div class="form-group">'+
                        '<label class="col-sm-5 control-label">'+v.name+'：</label>'+
                        '<div class="col-sm-6">'+
                        '<input id="'+v.id+'" name="warehouse" value="'+v.sort+'" class="form-control" type="text">'+
                        '</div></div>');
                })
            }
        });
     validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
        updatePiority();
	}
});
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#piorityForm").validate({
		rules : {
			name : {
				required : true,
                digits   : true
            }
		},
		messages : {
			name : {
				required : icon + "请输入序号",
                digits : icon + "请输入正整数"
			}
		}
	});
}

function updatePiority(){
    var rows = [];
    $('input[type=text]').each(function(i){
        var row = {};
        row['id']=$(this).attr('id');
        row['sort']=$(this).val();
        rows.push(row);
    });
    $.ajax({
        url: '/order/stock/updatePiority',
        type: 'POST',
        async: false,
        dataType:"json",
        contentType:"application/json",
        data: JSON.stringify(rows),
        success: function (data) {
            if (data.code == 0) {
                layer.msg("操作成功");
            } else {
                layer.alert(data.msg);
            }
        }
    });
}