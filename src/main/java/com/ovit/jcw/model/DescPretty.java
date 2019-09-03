package com.ovit.jcw.model;

public class DescPretty {
	private String col_name;
	private String col_data;

	public DescPretty() {
	}

	public DescPretty(String name, String data) {
		col_name = name;
		col_data = data;
	}

	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getCol_data() {
		return col_data;
	}

	public void setCol_data(String col_data) {
		this.col_data = col_data;
	}
}