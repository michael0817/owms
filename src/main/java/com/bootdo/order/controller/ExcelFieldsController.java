package com.bootdo.order.controller;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.order.domain.ExcelFieldsDO;
import com.bootdo.order.service.ExcelFieldsService;
import com.bootdo.order.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel模板字段
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-26 20:15:41
 */

@Controller
@RequestMapping("/order/excelFields")
public class ExcelFieldsController extends BaseController {
    @Autowired
    private ExcelFieldsService excelFieldsService;

    @Autowired
    private ModuleService moduleService;

    @GetMapping()
    String list() {
        return "order/excelFields/list";
    }

    @ResponseBody
    @GetMapping("/listAll/{moduleId}")
    public List<ExcelFieldsDO> listAll(@PathVariable("moduleId") Long moduleId) {
        // 查询列表数据
        String moduleName = moduleService.get(moduleId).getUrl();
        Map<String, Object> map = new HashMap<>(16);
        map.put("moduleName", moduleName);
        List<ExcelFieldsDO> excelFieldsList = excelFieldsService.list(map);
        return excelFieldsList;
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ExcelFieldsDO> excelFieldsList = excelFieldsService.list(query);
        int total = excelFieldsService.count(query);
        PageUtils pageUtils = new PageUtils(excelFieldsList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    String add() {
        return "order/excelFields/add";
    }

    @GetMapping("/edit/{moduleName}")
    String edit(@PathVariable("moduleName") String moduleName, Model model) {
        ExcelFieldsDO excelFields = excelFieldsService.get(moduleName);
        model.addAttribute("excelFields", excelFields);
        return "order/excelFields/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(ExcelFieldsDO excelFields) {
        if (excelFieldsService.save(excelFields) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(ExcelFieldsDO excelFields) {
        excelFieldsService.update(excelFields);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(String moduleName) {
        if (excelFieldsService.remove(moduleName) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") String[] moduleNames) {
        excelFieldsService.batchRemove(moduleNames);
        return R.ok();
    }

}
