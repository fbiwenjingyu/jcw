package com.ovit.jcw.controller.wisdomassociation;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import com.github.pagehelper.*;
import java.util.*;

@RestController
@RequestMapping({ "/associationLibrary" })
public class AssociationLibraryController extends BaseController
{
    private Logger logger;
    @Autowired
    private AssociationLibraryService associationLibraryService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private BusService busService;
    
    public AssociationLibraryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/query" })
    public Result query(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联库-人社局关联查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            Integer queryType = 0;
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = params.get("queryCondition").toString();
                final Integer type = Integer.parseInt(params.get("type").toString());
                String table = "";
                if (type == NormalEnum.TableType.RSJ_INFO.GetCode()) {
                    queryType = NormalEnum.QueryType.HumanAndSocial.getCode();
                    table = NormalEnum.TableType.RSJ_INFO.GetDesc();
                }
                else if (type == NormalEnum.TableType.SFJ_SFGZXX_INFO.GetCode()) {
                    queryType = NormalEnum.QueryType.LegalJustices.getCode();
                    table = NormalEnum.TableType.SFJ_SFGZXX_INFO.GetDesc();
                }
                else if (type == NormalEnum.TableType.SFJ_JDXX_INFO.GetCode()) {
                    queryType = NormalEnum.QueryType.Forensics.getCode();
                    table = NormalEnum.TableType.SFJ_JDXX_INFO.GetDesc();
                }
                else if (type == NormalEnum.TableType.NEWBIINFO.GetCode()) {
                    queryType = NormalEnum.QueryType.Project.getCode();
                    table = NormalEnum.TableType.NEWBIINFO.GetDesc();
                }
                descPretty = queryCondition;
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap = this.associationLibraryService.query(this.token, type, table, queryCondition, page, size);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForAssociationLibrary.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/humanAndSocial" })
    public Result humanAndSocial(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联库-人社局关联查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.LegalJustices.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.associationLibraryService.selectForHumanAndSocial(queryCondition);
                this.authority(resultMap, "RSJ_INFO");
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.humanAndSocial.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/legalJustices" })
    public Result legalJustices(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联库-司法公正信息");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.LegalJustices.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.associationLibraryService.selectForLegalJustices(queryCondition);
                this.authority(resultMap, "SFJ_SFGZXX_INFO");
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.legalJustices.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/forensics" })
    public Result forensics(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("关联库-司法鉴定信息");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.Forensics.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String queryCondition = descPretty = params.get("queryCondition").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                resultMap = this.associationLibraryService.selectForForensics(queryCondition);
                this.authority(resultMap, "SFJ_JDXX_INFO");
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.forensics.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private void authority(final List<Map<String, Object>> resultMap, final String table) {
        if (resultMap != null && resultMap.size() > 0) {
            final List<String> secretList = this.busService.queryEnglishSecretFiledByTable(table);
            final List<String> signList = this.busService.querySignFiledByTable(table);
            final List<String> redList = this.authorityService.getRedListByUser(null);
            final List<String> redAuthorityList = this.authorityService.getRedListByUser(this.token);
            final Map<String, Map<String, String>> keyList = this.authorityService.getTableFieldKey(table, 0);
            this.authorityService.redSignList(secretList, signList, redList, redAuthorityList, keyList, resultMap);
        }
    }
}
