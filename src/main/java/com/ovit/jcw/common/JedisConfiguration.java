package com.ovit.jcw.common;

import com.ovit.jcw.common.jedis.JedisProperties;
import com.ovit.jcw.common.pool.JedisPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({JedisPool.class})
@EnableConfigurationProperties({JedisProperties.class})
public class JedisConfiguration {
	private static Logger logger = LogManager.getLogger(JedisConfiguration.class);
	@Autowired
	private JedisProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public JedisPool masService() {
		JedisPool pool = new JedisPool(properties.getHosts(), properties.getPassword(),
				properties.getTimeout().intValue(), properties.getMaxTotal().intValue(),
				properties.getMaxIdle().intValue(), properties.getMinIdle().intValue(), properties.getMode());
		return pool;
	}
}