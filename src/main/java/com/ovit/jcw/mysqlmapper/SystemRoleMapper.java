package com.ovit.jcw.mysqlmapper;

import com.ovit.jcw.model.SystemRole;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface SystemRoleMapper {
	public abstract Integer insert(SystemRole paramSystemRole);

	public abstract void insertRoleRuleTable(Map<String, Object> paramMap);

	public abstract List<SystemRole> query();

	public abstract SystemRole selectByPrimaryKey(Integer paramInteger);

	public abstract void deleteByRoleId(Integer paramInteger);

	public abstract Integer updateByPrimaryKey(SystemRole paramSystemRole);

	public abstract void deleteByPrimaryKey(Integer paramInteger);

	public abstract List<SystemRole> queryAll();
}