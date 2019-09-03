package com.ovit.jcw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.util.Date;

public class AuditLog {
	private BigInteger id;
	private String ip;
	private String loginName;
	private String loginTime;
	private String interfaceName;
	private String descPretty;
	private BigInteger dataUsage;
	private Integer dataSecurity;
	private String sign;
	private Integer queryType;
	private String queryTypeValue;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getDescPretty() {
		return descPretty;
	}

	public void setDescPretty(String descPretty) {
		this.descPretty = descPretty;
	}

	public BigInteger getDataUsage() {
		return dataUsage;
	}

	public void setDataUsage(BigInteger dataUsage) {
		this.dataUsage = dataUsage;
	}

	public Integer getDataSecurity() {
		return dataSecurity;
	}

	public void setDataSecurity(Integer dataSecurity) {
		this.dataSecurity = dataSecurity;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public String getQueryTypeValue() {
		return queryTypeValue;
	}

	public void setQueryTypeValue(String queryTypeValue) {
		this.queryTypeValue = queryTypeValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}