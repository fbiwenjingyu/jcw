package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface SYYHDMapper {
	public abstract List<Map<String, Object>> QueryByCard(String paramString);

	public abstract List<Map<String, Object>> selectDetail(Map<String, Object> paramMap);

	public abstract List<String> selectForBFHM(String paramString);

	public abstract List<Map<String, Object>> queryByIdNumber(String paramString);
}