package com.ovit.jcw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.jcw.mapper.AssociationLibraryMapper;
import com.ovit.jcw.mysqlmapper.BusMapper;
import com.ovit.jcw.service.AssociationLibraryService;
import com.ovit.jcw.service.AuthorityService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociationLibraryServiceImpl implements AssociationLibraryService {
	private static Logger logger = LogManager.getLogger(AssociationLibraryServiceImpl.class);
	@Autowired
	private AssociationLibraryMapper associationLibraryMapper;
	@Autowired
	private BusMapper busMapper;
	@Autowired
	private AuthorityService authorityService;

	public List<Map<String, Object>> selectForHumanAndSocial(String queryCondition) {
		return associationLibraryMapper.selectForHumanAndSocial(queryCondition);
	}

	public List<Map<String, Object>> selectForLegalJustices(String queryCondition) {
		return associationLibraryMapper.selectForLegalJustices(queryCondition);
	}

	public List<Map<String, Object>> selectForForensics(String queryCondition) {
		return associationLibraryMapper.selectForForensics(queryCondition);
	}

	public List<Map<String, Object>> queryNormalByTableInfo(String library, String table, String queryCondition,
			List<String> fieldInfo) {
		return associationLibraryMapper.queryNormalByTableInfo(library, table, queryCondition, fieldInfo);
	}

	public Map<String, Object> query(String token, Integer type, String table, String queryCondition, Integer page,
			Integer size) {
		Map<String, Object> map = new HashMap();
		List<Map<String, Object>> resultMap = new ArrayList();
		try {
			List<String> fieldInfo = Arrays.asList(busMapper.selectFieldByTable(type).split(","));

			String library = busMapper.selectLibraryByTable(type);

			Map<String, String> filedMap = new HashMap();
			List<Map<String, Object>> filedList = busMapper.queryFiledByTable(table);
			if ((filedList != null) && (filedList.size() > 0)) {
				for (Map<String, Object> item : filedList) {
					filedMap.put(item.get("field").toString(), item.get("name").toString());
				}
			}
			map.put("field", filedMap);

			PageHelper.startPage(page.intValue(), size.intValue());

			resultMap = queryNormalByTableInfo(library, table, queryCondition, fieldInfo);
			if ((resultMap != null) && (resultMap.size() > 0)) {
				authority(resultMap, table, token);
				map.put("data", new PageInfo(resultMap));
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return map;
	}

	private void authority(List<Map<String, Object>> resultMap, String table, String token) {
		if ((resultMap != null) && (resultMap.size() > 0)) {
			List<String> secretList = busMapper.queryEnglishSecretFiledByTable(table);

			List<String> signList = busMapper.querySignFiledByTable(table);

			List<String> redList = authorityService.getRedListByUser(null);

			List<String> redAuthorityList = authorityService.getRedListByUser(token);

			Map<String, Map<String, String>> keyList = authorityService.getTableFieldKey(table, Integer.valueOf(0));
			authorityService.redSignList(secretList, signList, redList, redAuthorityList, keyList, resultMap);
		}
	}
}