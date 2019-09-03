package com.ovit.jcw.config;

import java.net.InetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.ovit.jcw.repository"})
public class EsConfig {
	@Value("${spring.elasticsearch.esHost}")
	private String EsHost;
	@Value("${spring.elasticsearch.esPort}")
	private int EsPort;
	@Value("${spring.elasticsearch.esClusterName}")
	private String EsClusterName;
	private Logger logger = LogManager.getLogger(getClass());

	@Bean
	public TransportClient transportClient() {
		logger.info("初始化elasticsearch TransportClient?始");
		TransportClient client = null;
		try {
			TransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort);

			Settings esSetting = Settings.builder().put("cluster.name", EsClusterName).build();

			client = new PreBuiltTransportClient(esSetting, new Class[0]);

			client.addTransportAddresses(new TransportAddress[]{transportAddress});

			logger.info("elasticsearch TransportClient初始化完成");
		} catch (Exception e) {
			logger.error("elasticsearch TransportClient create error!!!", e);
		}
		return client;
	}
}