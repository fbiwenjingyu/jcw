package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface AssociationLibraryMapper {
	public abstract List<Map<String, Object>> selectForHumanAndSocial(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> selectForLegalJustices(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> selectForForensics(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> queryNormalByTableInfo(@Param("library") String paramString1,
			@Param("table") String paramString2, @Param("queryCondition") String paramString3,
			@Param("fieldInfo") List<String> paramList);
}