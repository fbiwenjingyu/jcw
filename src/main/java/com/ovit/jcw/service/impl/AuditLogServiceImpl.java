package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import javax.servlet.http.*;
import java.math.*;
import com.ovit.jcw.model.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.common.pool.*;
import com.ovit.jcw.common.jedis.*;
import org.apache.logging.log4j.*;

@Service
public class AuditLogServiceImpl implements AuditLogService
{
    private static Logger logger;
    @Autowired
    private AuditLogMapper auditLogMapper;
    
    public void insertLogRecordsForLogin(final HttpServletRequest request, final String loginName, final String loginTime, final String interfaceName, final BigInteger dataUsage) {
        AuditLogServiceImpl.logger.info("开始日志记录");
        try {
            final AuditLog auditLog = new AuditLog();
            final String ip = IpUtils.getIpAddr(request);
            final String sign = request.getHeader("sign");
            Integer query = 0;
            if (sign != null && !sign.equals("")) {
                if (sign.equals("PC")) {
                    query = NormalEnum.QueryType.Login.getCode();
                }
                else if (sign.equals("APP")) {
                    query = NormalEnum.QueryType.AppLogin.getCode();
                }
            }
            auditLog.setIp(ip);
            auditLog.setLoginName(loginName);
            auditLog.setLoginTime(loginTime);
            auditLog.setInterfaceName(interfaceName);
            auditLog.setDataUsage(dataUsage);
            auditLog.setSign(sign);
            auditLog.setQueryType(query);
            this.auditLogMapper.insertLogRecords(auditLog);
        }
        catch (Exception e) {
            AuditLogServiceImpl.logger.error((Object)e);
        }
    }
    
    public void insertLogRecordsForNormal(final HttpServletRequest request, final String loginTime, final String interfaceName, final String descPretty, final BigInteger dataUsage, final Integer queryType) {
        AuditLogServiceImpl.logger.info("开始日志记录");
        try {
            final AuditLog auditLog = new AuditLog();
            final String ip = IpUtils.getIpAddr(request);
            final String sign = request.getHeader("sign");
            final String token = request.getHeader("token");
            final JedisProxy jedisProxy = JedisPool.getJedis();
            final String loginName = jedisProxy.get(token);
            auditLog.setIp(ip);
            auditLog.setLoginName(loginName);
            auditLog.setLoginTime(loginTime);
            auditLog.setInterfaceName(interfaceName);
            auditLog.setDescPretty(descPretty);
            auditLog.setDataUsage(dataUsage);
            auditLog.setSign(sign);
            auditLog.setQueryType(queryType);
            this.auditLogMapper.insertLogRecords(auditLog);
        }
        catch (Exception e) {
            AuditLogServiceImpl.logger.error((Object)e);
        }
    }
    
    public Integer selectCounts(final String descPretty) {
        Integer count = 0;
        count = this.auditLogMapper.selectCounts(descPretty);
        return count;
    }
    
    static {
        AuditLogServiceImpl.logger = LogManager.getLogger((Class)AuditLogServiceImpl.class);
    }
}
