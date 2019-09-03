package com.ovit.jcw.service;

import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;

public abstract interface AuditLogService {
	public abstract void insertLogRecordsForLogin(HttpServletRequest paramHttpServletRequest, String paramString1,
			String paramString2, String paramString3, BigInteger paramBigInteger);

	public abstract void insertLogRecordsForNormal(HttpServletRequest paramHttpServletRequest, String paramString1,
			String paramString2, String paramString3, BigInteger paramBigInteger, Integer paramInteger);

	public abstract Integer selectCounts(String paramString);
}