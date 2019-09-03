package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DZDAYHHCXXMapper {
	public abstract List<Map<String, Object>> queryUserByIDNumberForBank(String paramString);
}