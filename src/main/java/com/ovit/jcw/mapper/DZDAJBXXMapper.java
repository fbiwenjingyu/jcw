package com.ovit.jcw.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DZDAJBXXMapper {
	public abstract Map<String, Object> queryUserByIDNumber(String paramString);

	public abstract Map<String, Object> queryUser(String paramString);
}