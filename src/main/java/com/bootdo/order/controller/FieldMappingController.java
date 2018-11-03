package com.bootdo.order.controller;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.order.domain.FieldMappingDO;
import com.bootdo.order.service.FieldMappingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板字段映射
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 01:14:05
 */

@Controller
@RequestMapping("/order/fieldMapping")
public class FieldMappingController extends BaseController {
    @Autowired
    private FieldMappingService fieldMappingService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("order:fieldMapping:list")
    String list() {
        return "order/fieldMapping/list";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("order:fieldMapping:list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<FieldMappingDO> fmList = fieldMappingService.list(query);
        Map dictMap = new HashMap();
        dictMap.put("type", "order_module_2");
        if (query.get("moduleType") != null) {
            if ("1".equals(query.get("moduleType").toString())) {//特殊处理，平台订单模板复用订单宝模板字段
                dictMap.put("type", "order_module_2");
            } else {
                dictMap.put("type", "order_module_" + query.get("moduleType"));
            }
        }
        List<DictDO> dictList = dictService.list(dictMap);
        List<FieldMappingDO> fieldMappingList = new ArrayList<FieldMappingDO>();
        for (DictDO dictDO : dictList) {
            FieldMappingDO fmDO = new FieldMappingDO();
            fmDO.setModuleType(Integer.parseInt(query.get("moduleType").toString()));
            fmDO.setModuleId(Long.parseLong(query.get("moduleId").toString()));
            fmDO.setBusinessFieldName(dictDO.getName());
            for (FieldMappingDO tmpFmDO : fmList) {
                if (tmpFmDO.getBusinessFieldName().equals(dictDO.getName())) {
                    fmDO.setExcelFieldName(tmpFmDO.getExcelFieldName());
                    break;
                }
            }
            fieldMappingList.add(fmDO);
        }
        int total = fmList.size();
        PageUtils pageUtils = new PageUtils(fieldMappingList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("order:fieldMapping:add")
    String add() {
        return "order/fieldMapping/add";
    }

    @GetMapping("//{moduleId}")
    @RequiresPermissions("order:fieldMapping:edit")
    String edit(@PathVariable("moduleId") Long moduleId, Model model) {
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
    public R save(FieldMappingDO fieldMapping) {
        if (fieldMappingService.save(fieldMapping) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/batchSave")
    @RequiresPermissions("order:fieldMapping:add")
    public R batchSave(@RequestBody List<FieldMappingDO> fieldMappings) {
        if (fieldMappings != null && fieldMappings.size() > 0) {
            Long moduleId = fieldMappings.get(0).getModuleId();
            Map<String, Object> map = new HashMap();
            map.put("moduleId", moduleId);
            List<FieldMappingDO> fmDOs = fieldMappingService.list(map);
            Long[] moduleIds = new Long[fmDOs.size()];
            for (int i = 0; i < fmDOs.size(); i++) {
                moduleIds[i] = fmDOs.get(i).getModuleId();
                fieldMappingService.batchRemove(moduleIds);
            }
        }
        for (FieldMappingDO fmDO : fieldMappings) {
            fieldMappingService.save(fmDO);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("order:fieldMapping:edit")
    public R update(FieldMappingDO fieldMapping) {
        fieldMappingService.update(fieldMapping);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("order:fieldMapping:remove")
    public R remove(Long moduleId) {
        if (fieldMappingService.remove(moduleId) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("order:fieldMapping:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] moduleIds) {
        fieldMappingService.batchRemove(moduleIds);
        return R.ok();
    }

}
