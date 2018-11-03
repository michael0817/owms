package com.bootdo.order.controller;

import com.alibaba.fastjson.JSONArray;
import com.bootdo.common.config.BootdoFileConfig;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.*;
import com.bootdo.order.domain.ModuleDO;
import com.bootdo.order.domain.OrderDO;
import com.bootdo.order.enums.ModuleType;
import com.bootdo.order.service.FieldMappingService;
import com.bootdo.order.service.ModuleService;
import com.bootdo.order.service.OrderService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单模板
 *
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 15:18:03
 */

@Controller
@RequestMapping("/order/order")
public class OrderController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private FieldMappingService fieldMappingService;

    @Autowired
    private BootdoFileConfig bootdoConfig;

    @GetMapping()
    @RequiresPermissions("order:order:list")
    String list() {
        return "order/order/list";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("order:order:list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<OrderDO> orderList = orderService.list(query);
        int total = orderService.count(query);
        PageUtils pageUtils = new PageUtils(orderList, total);
        return pageUtils;
    }

    @GetMapping("/import")
    @RequiresPermissions("order:order:import")
    String mergeAndImport() {
        return "order/order/import";
    }

    @GetMapping("/append")
    @RequiresPermissions("order:order:import")
    String appendAndImport() {
        return "order/order/append";
    }

    @GetMapping("/export")
    @RequiresPermissions("order:order:export")
    String divideAndExport() {
        return "order/order/export";
    }

    @GetMapping("/edit/{orderId}")
    @RequiresPermissions("order:order:edit")
    String edit(@PathVariable("orderId") String orderId, Model model) {
        OrderDO list = orderService.get(orderId);
        model.addAttribute("order", list);
        return "order/order/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("order:order:import")
    public R save(OrderDO order) {
        if (orderService.save(order) > 0) {
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
    public R update(OrderDO order) {
        orderService.update(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("order:order:remove")
    public R remove(String orderId) {
        if (orderService.remove(orderId) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("order:order:batchRemove")
    public R batchRemove(@RequestParam("ids[]") String[] orderIds) {
        orderService.batchRemove(orderIds);
        return R.ok();
    }

    @ResponseBody
    @PostMapping("/import")
    @RequiresPermissions("order:order:import")
    @Transactional
    R upload(@RequestParam("createDate") Date createDate, @RequestParam("deleteFlag") String deleteFlag, @RequestParam("file") MultipartFile[] file) {
        List<String> failedList = new ArrayList<String>();
        List<String> ignoreList = new ArrayList<String>();
        List<String> successList = new ArrayList<String>();
        List<String> failedOrderList = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            String date = createDate == null ? sdf.format(new Date()) : sdf.format(createDate);
            List<OrderDO> totalOrderList = new ArrayList<OrderDO>();
            for (MultipartFile singleFile : file) {
                if (singleFile.getOriginalFilename().endsWith(".xls") || singleFile.getOriginalFilename().endsWith(".xlsx")) {
                    String name[] = singleFile.getOriginalFilename().split("\\\\");
                    if (name.length <= 1) {
                        name = singleFile.getOriginalFilename().split("/");
                    }
                    String filename = name[name.length-1];
                    if (!singleFile.getOriginalFilename().contains(date)) {
                        ignoreList.add(filename);
                        continue;
                    }
                    Map moduleMap = new HashMap();
                    moduleMap.put("moduleType", ModuleType.ORDER.getIndex());
                    List<ModuleDO> moduleList = moduleService.list(moduleMap);
                    boolean matchFlag = false;
                    for (ModuleDO moduleDO : moduleList) {
                        if (name.length == 2) {
                            if (!filename.startsWith(moduleDO.getPrefix())) {
                                continue;
                            }
                        }
                        try {
                            List<OrderDO> orderList = readOrders(singleFile.getInputStream(), moduleDO.getModuleId(), createDate);
                            totalOrderList.addAll(orderList);
                            matchFlag = true;
                            successList.add(filename);
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                            e.printStackTrace();
                            failedList.add(filename);
                        }
                    }
                    if (!matchFlag) {
                        ignoreList.add(filename);
                    }
                }
                if("1".equals(deleteFlag)) {
                    orderService.removeByDate(createDate == null ? new Date() : createDate);
                }
                for (OrderDO orderDO : totalOrderList) {
                    try {
//                        orderService.save(orderDO);
                        orderService.saveOrder(orderDO);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                        failedOrderList.add(orderDO.getOrderId());
                    }
                }
            }
            R r = R.ok("导入完成");
            r.put("successList", JSONArray.toJSONString(successList));
            r.put("ignoreList", JSONArray.toJSONString(ignoreList));
            r.put("failedList", JSONArray.toJSONString(failedList));
            r.put("failedOrderList", JSONArray.toJSONString(failedOrderList));
            return r;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return R.error("导入失败:请联系系统管理员");
        }

    }

    @ResponseBody
    @GetMapping("/export/{createDate}/{availableFlag}")
    @RequiresPermissions("order:order:export")
    void download(@PathVariable("createDate") Date createDate,
                  @PathVariable("availableFlag") String availableFlag,
                  HttpServletRequest request, HttpServletResponse response) {
        try {
            Map map = new HashMap();
            map.put("moduleType", ModuleType.MODULE.getIndex());
            List<ModuleDO> moduleList = moduleService.list(map);
            ModuleDO moduleDO = moduleList.get(0);
            String path = bootdoConfig.getUploadPath() + "/modules/" + moduleDO.getUrl();
            // path是指欲下载的文件的路径。
            File moduleFile = new File(path);

            // 取得文件名。
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            String date = createDate == null ? sdf.format(new Date()) : sdf.format(createDate);
            String filename = moduleDO.getUrl().substring(0, moduleDO.getUrl().indexOf("."))
                    + date + moduleFile.getName().substring(moduleFile.getName().lastIndexOf("."));

            // 写Excel
            Map params = new HashMap();
            params.put("createDate", date);
            params.put("availableFlag", availableFlag);
            Workbook workbook = writeOrders(moduleFile, moduleDO.getModuleId(), params);
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
    		String agent = request.getHeader("USER-AGENT");
            if(agent != null && agent.toLowerCase().indexOf("firefox") > 0){
            	filename = new String(filename.getBytes("GB2312"),"ISO-8859-1");
            }else {
            	filename = URLEncoder.encode(filename, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] content = bos.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            BufferedInputStream fromServer = new BufferedInputStream(is);
            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while ((-1 != (bytesRead = fromServer.read(buff, 0, buff.length)))) {
                toClient.write(buff, 0, bytesRead);
            }
            fromServer.close();
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private List<OrderDO> readOrders(InputStream is, Long moduleId, Date createDate) {
        List<OrderDO> orderList = new ArrayList<OrderDO>();
        try {
            ExcelUtils et = new ExcelUtils(is);
            logger.info("read excel");
            List<Map<String, Object>> excelFieldMapList = fieldMappingService.getFieldMapping(moduleId,ModuleType.MODULE);//业务字段和excel列名映射关系
            List<String> titleList = et.read(0, 0, 1).get(0);
            Map<String, Integer> columnMap = new HashMap<String, Integer>();//业务字段与excel列号映射关系
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
                OrderDO orderDO = new OrderDO();
                for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                     map.put(entry.getKey(), colList.get(entry.getValue()));
                     logger.info(entry.getKey()+","+colList.get(entry.getValue()));
                }
                RefBeanUtils.setFieldValue(orderDO, map);
                if (orderDO.getOrderId() == null) {
                    continue;
                }
                orderDO.setModuleId(moduleId);
                orderDO.setCreateDate(createDate);
                String address = "";
                String province = "";
                String city = "";
                String district = "";
                int index = 0;
                address = orderDO.getStreet() + " " + orderDO.getActualConsigneeName();
                if (orderDO.getProvince().equals(orderDO.getStreet()) && orderDO.getCity().equals(orderDO.getStreet())) {
                    index = address.indexOf("省");
                    if (index < 0) {
                        if(address.indexOf("自治区")>0) {
                            index = address.indexOf("自治区")+3;
                        } else {
                            index = address.indexOf("市");
                        }
                    }
                    province = address.substring(0, index + 1);
                    address = address.substring(index + 1);
                    index = address.indexOf("市");
                    city = address.substring(0, index + 1);
                    address = address.substring(index + 1);
                    orderDO.setProvince(province);
                    orderDO.setCity(city);
                    orderDO.setStreet(address);
                }
                index = address.indexOf("区 ");
                if (index < 0) {
                    index = address.indexOf("县 ");
                }
                if (index < 0) {
                    index = address.indexOf("市 ");
                }
                district = address.substring(0, index + 1);
                address = address.substring(index+2);
                orderDO.setDoorplate(district);
                orderDO.setStreet(address);
                orderDO.setZipCode(ZipCodeUtils.getZipCode(orderDO.getProvince(), orderDO.getCity()));
                orderDO.setConsigneeCountry("CN");
                orderDO.setInsuranceType("NI 无保/Not  Insurance");
                if(orderDO.getProvince().equals("上海")||orderDO.getProvince().equals("北京")
                        ||orderDO.getProvince().equals("天津")||orderDO.getProvince().equals("重庆")){
                    orderDO.setProvince(orderDO.getProvince()+"市");
                }
//                orderService.save(orderDO);
                orderList.add(orderDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    private Workbook writeOrders(File moduleFile, Long moduleId, Map params) {
        Workbook workbook = null;
        try {
            ExcelUtils et = new ExcelUtils(moduleFile);
            workbook = et.getWorkbook();
            List<Map<String, Object>> excelFieldMapList = fieldMappingService.getFieldMapping(moduleId, ModuleType.MODULE);//业务字段和excel列名映射关系
            List<String> titleList = et.read(0, 0, 1).get(0);
            Row firstRow = workbook.getSheetAt(0).getRow(1);
            Map<String, Integer> columnMap = new HashMap<String, Integer>();//业务字段与excel列号映射关系
            List<OrderDO> orderList = orderService.list(params);
            Map<String, List<OrderDO>> rowMap = new HashMap();
            for (OrderDO orderDO : orderList) {
                if (!rowMap.containsKey(orderDO.getOrderId()+orderDO.getWarehouseCode())) {
                    rowMap.put(orderDO.getOrderId()+orderDO.getWarehouseCode(), new ArrayList<OrderDO>());
                }
                rowMap.get(orderDO.getOrderId()+orderDO.getWarehouseCode()).add(orderDO);
            }
            int rownum = 2;
            // 第一行是标题，第二行是样例
            // 从第三行开始插入，最后再把第二行删除
            for (Map.Entry<String, List<OrderDO>> rowEntry : rowMap.entrySet()) {
                Map<String, String> singleRowMap = null;
                if (rowEntry.getValue() != null) {
                    OrderDO firstOrder = rowEntry.getValue().get(0);
                    Map<String, String> orderMap = RefBeanUtils.getFieldValueMap(firstOrder);
                    if (singleRowMap == null) {
                        singleRowMap = new HashMap<String, String>();
                        for (Map.Entry<String, String> orderEntry : orderMap.entrySet()) {
                            for (Map<String, Object> excelFieldMap : excelFieldMapList) {
                                if (excelFieldMap.get("excel_field_name") != null && !"".equals(excelFieldMap.get("excel_field_name"))) {
                                    if (excelFieldMap.get("value").equals(orderEntry.getKey())) {
                                        singleRowMap.put((String) excelFieldMap.get("excel_field_name"), orderEntry.getValue());
                                    }
                                }
                            }
                        }
                    }
                    String sku = "SKU#N#";
                    String quantity = "数量#N#/Quantity #N#";
                    int skuNum = 1;
                    for (OrderDO oneOrderDO : rowEntry.getValue()) {
                        orderMap = RefBeanUtils.getFieldValueMap(oneOrderDO);
                        for (int i = 0; i < titleList.size(); i++) {
                            if (titleList.get(i).equals(sku.replace("#N#", skuNum + ""))) {
                                singleRowMap.put(sku.replace("#N#", skuNum + ""), orderMap.get("sku"));
                            } else if (titleList.get(i).equals(quantity.replace("#N#", skuNum + ""))) {
                                singleRowMap.put(quantity.replace("#N#", skuNum + ""), orderMap.get("quantity"));
                            }
                        }
                        skuNum++;
                    }
                    if (singleRowMap != null) {
                        Row targetRow = workbook.getSheetAt(0).getRow(rownum);
                        if(targetRow == null){
                            workbook.getSheetAt(0).createRow(rownum);
                            targetRow = workbook.getSheetAt(0).getRow(rownum);
                        }
                        et.copyRow(firstRow, targetRow,true);
                        for (int colnum = 0; colnum < firstRow.getLastCellNum(); colnum++) {
                            Cell cell = workbook.getSheetAt(0).getRow(rownum).getCell(colnum);
                            if (singleRowMap.containsKey(titleList.get(colnum))) {
//                                if (singleRowMap.get(titleList.get(colnum)) == null || "".equals(singleRowMap.get(titleList.get(colnum)))) {
//                                    cell.setCellValue(firstRow.getCell(colnum).getStringCellValue());
//                                } else {
                                    cell.setCellValue(singleRowMap.get(titleList.get(colnum)));
//                                }
                            }
                        }
                        rownum++;
                    }
                }
            }
            List<? extends DataValidation> dvList = workbook.getSheetAt(0).getDataValidations();
            for(DataValidation dv : dvList){
                for(CellRangeAddress cra : dv.getRegions().getCellRangeAddresses()){
                    if(cra.getFirstRow()>0){
                        cra.setLastRow(rownum);
                        workbook.getSheetAt(0).addValidationData(dv);
                    }
                }
            }
            workbook.getSheetAt(0).shiftRows(2, rownum, -1);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return workbook;
    }

}
