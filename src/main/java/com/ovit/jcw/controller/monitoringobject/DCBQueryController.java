package com.ovit.jcw.controller.monitoringobject;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.service.*;
import com.ovit.jcw.service.impl.*;
import com.ovit.jcw.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.github.pagehelper.*;
import com.ovit.jcw.utils.*;
import com.alibaba.fastjson.*;
import org.springframework.web.multipart.*;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.*;
import java.util.*;
import javax.servlet.http.*;
import java.net.*;
import java.io.*;

@RestController
@RequestMapping({ "/dcbQuery" })
public class DCBQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private ExcelServiceImpl eXFLService;
    @Autowired
    private DCBMapper dcbMapper;
    @Value("${word.path}")
    private String path;
    
    public DCBQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/queryByLibrary" })
    public Result queryByLibrary(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("调查表-根据委办局查询:{}", (Object)params);
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                PageHelper.startPage(page, size);
                final List<Map<String, Object>> resultMap = (List<Map<String, Object>>)this.dcbMapper.queryJBXXData((Map)params);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportForDCB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/deleteForDCB" })
    public Result deleteForDCB(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("调查表-删除个人基本信息:{}", (Object)params);
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                final String card = params.get("card").toString();
                this.logger.info("删除条件：{}", (Object)card);
                this.eXFLService.deleteByCard(card, NormalEnum.ExcelTYPE.DCB);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.deleteForDCB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/deleteListForDCB" })
    public Result deleteListForDCB(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("调查表-批量删除个人基本信息:{}", (Object)params);
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                final String card = params.get("cardList").toString();
                this.logger.info("删除条件：{}", (Object)card);
                if (card != null && card.length() > 0) {
                    final List<String> alist = (List<String>)JSONArray.parseArray(card, (Class)String.class);
                    this.eXFLService.deleteList(alist, NormalEnum.ExcelTYPE.DCB);
                    this.logger.info("数据删除成功");
                    result.setMsg("数据删除成功");
                    dataUsage = BigInteger.valueOf(1L);
                }
                else {
                    this.logger.info("列表数据为空");
                    result.setMsg("列表数据为空");
                    result.setCode(ResultCode.FAIL);
                }
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.deleteForDCB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForDCB" })
    public Result queryForDCB(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("调查表-根据身份证查询:{}", (Object)params);
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                final String card = params.get("card").toString();
                this.logger.info("查询条件：{}", (Object)card);
                final Map<String, Object> map = this.eXFLService.query(card, NormalEnum.ExcelTYPE.DCB);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForDCB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/exportByStream" })
    public Result exportByStream(final HttpServletRequest request, @RequestParam("file") final MultipartFile file, @RequestParam("library") final String library) {
        this.logger.info("调查表-导入excel");
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
                final String card = this.eXFLService.exportByStream(library, inputStream, NormalEnum.ExcelTYPE.DCB);
                result.setData(card);
                result.setMsg("数据导入成功");
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg(e.getMessage());
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportForDCB.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
        @RequestMapping({ "/exportMultipart" })
    public Result exportMultipart(final HttpServletRequest request, @RequestParam("file") final MultipartFile[] file, @RequestParam("library") final String library) {
        this.logger.info("调查表-批量导入excel");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            final List<MultipartFile> flist = new ArrayList<MultipartFile>();
            if (file == null || file.length <= 0) {
                result.setMsg("文件列表为空");
                result.setCode(ResultCode.FAIL);
                return result;
            }
            for (final MultipartFile f : file) {
                if (!f.isEmpty()) {
                    flist.add(f);
                }
            }
            if (flist.size() == 0) {
                result.setMsg("文件列表数据错误");
                result.setCode(ResultCode.FAIL);
                return result;
            }
            try {
                final List<String> list = new ArrayList<String>();
                for (final MultipartFile f2 : flist) {
                    final InputStream inputStream = f2.getInputStream();
                    final String card = this.eXFLService.exportByStream(library, inputStream, NormalEnum.ExcelTYPE.DCB);
                    if (StringUtils.isNotEmpty((CharSequence)card)) {
                        list.add(card);
                    }
                }
                result.setData(list);
                result.setMsg("数据导入成功");
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.exportMultipart.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg("非法请求");
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/downDefaultExcel" })
    public void downDefaultExcel(final HttpServletRequest request, final HttpServletResponse response) {
        this.logger.info("调查表-导出excel模板");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                final String fileName = "监察对象调查库个人情况调查表.xlsx";
                final String downPath = this.path + "/" + fileName;
                response.setContentType("application/octet-stream");
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                final InputStream is = new FileInputStream(downPath);
                int len = 0;
                final byte[] b = new byte[1024];
                final OutputStream os = (OutputStream)response.getOutputStream();
                while ((len = is.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                os.flush();
                is.close();
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.downDefaultExcel.getCode(), descPretty, dataUsage, queryType);
            }
        }
    }
}
