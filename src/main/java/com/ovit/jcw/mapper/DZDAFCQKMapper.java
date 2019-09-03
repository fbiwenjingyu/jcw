package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;

public abstract interface DZDAFCQKMapper {
	public abstract List<Map<String, Object>> queryForFCQK(String paramString);
}