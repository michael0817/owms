
var prefix = "/order/fieldMapping"
var moduleList = [];
var excelFieldsList = [];
$(function() {
    loadModuleType();
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 200, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                moduleType: $('#moduleType option:selected').val(),
                                moduleId: $('#moduleList option:selected').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
                                {
									field : 'moduleId', 
									title : '模板编号',
                                    visible : false
								},
                                {
									field : 'moduleType', 
									title : '文件类型',
                                    visible : false
								},
                                {
									field : 'businessFieldName', 
									title : '业务字段'
								},
                                {
									field : 'excelFieldName', 
									title : 'EXCEL字段名',
                                    formatter : function(value, row, index) {
                                        return formatExcelFieldColumn(value,index);
                                    }
                                // },
                                // {
									// title : '操作',
									// field : 'id',
									// align : 'center',
									// formatter : function(value, row, index) {
									// 	var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
									// 			+ row.moduleId
									// 			+ '\')"><i class="fa fa-edit"></i></a> ';
									// 	var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
									// 			+ row.moduleId
									// 			+ '\')"><i class="fa fa-remove"></i></a> ';
									// 	var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
									// 			+ row.moduleId
									// 			+ '\')"><i class="fa fa-key"></i></a> ';
									// 	return e + d ;
									// }
								}]
					});
}
function reLoad() {
    //$('#exampleTable').bootstrapTable('destroy');
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'moduleId' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['moduleId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}

function loadModuleType() {
    $.ajax({
        url: '/order/module/listAll',
        async: false,
        success: function (data) {
            //加载数据
            moduleList = data;
        }
    });
    var html = "";
    $.ajax({
        url: '/common/dict/list/order_module',
        async: false,
        success: function (data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].value + '">' + data[i].name + '</option>'
            }
            $("#moduleType").append(html);
            // $("#moduleType").chosen({
            //     maxHeight: 200
            // });
            loadModuleList();
        }
    });
    //点击事件
    $('#moduleType').on('change', function(e, params) {
        loadModuleList();
    });
    //点击事件
    $('#moduleList').on('change', function(e, params) {
        loadExcelFields();
    });
}

function loadModuleList() {
    //加载数据
    var html = "";
    for (var i = 0; i < moduleList.length; i++) {
        if(moduleList[i].moduleType == $('#moduleType option:selected').val()){
            html += '<option value="' + moduleList[i].moduleId + '">' + moduleList[i].url + '</option>'
        }
    }
    $("#moduleList").empty();
    $("#moduleList").append(html);
    loadExcelFields();
}

function loadExcelFields(){
    $.ajax({
        url: '/order/excelFields/listAll/'+$('#moduleList option:selected').val(),
        async: false,
        success: function (data) {
            //加载数据
            excelFieldsList = data;
        }
    });
}

function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}

function formatExcelFieldColumn(value,index){
    var html = '';
    var forbidRow = [0,2,3,4,8,13];
    if($('#moduleType option:selected').val() == 1 && isInArray(forbidRow,index)){
    	html += '<select class="form-control chosen-select" autocomplete="off" disabled="disabled">';
        html += '<option value="">--自动填充--</option>';
	}else {
        html += '<select class="form-control chosen-select" autocomplete="off">';
        html += '<option value="">--空白--</option>';
        for (var i = 0; i < excelFieldsList.length; i++) {
            html += '<option value="' + excelFieldsList[i].moduleName + '"';
            if (value == excelFieldsList[i].excelFieldName) {
                html += ' selected="selected" ';
            }
            html += '>' + excelFieldsList[i].excelFieldName + '</option>';
        }
    }
    html+='</select>';
    return html;
}

function batchSave(){
    var rows = [];
    $('#exampleTable tbody tr').each(function(i){
        var row = {};
        row["moduleId"]=$('#moduleList option:selected').val();
        row["moduleType"]=$('#moduleType option:selected').val();
        $(this).children('td').each(function(j,e){
            if(j==0){
                row["businessFieldName"]=$(e).text();
            }else if(j==1){
                var fieldName = $(e).find('option:selected').text();
                if(fieldName != '--空白--' && fieldName != '--自动填充--'){
                    row["excelFieldName"]=$(e).find('option:selected').text();
                }else{
                    row["excelFieldName"]='';
                }
            }
        });
        rows.push(row);
    });
    $.ajax({
        url: '/order/fieldMapping/batchSave',
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