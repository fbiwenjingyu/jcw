package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface SDXHDMapper {
	public abstract List<Map<String, Object>> QueryByCard(String paramString);

	public abstract List<Map<String, Object>> selectCountsAndDH(String paramString);

	public abstract List<Map<String, Object>> selectDetail(Map<String, Object> paramMap);
}