package com.ovit.jcw.controller.app;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mapper.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import com.ovit.jcw.model.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import org.apache.commons.collections.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.common.*;
import java.util.*;

@RestController
@RequestMapping({ "/app/v1.0/oracleQuery" })
public class AppOracleQueryController extends BaseController
{
    private Logger logger;
    @Value("${spring.datasource.mysql.tableMapping}")
    private String tableMapping;
    @Autowired
    private MysqlQueryMapper mysqlQueryMapper;
    @Autowired
    private OracleQueryMapper oracleQueryMapper;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private AuthorityService authorityService;
    
    public AppOracleQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/queryForApp" })
    public Result queryForApp(final HttpServletRequest request, @RequestBody final Es2Oracle es2Oracle) {
        this.logger.info("APP数据库详情查询接口");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = es2Oracle.getQueryType();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)es2Oracle);
                final String dbUser = es2Oracle.getDb_user();
                final String tableName = es2Oracle.getTable_name().toUpperCase();
                final String pkColName = es2Oracle.getPk_col_name();
                final String pkColData = es2Oracle.getPk_col_data();
                descPretty = new StringBuffer().append(dbUser).append(",").append(tableName).append(",").append(pkColName).append(",").append(pkColData).toString();
                final Map<String, Object> queryMap = new HashMap<String, Object>();
                queryMap.put("dbUser", dbUser);
                queryMap.put("tableName", tableName);
                queryMap.put("pkColName", pkColName);
                queryMap.put("pkColData", pkColData);
                final List<Map<String, Object>> datalist = (List<Map<String, Object>>)this.oracleQueryMapper.selectDetails((Map)queryMap);
                queryMap.clear();
                queryMap.put("tableMapping", this.tableMapping);
                queryMap.put("tableName", tableName);
                final List<Map<String, Object>> mappingList = (List<Map<String, Object>>)this.mysqlQueryMapper.selectTableColumnMapping((Map)queryMap);
                final Map<String, Map<String, String>> keyList = this.authorityService.getTableFieldKey(tableName, 0);
                final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                for (final Map<String, Object> map : datalist) {
                    for (final Map.Entry<String, Object> entry : map.entrySet()) {
                        String colunmName = entry.getKey();
                        final Object objval = entry.getValue();
                        String columnData;
                        if (objval == null) {
                            columnData = "";
                        }
                        else {
                            columnData = String.valueOf(objval);
                            if (keyList.size() > 0 && keyList.containsKey(colunmName)) {
                                final Map<String, String> keyMap = keyList.get(colunmName);
                                if (keyMap.containsKey(columnData)) {
                                    columnData = keyMap.get(columnData);
                                }
                                else {
                                    columnData = keyMap.get(Utils.DefaultValue);
                                }
                            }
                        }
                        final Map<String, Object> dataMap = new HashMap<String, Object>();
                        dataMap.put("data", columnData);
                        dataMap.put("col_name", colunmName);
                        for (final Map<String, Object> mappingMap : mappingList) {
                            if (colunmName.equalsIgnoreCase(MapUtils.getString((Map)mappingMap, (Object)"COLUMN_NAME"))) {
                                colunmName = MapUtils.getString((Map)mappingMap, (Object)"COLUMN_DESC");
                                break;
                            }
                        }
                        dataMap.put("c_name", colunmName);
                        resultList.add(dataMap);
                    }
                }
                this.logger.info("数据查询成功：{}", (Object)resultList);
                result.setData(resultList);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForApp.getCode(), descPretty, dataUsage, queryType);
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
