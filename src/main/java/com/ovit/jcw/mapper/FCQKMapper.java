package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface FCQKMapper {
	public abstract List<Map<String, Object>> queryForFCDYXX(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> queryForFCQSXX(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> queryForFCBAXX(String paramString);

	public abstract List<Map<String, Object>> queryForFCCFXX(Map<String, Object> paramMap);
}