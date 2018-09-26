package com.bootdo.order.controller;

import java.util.HashMap;
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

import com.bootdo.order.domain.ExcelFieldsDO;
import com.bootdo.order.service.ExcelFieldsService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * Excel模板字段
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 20:15:41
 */
 
@Controller
@RequestMapping("/order/excelFields")
public class ExcelFieldsController {
	@Autowired
	private ExcelFieldsService excelFieldsService;
	
	@GetMapping()
	String ExcelFields(){
	    return "order/excelFields/list";
	}

	@ResponseBody
	@GetMapping("/listAll")
	public List<ExcelFieldsDO> listAll() {
		// 查询列表数据
		Map<String, Object> map = new HashMap<>(16);
		List<ExcelFieldsDO> excelFieldsList = excelFieldsService.list(map);
		return excelFieldsList;
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ExcelFieldsDO> excelFieldsList = excelFieldsService.list(query);
		int total = excelFieldsService.count(query);
		PageUtils pageUtils = new PageUtils(excelFieldsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	String add(){
	    return "order/excelFields/add";
	}

	@GetMapping("/edit/{moduleName}")
	String edit(@PathVariable("moduleName") String moduleName,Model model){
		ExcelFieldsDO excelFields = excelFieldsService.get(moduleName);
		model.addAttribute("excelFields", excelFields);
	    return "order/excelFields/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( ExcelFieldsDO excelFields){
		if(excelFieldsService.save(excelFields)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( ExcelFieldsDO excelFields){
		excelFieldsService.update(excelFields);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String moduleName){
		if(excelFieldsService.remove(moduleName)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] moduleNames){
		excelFieldsService.batchRemove(moduleNames);
		return R.ok();
	}
	
}
