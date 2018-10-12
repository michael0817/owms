package com.bootdo.order.controller;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.*;
import com.bootdo.order.domain.ExpressDO;
import com.bootdo.order.domain.ModuleDO;
import com.bootdo.order.service.ExpressService;
import com.bootdo.order.service.FieldMappingService;
import com.bootdo.order.service.ModuleService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 运单详情
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-09-30 14:03:43
 */

@Controller
@RequestMapping("/order/express")
public class ExpressController extends BaseController {
    @Autowired
    private ExpressService expressService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private FieldMappingService fieldMappingService;

    @Autowired
    private DictService dictService;

    @Autowired
    private BootdoConfig bootdoConfig;

    @GetMapping()
    @RequiresPermissions("order:express:list")
    String List() {
        return "order/express/list";
    }

    @GetMapping("/import")
    @RequiresPermissions("order:express:import")
    String mergeAndImport() {
        return "order/express/import";
    }

    @GetMapping("/export")
    @RequiresPermissions("order:express:export")
    String divideAndExport() {
        return "order/express/export";
    }

    @ResponseBody
    @PostMapping("/import")
    @RequiresPermissions("order:express:import")
    R upload(@RequestParam("createDate") Date createDate, @RequestParam("file") MultipartFile file) {
        List<String> failedExpressList = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            String date = createDate == null ? sdf.format(new Date()) : sdf.format(createDate);
            String filename = file.getOriginalFilename();
            if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
                return R.error("导入失败:不是Excel格式文件");
            }
            if (!filename.contains(date)) {
                return R.error("导入失败:不是当前日期的文件");
            }
            Map moduleMap = new HashMap();
            moduleMap.put("moduleType", 3);
            List<ModuleDO> moduleList = moduleService.list(moduleMap);
            boolean matchFlag = false;
            for (ModuleDO moduleDO : moduleList) {
                if (!filename.startsWith(moduleDO.getPrefix())) {
                    continue;
                }
                matchFlag = true;
                List<ExpressDO> expressList = readExpresses(file.getInputStream(), moduleDO.getModuleId(), createDate);
                expressService.remove(createDate);
                for (ExpressDO expressDO : expressList) {
                    try {
                        expressDO.setCreateDate(createDate);
                        expressService.save(expressDO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!matchFlag) {
                return R.error("导入失败:不是运单文件");
            }
            return R.ok("导入完成");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("导入失败:请联系系统管理员");
        }
    }

    @ResponseBody
    @GetMapping("/export/{createDate}")
    @RequiresPermissions("order:express:export")
    void download(@PathVariable("createDate") Date createDate, HttpServletResponse response) {
        File tmpFolder = new File(bootdoConfig.getUploadPath() + "/tmp/" + new Date().toString());
        try {
            Map map = new HashMap();
            map.put("moduleType", 3);
            List<ModuleDO> moduleList = moduleService.list(map);
            ModuleDO moduleDO = moduleList.get(0);
            String path = bootdoConfig.getUploadPath() + "/modules/" + moduleDO.getUrl();
            // path是指欲下载的文件的路径。
            File moduleFile = new File(path);

            // 取得文件名。
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            String date = createDate == null ? sdf.format(new Date()) : sdf.format(createDate);

            // 写Excel
            if (!tmpFolder.exists()) {
                tmpFolder.mkdirs();
            }
            Map<Long, Workbook> workbookList = writeExpresses(moduleFile, moduleDO.getModuleId(), createDate);
            for (Map.Entry<Long, Workbook> entry : workbookList.entrySet()) {
                String divideFilename = moduleService.get(entry.getKey()).getUrl();
                File divideFile = new File(tmpFolder.getAbsolutePath() + "/" + divideFilename);
                entry.getValue().write(new FileOutputStream(divideFile));
            }

            //zip
            String zipName = moduleDO.getUrl().substring(0, moduleDO.getUrl().indexOf(".")) + date + ".zip";
            File zipFile = new File(tmpFolder + "/" + zipName);
            ZipUtils.zipFiles(tmpFolder.listFiles(), zipFile);

            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipName, "UTF-8"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileInputStream fromServer = new FileInputStream(zipFile);
            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while ((-1 != (bytesRead = fromServer.read(buff, 0, buff.length)))) {
                toClient.write(buff, 0, bytesRead);
            }
            fromServer.close();
            toClient.flush();
            toClient.close();
            response.getOutputStream().write("<script>window.close()</script>".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            for (File file : tmpFolder.listFiles()) {
                file.delete();
            }
            tmpFolder.delete();
        }
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("order:express:list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ExpressDO> expressList = expressService.list(query);
        int total = expressService.count(query);
        PageUtils pageUtils = new PageUtils(expressList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("order:express:add")
    String add() {
        return "order/express/add";
    }

//	@GetMapping("/edit/{orderId}")
//	@RequiresPermissions("order:express:edit")
//	String edit(@PathVariable("orderId") Date createDate,Model model){
//		ExpressDO express = expressService.get(createDate);
//		model.addAttribute("express", express);
//	    return "order/express/edit";
//	}

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("order:express:add")
    public R save(ExpressDO express) {
        if (expressService.save(express) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("order:express:edit")
    public R update(ExpressDO express) {
        expressService.update(express);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("order:express:remove")
    public R remove(Date createDate) {
        if (expressService.remove(createDate) > 0) {
            return R.ok();
        }
        return R.error();
    }

    private List<ExpressDO> readExpresses(InputStream is, Long moduleId, Date createDate) {
        List<ExpressDO> expressList = new ArrayList<ExpressDO>();
        try {
            ExcelUtils et = new ExcelUtils(is);
            List<Map<String, Object>> excelFieldMapList = fieldMappingService.getExpressMapping(moduleId);
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
                ExpressDO expressDO = new ExpressDO();
                for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                    map.put(entry.getKey(), colList.get(entry.getValue()));
                }
                RefBeanUtils.setFieldValue(expressDO, map);
                if (expressDO.getOrderId() == null || expressDO.getExpressId() == null) {
                    continue;
                }
                expressList.add(expressDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expressList;
    }

    private Map<Long, Workbook> writeExpresses(File moduleFile, Long moduleId, Date createDate) {
        Map<Long, Workbook> workbookList = new HashMap<Long, Workbook>();
        try {
            List<Map<String, Object>> excelFieldMapList = fieldMappingService.getExpressMapping(moduleId);
            ExcelUtils et = new ExcelUtils(moduleFile);
            List<String> titleList = et.read(0, 0, 1).get(0);
            Map<String, Integer> columnMap = new HashMap<String, Integer>();
            List<Map<String, Object>> expressList = expressService.getGroup(createDate);
            Map<Long, List<ExpressDO>> groupedExpressList = new HashMap<Long, List<ExpressDO>>();
            for (Map<String, Object> map : expressList) {
                Long orderModuleId = (Long) map.get("module_id");
                ExpressDO express = new ExpressDO();
                express.setSku((String) map.get("sku"));
                express.setOrderId((String) map.get("order_id"));
                express.setExpressId((String) map.get("express_id"));
                express.setExpressCompany((String) map.get("express_company"));
                if (!groupedExpressList.containsKey(orderModuleId)) {
                    groupedExpressList.put(orderModuleId, new ArrayList<ExpressDO>());
                }
                groupedExpressList.get(orderModuleId).add(express);
            }
            for (Map.Entry<Long, List<ExpressDO>> entry : groupedExpressList.entrySet()) {
                Workbook workbook = et.getWorkbook();
                int rownum = 1;
                for (ExpressDO expressDO : entry.getValue()) {
                    Map<String, String> singleRowMap = null;
                    if (singleRowMap == null) {
                        singleRowMap = new HashMap<String, String>();
                        Map<String, String> expressMap = RefBeanUtils.getFieldValueMap(expressDO);
                        for (Map.Entry<String, String> orderEntry : expressMap.entrySet()) {
                            for (Map<String, Object> excelFieldMap : excelFieldMapList) {
                                if (excelFieldMap.get("excel_field_name") != null && !"".equals(excelFieldMap.get("excel_field_name"))) {
                                    if (excelFieldMap.get("value").equals(orderEntry.getKey())) {
                                        singleRowMap.put((String) excelFieldMap.get("excel_field_name"), orderEntry.getValue());
                                    }
                                }
                            }
                        }
                    }
                    if (workbook.getSheetAt(0).getRow(rownum) == null) {
                        workbook.getSheetAt(0).createRow(rownum);
                    }
                    for (int colnum = 0; colnum < singleRowMap.size(); colnum++) {
                        if (workbook.getSheetAt(0).getRow(rownum).getCell(colnum) == null) {
                            workbook.getSheetAt(0).getRow(rownum).createCell(colnum);
                        }
                        if (singleRowMap.containsKey(titleList.get(colnum))) {
                            workbook.getSheetAt(0).getRow(rownum).getCell(colnum).setCellValue(singleRowMap.get(titleList.get(colnum)));
                        }
                    }
                    rownum++;
                }
                workbookList.put(entry.getKey(), workbook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbookList;
    }

}
