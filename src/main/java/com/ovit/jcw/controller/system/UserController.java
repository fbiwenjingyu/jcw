package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.service.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import org.springframework.web.bind.annotation.*;
import com.ovit.jcw.model.*;
import java.util.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;

@RestController
@RequestMapping({ "/system/user" })
public class UserController extends BaseController
{
    private Logger logger;
    @Autowired
    private UserService userService;
    
    public UserController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/addUser" })
    public Result addUser(@RequestBody final Map<String, Object> params) {
        this.logger.info("创建用户");
        final Result result = new Result();
        this.logger.info("查询参数：{}", (Object)params);
        try {
            final String loginName = params.get("loginName").toString();
            final User loginUser = this.userService.queryUserByLoginName(loginName);
            if (loginUser != null) {
                result.setMsg("当前账号已被使用");
                return result;
            }
            String loginPassword = params.get("loginPassword").toString();
            final String userName = params.get("userName").toString();
            final String loginUserId = Utils.randomStringByUUID();
            loginPassword = SHA1Utils.getSha1(loginPassword);
            final User user = new User();
            user.setLoginUserId(loginUserId);
            user.setLoginName(loginName);
            user.setLoginPassword(loginPassword);
            user.setUserName(userName);
            final List<Integer> list = (List<Integer>)params.get("roleId");
            final int i = this.userService.insert(user);
            if (i > 0) {
                if (list != null && list.size() > 0) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    for (final Integer item : list) {
                        map.put("userId", loginUserId);
                        map.put("userName", loginName);
                        map.put("roleId", item);
                        this.userService.insertUserRoleTable(map);
                    }
                }
                this.logger.info("数据创建成功：{}", (Object)user);
                result.setData("");
                result.setMsg("数据创建成功");
            }
            else {
                this.logger.error("数据创建异常");
                result.setMsg("数据查创建异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            this.logger.error("数据创建异常：{}", (Throwable)e);
            result.setMsg("数据创建异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/getUser" })
    public Result getRule(@RequestBody final Map<String, Object> params) {
        this.logger.info("用户列表查询");
        final Result result = new Result();
        try {
            final List<User> resultMap = this.userService.query(params);
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(resultMap);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/getUserDetail" })
    public Result getRoleDetail(@RequestBody final Map<String, Object> params) {
        this.logger.info("用户详情查询");
        final Result result = new Result();
        this.logger.info("查询条件：{}", (Object)params);
        try {
            final String loginUserId = params.get("loginUserId").toString();
            final User resultMap = this.userService.selectByPrimaryKey(loginUserId);
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(resultMap);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/editUser" })
    public Result editUser(@RequestBody final Map<String, Object> params) {
        this.logger.info("编辑用户");
        final Result result = new Result();
        this.logger.info("条件参数：{}", (Object)params);
        try {
            final String loginUserId = params.get("loginUserId").toString();
            final String loginName = params.get("loginName").toString();
            String loginPassword = params.get("loginPassword").toString();
            loginPassword = SHA1Utils.getSha1(loginPassword);
            final String userName = params.get("userName").toString();
            final User user = new User();
            user.setLoginUserId(loginUserId);
            user.setLoginName(loginName);
            user.setLoginPassword(loginPassword);
            user.setUserName(userName);
            final List<Integer> list = (List<Integer>)params.get("roleId");
            this.userService.deleteByUserId(loginUserId);
            final int i = this.userService.updateByPrimaryKey(user);
            if (i > 0) {
                if (list != null && list.size() > 0) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    for (final Integer item : list) {
                        map.put("userId", loginUserId);
                        map.put("userName", loginName);
                        map.put("roleId", item);
                        this.userService.insertUserRoleTable(map);
                    }
                }
                this.logger.info("编辑成功");
                result.setMsg("编辑成功");
            }
            else {
                result.setMsg("编辑失败");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/deleteUser" })
    public Result deleteRole(@RequestBody final Map<String, Object> params) {
        this.logger.info("删除用户");
        final Result result = new Result();
        this.logger.info("条件参数：{}", (Object)params);
        try {
            final String loginUserId = params.get("loginUserId").toString();
            this.userService.deleteByUserId(loginUserId);
            this.userService.deleteByPrimaryKey(loginUserId);
            this.logger.info("删除成功");
            result.setData("");
            result.setMsg("删除成功");
        }
        catch (Exception e) {
            this.logger.error("数据删除失败：{}", (Throwable)e);
            result.setMsg("数据删除失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/resetPassword" })
    public Result resetPassword(@RequestBody final Map<String, Object> params) {
        this.logger.info("重置密码");
        final Result result = new Result();
        this.logger.info("条件参数：{}", (Object)params);
        try {
            final String loginName = params.get("loginName").toString();
            if (loginName != null) {
                final String resetPassword = StrongPasswordUtils.generateStrongPassword();
                final String password = SHA1Utils.getSha1(resetPassword);
                final User user = new User();
                user.setLoginName(loginName);
                user.setLoginPassword(password);
                user.setIsFirstLogin(NormalEnum.IsFirstLogin.Yes.GetDesc());
                this.userService.updatePassword(user);
                this.logger.info("密码重置成功");
                user.setLoginPassword(resetPassword);
                result.setData(user);
                result.setMsg("密码重置成功");
            }
            else {
                result.setMsg("登陆用户不能为空");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("密码重置失败：{}", (Throwable)e);
            result.setMsg("密码重置失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/changePassword" })
    public Result changePassword(@RequestBody final Map<String, Object> params) {
        this.logger.info("修改密码");
        final Result result = new Result();
        this.logger.info("条件参数：{}", (Object)params);
        try {
            final String loginName = params.get("loginName").toString();
            final String oldLoginPassword = params.get("oldLoginPassword").toString();
            final String loginPassword = params.get("loginPassword").toString();
            if (loginName != null) {
                final String decrypt = AESUtils.aesDecrypt(oldLoginPassword);
                final String resultPassword = SHA1Utils.getSha1(decrypt);
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("loginName", loginName);
                map.put("loginPassword", resultPassword);
                final User resultUser = this.userService.authUser(map);
                if (resultUser != null) {
                    if (resultUser.getIsLock().equals(NormalEnum.IsLock.No.GetDesc())) {
                        if (!oldLoginPassword.equals(loginPassword)) {
                            final String password = AESUtils.aesDecrypt(loginPassword);
                            final String passwordSHA = SHA1Utils.getSha1(password);
                            final User user = new User();
                            user.setLoginName(loginName);
                            user.setLoginPassword(passwordSHA);
                            user.setIsFirstLogin(NormalEnum.IsFirstLogin.No.GetDesc());
                            this.userService.updatePassword(user);
                            this.logger.info("密码修改成功");
                            user.setLoginPassword("");
                            result.setData(user);
                            result.setMsg("密码修改成功");
                        }
                        else {
                            result.setMsg("新密码不能和旧密码相同");
                            result.setCode(ResultCode.FAIL);
                        }
                    }
                    else {
                        result.setMsg("当前账号被锁定，不能修改密码");
                        result.setCode(ResultCode.FAIL);
                    }
                }
                else {
                    result.setMsg("账号或旧密码错误，无法修改密码");
                    result.setCode(ResultCode.FAIL);
                }
            }
            else {
                result.setMsg("登陆用户不能为空");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("密码修改失败：{}", (Throwable)e);
            result.setMsg("密码修改失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    @RequestMapping({ "/accountLockOption" })
    public Result accountLockOption(@RequestBody final Map<String, Object> params) {
        this.logger.info("账号锁定解锁操作");
        final Result result = new Result();
        this.logger.info("条件参数：{}", (Object)params);
        try {
            final String loginName = params.get("loginName").toString();
            final String isLock = params.get("isLock").toString();
            if (loginName != null) {
                final User user = new User();
                user.setLoginName(loginName);
                user.setIsLock(isLock);
                this.userService.updateIsLock(user);
                this.logger.info("操作成功");
                result.setData(user);
                result.setMsg("操作成功");
            }
            else {
                result.setMsg("登陆用户不能为空");
                result.setCode(ResultCode.FAIL);
            }
        }
        catch (Exception e) {
            this.logger.error("操作失败：{}", (Throwable)e);
            result.setMsg("操作失败");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
