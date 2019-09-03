package com.ovit.jcw.service.impl;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mapper.*;
import com.ovit.jcw.service.*;
import com.ovit.jcw.mysqlmapper.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.common.*;
import java.util.*;
import org.apache.logging.log4j.*;

@Service
public class AnalysisJudgmentServiceImpl implements AnalysisJudgmentService
{
    private static Logger logger;
    @Autowired
    private AnalysisJudgmentMapper analysisJudgmentMapper;
    @Autowired
    private OracleQueryMapper oracleQueryMapper;
    @Autowired
    private BusMapper busMapper;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private AuditLogMapper auditLogMapper;
    
    public List<Map<String, Object>> queryTablesByType(final Integer tag) {
        List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(tag);
        return tableList;
    }
    
    public List<Map<String, Object>> queryContent(final Map<String, Object> map) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        resultMap = (List<Map<String, Object>>)this.oracleQueryMapper.queryContent((Map)map);
        return resultMap;
    }
    
    public Integer queryCounts(final Map<String, Object> map) {
        Integer count = 0;
        count = this.oracleQueryMapper.queryCounts((Map)map);
        return count;
    }
    
    public List<Map<String, Object>> fuzzyQuery(final Map<String, Object> map) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        resultMap = (List<Map<String, Object>>)this.oracleQueryMapper.fuzzyQuery((Map)map);
        return resultMap;
    }
    
    public List<Map<String, Object>> associatedQuery(final Map<String, Object> map) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        resultMap = (List<Map<String, Object>>)this.oracleQueryMapper.associatedQuery((Map)map);
        return resultMap;
    }
    
    public List<String> queryForBFHM(final String idNumber) {
        final List<String> list = (List<String>)this.oracleQueryMapper.queryForBFHM(idNumber);
        return list;
    }
    
    public List<Map<String, Object>> queryWithYYHD(final String idNumber) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        resultMap = (List<Map<String, Object>>)this.oracleQueryMapper.queryWithYYHD(idNumber);
        return resultMap;
    }
    
    public List<Map<String, Object>> queryWithDXHD(final String idNumber) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        resultMap = (List<Map<String, Object>>)this.oracleQueryMapper.queryWithDXHD(idNumber);
        return resultMap;
    }
    
    public List<String> selectAllTables() {
        return (List<String>)this.analysisJudgmentMapper.selectAllTables();
    }
    
    public void query(final String user, final String condition, final Result result) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (this.chargeAuth(user, condition)) {
                final List<String> redList = (List<String>)this.authorityService.getRedListByUser((String)null);
                if (redList != null && redList.size() > 0) {
                    result.setCode(ResultCode.SUCCESS);
                    if (redList.contains(condition)) {
                        final List<String> redAuthorityList = (List<String>)this.authorityService.getRedListByUser(user);
                        if (redAuthorityList != null && redAuthorityList.size() > 0) {
                            if (redAuthorityList.contains(condition)) {
                                resultMap = this.queryByCondition(condition);
                                AnalysisJudgmentServiceImpl.logger.info("数据查询成功：{}", (Object)resultMap);
                                result.setData(resultMap);
                                result.setMsg("数据查询成功");
                            }
                            else {
                                result.setMsg("没有权限查询此数据");
                                result.setCode(ResultCode.FAIL);
                            }
                        }
                        else {
                            result.setMsg("没有权限查询此数据");
                            result.setCode(ResultCode.FAIL);
                        }
                    }
                    else {
                        resultMap = this.queryByCondition(condition);
                        AnalysisJudgmentServiceImpl.logger.info("数据查询成功：{}", (Object)resultMap);
                        result.setData(resultMap);
                        result.setMsg("数据查询成功");
                    }
                }
                else {
                    resultMap = this.queryByCondition(condition);
                    AnalysisJudgmentServiceImpl.logger.info("数据查询成功：{}", (Object)resultMap);
                    result.setData(resultMap);
                    result.setMsg("数据查询成功");
                }
            }
            else {
                result.setMsg("没有权限查询此数据");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            AnalysisJudgmentServiceImpl.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
    }
    
    private Boolean chargeAuth(final String user, final String condition) {
        Boolean flag = false;
        final Integer count = this.busMapper.queryUserSecretByUser(user, condition);
        if (count > 0) {
            flag = true;
        }
        else {
            flag = false;
        }
        return flag;
    }
    
    private Map<String, Object> queryByCondition(final String idNumber) {
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.UserInfo.GetCode()));
        this.queryData(tableList, resultMap, "user_", idNumber);
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.AssetInfo.GetCode()));
        this.queryData(tableList, resultMap, "asset_", idNumber);
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.SocialInfo.GetCode()));
        this.queryData(tableList, resultMap, "social_", idNumber);
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.MechanismInfo.GetCode()));
        this.queryData(tableList, resultMap, "mechanism_", idNumber);
        final List<String> list = this.queryForBFHM(idNumber);
        resultMap.put("communication_tel", list);
        final List<Map<String, Object>> yyList = this.queryWithYYHD(idNumber);
        if (yyList != null && yyList.size() > 0) {
            resultMap.put("communication_contact_yy", yyList.get(0));
        }
        else {
            resultMap.put("communication_contact_yy", "");
        }
        final List<Map<String, Object>> dxList = this.queryWithDXHD(idNumber);
        if (dxList != null && dxList.size() > 0) {
            resultMap.put("communication_contact_dx", dxList.get(0));
        }
        else {
            resultMap.put("communication_contact_dx", "");
        }
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.CaseInfo.GetCode()));
        this.queryData(tableList, resultMap, "case_", idNumber);
        final Integer count = this.auditLogMapper.selectCounts(idNumber);
        resultMap.put("case_dccs", count);
        tableList = (List<Map<String, Object>>)this.analysisJudgmentMapper.queryTablesByType(Integer.valueOf(NormalEnum.AnalysisJudgmentType.TrajectoryInfo.GetCode()));
        this.queryData(tableList, resultMap, "trajectory_", idNumber);
        return resultMap;
    }
    
    private void queryData(final List<Map<String, Object>> tableList, final Map<String, Object> resultMap, final String prefix, final String idNumber) {
        if (tableList != null && tableList.size() > 0) {
            for (final Map<String, Object> item : tableList) {
                List<Map<String, Object>> resultInfoList = new ArrayList<Map<String, Object>>();
                Integer count = 0;
                final Map<String, Object> map = new HashMap<String, Object>();
                final Integer type = Integer.parseInt(item.get("query_type").toString());
                map.put("dbUser", item.get("db_user"));
                map.put("table", item.get("table"));
                map.put("pkColName", item.get("pk_col_name"));
                map.put("idNumber", idNumber);
                if (type == NormalEnum.AnalysisJudgmentQueryType.QueryContent.GetCode()) {
                    resultInfoList = this.queryContent(map);
                    resultMap.put(prefix + item.get("table").toString(), resultInfoList);
                }
                else if (type == NormalEnum.AnalysisJudgmentQueryType.QueryCounts.GetCode()) {
                    count = this.queryCounts(map);
                    resultMap.put(prefix + item.get("table").toString(), count);
                }
                else if (type == NormalEnum.AnalysisJudgmentQueryType.FuzzyQuery.GetCode()) {
                    resultInfoList = this.fuzzyQuery(map);
                    resultMap.put(prefix + item.get("table").toString(), resultInfoList);
                }
                else {
                    if (type != NormalEnum.AnalysisJudgmentQueryType.AssociatedQuery.GetCode()) {
                        continue;
                    }
                    map.put("relatedUser", item.get("related_user"));
                    map.put("relatedTable", item.get("related_table"));
                    map.put("relatedNameSelf", item.get("related_name_self"));
                    map.put("relatedName", item.get("related_name"));
                    resultInfoList = this.associatedQuery(map);
                    resultMap.put(prefix + item.get("table").toString(), resultInfoList);
                }
            }
        }
    }
    
    public Boolean addAuth(final String table, final String user, final Integer flag, final String condition) {
        final Boolean charge = this.chargeAuth(user, condition);
        if (charge) {
            return false;
        }
        this.busMapper.insert(table, user, flag, condition);
        return true;
    }
    
    static {
        AnalysisJudgmentServiceImpl.logger = LogManager.getLogger((Class)AnalysisJudgmentServiceImpl.class);
    }
}
