package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.SystemRole;
import com.ovit.jcw.mysqlmapper.SystemRoleMapper;
import com.ovit.jcw.service.SystemRoleService;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {
	private static Logger logger = LogManager.getLogger(SystemRoleServiceImpl.class);
	@Autowired
	private SystemRoleMapper systemRoleMapper;

	public Integer insert(SystemRole systemRole) {
		return systemRoleMapper.insert(systemRole);
	}

	public void insertRoleRuleTable(Map<String, Object> map) {
		systemRoleMapper.insertRoleRuleTable(map);
	}

	public List<SystemRole> query() {
		List<SystemRole> systemRoleList = systemRoleMapper.query();
		return systemRoleList;
	}

	public SystemRole selectByPrimaryKey(Integer id) {
		return systemRoleMapper.selectByPrimaryKey(id);
	}

	public void deleteByRoleId(Integer roleId) {
		systemRoleMapper.deleteByRoleId(roleId);
	}

	public Integer updateByPrimaryKey(SystemRole systemRole) {
		return systemRoleMapper.updateByPrimaryKey(systemRole);
	}

	public void deleteByPrimaryKey(Integer id) {
		systemRoleMapper.deleteByPrimaryKey(id);
	}

	public List<SystemRole> queryAll() {
		List<SystemRole> systemRoleList = systemRoleMapper.queryAll();
		return systemRoleList;
	}
}