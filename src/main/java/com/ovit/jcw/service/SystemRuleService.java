package com.ovit.jcw.service;

import com.ovit.jcw.model.SystemRule;
import java.util.List;

public abstract interface SystemRuleService {
	public abstract List<SystemRule> GetSystemRoleTree();

	public abstract List<SystemRule> selectAllRule(String paramString);
}