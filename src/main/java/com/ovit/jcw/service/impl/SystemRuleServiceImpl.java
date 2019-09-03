package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.SystemRule;
import com.ovit.jcw.mysqlmapper.SystemRuleMapper;
import com.ovit.jcw.service.SystemRuleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRuleServiceImpl implements SystemRuleService {
	private static Logger logger = LogManager.getLogger(SystemRuleServiceImpl.class);
	@Autowired
	private SystemRuleMapper systemRuleMapper;

	public List<SystemRule> GetSystemRoleTree() {
		List<SystemRule> systemRuleList = systemRuleMapper.queryAll();
		if (systemRuleList != null) {
			for (SystemRule rule : systemRuleList) {
				getTreeNodeData(rule);
			}
		}
		return systemRuleList;
	}

	private void getTreeNodeData(SystemRule systemRule) {
		List<SystemRule> systemRuleChild = systemRule.getChild();
		if ((systemRuleChild != null) && (systemRuleChild.size() > 0)) {
			for (SystemRule systemRule1 : systemRuleChild) {
				List<SystemRule> systemRuleList = systemRuleMapper.selectByParent(systemRule1.getId());
				if ((systemRuleList != null) && (systemRuleList.size() > 0)) {
					systemRule1.setChild(systemRuleList);
					for (SystemRule systemRule2 : systemRuleList) {
						getTreeNodeData(systemRule2);
					}
				}
			}
		}
	}

	public List<SystemRule> selectAllRule(String loginName) {
		List<SystemRule> systemRules = new ArrayList();

		List<Integer> list = systemRuleMapper.selectUserRoleRule(loginName);
		if ((list != null) && (list.size() > 0)) {
			Map<String, Object> queryParam = new HashMap();
			queryParam.put("list", list);

			systemRules = systemRuleMapper.selectAllRule(queryParam);
		}
		return systemRules;
	}
}