
var prefix = "/order/order"
$(function() {
    laydate({
        elem: '#createDate'
    });
    $('#createDate').val(laydate.now());
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
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : false, // 是否显示搜索框
						showColumns : true, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
					            createDate:$('#createDate').val(),
								moduleId:$('#moduleId').val(),
                                orderId:$('#orderId').val()
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
									checkbox : true
								},
								{
									field : 'orderId', 
									title : '订单编号' 
								},
								{
									field : 'moduleId',
									title : '模板编号' 
								},
								{
									field : 'warehouseCode',
									title : '仓库代码'
								},
								{
									field : 'deliveryStyle',
									title : '配送方式'
								},
								{
									field : 'insuranceType',
									title : '保险类型'
								},
								{
									field : 'insuranceValue',
									title : '投保金额'
								},						{
									field : 'consigneeName', 
									title : '收件人姓名' 
								},
								{
									field : 'consigneePhone', 
									title : '收件人电话' 
								},
								{
									field : 'consigneeId',
									title : '收件人身份证号'
								},
								{
									field : 'consigneeCountry', 
									title : '收件人国家' 
								},
								{
									field : 'province', 
									title : '州/省' 
								},
								{
									field : 'city', 
									title : '城市/市' 
								},
								{
									field : 'street',
									title : '街道'
								},
								{
									field : 'doorplate', 
									title : '门牌号/区' 
								},
								{
									field : 'zipCode', 
									title : '邮编' 
								},
								{
									field : 'sku', 
									title : 'SKU' 
								},
								{
									field : 'quantity',
									title : '数量' 
								},
								{
									field : 'createDate', 
									title : '创建日期',
									formatter : function(value, row, index) {
                                        return value.substr(0,10);
                                    }
								// },
								// {
								// 	title : '操作',
								// 	field : 'id',
								// 	align : 'center',
								// 	formatter : function(value, row, index) {
								// 		var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
								// 				+ row.orderId
								// 				+ '\')"><i class="fa fa-edit"></i></a> ';
								// 		var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
								// 				+ row.orderId
								// 				+ '\')"><i class="fa fa-remove"></i></a> ';
								// 		var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
								// 				+ row.orderId
								// 				+ '\')"><i class="fa fa-key"></i></a> ';
								// 		return e + d ;
								// 	}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function mergeAndImport() {
	layer.open({
		type : 2,
		title : '导入',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/import' // iframe的url
	});
}
function appendAndImport() {
    layer.open({
        type : 2,
        title : '导入',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/append' // iframe的url
    });
}
function divideAndExport() {
    layer.open({
        type : 2,
        title : '导出',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/export' // iframe的url
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
				'orderId' : id
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
			ids[i] = row['orderId'];
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