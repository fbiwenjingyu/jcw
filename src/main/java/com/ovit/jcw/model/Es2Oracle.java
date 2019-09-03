package com.ovit.jcw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "#{esProperties.indexName}", type = "#{esProperties.type}")
public class Es2Oracle {
	@Id
	private String id;
	private String db_nick_name;
	private String table_name;
	private String table_nick_name;
	private String pk_col_name;
	private String pk_col_data;
	private String db_user;
	private String desc;
	private String desc_pretty;
	private Integer queryType;
	private Integer flag;
	private String data_update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_nick_name() {
		return db_nick_name;
	}

	public void setDb_nick_name(String db_nick_name) {
		this.db_nick_name = db_nick_name;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_nick_name() {
		return table_nick_name;
	}

	public void setTable_nick_name(String table_nick_name) {
		this.table_nick_name = table_nick_name;
	}

	public String getPk_col_name() {
		return pk_col_name;
	}

	public void setPk_col_name(String pk_col_name) {
		this.pk_col_name = pk_col_name;
	}

	public String getPk_col_data() {
		return pk_col_data;
	}

	public void setPk_col_data(String pk_col_data) {
		this.pk_col_data = pk_col_data;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getData_update_time() {
		return data_update_time;
	}

	public void setData_update_time(String data_update_time) {
		this.data_update_time = data_update_time;
	}

	public String getDesc_pretty() {
		return desc_pretty;
	}

	public void setDesc_pretty(String desc_pretty) {
		this.desc_pretty = desc_pretty;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String toString() {
		return "Es2Oracle{db_nick_name='" + db_nick_name + '\'' + ", table_nick_name='" + table_nick_name + '\''
				+ ", db_user='" + db_user + '\'' + ", table_name=" + table_name + ", pk_col_name=" + pk_col_name
				+ ", pk_col_data=" + pk_col_data + ", desc=" + desc + ", data_update_time=" + data_update_time + '}';
	}
}