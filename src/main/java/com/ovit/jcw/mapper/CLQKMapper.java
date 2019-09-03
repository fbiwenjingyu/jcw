package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface CLQKMapper {
	public abstract List<Map<String, Object>> queryForCLQK(String paramString);

	public abstract List<Map<String, Object>> queryForWGXXByHPHM(String paramString);

	public abstract List<String> selectForCPHM(String paramString);

	public abstract List<Map<String, Object>> queryByIdNumber(String paramString);
}