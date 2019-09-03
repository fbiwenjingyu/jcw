package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DZDACXGJZSMapper {
	public abstract List<Map<String, Object>> queryUserByIDNumberForStay(String paramString);

	public abstract List<Map<String, Object>> selectZsCounts(String paramString);

	public abstract List<Map<String, Object>> selectDetail(Map<String, Object> paramMap);

	public abstract Integer queryByIdNumber(Map<String, Object> paramMap);

	public abstract String selectLastDate(String paramString);

	public abstract List<Map<String, Object>> selectLastData(String paramString);
}