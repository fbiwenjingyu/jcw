package com.ovit.jcw.service;

import com.ovit.jcw.model.SystemRole;
import java.util.List;
import java.util.Map;

public abstract interface SystemRoleService {
	public abstract Integer insert(SystemRole paramSystemRole);

	public abstract void insertRoleRuleTable(Map<String, Object> paramMap);

	public abstract List<SystemRole> query();

	public abstract SystemRole selectByPrimaryKey(Integer paramInteger);

	public abstract void deleteByRoleId(Integer paramInteger);

	public abstract Integer updateByPrimaryKey(SystemRole paramSystemRole);

	public abstract void deleteByPrimaryKey(Integer paramInteger);

	public abstract List<SystemRole> queryAll();
}