package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mysqlmapper.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.common.*;
import java.util.*;

@Service
public class BIServiceImpl implements BIService
{
    private Logger logger;
    @Autowired
    private BIRHZXMapper bIRHZXMapper;
    @Autowired
    private BIHJZXMapper bIHJZXMapper;
    @Autowired
    private BIWJZXMapper wJZXMapper;
    @Autowired
    private BIJCXTMapper jCXTMapper;
    
    public BIServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @Override
    public List<Map<String, Object>> selectForRHZXDZJZ() {
        return (List<Map<String, Object>>)this.bIRHZXMapper.selectForRHZXDZJZ();
    }
    
    @Override
    public Map<String, Object> selectForRHZXSJSL() {
        return (Map<String, Object>)this.bIRHZXMapper.selectForRHZXSJSL();
    }
    
    @Override
    public List<Map<String, Object>> selectForRHZXWBJ() {
        return (List<Map<String, Object>>)this.bIRHZXMapper.selectForRHZXWBJ();
    }
    
    @Override
    public List<Map<String, Object>> selectByHJZXSJZL() {
        return (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXSJZL(Integer.valueOf(1));
    }
    
    @Override
    public Map<String, List<Map<String, Object>>> selectByHJZXKSJZL() {
        final Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXType(NormalEnum.HJZXType.KZL.GetCode());
        if (list != null && list.size() > 0) {
            for (final Map<String, Object> m : list) {
                final String dataName = m.get("name").toString();
                final Integer key = Integer.parseInt(m.get("key").toString());
                if (key > 1) {
                    final List<Map<String, Object>> data = (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXSJZL(key);
                    result.put(dataName, data);
                }
            }
        }
        return result;
    }
    
    @Override
    public List<Map<String, Object>> selectByHJZXSJAL() {
        return (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXSJAL();
    }
    
    @Override
    public Map<String, Object> selectForHJZX(final NormalEnum.HJZXType type) {
        final Map<String, Object> result = new HashMap<String, Object>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXSJFL(type.GetCode());
        if (list != null && list.size() > 0) {
            for (final Map<String, Object> m : list) {
                final String dataName = m.get("name").toString();
                final Integer dataValue = Integer.parseInt(m.get("count").toString());
                result.put(dataName, dataValue);
                final Integer from = Integer.parseInt(m.get("from").toString());
                if (from > 0) {}
            }
        }
        return result;
    }
    
    @Override
    public Map<String, List<Map<String, Object>>> selectForHJZXQST(final NormalEnum.HJZXType type) {
        final Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXType(type.GetCode());
        if (list != null && list.size() > 0) {
            for (final Map<String, Object> m : list) {
                final String dataName = m.get("name").toString();
                final Integer key = Integer.parseInt(m.get("key").toString());
                final List<Map<String, Object>> data = (List<Map<String, Object>>)this.bIHJZXMapper.selectByHJZXSJFLINFO(key, type.GetCode());
                result.put(dataName, data);
            }
        }
        return result;
    }
    
    @Override
    public List<Map<String, Object>> selectByLSQSQK() {
        return (List<Map<String, Object>>)this.wJZXMapper.selectByLSQSQK();
    }
    
    @Override
    public Map<String, Object> selectBySJZLQK() {
        return (Map<String, Object>)this.wJZXMapper.selectBySJZLQK();
    }
    
    @Override
    public List<Map<String, Object>> selectByFSLQSQK() {
        return (List<Map<String, Object>>)this.wJZXMapper.selectByFSLQSQK();
    }
    
    @Override
    public List<Map<String, Object>> selectByWBJSJZB() {
        return (List<Map<String, Object>>)this.wJZXMapper.selectByWBJSJZB();
    }
    
    @Override
    public List<Map<String, Object>> selectByWTSJFLZB() {
        return (List<Map<String, Object>>)this.wJZXMapper.selectByWTSJFLZB();
    }
    
    @Override
    public Map<String, Object> selectBySJZLQKFX() {
        final Map<String, Object> result = new HashMap<String, Object>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.wJZXMapper.selectBySJZLQKFX();
        if (list != null && list.size() > 0) {
            Integer allCount = 0;
            for (final Map<String, Object> m : list) {
                final String dataName = m.get("typeName").toString();
                final Integer dataValue = Integer.parseInt(m.get("problemCount").toString());
                allCount += dataValue;
                result.put(dataName, dataValue);
            }
            for (final Map.Entry<String, Object> i : result.entrySet()) {
                final Integer dataValue2 = Integer.getInteger(i.getValue().toString());
                result.put(i.getKey(), dataValue2 * 100 / allCount);
            }
        }
        return result;
    }
    
    @Override
    public Map<String, Object> selectForJCXT(final NormalEnum.JCXTType type) {
        final Map<String, Object> result = new HashMap<String, Object>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.jCXTMapper.selectByJCXTFL(type.GetCode());
        if (list != null && list.size() > 0) {
            for (final Map<String, Object> m : list) {
                final String dataName = m.get("name").toString();
                final Integer dataValue = Integer.parseInt(m.get("count").toString());
                result.put(dataName, dataValue);
            }
        }
        return result;
    }
    
    @Override
    public List<Map<String, Object>> selectByJCXTQYAJFX() {
        return (List<Map<String, Object>>)this.jCXTMapper.selectByJCXTQYAJFX();
    }
    
    @Override
    public List<Map<String, Object>> selectByJCXTJCRYTJ() {
        return (List<Map<String, Object>>)this.jCXTMapper.selectByJCXTJCRYTJ();
    }
}
