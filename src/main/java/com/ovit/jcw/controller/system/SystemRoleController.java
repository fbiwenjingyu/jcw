package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.BaseController;
import com.ovit.jcw.model.SystemRole;
import com.ovit.jcw.service.SystemRoleService;
import com.ovit.jcw.utils.Result;
import com.ovit.jcw.utils.ResultCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/system/role"})
public class SystemRoleController extends BaseController {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private SystemRoleService systemRoleService;

	@RequestMapping({"/addRole"})
	public Result addRole(@RequestBody Map<String, Object> params) {
		logger.info("?建角色");
		Result result = new Result();
		logger.info("查???：{}", params);
		try {
			String roleName = params.get("roleName").toString();
			SystemRole systemRole = new SystemRole();
			systemRole.setRoleName(roleName);
			List<Integer> list = (List) params.get("ruleId");

			int i = systemRoleService.insert(systemRole).intValue();
			if (i > 0) {
				int roleId = systemRole.getId().intValue();
				Map<String, Object> map;
				if ((list != null) && (list.size() > 0)) {
					map = new HashMap();
					for (Integer item : list) {
						map.put("roleId", Integer.valueOf(roleId));
						map.put("ruleId", item);
						systemRoleService.insertRoleRuleTable(map);
					}
				}
				logger.info("?据?建成功：{}", systemRole);
				result.setData(systemRole);
				result.setMsg("?据?建成功");
			} else {
				logger.error("?据?建异常");
				result.setMsg("?据查?建异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("?据?建异常：{}", e);
			result.setMsg("?据查?建异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/getRole"})
	public Result getRule() {
		logger.info("角色列表查?");
		Result result = new Result();
		try {
			List<SystemRole> resultMap = systemRoleService.query();
			logger.info("?据查?成功：{}", resultMap);
			result.setData(resultMap);
			result.setMsg("?据查?成功");
		} catch (Exception e) {
			logger.error("?据查?异常：{}", e);
			result.setMsg("?据查?异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/getAllRoles"})
	public Result getAllRoles() {
		logger.info("角色?据下拉框");
		Result result = new Result();
		try {
			List<SystemRole> resultMap = systemRoleService.queryAll();
			logger.info("?据查?成功：{}", resultMap);
			result.setData(resultMap);
			result.setMsg("?据查?成功");
		} catch (Exception e) {
			logger.error("?据查?异常：{}", e);
			result.setMsg("?据查?异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/getRoleDetail"})
	public Result getRoleDetail(@RequestBody Map<String, Object> params) {
		logger.info("角色?情查?");
		Result result = new Result();
		logger.info("查??件：{}", params);
		try {
			Integer id = Integer.valueOf(Integer.parseInt(params.get("id").toString()));
			SystemRole resultMap = systemRoleService.selectByPrimaryKey(id);
			logger.info("?据查?成功：{}", resultMap);
			result.setData(resultMap);
			result.setMsg("?据查?成功");
		} catch (Exception e) {
			logger.error("?据查?异常：{}", e);
			result.setMsg("?据查?异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/editRole"})
	public Result editRole(@RequestBody Map<String, Object> params) {
		logger.info("??角色");
		Result result = new Result();
		logger.info("?件??：{}", params);
		try {
			Integer id = Integer.valueOf(Integer.parseInt(params.get("id").toString()));
			String roleName = params.get("roleName").toString();
			SystemRole systemRole = new SystemRole();
			systemRole.setRoleName(roleName);
			systemRole.setId(id);
			List<Integer> list = (List) params.get("ruleId");

			systemRoleService.deleteByRoleId(id);

			int i = systemRoleService.updateByPrimaryKey(systemRole).intValue();
			if (i > 0) {
				Map<String, Object> map;
				if ((list != null) && (list.size() > 0)) {
					map = new HashMap();
					for (Integer item : list) {
						map.put("roleId", id);
						map.put("ruleId", item);
						systemRoleService.insertRoleRuleTable(map);
					}
				}
				logger.info("??成功：{}", systemRole);
				result.setData(systemRole);
				result.setMsg("??成功");
			} else {
				result.setMsg("??失?");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("?据查?异常：{}", e);
			result.setMsg("?据查?异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/deleteRole"})
	public Result deleteRole(@RequestBody Map<String, Object> params) {
		logger.info("?除角色");
		Result result = new Result();
		logger.info("?件??：{}", params);
		try {
			Integer id = Integer.valueOf(Integer.parseInt(params.get("id").toString()));

			systemRoleService.deleteByRoleId(id);

			systemRoleService.deleteByPrimaryKey(id);
			logger.info("?除成功");
			result.setData("");
			result.setMsg("?除成功");
		} catch (Exception e) {
			logger.error("?据?除失?：{}", e);
			result.setMsg("?据?除失?");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}