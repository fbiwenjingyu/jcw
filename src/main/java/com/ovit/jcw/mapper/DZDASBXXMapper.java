package com.ovit.jcw.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DZDASBXXMapper {
	public abstract Map<String, Object> queryForGRSBXX(String paramString);
}