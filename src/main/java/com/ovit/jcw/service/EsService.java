package com.ovit.jcw.service;

import com.ovit.jcw.utils.Result;

public abstract interface EsService {
	public abstract Result query(String paramString1, String paramString2, String paramString3, Integer paramInteger1,
			Integer paramInteger2);
}