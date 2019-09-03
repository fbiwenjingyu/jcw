package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface QGMHLGMapper {
	public abstract List<Map<String, Object>> QueryByCard(String paramString);

	public abstract List<Map<String, Object>> selectSite(String paramString);

	public abstract long selectCounts(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> selectDetail(Map<String, Object> paramMap);

	public abstract Integer queryByIdNumber(Map<String, Object> paramMap);

	public abstract String selectLastDate(String paramString);

	public abstract List<Map<String, Object>> selectLastData(String paramString);

	public abstract List<Map<String, Object>> queryForTimingDiagram(String paramString);
}