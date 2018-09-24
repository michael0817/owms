package com.bootdo.order.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.order.domain.OrderDO;
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

import com.bootdo.order.service.OrderService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 订单模板
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-22 15:18:03
 */
 
@Controller
@RequestMapping("/order/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping()
	@RequiresPermissions("order:order:list")
	String order(){
	    return "/order/order/list";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:order:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> listList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(listList, total);
		return pageUtils;
	}
	
	@GetMapping("/import")
	@RequiresPermissions("order:order:import")
	String mergeAndImport(){
	    return "com/bootdo/order/order/import";
	}

	@GetMapping("/edit/{orderId}")
	@RequiresPermissions("order:order:edit")
	String edit(@PathVariable("orderId") Long orderId,Model model){
		OrderDO list = orderService.get(orderId);
		model.addAttribute("list", list);
	    return "com/bootdo/order/order/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:order:import")
	public R save( OrderDO list){
		if(orderService.save(list)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:order:edit")
	public R update( OrderDO list){
		orderService.update(list);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:order:remove")
	public R remove( Long orderId){
		if(orderService.remove(orderId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:order:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] orderIds){
		orderService.batchRemove(orderIds);
		return R.ok();
	}
	
}
