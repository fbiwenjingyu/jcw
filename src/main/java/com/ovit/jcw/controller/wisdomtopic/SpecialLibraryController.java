package com.ovit.jcw.controller.wisdomtopic;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.github.pagehelper.*;
import com.ovit.jcw.utils.*;
import java.util.*;

@RestController
@RequestMapping({ "/specialLibrary" })
public class SpecialLibraryController extends BaseController
{
    private Logger logger;
    @Autowired
    private SpecialLibraryService specialLibraryService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private BusService busService;
    
    public SpecialLibraryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/projectTender" })
    public Result projectTender(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-工程招投标库");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ProjectTender.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.specialLibraryService.selectForProjectTender(queryCondition);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(resultMap.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.projectTender.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/lawsAndRegulations" })
    public Result lawsAndRegulations(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-法律法规库");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.LawsAndRegulations.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.specialLibraryService.selectForLawsAndRegulations(params);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(resultMap.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.lawsAndRegulations.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/caseLibrary" })
    public Result caseLibrary(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-案例库");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.CaseLibrary.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.specialLibraryService.selectForCaseLibrary(params);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(resultMap.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.caseLibrary.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/archive" })
    public Result archive(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-档案库");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.Archive.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                final Map<String, Object> baseInfo = this.specialLibraryService.selectForArchiveBaseInfo(queryCondition);
                final List<Map<String, Object>> marriageList = this.specialLibraryService.queryForArchiveHYXX(queryCondition);
                final String xb = baseInfo.get("XB").toString();
                if (marriageList != null && marriageList.size() > 0) {
                    if (xb.equals("1")) {
                        for (final Map<String, Object> item : marriageList) {
                            if (item.get("YWLB").equals("结婚")) {
                                item.put("GX", "妻子");
                            }
                            else if (item.get("YWLB").equals("离婚")) {
                                item.put("GX", "前妻");
                            }
                            else {
                                item.put("GX", "");
                            }
                            item.put("XB", "女");
                            item.remove("MNAME");
                            item.remove("MZJH");
                        }
                    }
                    else if (xb.equals("2")) {
                        for (final Map<String, Object> item : marriageList) {
                            if (item.get("YWLB").equals("结婚")) {
                                item.put("GX", "丈夫");
                            }
                            else if (item.get("YWLB").equals("离婚")) {
                                item.put("GX", "前夫");
                            }
                            else {
                                item.put("GX", "");
                            }
                            item.put("XB", "男");
                            item.remove("FMNAME");
                            item.remove("FZJH");
                        }
                    }
                }
                final List<Map<String, Object>> childList = this.specialLibraryService.queryArchiveZNXX(queryCondition);
                if (childList != null && childList.size() > 0) {
                    if (xb.equals("1")) {
                        for (final Map<String, Object> item2 : childList) {
                            if (item2.get("XSEXBDM").equals("1")) {
                                item2.put("GX", "父子");
                            }
                            else if (item2.get("XSEXBDM").equals("2")) {
                                item2.put("GX", "父女");
                            }
                            else {
                                item2.put("GX", "");
                            }
                        }
                    }
                    else if (xb.equals("2")) {
                        for (final Map<String, Object> item2 : childList) {
                            if (item2.get("XSEXBDM").equals("1")) {
                                item2.put("GX", "母子");
                            }
                            else if (item2.get("XSEXBDM").equals("2")) {
                                item2.put("GX", "母女");
                            }
                            else {
                                item2.put("GX", "");
                            }
                        }
                    }
                }
                if (baseInfo != null && baseInfo.size() > 0) {
                    this.busService.encryptionDataForSpecialLibrary(this.token, queryCondition, "ZWY_RYXX_GRJBXX", baseInfo);
                }
                if (baseInfo != null && baseInfo.size() > 0) {
                    this.busService.encryptionDataForSpecialLibrary(this.token, queryCondition, "WHSJGRYXX", baseInfo);
                }
                if (marriageList != null && marriageList.size() > 0) {
                    for (final Map<String, Object> map : marriageList) {
                        this.busService.encryptionDataForSpecialLibrary(this.token, queryCondition, "HYDJ", map);
                    }
                }
                if (childList != null && childList.size() > 0) {
                    for (final Map<String, Object> map : childList) {
                        this.busService.encryptionDataForSpecialLibrary(this.token, queryCondition, "CSYXZM_JBXX", map);
                    }
                }
                resultMap.put("baseInfo", baseInfo);
                resultMap.put("marriageList", marriageList);
                resultMap.put("childList", childList);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(baseInfo.size() + marriageList.size() + childList.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.archive.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/vehicleViolationLibrary" })
    public Result vehicleViolationLibrary(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-驾驶证关联违章信息库");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.VehicleViolationLibrary.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.specialLibraryService.selectForVehicleViolationLibrary(queryCondition);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(resultMap.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.vehicleViolationLibrary.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/vehicleViolationLibraryDetail" })
    public Result vehicleViolationLibraryDetail(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("专题库-驾驶证关联违章信息库详情");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.VehicleViolationLibraryDetail.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.specialLibraryService.selectForVehicleViolationLibraryDetail(queryCondition);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(resultMap.size());
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.vehicleViolationLibraryDetail.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
}
