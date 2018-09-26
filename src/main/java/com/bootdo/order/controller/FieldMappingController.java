package com.bootdo.order.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.order.domain.FieldMappingDO;
import com.bootdo.order.service.FieldMappingService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 模板字段映射
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 01:14:05
 */
 
@Controller
@RequestMapping("/order/fieldMapping")
public class FieldMappingController {
	@Autowired
	private FieldMappingService fieldMappingService;
	
	@GetMapping()
	@RequiresPermissions("order:fieldMapping:list")
	String FieldMapping(){
	    return "order/fieldMapping/list";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:fieldMapping:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<FieldMappingDO> fieldMappingList = fieldMappingService.list(query);
		int total = fieldMappingList.size();
		PageUtils pageUtils = new PageUtils(fieldMappingList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("order:fieldMapping:add")
	String add(){
	    return "order/fieldMapping/add";
	}

	@GetMapping("/edit/{moduleId}")
	@RequiresPermissions("order:fieldMapping:edit")
	String edit(@PathVariable("moduleId") Integer moduleId,Model model){
		FieldMappingDO fieldMapping = fieldMappingService.get(moduleId);
		model.addAttribute("fieldMapping", fieldMapping);
	    return "order/fieldMapping/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:fieldMapping:add")
	public R save( FieldMappingDO fieldMapping){
		if(fieldMappingService.save(fieldMapping)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:fieldMapping:edit")
	public R update( FieldMappingDO fieldMapping){
		fieldMappingService.update(fieldMapping);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:fieldMapping:remove")
	public R remove( Integer moduleId){
		if(fieldMappingService.remove(moduleId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:fieldMapping:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] moduleIds){
		fieldMappingService.batchRemove(moduleIds);
		return R.ok();
	}
	
}
