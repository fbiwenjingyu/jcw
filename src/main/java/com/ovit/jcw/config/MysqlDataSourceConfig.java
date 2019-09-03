package com.ovit.jcw.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {"com.ovit.jcw.mysqlmapper"}, sqlSessionFactoryRef = "DBDataSqlSessionFactoryForMysql")
public class MysqlDataSourceConfig {
	@Bean(name = {"mysqlDataSource"})
	@Qualifier("mysqlDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = {"DBDataSqlSessionFactoryForMysql"})
	public SqlSessionFactory sqlSessionFactoryForMysql(@Qualifier("mysqlDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mysqlmapper/*"));
		return bean.getObject();
	}

	@Bean(name = {"DBDataTransactionManagerForMysql"})
	public DataSourceTransactionManager transactionManagerForMysql(
			@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = {"DBDataSqlSessionTemplateForMysql"})
	public SqlSessionTemplate sqlSessionTemplateForMysql(
			@Qualifier("DBDataSqlSessionFactoryForMysql") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}