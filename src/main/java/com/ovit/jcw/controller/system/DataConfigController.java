package com.ovit.jcw.controller.system;

import com.ovit.jcw.controller.BaseController;
import com.ovit.jcw.model.DataConfig;
import com.ovit.jcw.mysqlmapper.BusMapper;
import com.ovit.jcw.service.DataConfigService;
import com.ovit.jcw.service.impl.BusServiceImpl;
import com.ovit.jcw.utils.Result;
import com.ovit.jcw.utils.ResultCode;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/system/dataConfig"})
public class DataConfigController extends BaseController {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private DataConfigService dataConfigService;
	@Autowired
	private BusServiceImpl busService;
	@Autowired
	private BusMapper busMapper;

	@RequestMapping({"/queryByDataType"})
	public Result queryByDataType(@RequestBody Map<String, Object> params) {
		logger.info("?据字典查?");
		Result result = new Result();
		logger.info("查??件：{}", params);
		try {
			String dataType = params.get("dataType").toString();
			List<DataConfig> resultMap = dataConfigService.query(dataType);
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

	@RequestMapping({"/queryLibrary"})
	public Result queryLibrary() {
		logger.info("委?局字典查?");
		Result result = new Result();
		try {
			List<String> resultMap = busMapper.queryLibrary();
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

	@RequestMapping({"/createTableField"})
	public Result createTableField(@RequestBody Map<String, Object> params) {
		Result result = new Result();
		try {
			Integer tag = Integer.valueOf(Integer.parseInt(params.get("tag").toString()));
			Integer type = Integer.valueOf(Integer.parseInt(params.get("type").toString()));
			if (type.intValue() == 0) {
				Map<String, String> resultMap = busService.createTableField(tag);
				result.setData(resultMap);
			} else {
				Map<String, Object> resultMap = busService.createTableExcelField(tag);
				result.setData(resultMap);
			}
			result.setMsg("操作成功");
		} catch (Exception e) {
			logger.error(e);
			result.setMsg("操作成功");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/createTable"})
	public Result createTable(@RequestBody Map<String, Object> params) {
		Result result = new Result();
		try {
			Integer tag = Integer.valueOf(Integer.parseInt(params.get("tag").toString()));
			Integer type = Integer.valueOf(Integer.parseInt(params.get("type").toString()));
			busService.createTable(tag, type);
			result.setMsg("操作成功");
		} catch (Exception e) {
			logger.error(e);
			result.setMsg("操作成功");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({"/createConfig"})
	public Result createConfig(@RequestBody Map<String, Object> params) {
		Result result = new Result();
		try {
			Integer tag = Integer.valueOf(Integer.parseInt(params.get("tag").toString()));
			busService.createConfigFile(tag);
			result.setMsg("操作成功");
		} catch (Exception e) {
			logger.error(e);
			result.setMsg("操作成功");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}