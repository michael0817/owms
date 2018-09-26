package com.bootdo.order.controller;

import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.*;
import com.bootdo.order.domain.ExcelFieldsDO;
import com.bootdo.order.domain.ModuleDO;
import com.bootdo.order.service.ExcelFieldsService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单模板
 *
 * @author xumx
 * @email michael0817@126.com
 * @date 2018-09-22 16:27:23
 */
 
@Controller
@RequestMapping("/order/module")
public class ModuleController extends BaseController {
	@Autowired
	private ModuleService moduleService;

    @Autowired
    private FileService sysFileService;

    @Autowired
    private ExcelFieldsService excelFieldService;

    @Autowired
    private BootdoConfig bootdoConfig;
	
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

	@ResponseBody
	@GetMapping("/listAll")
    @RequiresPermissions("order:module:list")
    public List<ModuleDO> listAll() {
		// 查询列表数据
		Map<String, Object> map = new HashMap<>(16);
		List<ModuleDO> moduleList = moduleService.list(map);
		return moduleList;
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
		if(module.getModuleType() == 2){
			Map params = new HashMap();
			params.put("moduleType", 2);
			if(moduleService.list(params).size() > 0){
				return R.error("订单宝模板已经存在，不能重复导入");
			}
		}else if(module.getModuleType() == 3){
			Map params = new HashMap();
			params.put("moduleType", 3);
			if(moduleService.list(params).size() > 0){
				return R.error("运单模板已经存在，不能重复导入");
			}
		}
		module.setCreateDate(new Date());
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

		String fileName = bootdoConfig.getUploadPath() + "/modules/" + moduleService.get(moduleId).getUrl();
		if (moduleService.remove(moduleId)>0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
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

    @ResponseBody
    @PostMapping("/upload")
    R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String fileName = file.getOriginalFilename();
        //fileName = FileUtil.renameToUUID(fileName);
        try {
            FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath()+"/modules/", fileName);
        } catch (Exception e) {
            return R.error();
        }
        ExcelUtils et;
        try {
            et = new ExcelUtils(file.getInputStream());
            List<String> titleList = et.read(0,0,1).get(0);
            excelFieldService.remove(fileName);
            for(String title : titleList){
                ExcelFieldsDO efDO = new ExcelFieldsDO();
                efDO.setModuleName(fileName);
                efDO.setExcelFieldName(title);
                excelFieldService.save(efDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok().put("fileName",fileName);
    }

}
