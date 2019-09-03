package com.ovit.jcw.common.pool;

import com.ovit.jcw.common.jedis.JedisProxy;
import com.ovit.jcw.common.jedis.JedisProxyAliyun;
import com.ovit.jcw.common.jedis.JedisProxyCluster;
import com.ovit.jcw.common.jedis.JedisProxySingle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JedisPool {
	private Logger log = LogManager.getLogger(JedisPool.class);
	private static JedisProxy jedis;

	public JedisPool(String jedisHosts, String password, int timeout, int maxTotal, int maxIdle, int minIdle,
			String jedisMode) {
		if ("cluster".equalsIgnoreCase(jedisMode)) {
			jedis = new JedisProxyCluster(jedisHosts, maxTotal, maxIdle, minIdle);
		} else if ("aliyun".equalsIgnoreCase(jedisMode)) {
			jedis = new JedisProxyAliyun(jedisHosts, password, maxTotal, maxIdle, minIdle, timeout);
		} else {
			jedis = new JedisProxySingle(jedisHosts, maxTotal, maxIdle, minIdle);
		}
		log.info("Initializing redis pool... {}", jedis);
	}

	public static JedisProxy getJedis() {
		return jedis;
	}
}