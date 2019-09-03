package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import com.github.pagehelper.*;
import com.ovit.jcw.model.*;
import com.alibaba.fastjson.*;
import org.springframework.web.multipart.*;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import com.ovit.jcw.utils.*;
import java.io.*;
import org.apache.poi.ss.usermodel.*;

@RestController
@RequestMapping({ "/system/mobileDevice" })
public class MobileDeviceController extends BaseController
{
    private Logger logger;
    @Autowired
    private MobileDeviceService mobileDeviceService;
    @Autowired
    private UserService userService;
    
    public MobileDeviceController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/query" })
    public Result query(@RequestBody final Map<String, Object> params) {
        this.logger.info("移动设备管理列表及查询");
        final Result result = new Result();
        try {
            this.logger.info("查询条件：{}", (Object)params);
            final int size = Integer.parseInt(params.get("size").toString());
            final int page = Integer.parseInt(params.get("page").toString());
            PageHelper.startPage(page, size);
            final List<MobileDevice> resultMap = this.mobileDeviceService.query(params);
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(new PageInfo((List)resultMap));
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/optionStatus" })
    public Result optionStatus(@RequestBody final Map<String, Object> params) {
        this.logger.info("移动设备启用禁用操作");
        final Result result = new Result();
        try {
            this.logger.info("参数条件：{}", (Object)params);
            final String json = JSONObject.toJSONString(params.get("entity"));
            if (json != null && !json.equals("")) {
                final MobileDevice mobileDevice = (MobileDevice)JSONObject.parseObject(json, (Class)MobileDevice.class);
                if (mobileDevice != null) {
                    this.mobileDeviceService.updateStatusByIMEI(mobileDevice);
                    result.setData("");
                    result.setMsg("操作成功");
                }
                else {
                    result.setMsg("参数传输为空");
                    result.setCode(ResultCode.FAIL);
                }
            }
            else {
                result.setMsg("参数传输异常");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("数据操作异常：{}", (Throwable)e);
            result.setMsg("数据操作异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/bind" })
    public Result bind(@RequestBody final Map<String, Object> params) {
        this.logger.info("移动设备绑定解绑操作");
        final Result result = new Result();
        try {
            this.logger.info("参数条件：{}", (Object)params);
            final String json = JSONObject.toJSONString(params.get("entity"));
            if (json != null && !json.equals("")) {
                final List<MobileDevice> mobileDeviceList = (List<MobileDevice>)JSONObject.parseArray(json, (Class)MobileDevice.class);
                if (mobileDeviceList != null && mobileDeviceList.size() > 0) {
                    for (final MobileDevice mobileDevice : mobileDeviceList) {
                        this.mobileDeviceService.updateBindByIMEI(mobileDevice);
                    }
                    result.setData("");
                    result.setMsg("操作成功");
                }
                else {
                    result.setMsg("参数传输为空");
                    result.setCode(ResultCode.FAIL);
                }
            }
            else {
                result.setMsg("参数传输异常");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("数据操作异常：{}", (Throwable)e);
            result.setMsg("数据操作异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/save" })
    public Result save(@RequestBody final Map<String, Object> params) {
        this.logger.info("创建移动设备管理数据");
        final Result result = new Result();
        try {
            this.logger.info("参数条件：{}", (Object)params);
            final String json = JSONObject.toJSONString(params.get("entity"));
            if (json != null && !json.equals("")) {
                final MobileDevice mobileDevice = (MobileDevice)JSONObject.parseObject(json, (Class)MobileDevice.class);
                final String imei = mobileDevice.getImei();
                if (imei != null && !imei.equals("")) {
                    final int count = this.mobileDeviceService.selectCountByIMEI(imei);
                    if (count > 0) {
                        result.setMsg("IMEI对应的手机设备已存在");
                        result.setCode(ResultCode.FAIL);
                    }
                    else {
                        this.mobileDeviceService.insert(mobileDevice);
                        this.logger.info("数据创建成功：{}", (Object)mobileDevice);
                        result.setData(mobileDevice);
                        result.setMsg("数据创建成功");
                    }
                }
                else {
                    result.setMsg("IMEI数据为空");
                    result.setCode(ResultCode.FAIL);
                }
            }
            else {
                result.setMsg("参数传输异常");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("数据创建失败：{}", (Throwable)e);
            result.setMsg("数据创建失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/delete" })
    public Result delete(@RequestBody final Map<String, Object> params) {
        this.logger.info("删除移动设备管理数据");
        final Result result = new Result();
        try {
            this.logger.info("参数条件：{}", (Object)params);
            final Integer id = Integer.parseInt(params.get("id").toString());
            this.mobileDeviceService.deleteByPrimaryKey(id);
            result.setMsg("数据删除成功");
        }
        catch (Exception e) {
            this.logger.error("数据删除失败：{}", (Throwable)e);
            result.setMsg("数据删除失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/getLoginUser" })
    public Result getLoginUser() {
        this.logger.info("获取账号数据");
        final Result result = new Result();
        try {
            final List<String> resultMap = this.userService.selectLoginName();
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(resultMap);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/getDeviceName" })
    public Result getDeviceName() {
        this.logger.info("获取设备名称");
        final Result result = new Result();
        try {
            final List<String> resultMap = this.mobileDeviceService.selectDeviceName();
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(resultMap);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/export" })
    public Result export(@RequestParam("file") final MultipartFile file) {
        this.logger.info("导入移动设备管理数据");
        final Result result = new Result();
        InputStream inputStream = null;
        if (file.isEmpty()) {
            result.setMsg("文件不存在");
            result.setCode(ResultCode.FAIL);
            return result;
        }
        final List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        try {
            inputStream = file.getInputStream();
            final String fileName = file.getOriginalFilename();
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith(".xlsx")) {
                workbook = (Workbook)new XSSFWorkbook(inputStream);
            }
            else if (fileName.toLowerCase().endsWith(".xls")) {
                workbook = (Workbook)new HSSFWorkbook(inputStream);
            }
            Sheet sheet = null;
            Row row = null;
            if (workbook != null) {
                for (int w = 0; w < workbook.getNumberOfSheets(); ++w) {
                    sheet = workbook.getSheetAt(w);
                    final int rowCount = sheet.getLastRowNum();
                    final Map<Integer, String> titleMap = new HashMap<Integer, String>();
                    for (int i = 0; i < rowCount + 1; ++i) {
                        row = sheet.getRow(i);
                        final int cellCount = row.getLastCellNum();
                        if (i == 0) {
                            for (int j = 0; j < cellCount; ++j) {
                                titleMap.put(j, row.getCell(j).getStringCellValue().trim());
                            }
                        }
                        else if (titleMap.size() > 0) {
                            final Map<String, Object> dataMap = new HashMap<String, Object>();
                            dataList.add(dataMap);
                            for (int k = 0; k < cellCount; ++k) {
                                final Cell cell = row.getCell(k);
                                final String title = titleMap.get(k).toString();
                                final String value = ExcelUtils.getCellValue(cell);
                                dataMap.put(title, value);
                            }
                        }
                    }
                }
            }
            workbook.close();
            if (dataList != null && dataList.size() > 0) {
                for (final Map<String, Object> map : dataList) {
                    try {
                        this.mobileDeviceService.insertObject(map);
                    }
                    catch (Exception ex) {
                        this.logger.error("数据插入异常：{}", (Object)ex.getMessage());
                    }
                }
            }
            result.setMsg("数据导入成功");
        }
        catch (Exception e) {
            this.logger.error("数据导入失败：{}", (Throwable)e);
            result.setMsg("数据创导入败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e2) {
                    this.logger.error("文件操作异常", (Throwable)e2);
                }
            }
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e3) {
                    this.logger.error("文件操作异常", (Throwable)e3);
                }
            }
        }
        return result;
    }
}
