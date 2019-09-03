package com.ovit.jcw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("esProperties")
public class ElasticsearchProperties {
	@Value("${spring.elasticsearch.indexName}")
	private String indexName;
	@Value("${spring.elasticsearch.type}")
	private String type;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}