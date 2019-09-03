package com.ovit.jcw.service;

import com.ovit.jcw.utils.Result;
import java.util.List;
import java.util.Map;

public abstract interface AnalysisJudgmentService {
	public abstract List<Map<String, Object>> queryTablesByType(Integer paramInteger);

	public abstract List<Map<String, Object>> queryContent(Map<String, Object> paramMap);

	public abstract Integer queryCounts(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> fuzzyQuery(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> associatedQuery(Map<String, Object> paramMap);

	public abstract List<String> queryForBFHM(String paramString);

	public abstract List<Map<String, Object>> queryWithYYHD(String paramString);

	public abstract List<Map<String, Object>> queryWithDXHD(String paramString);

	public abstract List<String> selectAllTables();

	public abstract void query(String paramString1, String paramString2, Result paramResult);

	public abstract Boolean addAuth(String paramString1, String paramString2, Integer paramInteger,
			String paramString3);
}