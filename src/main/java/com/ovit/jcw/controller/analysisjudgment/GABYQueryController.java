package com.ovit.jcw.controller.analysisjudgment;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.service.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.impl.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.multipart.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import java.io.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/gabyQuery" })
public class GABYQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private ExcelServiceImpl eXFLService;
    
    public GABYQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/exportByStream" })
    public Result exportByStream(final HttpServletRequest request, @RequestParam("file") final MultipartFile file) {
        this.logger.info("公安部云-导入excel");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            if (file.isEmpty()) {
                result.setMsg("文件不存在");
                result.setCode(ResultCode.FAIL);
                return result;
            }
            try {
                final InputStream inputStream = file.getInputStream();
                final String card = this.eXFLService.exportByStream("", inputStream, NormalEnum.ExcelTYPE.GABY);
                result.setData(card);
                result.setMsg("数据导入成功");
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportForGABY.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForGABY" })
    public Result queryForGABY(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("公安部云-查询身份证:{}", (Object)params);
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.FZBFLibrary.getCode();
            final String descPretty = "";
            try {
                final String card = params.get("card").toString();
                this.logger.info("查询条件：{}", (Object)card);
                final Map<String, Object> map = this.eXFLService.query(card, NormalEnum.ExcelTYPE.GABY);
                result.setData(map);
                this.logger.info("数据查询成功：{}", (Object)map);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForGABY.getCode(), descPretty, dataUsage, queryType);
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
