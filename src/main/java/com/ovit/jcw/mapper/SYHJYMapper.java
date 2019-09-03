package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface SYHJYMapper {
	public abstract List<Map<String, Object>> QueryByCard(String paramString);

	public abstract List<Map<String, Object>> selectCountsAndZH(String paramString);

	public abstract List<Map<String, Object>> selectDetail(Map<String, Object> paramMap);
}