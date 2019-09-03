package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import com.ovit.jcw.mapper.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mysqlmapper.*;
import com.github.pagehelper.*;
import com.ovit.jcw.common.*;
import java.util.*;
import org.apache.logging.log4j.*;

@Service
public class CorrelationAnalysisServiceImpl implements CorrelationAnalysisService
{
    private static Logger logger;
    @Autowired
    private CorrelationAnalysisMapper correlationAnalysisMapper;
    @Autowired
    private BusMapper busMapper;
    
    @Override
    public List<Map<String, Object>> selectByTerm(final String queryCondition, final Integer type, final Integer page, final Integer size) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        PageHelper.startPage((int)page, (int)size);
        if (type == NormalEnum.AnalysisType.CompanyName.GetCode()) {
            resultMap = (List<Map<String, Object>>)this.correlationAnalysisMapper.selectCompanyByTerm(queryCondition);
        }
        else if (type == NormalEnum.AnalysisType.ProjectName.GetCode()) {
            resultMap = (List<Map<String, Object>>)this.correlationAnalysisMapper.selectProjectNameByTerm(queryCondition);
        }
        else if (type == NormalEnum.AnalysisType.ProjectAdd.GetCode()) {
            resultMap = (List<Map<String, Object>>)this.correlationAnalysisMapper.selectProjectAddByTerm(queryCondition);
        }
        return resultMap;
    }
    
    @Override
    public List<Map<String, Object>> selectChildByTerm(final String queryCondition, final Integer type) {
        final List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        try {
            final List<Map<String, Object>> queryMap = (List<Map<String, Object>>)this.busMapper.selectFieldByTag(type);
            if (queryMap != null && queryMap.size() > 0) {
                for (final Map<String, Object> map : queryMap) {
                    map.put("queryCondition", queryCondition);
                    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                    result = (List<Map<String, Object>>)this.correlationAnalysisMapper.selectChildByTermNotClob((Map)map);
                    resultMap.addAll(result);
                }
            }
        }
        catch (Exception e) {
            CorrelationAnalysisServiceImpl.logger.error("数据查询异常：{}", (Throwable)e);
        }
        return resultMap;
    }
    
    static {
        CorrelationAnalysisServiceImpl.logger = LogManager.getLogger((Class)CorrelationAnalysisServiceImpl.class);
    }
}
