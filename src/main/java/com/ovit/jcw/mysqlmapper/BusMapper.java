package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface BusMapper {
	public abstract List<String> queryLibrary();

	public abstract List<Map<String, Object>> queryByCreateTag(Integer paramInteger);

	public abstract List<Map<String, Object>> queryEXByType(Integer paramInteger);

	public abstract List<Map<String, Object>> queryByLibrary(Integer paramInteger);

	public abstract List<Map<String, Object>> queryFiledByTable(String paramString);

	public abstract Map<String, Object> queryInfoByTable(String paramString);

	public abstract List<String> queryChineseSecretFiledByTable(@Param("table") String paramString);

	public abstract List<String> queryEnglishSecretFiledByTable(@Param("table") String paramString);

	public abstract Map<String, Object> queryBaseConfig();

	public abstract List<Map<String, Object>> queryByConfigTag(Integer paramInteger);

	public abstract List<Map<String, Object>> queryEnglishFiledKeyByTable(String paramString);

	public abstract List<Map<String, Object>> queryChineseFiledKeyByTable(String paramString);

	public abstract List<String> queryUserSecretByTable(@Param("table") String paramString1,
			@Param("user") String paramString2);

	public abstract List<String> queryUserSecretByLibrary(@Param("library") String paramString1,
			@Param("user") String paramString2);

	public abstract Integer queryUserSecretByUser(@Param("user") String paramString1,
			@Param("condition") String paramString2);

	public abstract List<String> queryShowFiledByTable(@Param("table") String paramString);

	public abstract List<String> querySignFiledByTable(@Param("table") String paramString);

	public abstract List<String> querySignNameByTable(@Param("table") String paramString);

	public abstract List<String> querySpecialFiledByTable(@Param("table") String paramString);

	public abstract List<String> queryRedList();

	public abstract List<String> queryRedListByUser(@Param("user") String paramString);

	public abstract List<String> queryUserSecretByCondition(@Param("condition") String paramString1,
			@Param("user") String paramString2);

	public abstract List<String> queryUserSecretByConditionForExact(@Param("condition") String paramString1,
			@Param("user") String paramString2);

	public abstract Integer selectAllTables();

	public abstract String selectFieldByTable(Integer paramInteger);

	public abstract String selectLibraryByTable(Integer paramInteger);

	public abstract List<Map<String, Object>> selectRelationshipContrast();

	public abstract List<Map<String, Object>> selectRelationshipContrastInfo(Integer paramInteger);

	public abstract List<Map<String, Object>> selectFieldByTag(Integer paramInteger);

	public abstract void insert(@Param("table") String paramString1, @Param("user") String paramString2,
			@Param("flag") Integer paramInteger, @Param("condition") String paramString3);
}