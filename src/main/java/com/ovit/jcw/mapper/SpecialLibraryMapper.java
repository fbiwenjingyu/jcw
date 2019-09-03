package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface SpecialLibraryMapper {
	public abstract List<Map<String, Object>> selectForProjectTender(String paramString);

	public abstract List<Map<String, Object>> selectForLawsAndRegulations(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> selectForCaseLibrary(Map<String, Object> paramMap);

	public abstract Map<String, Object> selectForArchiveBaseInfo(String paramString);

	public abstract List<Map<String, Object>> queryForArchiveHYXX(String paramString);

	public abstract List<Map<String, Object>> queryArchiveZNXX(String paramString);

	public abstract List<Map<String, Object>> selectForVehicleViolationLibrary(String paramString);

	public abstract List<Map<String, Object>> selectForVehicleViolationLibraryDetail(String paramString);
}