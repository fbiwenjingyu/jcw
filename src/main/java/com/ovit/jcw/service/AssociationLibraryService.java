package com.ovit.jcw.service;

import java.util.List;
import java.util.Map;

public abstract interface AssociationLibraryService {
	public abstract List<Map<String, Object>> selectForHumanAndSocial(String paramString);

	public abstract List<Map<String, Object>> selectForLegalJustices(String paramString);

	public abstract List<Map<String, Object>> selectForForensics(String paramString);

	public abstract List<Map<String, Object>> queryNormalByTableInfo(String paramString1, String paramString2,
			String paramString3, List<String> paramList);

	public abstract Map<String, Object> query(String paramString1, Integer paramInteger1, String paramString2,
			String paramString3, Integer paramInteger2, Integer paramInteger3);
}