$().ready(function() {
    validateRule();
    laydate({
        elem: '#createDate'
    });
    $('#createDate').val(laydate.now());
});

$.validator.setDefaults({
	submitHandler : function() {
		download();
	}
});

function download() {
    window.open("/order/order/export/"+$('#createDate').val()+"/"+$('#availableFlag').val());
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#exportForm").validate({
        rules : {
            createDate : {
                required : true
            }
        },
        messages : {
            createDate : {
                required : icon + "请选择日期"
            }
        }
    })
}
