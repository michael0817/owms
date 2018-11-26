$().ready(function() {
    validateRule();
    laydate ({
        elem: '#createDate',
        choose: function(value){
            loadOrderBatchNums();
        }
    });
    $('#createDate').val(laydate.now());
    loadOrderBatchNums();
});

$.validator.setDefaults({
	submitHandler : function() {
		download();
	}
});
function download() {
    window.open("/order/order/export/"+$('#createDate').val()+"/"+$('#orderBatch').val()+"/"+$('#availableFlag').val());
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


function loadOrderBatchNums(){
    $.ajax({
        type : 'GET',
        url : '/order/order/getOrderBatchNums/'+$('#createDate').val(),
        success : function(data) {
            $('#orderBatch option:gt(0)').remove();
            if(data.length > 0){
                $.each(data,function(i,v){
                    $('#orderBatch').append('<option value="'+v+ '">'+v+'</option>');
                })
            }
            // $.each(data,function(v){
            //     alert(v);
            // })
        }
    });
}