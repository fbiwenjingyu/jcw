package com.ovit.jcw.controller.user;

import com.ovit.jcw.controller.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.service.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.common.pool.*;
import com.ovit.jcw.utils.*;
import java.util.*;
import com.ovit.jcw.model.*;
import com.ovit.jcw.common.jedis.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({ "/jcwdl/api" })
public class UserLoginController extends BaseController
{
    private Logger logger;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditLogService auditLogService;
    
    public UserLoginController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
    @ResponseBody
    public Result handleFileUpload(final HttpServletRequest request, @RequestBody final JSONObject json) {
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            this.logger.info("用户登录验证");
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            String loginName = "";
            try {
                final JSONObject loginUser = json.getJSONObject("user");
                this.logger.info("用户的信息为:{}", (Object)loginUser);
                final User userParam = JsonUtils.parseObject(loginUser.toString(), User.class);
                if (userParam != null) {
                    loginName = userParam.getLoginName();
                    final String loginPassword = userParam.getLoginPassword();
                    if (loginName == null || loginPassword == null) {
                        result.setMsg("登录名或密码为空");
                        result.setCode(ResultCode.FAIL);
                        return result;
                    }
                    final User user = this.userService.queryUserByLoginName(loginName);
                    if (user != null) {
                        if (user.getIsLock().equals(NormalEnum.IsLock.No.GetDesc())) {
                            if (user.getIsFirstLogin().equals(NormalEnum.IsFirstLogin.No.GetDesc())) {
                                this.logger.info("获得前端传来的加密后的密码：{}", (Object)loginPassword);
                                final String decrypt = AESUtils.aesDecrypt(loginPassword);
                                this.logger.info("解密后：{}", (Object)decrypt);
                                final String resultPassword = SHA1Utils.getSha1(decrypt);
                                final Map<String, Object> map = new HashMap<String, Object>();
                                map.put("loginName", loginName);
                                map.put("loginPassword", resultPassword);
                                final BasicUser resultUser = this.userService.authBasicUser(map);
                                if (resultUser != null) {
                                    this.logger.info("数据查询成功：{}", (Object)resultUser);
                                    final JedisProxy jedisProxy = JedisPool.getJedis();
                                    final String accessSecret = jedisProxy.get("ACCESS_ID_" + loginName);
                                    if (accessSecret != null && !accessSecret.equals("")) {
                                        jedisProxy.del("ACCESS_ID_" + loginName);
                                        final String cacheUser = jedisProxy.get(accessSecret);
                                        if (cacheUser != null && !cacheUser.equals("")) {
                                            jedisProxy.del(accessSecret);
                                        }
                                    }
                                    final String code = Utils.randomStringByUUID();
                                    jedisProxy.set("ACCESS_ID_" + loginName, code);
                                    jedisProxy.set(code, loginName);
                                    final LoginUserAuth loginUserAuth = new LoginUserAuth(code, resultUser);
                                    result.setData(loginUserAuth);
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
                        result.setMsg("当前用户不存在");
                        result.setCode(ResultCode.FAIL);
                    }
                }
                else {
                    result.setMsg("登录的用户信息为空");
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
                this.auditLogService.insertLogRecordsForLogin(request, loginName, loginTime, NormalEnum.InterfaceName.login.getCode(), dataUsage);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping(value = { "/auth" }, method = { RequestMethod.POST })
    @ResponseBody
    public Result auth(final HttpServletRequest request, @RequestBody final JSONObject json) {
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            this.logger.info("用户登录验证");
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            String loginName = "";
            try {
                final JSONObject loginUser = json.getJSONObject("user");
                this.logger.info("用户的信息为:{}", (Object)loginUser);
                final User userParam = JsonUtils.parseObject(loginUser.toString(), User.class);
                if (userParam != null) {
                    loginName = userParam.getLoginName();
                    final String loginPassword = userParam.getLoginPassword();
                    if (loginName == null || loginPassword == null) {
                        result.setMsg("登录名或密码为空");
                        result.setCode(ResultCode.FAIL);
                        return result;
                    }
                    final User user = this.userService.queryUserByLoginName(loginName);
                    if (user != null) {
                        if (user.getIsLock().equals(NormalEnum.IsLock.No.GetDesc())) {
                            if (user.getIsFirstLogin().equals(NormalEnum.IsFirstLogin.No.GetDesc())) {
                                this.logger.info("获得前端传来的加密后的密码：{}", (Object)loginPassword);
                                final String decrypt = AESUtils.aesDecrypt(loginPassword);
                                this.logger.info("解密后：{}", (Object)decrypt);
                                final String resultPassword = SHA1Utils.getSha1(decrypt);
                                final Map<String, Object> map = new HashMap<String, Object>();
                                map.put("loginName", loginName);
                                map.put("loginPassword", resultPassword);
                                final BasicUser resultUser = this.userService.authBasicUser(map);
                                if (resultUser != null) {
                                    this.logger.info("数据查询成功：{}", (Object)resultUser);
                                    final JedisProxy jedisProxy = JedisPool.getJedis();
                                    final String accessSecret = jedisProxy.get("ACCESS_ID_" + loginName);
                                    if (accessSecret != null && !accessSecret.equals("")) {
                                        jedisProxy.del("ACCESS_ID_" + loginName);
                                        final String cacheUser = jedisProxy.get(accessSecret);
                                        if (cacheUser != null && !cacheUser.equals("")) {
                                            jedisProxy.del(accessSecret);
                                        }
                                    }
                                    final String code = Utils.randomStringByUUID();
                                    jedisProxy.set("ACCESS_ID_" + loginName, code);
                                    jedisProxy.set(code, loginName);
                                    final LoginUserAuth loginUserAuth = new LoginUserAuth(code, (BasicUser)null);
                                    result.setData(loginUserAuth);
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
                        result.setMsg("当前用户不存在");
                        result.setCode(ResultCode.FAIL);
                    }
                }
                else {
                    result.setMsg("登录的用户信息为空");
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
                this.auditLogService.insertLogRecordsForLogin(request, loginName, loginTime, NormalEnum.InterfaceName.auth.getCode(), dataUsage);
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
