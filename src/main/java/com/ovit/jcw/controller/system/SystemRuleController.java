package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.BaseController;
import com.ovit.jcw.model.SystemRule;
import com.ovit.jcw.service.SystemRuleService;
import com.ovit.jcw.utils.Result;
import com.ovit.jcw.utils.ResultCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/system/rule"})
public class SystemRuleController extends BaseController {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private SystemRuleService systemRuleService;

	@RequestMapping({"/getRuleTree"})
	public Result getRuleTree() {
		logger.info("?限??架构?查?");
		Result result = new Result();
		try {
			List<SystemRule> resultMap = systemRuleService.GetSystemRoleTree();
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

	@RequestMapping({"/getRule"})
	public Result getRule() {
		logger.info("?取?前登?用?的?限");
		Result result = new Result();
		try {
			logger.info("登???：{}", token);
			List<SystemRule> resultMap = systemRuleService.selectAllRule(token);
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
}