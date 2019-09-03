package com.ovit.jcw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class DataConfig {
	private Integer id;
	private String dataType;
	private Integer pkColName;
	private String pkColData;
	private String remark;
	private Integer order;
	private Integer isEnable;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getPkColName() {
		return pkColName;
	}

	public void setPkColName(Integer pkColName) {
		this.pkColName = pkColName;
	}

	public String getPkColData() {
		return pkColData;
	}

	public void setPkColData(String pkColData) {
		this.pkColData = pkColData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}