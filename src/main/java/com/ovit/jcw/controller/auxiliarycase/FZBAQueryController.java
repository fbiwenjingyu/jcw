package com.ovit.jcw.controller.auxiliarycase;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mapper.*;
import com.ovit.jcw.mysqlmapper.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.common.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import com.github.pagehelper.*;
import org.springframework.web.multipart.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;
import org.apache.poi.poifs.filesystem.*;
import com.ovit.jcw.utils.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

@RestController
@RequestMapping({ "/fzbaQuery" })
public class FZBAQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private FZBAFLFGMapper fLFGMapper;
    @Autowired
    private FZBAZFALMapper zFALMapper;
    @Autowired
    private EsService esService;
    @Autowired
    private DataConfigMapper dataConfigMapper;
    @Autowired
    private AuditLogService auditLogService;
    
    public FZBAQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/queryByType" })
    public Result queryByType(@RequestBody final Map<String, Object> params) {
        this.logger.info("数据字典查询");
        final Result result = new Result();
        this.logger.info("查询条件：{}", (Object)params);
        try {
            final Integer type = Integer.parseInt(params.get("type").toString());
            final String dataType = NormalEnum.FZBAType.values()[type].GetCode();
            final List<String> resultMap = (List<String>)this.dataConfigMapper.queryData(dataType);
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
    
    @RequestMapping({ "/queryFZBACount" })
    public Result queryFZBACount(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-首页数量信息");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            try {
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                Integer count = this.zFALMapper.selectCount();
                resultMap.put("zfalCount", count);
                count = this.fLFGMapper.selectCount();
                resultMap.put("flfgCount", count);
                count = this.zFALMapper.selectTodayCount();
                resultMap.put("zfalTodayCount", count);
                count = this.fLFGMapper.selectTodayCount();
                resultMap.put("flfgTodayCount", count);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryFZBACount.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryFZBAInfo" })
    public Result queryFZBAInfo(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-首页近期更新信息");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            try {
                this.logger.info("查询条件：{}", (Object)params);
                Integer todayCount = this.zFALMapper.selectTodayCount();
                todayCount += this.fLFGMapper.selectTodayCount();
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                PageHelper.startPage(page, size);
                final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)((todayCount > 0) ? this.fLFGMapper.queryAll() : this.fLFGMapper.queryJQ());
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryFZBAInfo.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForFLFG" })
    public Result queryForFLFG(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-法律法规");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            String info = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String lb = params.get("lb").toString();
                info = params.get("info").toString();
                PageHelper.startPage(page, size);
                final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)this.fLFGMapper.query(lb, info);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForFLFG.getCode(), info, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryEsForFLFG" })
    public Result queryEsForFLFG(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-法律法规");
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            String info = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String lb = params.get("lb").toString();
                info = params.get("info").toString();
                result = this.esService.query("FLFG", lb, info, page, size);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForFLFG.getCode(), info, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryEsForZFAL" })
    public Result queryEsForZFAL(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-执法案例");
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            String info = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String lb = params.get("lb").toString();
                info = params.get("info").toString();
                result = this.esService.query("ZFAL", lb, info, page, size);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForZFAL.getCode(), info, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForZFAL" })
    public Result queryForZFAL(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-执法案例");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            String info = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String lb = params.get("lb").toString();
                info = params.get("info").toString();
                PageHelper.startPage(page, size);
                final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)this.zFALMapper.query(lb, info);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForZFAL.getCode(), info, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryOneFLFG" })
    public Result queryOneFLFG(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-单条法律法规");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final Integer id = Integer.parseInt(params.get("id").toString());
                final Map<String, Object> resultMap = (Map<String, Object>)this.fLFGMapper.select(id);
                final String zw = this.fLFGMapper.selectZW(id);
                resultMap.put("ZW", zw);
                result.setData(resultMap);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryOneFLFG.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryOneZFAL" })
    public Result queryOneZFAL(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("辅助办案-单条执法案例");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final Integer id = Integer.parseInt(params.get("id").toString());
                final Map<String, Object> resultMap = (Map<String, Object>)this.zFALMapper.select(id);
                final String zw = this.zFALMapper.selectZW(id);
                resultMap.put("ZW", zw);
                result.setData(resultMap);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryOneZFAL.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/exportByFLFG" })
    public Result exportByFLFG(final HttpServletRequest request, @RequestParam("file") final MultipartFile file) {
        this.logger.info("辅助办案-导入法律法规");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            if (file.isEmpty()) {
                result.setMsg("文件不存在");
                result.setCode(ResultCode.FAIL);
                return result;
            }
            try {
                final InputStream inputStream = file.getInputStream();
                final List<Map<String, String>> dataList = this.JX(inputStream);
                if (dataList != null && dataList.size() > 0) {
                    for (final Map<String, String> map : dataList) {
                        try {
                            if (!map.containsKey("ss")) {
                                map.put("ss", "中纪委");
                            }
                            this.fLFGMapper.InsertObject((Map)map);
                        }
                        catch (Exception ex) {
                            this.logger.error("数据插入异常：{}", (Object)ex.getMessage());
                        }
                    }
                }
                result.setMsg("数据导入成功");
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportByFLFG.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/exportByZFAL" })
    public Result exportByZFAL(final HttpServletRequest request, @RequestParam("file") final MultipartFile file) {
        this.logger.info("辅助办案-导入执法案例");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            if (file.isEmpty()) {
                result.setMsg("文件不存在");
                result.setCode(ResultCode.FAIL);
                return result;
            }
            try {
                final InputStream inputStream = file.getInputStream();
                final List<Map<String, String>> dataList = this.JX(inputStream);
                if (dataList != null && dataList.size() > 0) {
                    for (final Map<String, String> map : dataList) {
                        try {
                            this.zFALMapper.InsertObject((Map)map);
                        }
                        catch (Exception ex) {
                            this.logger.error("数据插入异常：{}", (Object)ex.getMessage());
                        }
                    }
                }
                result.setMsg("数据导入成功");
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportByZFAL.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private List<Map<String, String>> JX(final InputStream stream) {
        HSSFWorkbook workbook = null;
        POIFSFileSystem fs = null;
        final List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        try {
            fs = new POIFSFileSystem(stream);
            workbook = new HSSFWorkbook(fs);
            final HSSFSheet sheet = workbook.getSheetAt(0);
            final int rowCount = sheet.getLastRowNum();
            final Map<Integer, String> titleMap = new HashMap<Integer, String>();
            for (int i = 0; i < rowCount + 1; ++i) {
                final HSSFRow row = sheet.getRow(i);
                final int cellCount = row.getLastCellNum();
                if (i == 0) {
                    for (int j = 0; j < cellCount; ++j) {
                        titleMap.put(j, row.getCell(j).getStringCellValue().trim());
                    }
                }
                else if (titleMap.size() > 0) {
                    final Map<String, String> dataMap = new HashMap<String, String>();
                    dataList.add(dataMap);
                    for (int k = 0; k < cellCount; ++k) {
                        final HSSFCell cell = row.getCell(k);
                        final String title = titleMap.get(k).toString();
                        String value = ExcelUtils.getCellValue((Cell)cell);
                        if (title.equals("ss")) {
                            value = value.split("-")[value.split("-").length - 1];
                        }
                        dataMap.put(title, value);
                    }
                }
            }
            workbook.close();
            fs.close();
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return dataList;
    }
}
