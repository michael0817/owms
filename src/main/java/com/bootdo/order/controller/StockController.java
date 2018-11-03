package com.bootdo.order.controller;

import com.alibaba.fastjson.JSONArray;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.*;
import com.bootdo.order.domain.ModuleDO;
import com.bootdo.order.domain.StockDO;
import com.bootdo.order.enums.ModuleType;
import com.bootdo.order.service.FieldMappingService;
import com.bootdo.order.service.ModuleService;
import com.bootdo.order.service.StockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * 库存信息
 * 
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-10-23 03:02:38
 */
 
@Controller
@RequestMapping("/order/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private ModuleService moduleService;

    @Autowired
    private FieldMappingService fieldMappingService;

    @Autowired
	private DictService dictService;

    @GetMapping()
	@RequiresPermissions("order:stock:list")
	String list(){
	    return "order/stock/list";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:stock:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<StockDO> stockList = stockService.list(query);
		int total = stockService.count(query);
		PageUtils pageUtils = new PageUtils(stockList, total);
		return pageUtils;
	}

	@GetMapping("/import")
	@RequiresPermissions("order:stock:import")
	String mergeAndImport() {
		return "order/stock/import";
	}

	@ResponseBody
	@PostMapping("/import")
	@RequiresPermissions("order:stock:import")
	R upload(@RequestParam("file") MultipartFile file) {
		List<String> failedStockList = new ArrayList<String>();
		try {
			String filename = file.getOriginalFilename();
			if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
				return R.error("导入失败:不是Excel格式文件");
			}
			Map moduleMap = new HashMap();
			moduleMap.put("moduleType", ModuleType.STOCK.getIndex());
			List<ModuleDO> moduleList = moduleService.list(moduleMap);
			boolean matchFlag = false;
			for (ModuleDO moduleDO : moduleList) {
				if (!filename.startsWith(moduleDO.getPrefix())) {
					continue;
				}
				matchFlag = true;
				List<StockDO> stockList = readStock(file.getInputStream(), moduleDO.getModuleId());
				for (StockDO stockDO : stockList) {
					try {
						stockService.save(stockDO);
					} catch (Exception e) {
                        failedStockList.add(stockDO.getSku());
                        e.printStackTrace();
					}
				}
			}
			if (!matchFlag) {
				return R.error("导入失败:不是库存文件");
			}
            R r = R.ok("导入完成");
            r.put("failedStockList", JSONArray.toJSONString(failedStockList));
            return r;
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("导入失败:请联系系统管理员");
		}
	}

	@GetMapping("/edit/{warehouseCode}/{sku}")
	@RequiresPermissions("order:stock:edit")
	String edit(@PathVariable("warehouseCode") String warehouseCode,
                @PathVariable("sku") String sku,
                Model model){
		StockDO stock = stockService.get(warehouseCode, sku);
		model.addAttribute("stock", stock);
	    return "order/stock/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("order:stock:add")
	public R save( StockDO list){
		if(stockService.save(list)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("order:stock:edit")
	public R update( StockDO stock){
		stockService.update(stock);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:stock:remove")
	public R remove( String warehouseCode, String sku ){
		if(stockService.remove(warehouseCode, sku)>0){
		return R.ok();
		}
		return R.error();
	}

    private List<StockDO> readStock(InputStream is, Long moduleId) {
        List<StockDO> stockList = new ArrayList<StockDO>();
        try {
            ExcelUtils et = new ExcelUtils(is);
            List<Map<String, Object>> excelFieldMapList = fieldMappingService.getFieldMapping(moduleId, ModuleType.STOCK);
            List<String> titleList = et.read(0, 0, 1).get(0);
            Map<String, Integer> columnMap = new HashMap<String, Integer>();
            for (Map<String, Object> map : excelFieldMapList) {
                if (map.get("excel_field_name") != null && !"".equals(map.get("excel_field_name"))) {
                    for (int i = 0; i < titleList.size(); i++) {
                        if (map.get("excel_field_name").equals(titleList.get(i))) {
                            columnMap.put(map.get("value").toString(), i);
                        }
                    }
                }
            }
            List<List<String>> rowList = et.read(0, 1, et.getRowCount(0));
            for (List<String> colList : rowList) {
                Map<String, String> map = new HashMap<String, String>();
                StockDO stockDO = new StockDO();
                for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                    map.put(entry.getKey(), colList.get(entry.getValue()));
                }
                RefBeanUtils.setFieldValue(stockDO, map);
                if (stockDO.getSku() == null || stockDO.getWarehouseCode() == null) {
                    continue;
                }
                stockDO.setDataDate(new Date());
                stockList.add(stockDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockList;
    }

    @GetMapping("/editPiority")
    @RequiresPermissions("order:stock:edit")
    String edit(Model model){
        return "order/stock/editPiority";
    }

    /**
     * 获取仓库优先级
     */
    @ResponseBody
    @PostMapping("/getPiority")
    public List<DictDO> getPiority() {
        List<DictDO> warehouseList = dictService.listByType("warehouse");
        return warehouseList;
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/updatePiority")
    @RequiresPermissions("order:stock:edit")
    public R updatePiority(@RequestBody List<DictDO> warehouseList) {
        for(DictDO dictDO : warehouseList){
            dictService.update(dictDO);
        }
        return R.ok();
    }
}
