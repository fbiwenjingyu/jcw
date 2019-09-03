package com.ovit.jcw.service;

import com.ovit.jcw.model.DescPretty;
import java.util.List;
import java.util.Map;

public abstract interface AuthorityService {
	public abstract Map<String, Object> encryptionMap(String paramString, Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> encryptionList(String paramString, List<Map<String, Object>> paramList);

	public abstract void encrypt(List<String> paramList, List<DescPretty> paramList1, Map<String, Object> paramMap);

	public abstract void encryptionListBySecret(List<Map<String, Object>> paramList, List<String> paramList1,
			Map<String, Map<String, String>> paramMap);

	public abstract void encryptionMapBySecret(Map<String, Object> paramMap, List<String> paramList,
			Map<String, Map<String, String>> paramMap1);

	public abstract void setFiledMapByKey(Map<String, Object> paramMap, List<String> paramList,
			Map<String, Map<String, String>> paramMap1);

	public abstract void setEsFiledMapByKey(DescPretty paramDescPretty, Boolean paramBoolean,
			Map<String, Map<String, String>> paramMap);

	public abstract boolean encryptionEsMapBySecret(List<DescPretty> paramList, List<String> paramList1,
			Map<String, Map<String, String>> paramMap);

	public abstract void redSignList(List<String> paramList1, List<String> paramList2, List<String> paramList3,
			List<String> paramList4, Map<String, Map<String, String>> paramMap, List<Map<String, Object>> paramList);

	public abstract boolean chargeTableSecret(String paramString1, String paramString2, String paramString3);

	public abstract boolean chargeLibrarySecret(String paramString1, String paramString2, String paramString3);

	public abstract List<String> getRedListByUser(String paramString);

	public abstract String setFiledMapValueByKey(String paramString1, String paramString2, List<String> paramList,
			Map<String, Map<String, String>> paramMap);

	public abstract Map<String, String> getTableFieldInfo(String paramString, Integer paramInteger);

	public abstract Map<String, Map<String, String>> getTableFieldKey(String paramString, Integer paramInteger);
}