package com.ovit.jcw.controller.correlationanalysis;

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
import java.util.*;
import com.ovit.jcw.utils.*;

@RestController
@RequestMapping({ "/correlationAnalysis" })
public class CorrelationAnalysisController extends BaseController
{
    private Logger logger;
    @Autowired
    private CorrelationAnalysisService correlationAnalysisService;
    @Autowired
    private AuditLogService auditLogService;
    
    public CorrelationAnalysisController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/query" })
    public Result query(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联分析查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.CorrelationAnalysis.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = params.get("queryCondition").toString();
                final Integer type = Integer.parseInt(params.get("type").toString());
                descPretty = queryCondition;
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                resultMap = this.correlationAnalysisService.selectByTerm(queryCondition, type, page, size);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForCorrelationAnalysis.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryChild" })
    public Result queryChild(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联分析-关联关系查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.CorrelationAnalysis.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = params.get("queryCondition").toString();
                final Integer type = Integer.parseInt(params.get("type").toString());
                descPretty = queryCondition;
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                resultMap = this.correlationAnalysisService.selectChildByTerm(queryCondition, type);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForCorrelationAnalysis.getCode(), descPretty, dataUsage, queryType);
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
