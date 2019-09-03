package com.ovit.jcw.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {"com.ovit.jcw.mapper"}, sqlSessionTemplateRef = "marketingSqlSessionTemplate")
public class DataSourceMysqlConfig {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceMysqlConfig.class);

	@Bean(name = {"marketingDataSource"})
	@ConfigurationProperties(prefix = "spring.datasource.mysql1")
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = {"marketingSqlSessionFactory"})
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("marketingDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//2019-9-2
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
		logger.info("加?mysql?据??接......");
		return bean.getObject();
	}

	@Bean(name = {"marketingTransactionManager"})
	public DataSourceTransactionManager testTransactionManager(
			@Qualifier("marketingDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = {"marketingSqlSessionTemplate"})
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("marketingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}