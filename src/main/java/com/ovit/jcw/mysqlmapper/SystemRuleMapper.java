package com.ovit.jcw.mysqlmapper;

import com.ovit.jcw.model.SystemRule;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface SystemRuleMapper {
	public abstract List<SystemRule> queryAll();

	public abstract List<SystemRule> selectAllRule(Map<String, Object> paramMap);

	public abstract List<SystemRule> selectByParent(Integer paramInteger);

	public abstract List<Integer> selectUserRoleRule(String paramString);
}