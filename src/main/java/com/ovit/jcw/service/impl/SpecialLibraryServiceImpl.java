package com.ovit.jcw.service.impl;

import com.ovit.jcw.mapper.SpecialLibraryMapper;
import com.ovit.jcw.service.SpecialLibraryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialLibraryServiceImpl implements SpecialLibraryService {
	private static Logger logger = LogManager.getLogger(SpecialLibraryServiceImpl.class);
	@Autowired
	private SpecialLibraryMapper specialLibraryMapper;

	public List<Map<String, Object>> selectForProjectTender(String queryCondition) {
		List<Map<String, Object>> list = new ArrayList();
		list = specialLibraryMapper.selectForProjectTender(queryCondition);
		return list;
	}

	public List<Map<String, Object>> selectForLawsAndRegulations(Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList();
		list = specialLibraryMapper.selectForLawsAndRegulations(map);
		return list;
	}

	public List<Map<String, Object>> selectForCaseLibrary(Map<String, Object> map) {
		List<Map<String, Object>> list = new ArrayList();
		list = specialLibraryMapper.selectForCaseLibrary(map);
		return list;
	}

	public Map<String, Object> selectForArchiveBaseInfo(String queryCondition) {
		return specialLibraryMapper.selectForArchiveBaseInfo(queryCondition);
	}

	public List<Map<String, Object>> queryForArchiveHYXX(String queryCondition) {
		return specialLibraryMapper.queryForArchiveHYXX(queryCondition);
	}

	public List<Map<String, Object>> queryArchiveZNXX(String queryCondition) {
		return specialLibraryMapper.queryArchiveZNXX(queryCondition);
	}

	public List<Map<String, Object>> selectForVehicleViolationLibrary(String queryCondition) {
		return specialLibraryMapper.selectForVehicleViolationLibrary(queryCondition);
	}

	public List<Map<String, Object>> selectForVehicleViolationLibraryDetail(String queryCondition) {
		return specialLibraryMapper.selectForVehicleViolationLibraryDetail(queryCondition);
	}
}