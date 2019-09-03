package com.ovit.jcw.service;

import java.util.List;
import java.util.Map;

public abstract interface CorrelationAnalysisService {
	public abstract List<Map<String, Object>> selectByTerm(String paramString, Integer paramInteger1,
			Integer paramInteger2, Integer paramInteger3);

	public abstract List<Map<String, Object>> selectChildByTerm(String paramString, Integer paramInteger);
}