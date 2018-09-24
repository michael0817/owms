package com.bootdo.order.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.order.domain.ModuleDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.order.service.ModuleService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 订单模板
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-22 16:27:23
 */
 
@Controller
@RequestMapping("/order/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;
	
	@GetMapping()
	@RequiresPermissions("order:module:list")
	String module(){
		return "order/module/list";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:module:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ModuleDO> moduleList = moduleService.list(query);
		int total = moduleService.count(query);
		PageUtils pageUtils = new PageUtils(moduleList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("order:module:add")
	String add(){
	    return "order/module/add";
	}

	@GetMapping("/edit/{moduleId}")
	@RequiresPermissions("order:module:edit")
	String edit(@PathVariable("moduleId") Long moduleId,Model model){
		ModuleDO module = moduleService.get(moduleId);
		model.addAttribute("module", module);
	    return "order/module/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:module:add")
	public R save( ModuleDO module){
		if(moduleService.save(module)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:module:edit")
	public R update( ModuleDO module){
		moduleService.update(module);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:module:remove")
	public R remove( Long moduleId){
		if(moduleService.remove(moduleId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:module:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] moduleIds){
		moduleService.batchRemove(moduleIds);
		return R.ok();
	}
	
}
