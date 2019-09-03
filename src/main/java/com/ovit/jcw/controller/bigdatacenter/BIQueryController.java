package com.ovit.jcw.controller.bigdatacenter;

import com.ovit.jcw.controller.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import java.util.*;

@RestController
@RequestMapping({ "/biQuery" })
public class BIQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private BIService biService;
    @Autowired
    private AuditLogService auditLogService;
    
    public BIQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/queryForRHZXDZJZ" })
    public Result queryForRHZXDZJZ(final HttpServletRequest request) {
        this.logger.info("融合中心-数据使用情况");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectForRHZXDZJZ();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForRHZXDZJZ.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForRHZXSJSL" })
    public Result queryForRHZXSJSL(final HttpServletRequest request) {
        this.logger.info("融合中心-各市委办局数据被使用情况");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final Map<String, Object> resultMap = this.biService.selectForRHZXSJSL();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForRHZXSJSL.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForRHZXWBJ" })
    public Result queryForRHZXWBJ(final HttpServletRequest request) {
        this.logger.info("融合中心-各市委办局数据被使用统计");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectForRHZXWBJ();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForRHZXWBJ.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXSJXXTJ" })
    public Result queryForHJZXSJXXTJ(final HttpServletRequest request) {
        this.logger.info("汇集中心-数据信息统计");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryByHJZX(NormalEnum.HJZXType.SJXXTJ, NormalEnum.InterfaceName.queryForHJZXSJXXTJ, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXSJCCFX" })
    public Result queryForHJZXSJCCFX(final HttpServletRequest request) {
        this.logger.info("汇集中心-数据存储分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryByHJZX(NormalEnum.HJZXType.SJCCFX, NormalEnum.InterfaceName.queryForHJZXSJCCFX, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXDSJFX" })
    public Result queryForHJZXDSJFX(final HttpServletRequest request) {
        this.logger.info("汇集中心-大数据分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryByHJZX(NormalEnum.HJZXType.DSJFX, NormalEnum.InterfaceName.queryForHJZXDSJFX, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private void queryByHJZX(final NormalEnum.HJZXType type, final NormalEnum.InterfaceName ifName, final Result result, final HttpServletRequest request, final String loginTime, BigInteger dataUsage, final Integer queryType) {
        try {
            final Map<String, Object> resultMap = this.biService.selectForHJZX(type);
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
            this.auditLogService.insertLogRecordsForNormal(request, loginTime, ifName.getCode(), "", dataUsage, queryType);
        }
    }
    
    @RequestMapping({ "/queryForHJZXSJZL" })
    public Result queryForHJZXSJZL(final HttpServletRequest request) {
        this.logger.info("汇集中心-各委办局数据总量");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByHJZXSJZL();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForHJZXSJZL.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByHJZXKSJZL" })
    public Result queryByHJZXKSJZL(final HttpServletRequest request) {
        this.logger.info("汇集中心-各委办局数据结构量");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final Map<String, List<Map<String, Object>>> resultMap = this.biService.selectByHJZXKSJZL();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByHJZXKSJZL.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXSJAL" })
    public Result queryForHJZXSJAL(final HttpServletRequest request) {
        this.logger.info("汇集中心-各委办局数据增量");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByHJZXSJAL();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForHJZXSJAL.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXYQST" })
    public Result queryForHJZXYQST(final HttpServletRequest request) {
        this.logger.info("汇集中心-数据量更新月趋势图");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryQSTByHJZX(NormalEnum.HJZXType.YQST, NormalEnum.InterfaceName.queryForHJZXYQST, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForHJZXZQST" })
    public Result queryForHJZXZQST(final HttpServletRequest request) {
        this.logger.info("汇集中心-数据量更新周趋势图");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryQSTByHJZX(NormalEnum.HJZXType.ZQST, NormalEnum.InterfaceName.queryForHJZXZQST, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private void queryQSTByHJZX(final NormalEnum.HJZXType type, final NormalEnum.InterfaceName ifName, final Result result, final HttpServletRequest request, final String loginTime, BigInteger dataUsage, final Integer queryType) {
        try {
            final Map<String, List<Map<String, Object>>> resultMap = this.biService.selectForHJZXQST(type);
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
            this.auditLogService.insertLogRecordsForNormal(request, loginTime, ifName.getCode(), "", dataUsage, queryType);
        }
    }
    
    @RequestMapping({ "/queryByWJZXLSQSQK" })
    public Result queryByWJZXLSQSQK(final HttpServletRequest request) {
        this.logger.info("挖掘中心-问题数据发现次数历史趋势情况");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByLSQSQK();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXLSQSQK.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByWJZXSJZLQK" })
    public Result queryByWJZXSJZLQK(final HttpServletRequest request) {
        this.logger.info("挖掘中心-数据质量情况分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final Map<String, Object> resultMap = this.biService.selectBySJZLQK();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXSJZLQK.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByWJZXFSLQSQK" })
    public Result queryByWJZXFSLQSQK(final HttpServletRequest request) {
        this.logger.info("挖掘中心-问题发生率历史趋势情况");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByFSLQSQK();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXFSLQSQK.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByWJZXWBJSJZB" })
    public Result queryByWJZXWBJSJZB(final HttpServletRequest request) {
        this.logger.info("挖掘中心-各委办局问题数据占比top");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByWBJSJZB();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXWBJSJZB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByWJZXWTSJFLZB" })
    public Result queryByWJZXWTSJFLZB(final HttpServletRequest request) {
        this.logger.info("挖掘中心-问题数据分类占比");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByWTSJFLZB();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXWTSJFLZB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryByWJZXSJZLQKFX" })
    public Result queryByWJZXSJZLQKFX(final HttpServletRequest request) {
        this.logger.info("挖掘中心-数据质量情况分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            final String descPretty = "";
            try {
                final Map<String, Object> resultMap = this.biService.selectBySJZLQKFX();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryByWJZXSJZLQKFX.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTJBSJLX" })
    public Result queryForJCXTJBSJLX(final HttpServletRequest request) {
        this.logger.info("监察系统-基本数据类型");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.JBSJLX, NormalEnum.InterfaceName.queryForJCXTJBSJLX, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTDZZJ" })
    public Result queryForJCXTDZZJ(final HttpServletRequest request) {
        this.logger.info("监察系统-电子证据");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.DZZJ, NormalEnum.InterfaceName.queryForJCXTDZZJ, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTCXFX" })
    public Result queryForJCXTCXFX(final HttpServletRequest request) {
        this.logger.info("监察系统-查询方式分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.CXFX, NormalEnum.InterfaceName.queryForJCXTCXFX, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTCXRD" })
    public Result queryForJCXTCXRD(final HttpServletRequest request) {
        this.logger.info("监察系统-查询热度分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.CXRD, NormalEnum.InterfaceName.queryForJCXTCXRD, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTAJLFX" })
    public Result queryForJCXTAJLFX(final HttpServletRequest request) {
        this.logger.info("监察系统-案件量分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.AJLFX, NormalEnum.InterfaceName.queryForJCXTAJLFX, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTAJJZTJ" })
    public Result queryForJCXTAJJZTJ(final HttpServletRequest request) {
        this.logger.info("监察系统-案件进展统计");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.AJJZTJ, NormalEnum.InterfaceName.queryForJCXTAJJZTJ, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTSARYFB" })
    public Result queryForJCXTSARYFB(final HttpServletRequest request) {
        this.logger.info("监察系统-涉案人员分布");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.SARYFB, NormalEnum.InterfaceName.queryForJCXTSARYFB, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTSAAJFL" })
    public Result queryForJCXTSAAJFL(final HttpServletRequest request) {
        this.logger.info("监察系统-涉案案件分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.SAAJFL, NormalEnum.InterfaceName.queryForJCXTSAAJFL, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTWBJBL" })
    public Result queryForJCXTWBJBL(final HttpServletRequest request) {
        this.logger.info("监察系统-覆盖委办局比例");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.WBJBL, NormalEnum.InterfaceName.queryForJCXTWBJBL, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTSAZWFL" })
    public Result queryForJCXTSAZWFL(final HttpServletRequest request) {
        this.logger.info("监察系统-涉案职位分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            final BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            this.queryForJCXT(NormalEnum.JCXTType.SAZWFL, NormalEnum.InterfaceName.queryForJCXTSAZWFL, result, request, loginTime, dataUsage, queryType);
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private void queryForJCXT(final NormalEnum.JCXTType type, final NormalEnum.InterfaceName ifName, final Result result, final HttpServletRequest request, final String loginTime, BigInteger dataUsage, final Integer queryType) {
        try {
            final Map<String, Object> resultMap = this.biService.selectForJCXT(type);
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
            this.auditLogService.insertLogRecordsForNormal(request, loginTime, ifName.getCode(), "", dataUsage, queryType);
        }
    }
    
    @RequestMapping({ "/queryForJCXTQYAJFX" })
    public Result queryForJCXTQYAJFX(final HttpServletRequest request) {
        this.logger.info("监察系统-区域案件分析");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByJCXTQYAJFX();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForJCXTQYAJFX.getCode(), "", dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForJCXTJCRYTJ" })
    public Result queryForJCXTJCRYTJ(final HttpServletRequest request) {
        this.logger.info("监察系统-各委办局检测人员统计");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.BITJLibrary.getCode();
            try {
                final List<Map<String, Object>> resultMap = this.biService.selectByJCXTJCRYTJ();
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForJCXTJCRYTJ.getCode(), "", dataUsage, queryType);
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
