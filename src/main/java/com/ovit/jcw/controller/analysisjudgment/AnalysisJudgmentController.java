package com.ovit.jcw.controller.analysisjudgment;

import com.ovit.jcw.common.NormalEnum;
import com.ovit.jcw.common.NormalEnum.InterfaceName;
import com.ovit.jcw.common.NormalEnum.QueryType;
import com.ovit.jcw.controller.BaseController;
import com.ovit.jcw.service.AnalysisJudgmentService;
import com.ovit.jcw.service.AuditLogService;
import com.ovit.jcw.service.AuthorityService;
import com.ovit.jcw.service.BusService;
import com.ovit.jcw.service.RelationshipService;
import com.ovit.jcw.utils.Result;
import com.ovit.jcw.utils.ResultCode;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/analysisJudgment"})
public class AnalysisJudgmentController extends BaseController {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private AnalysisJudgmentService analysisJudgmentService;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private BusService busService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private RelationshipService relationshipService;

	@RequestMapping({"/query"})
	public Result query(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		logger.info("分析研判-搜索?果?查?");
		Result result = new Result();
		Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag.booleanValue()) {
			String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			BigInteger dataUsage = BigInteger.valueOf(0L);

			Integer queryType = Integer.valueOf(NormalEnum.QueryType.FXYPLibrary.getCode());

			String descPretty = "";
			try {
				logger.info("查??件：{}", params);
				String idNumber = params.get("idNumber").toString();
				descPretty = idNumber;

				analysisJudgmentService.query(token, idNumber, result);

				dataUsage = BigInteger.valueOf(1L);
			} catch (Exception e) {
				logger.error("?据查?异常：{}", e);
				result.setMsg("?据查?异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);

				dataUsage = BigInteger.valueOf(0L);
			} finally {
				auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.queryForAnalysisJudgment.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({"/auth"})
	public Result auth(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		logger.info("分析研判-生成?子?案");
		Result result = new Result();
		Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag.booleanValue()) {
			String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			BigInteger dataUsage = BigInteger.valueOf(0L);

			Integer queryType = Integer.valueOf(NormalEnum.QueryType.FXYPLibrary.getCode());

			String descPretty = "";
			try {
				logger.info("查??件：{}", params);
				String idNumber = params.get("idNumber").toString();
				descPretty = idNumber;

				Boolean charge = analysisJudgmentService.addAuth("", token, Integer.valueOf(1), idNumber);
				if (charge.booleanValue()) {
					result.setCode(ResultCode.SUCCESS);
					result.setMsg("操作成功");
				} else {
					result.setCode(ResultCode.FAIL);
					result.setMsg("??据的?子?案已生成");
				}
			} catch (Exception e) {
				logger.error("操作异常：{}", e);
				result.setMsg("操作异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);

				dataUsage = BigInteger.valueOf(0L);
			} finally {
				auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.AnalysisJudgmentWithAuth.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({"/queryAnalysis"})
	public Result queryAnalysis(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		logger.info("分析研判-人物?系?果");
		Result result = new Result();
		Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag.booleanValue()) {
			String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			BigInteger dataUsage = BigInteger.valueOf(0L);

			Integer queryType = Integer.valueOf(NormalEnum.QueryType.FXYPLibrary.getCode());

			String descPretty = "";
			try {
				logger.info("查??件：{}", params);
					List<Map<String, String>> alist = (List) params.get("cardList");

				Map<String, Object> resultMap = relationshipService.query(alist, 0);
				logger.info("?据查?成功：{}", resultMap);
				result.setData(resultMap);
				result.setMsg("?据查?成功");

				dataUsage = BigInteger.valueOf(1L);
			} catch (Exception e) {
				logger.error("?据查?异常：{}", e);
				result.setMsg("?据查?异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);

				dataUsage = BigInteger.valueOf(0L);
			} finally {
				auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.queryForAnalysisJudgment.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({"/queryAnalysisResult"})
	public Result queryAnalysisResult(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		logger.info("分析研判-人物?系?果分析");
		Result result = new Result();
		Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag.booleanValue()) {
			String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			BigInteger dataUsage = BigInteger.valueOf(0L);

			Integer queryType = Integer.valueOf(NormalEnum.QueryType.FXYPLibrary.getCode());

			String descPretty = "";
			try {
				logger.info("查??件：{}", params);
				List<Map<String, String>> alist = (List) params.get("cardList");

				Map<String, Object> resultMap = relationshipService.query(alist, 1);
				logger.info("?据查?成功：{}", resultMap);

				result.setData(resultMap);
				result.setMsg("?据查?成功");

				dataUsage = BigInteger.valueOf(1L);
			} catch (Exception e) {
				logger.error("?据查?异常：{}", e);
				result.setMsg("?据查?异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);

				dataUsage = BigInteger.valueOf(0L);
			} finally {
				auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.queryForAnalysisJudgment.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}
}