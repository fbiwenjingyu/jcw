package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.*;
import org.springframework.web.bind.annotation.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import java.util.*;

@RestController
@RequestMapping({ "/bq" })
public class FLTJBQController extends BaseController
{
    private Logger logger;
    @Autowired
    private FLTJBQMapper fltjbqMapper;
    
    public FLTJBQController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/queryBQ" })
    public Result queryBQ() {
        this.logger.info("标准库下标签数据查询");
        final Result result = new Result();
        try {
            final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)this.fltjbqMapper.findAll(Integer.valueOf(NormalEnum.FLType.BZK_BS.GetCode()));
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
    
    @RequestMapping({ "/queryBQGL" })
    public Result queryBQGL() {
        this.logger.info("标准关联下标签数据查询");
        final Result result = new Result();
        try {
            final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)this.fltjbqMapper.findAll(Integer.valueOf(NormalEnum.FLType.BZK_GL.GetCode()));
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
}
