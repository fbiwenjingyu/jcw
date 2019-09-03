package com.ovit.jcw.controller;

import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.common.pool.*;
import org.apache.commons.lang3.*;
import com.ovit.jcw.model.*;
import com.ovit.jcw.common.jedis.*;
import org.springframework.web.bind.annotation.*;

public class BaseController
{
    private Logger logger;
    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private MobileDeviceService mobileDeviceService;
    private Boolean flag;
    protected String token;
    protected String message;
    
    public BaseController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @ModelAttribute
    public Boolean baseQuery(final HttpServletRequest request) {
        this.flag = true;
        this.token = null;
        try {
            final String ip = IpUtils.getIpAddr(request);
            final String sign = request.getHeader("sign");
            final String code = request.getHeader("token");
            final String action = request.getHeader("action");
            final String uri = request.getRequestURI();
            this.logger.info("uri:{}", (Object)uri);
            this.logger.info("来源IP：{}，客户端来源：{}，用户随机code：{}，测试时请求接口标识：{}", (Object)ip, (Object)sign, (Object)code, (Object)action);
            if (sign.equals("PC")) {
                final SystemConfig systemConfig = this.systemConfigMapper.queryByIp(ip);
                if (systemConfig == null) {
                    this.logger.info("非法请求！");
                    this.message = "非法请求";
                    this.flag = false;
                }
                else if (uri != null && !uri.equals(NormalEnum.Action.login.GetCode()) && !uri.equals(NormalEnum.Action.auth.GetCode())) {
                    if (action != null && action.equals(NormalEnum.Action.special.GetCode())) {
                        this.token = code;
                        this.flag = true;
                    }
                    else if (code != null && code.length() > 0) {
                        final JedisProxy jedisProxy = JedisPool.getJedis();
                        this.token = jedisProxy.get(code);
                        if (this.token != null && this.token.length() > 0) {
                            final String accessSecret = jedisProxy.get("ACCESS_ID_" + this.token);
                            if (StringUtils.isBlank((CharSequence)accessSecret)) {
                                this.logger.info("当前用户没有登录！");
                                this.message = "当前用户没有登录！";
                                this.flag = false;
                            }
                            if (!accessSecret.equals(code)) {
                                this.logger.info("登录信息已失效或在其他电脑登录！");
                                this.message = "登录信息已失效或在其他电脑登录！";
                                this.flag = false;
                            }
                        }
                        else {
                            this.logger.info("用户信息错误！");
                            this.message = "用户信息错误！";
                            this.flag = false;
                        }
                    }
                    else {
                        this.logger.info("code信息错误！");
                        this.message = "code信息错误！";
                        this.flag = false;
                    }
                }
                else if (uri != null && uri.equals(NormalEnum.Action.login.GetCode())) {
                    if (code != null && code.length() > 0) {
                        this.logger.info("登录参数错误！");
                        this.message = "登录参数错误！";
                        this.flag = false;
                    }
                }
                else if (uri != null && uri.equals(NormalEnum.Action.auth.GetCode())) {
                    if (code != null && code.length() > 0) {
                        this.logger.info("登录参数错误！");
                        this.message = "登录参数错误！";
                        this.flag = false;
                    }
                }
                else {
                    this.logger.info("action参数错误！");
                    this.message = "action参数错误！";
                    this.flag = false;
                }
            }
            else if (sign.equals("EXT")) {
                this.flag = false;
                final SystemConfig systemConfig = this.systemConfigMapper.queryByIp(ip);
                if (systemConfig == null) {
                    this.logger.info("非法请求！");
                    this.message = "非法请求";
                }
                else if (uri != null) {
                    if (uri.equals(NormalEnum.Action.loginTest.GetCode()) || uri.equals(NormalEnum.Action.auth.GetCode())) {
                        if (StringUtils.isNotEmpty((CharSequence)code)) {
                            this.logger.info("登录参数错误！");
                            this.message = "登录参数错误！";
                        }
                        else {
                            this.flag = true;
                        }
                    }
                    else if (action != null && action.equals(NormalEnum.Action.special.GetCode())) {
                        this.token = "admin";
                        this.flag = true;
                    }
                    else if (code != null && code.length() > 0) {
                        final JedisProxy jedisProxy = JedisPool.getJedis();
                        this.token = jedisProxy.get(code);
                        if (StringUtils.isNotEmpty((CharSequence)this.token)) {
                            final String accessSecret = jedisProxy.get("ACCESS_ID_" + this.token);
                            if (StringUtils.isBlank((CharSequence)accessSecret)) {
                                this.logger.info("当前用户没有登录！");
                                this.message = "当前用户没有登录！";
                            }
                            if (!accessSecret.equals(code)) {
                                this.logger.info("登录信息已失效或在其他电脑登录！");
                                this.message = "登录信息已失效或在其他电脑登录！";
                            }
                            else {
                                this.flag = true;
                            }
                        }
                        else {
                            this.logger.info("用户信息错误！");
                            this.message = "用户信息错误！";
                        }
                    }
                    else {
                        this.logger.info("code信息错误！");
                        this.message = "code信息错误！";
                    }
                }
                else {
                    this.logger.info("url接口参数错误！");
                    this.message = "url接口参数错误！";
                }
            }
            else if (sign.equals("APP")) {
                final JedisProxy jedisProxy2 = JedisPool.getJedis();
                if (uri != null && !uri.equals(NormalEnum.Action.appLogin.GetCode())) {
                    if (action != null && action.equals(NormalEnum.Action.special.GetCode())) {
                        this.token = "admin";
                        this.flag = true;
                    }
                    else if (code != null && code.length() > 0) {
                        final String IMEI = jedisProxy2.get("ACCESS_TOKEN_" + code);
                        if (IMEI != null && !IMEI.equals("")) {
                            final int count = this.mobileDeviceService.selectCountByIMEI(IMEI);
                            if (count > 0) {
                                final Integer status = this.mobileDeviceService.selectStatusByIMEI(IMEI);
                                if (status == NormalEnum.StatusType.Enable.GetCode()) {
                                    this.token = jedisProxy2.get(IMEI + code);
                                    if (StringUtils.isNotEmpty((CharSequence)this.token)) {
                                        final String accessSecret2 = jedisProxy2.get("ACCESS_IMEI_" + IMEI);
                                        if (StringUtils.isBlank((CharSequence)accessSecret2)) {
                                            this.logger.info("当前用户没有登录！");
                                            this.message = "当前用户没有登录！";
                                        }
                                        if (!accessSecret2.equals(code)) {
                                            this.logger.info("登录信息已失效或在其他设备登录！");
                                            this.message = "登录信息已失效或在其他设备登录！";
                                        }
                                        else {
                                            this.flag = true;
                                        }
                                    }
                                    else {
                                        this.logger.info("用户信息错误！");
                                        this.message = "用户信息错误！";
                                    }
                                }
                                else {
                                    this.logger.info("当前设备被禁用！");
                                    this.message = "当前设备被禁用！";
                                    this.flag = false;
                                }
                            }
                            else {
                                this.logger.info("当前设备没有权限使用系统！");
                                this.message = "当前设备没有权限使用系统！";
                                this.flag = false;
                            }
                        }
                        else {
                            this.logger.info("token信息错误！");
                            this.message = "token信息错误！";
                            this.flag = false;
                        }
                    }
                    else {
                        this.logger.info("token信息为空！");
                        this.message = "token信息为空！";
                        this.flag = false;
                    }
                }
                else if (uri != null && uri.equals(NormalEnum.Action.appLogin.GetCode())) {
                    if (code != null && code.length() > 0) {
                        this.logger.info("登录参数错误！");
                        this.message = "登录参数错误！";
                        this.flag = false;
                    }
                }
                else {
                    this.logger.info("action参数错误！");
                    this.message = "action参数错误！";
                    this.flag = false;
                }
            }
            else {
                this.flag = false;
            }
        }
        catch (Exception e) {
            this.logger.error("header参数错误");
            this.logger.error((Object)e);
            this.flag = false;
        }
        request.setAttribute("flag", (Object)this.flag);
        return this.flag;
    }
}
