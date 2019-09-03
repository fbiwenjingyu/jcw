package com.ovit.jcw.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DZDAGLLXFSMapper {
	public abstract Map<String, Object> queryByIDNumberForRelation(String paramString);
}