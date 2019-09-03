package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import java.math.*;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.*;
import java.util.*;
import com.ovit.jcw.model.*;

@RestController
@RequestMapping({ "/system/auditLog" })
public class AuditLogController extends BaseController
{
    private Logger logger;
    @Autowired
    private AuditLogMapper auditLogMapper;
    
    public AuditLogController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/dataStatistics" })
    public Result queryByDataType() {
        this.logger.info("数据统计查询");
        final Result result = new Result();
        final Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("opt", NormalEnum.DataStatistics.Today.getCode());
            final Integer todayFrequency = this.auditLogMapper.selectFrequency((Map)map);
            map.put("opt", NormalEnum.DataStatistics.Yesterday.getCode());
            final Integer yesterdayFrequency = this.auditLogMapper.selectFrequency((Map)map);
            map.put("opt", NormalEnum.DataStatistics.CurrentMonth.getCode());
            final Integer currentMonthFrequency = this.auditLogMapper.selectFrequency((Map)map);
            map.put("opt", NormalEnum.DataStatistics.Today.getCode());
            final BigInteger todayUsageAmount = this.auditLogMapper.selectUsageAmount((Map)map);
            map.put("opt", NormalEnum.DataStatistics.Yesterday.getCode());
            final BigInteger yesterdayUsageAmount = this.auditLogMapper.selectUsageAmount((Map)map);
            map.put("opt", NormalEnum.DataStatistics.CurrentMonth.getCode());
            final BigInteger currentMonthUsageAmount = this.auditLogMapper.selectUsageAmount((Map)map);
            map.clear();
            map.put("todayFrequency", todayFrequency);
            map.put("yesterdayFrequency", yesterdayFrequency);
            map.put("currentMonthFrequency", currentMonthFrequency);
            map.put("todayUsageAmount", todayUsageAmount);
            map.put("yesterdayUsageAmount", yesterdayUsageAmount);
            map.put("currentMonthUsageAmount", currentMonthUsageAmount);
            this.logger.info("数据查询成功：{}", (Object)map);
            result.setData(map);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/query" })
    public Result query(@RequestBody final Map<String, Object> params) {
        this.logger.info("日志列表查询");
        final Result result = new Result();
        try {
            this.logger.info("查询条件：{}", (Object)params);
            final int size = Integer.parseInt(params.get("size").toString());
            final int page = Integer.parseInt(params.get("page").toString());
            PageHelper.startPage(page, size);
            final List<AuditLog> resultMap = (List<AuditLog>)this.auditLogMapper.query((Map)params);
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
}
