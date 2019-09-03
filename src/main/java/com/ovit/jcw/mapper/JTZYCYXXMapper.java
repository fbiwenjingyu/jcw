package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface JTZYCYXXMapper {
	public abstract List<Map<String, Object>> QueryByCard(String paramString);

	public abstract List<Map<String, Object>> queryForHYXX(String paramString);

	public abstract List<Map<String, Object>> queryForHJZNXX(String paramString);

	public abstract List<Map<String, Object>> queryForLDZNXX(String paramString);

	public abstract List<Map<String, Object>> queryForHYXXByMZJ(String paramString);

	public abstract Map<String, Object> queryForHYXXByJGBZ(String paramString);
}