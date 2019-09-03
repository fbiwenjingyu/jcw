package com.ovit.jcw.controller.app;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import java.util.*;
import com.ovit.jcw.common.pool.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.model.*;
import com.ovit.jcw.common.jedis.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/app/v1.0" })
public class UserAppLoginController extends BaseController
{
    private Logger logger;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private MobileDeviceService mobileDeviceService;
    
    public UserAppLoginController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
    @ResponseBody
    public Result handleFileUpload(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            this.logger.info("用户登录验证");
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            String loginName = "";
            try {
                loginName = params.get("loginName").toString();
                final String loginPassword = params.get("loginPassword").toString();
                final String IMEI = params.get("IMEI").toString();
                final String position = params.get("position").toString();
                this.logger.info("登录账号:{}，登录密码：{}，设备IMEI：{}，所在位置：{}", (Object)loginName, (Object)loginPassword, (Object)IMEI, (Object)position);
                if (IMEI != null && !IMEI.equals("")) {
                    final int count = this.mobileDeviceService.selectCountByIMEI(IMEI);
                    if (count > 0) {
                        final Integer status = this.mobileDeviceService.selectStatusByIMEI(IMEI);
                        if (status == NormalEnum.StatusType.Enable.GetCode()) {
                            final User user = this.userService.queryUserByLoginName(loginName);
                            if (user != null) {
                                final Map<String, Object> map = new HashMap<String, Object>();
                                map.put("imei", IMEI);
                                map.put("loginUser", loginName);
                                final int counts = this.mobileDeviceService.selectCountByIMEIAndUser(map);
                                if (counts > 0) {
                                    if (user.getIsLock().equals(NormalEnum.IsLock.No.GetDesc())) {
                                        if (user.getIsFirstLogin().equals(NormalEnum.IsFirstLogin.No.GetDesc())) {
                                            this.logger.info("获得前端传来的加密后的密码：{}", (Object)loginPassword);
                                            final String decrypt = AESUtils.aesDecrypt(loginPassword);
                                            this.logger.info("解密后：{}", (Object)decrypt);
                                            final String resultPassword = SHA1Utils.getSha1(decrypt);
                                            map.clear();
                                            map.put("loginName", loginName);
                                            map.put("loginPassword", resultPassword);
                                            final BasicUser resultUser = this.userService.authBasicUser(map);
                                            if (resultUser != null) {
                                                this.logger.info("数据查询成功：{}", (Object)resultUser);
                                                final JedisProxy jedisProxy = JedisPool.getJedis();
                                                final String accessSecret = jedisProxy.get("ACCESS_IMEI_" + IMEI);
                                                if (accessSecret != null && !accessSecret.equals("")) {
                                                    jedisProxy.del("ACCESS_IMEI_" + IMEI);
                                                    final String cacheIMEI = jedisProxy.get("ACCESS_TOKEN_" + accessSecret);
                                                    if (cacheIMEI != null && !cacheIMEI.equals("")) {
                                                        jedisProxy.del("ACCESS_TOKEN_" + accessSecret);
                                                    }
                                                    final String cacheUser = jedisProxy.get(IMEI + accessSecret);
                                                    if (cacheUser != null && !cacheUser.equals("")) {
                                                        jedisProxy.del(IMEI + accessSecret);
                                                    }
                                                }
                                                final String code = Utils.randomStringByUUID();
                                                jedisProxy.set("ACCESS_IMEI_" + IMEI, code);
                                                jedisProxy.set("ACCESS_TOKEN_" + code, IMEI);
                                                jedisProxy.set(IMEI + code, loginName);
                                                map.clear();
                                                map.put("position", position);
                                                map.put("imei", IMEI);
                                                this.mobileDeviceService.updatePositionByIMEI(map);
                                                map.clear();
                                                map.put("token", code);
                                                result.setData(map);
                                                result.setMsg("登录成功");
                                            }
                                            else {
                                                result.setMsg("密码不正确");
                                                result.setCode(ResultCode.FAIL);
                                            }
                                        }
                                        else {
                                            result.setMsg("请修改密码后登陆");
                                            result.setCode(ResultCode.FAIL);
                                        }
                                    }
                                    else {
                                        result.setMsg("当前用户账号被锁定");
                                        result.setCode(ResultCode.FAIL);
                                    }
                                }
                                else {
                                    result.setMsg("当前设备没有使用" + loginName + "账号的权限");
                                    result.setCode(ResultCode.FAIL);
                                }
                            }
                            else {
                                result.setMsg("当前用户不存在");
                                result.setCode(ResultCode.FAIL);
                            }
                        }
                        else {
                            result.setMsg("当前设备被禁用");
                            result.setCode(ResultCode.FAIL);
                        }
                    }
                    else {
                        result.setMsg("当前设备没有权限使用系统");
                        result.setCode(ResultCode.FAIL);
                    }
                }
                else {
                    result.setMsg("设备参数为空");
                    result.setCode(ResultCode.FAIL);
                }
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                dataUsage = BigInteger.valueOf(0L);
                this.logger.error((Object)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
            finally {
                this.auditLogService.insertLogRecordsForLogin(request, loginName, loginTime, NormalEnum.InterfaceName.appLogin.getCode(), dataUsage);
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
