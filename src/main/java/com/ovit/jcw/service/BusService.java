package com.ovit.jcw.service;

import java.util.List;
import java.util.Map;

public abstract interface BusService {
	public abstract void createTable(Integer paramInteger1, Integer paramInteger2);

	public abstract Map<String, String> createTableField(Integer paramInteger);

	public abstract Map<String, Object> createTableExcelField(Integer paramInteger);

	public abstract void createConfigFile(Integer paramInteger);

	public abstract Map<String, Object> encryptionData(String paramString1, String paramString2,
			Map<String, Object> paramMap);

	public abstract void encryptionDataForQueryUser(String paramString1, String paramString2, String paramString3,
			String paramString4, Map<String, Object> paramMap);

	public abstract void encryptionDataForDZDA(String paramString1, Integer paramInteger, String paramString2,
			String paramString3, Map<String, Object> paramMap);

	public abstract void encryptionDataForDZDAList(String paramString1, Integer paramInteger, String paramString2,
			String paramString3, List<Map<String, Object>> paramList);

	public abstract Map<String, Object> query(String paramString1, String paramString2, String paramString3,
			Integer paramInteger1, Integer paramInteger2);

	public abstract List<String> queryUserSecretByCondition(String paramString1, String paramString2);

	public abstract Integer selectAllTables();

	public abstract List<String> queryUserSecretByConditionForExact(String paramString1, String paramString2);

	public abstract Map<String, Object> encryptionDataForSpecialLibrary(String paramString1, String paramString2,
			String paramString3, Map<String, Object> paramMap);

	public abstract List<String> queryEnglishSecretFiledByTable(String paramString);

	public abstract List<String> querySignFiledByTable(String paramString);
}