package com.ovit.jcw.common.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class JedisProxySingle implements JedisProxy {
	private JedisPool pool;

	public JedisProxySingle(String jedisHosts, int maxTotal, int maxIdle, int minIdle) {
		String[] hostAndPort = jedisHosts.replaceAll(" ", "").split(":");

		JedisPoolConfig poolconfig = new JedisPoolConfig();
		poolconfig.setMaxTotal(maxTotal);
		poolconfig.setMaxIdle(maxIdle);
		poolconfig.setMinIdle(minIdle);

		pool = new JedisPool(poolconfig, hostAndPort[0], Integer.parseInt(hostAndPort[1]));
	}

	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String set(String key, String value, String nxxx, String expx, long time) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key, value, nxxx, expx, time);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long persist(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.persist(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String type(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.type(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.expire(key, seconds);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long pexpire(String key, long milliseconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pexpire(key, milliseconds);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long expireAt(String key, long unixTime) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.expireAt(key, unixTime);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pexpireAt(key, millisecondsTimestamp);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long ttl(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.ttl(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean setbit(String key, long offset, boolean value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setbit(key, offset, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean setbit(String key, long offset, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setbit(key, offset, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean getbit(String key, long offset) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.getbit(key, offset);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long setrange(String key, long offset, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setrange(key, offset, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String getrange(String key, long startOffset, long endOffset) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.getrange(key, startOffset, endOffset);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String getSet(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.getSet(key, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setnx(key, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setex(key, seconds, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long decrBy(String key, long integer) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.decrBy(key, integer);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long decr(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.decr(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long incrBy(String key, long integer) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incrBy(key, integer);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Double incrByFloat(String key, double value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incrByFloat(key, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incr(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long append(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.append(key, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hset(key, field, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hget(key, field);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long hsetnx(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hsetnx(key, field, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmset(key, hash);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmget(key, fields);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long hincrBy(String key, String field, long value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hincrBy(key, field, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean hexists(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hexists(key, field);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long hdel(String key, String... field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hdel(key, field);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long hlen(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hlen(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hkeys(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> hvals(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hvals(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hgetAll(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long rpush(String key, String... string) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpush(key, string);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long lpush(String key, String... string) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpush(key, string);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long llen(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.llen(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String ltrim(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.ltrim(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String lindex(String key, long index) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lindex(key, index);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String lset(String key, long index, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lset(key, index, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long lrem(String key, long count, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lrem(key, count, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String lpop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpop(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String rpop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpop(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long sadd(String key, String... member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sadd(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> smembers(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.smembers(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long srem(String key, String... member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srem(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String spop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.spop(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> spop(String key, long count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.spop(key, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.scard(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Boolean sismember(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String srandmember(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srandmember(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> srandmember(String key, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.srandmember(key, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long strlen(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.strlen(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zadd(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zadd(key, score, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zadd(key, scoreMembers);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrange(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zrem(String key, String... member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrem(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Double zincrby(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zincrby(key, score, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zrank(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrank(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zrevrank(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrank(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrange(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeWithScores(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeWithScores(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zcard(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zcard(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Double zscore(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zscore(key, member);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> sort(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sort(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sort(key, sortingParameters);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zcount(String key, double min, double max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zcount(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zcount(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zcount(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScore(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScore(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScore(key, min, max, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScore(key, min, max, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key, max, min, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScore(key, max, min, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByScoreWithScores(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByScoreWithScores(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zremrangeByRank(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zremrangeByRank(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zremrangeByScore(String key, double start, double end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zremrangeByScore(String key, String start, String end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zremrangeByScore(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zlexcount(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zlexcount(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByLex(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByLex(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrangeByLex(key, min, max, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByLex(key, max, min);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zrevrangeByLex(key, max, min, offset, count);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long zremrangeByLex(String key, String min, String max) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zremrangeByLex(key, min, max);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.linsert(key, where, pivot, value);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long lpushx(String key, String... string) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpushx(key, string);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long rpushx(String key, String... string) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpushx(key, string);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.del(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String echo(String string) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.echo(string);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long move(String key, int dbIndex) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.move(key, dbIndex);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long bitcount(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.bitcount(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long bitcount(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.bitcount(key, start, end);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Map<String, JedisPool> getClusterNodes() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return new HashMap(0);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hscan(key, cursor);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public ScanResult<String> sscan(String key, String cursor) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sscan(key, cursor);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public ScanResult<Tuple> zscan(String key, String cursor) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.zscan(key, cursor);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long pfadd(String key, String... elements) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pfadd(key, elements);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public long pfcount(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pfcount(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> blpop(int timeout, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.blpop(timeout, key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public List<String> brpop(int timeout, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.brpop(timeout, key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Set<String> keys(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.keys(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long pttl(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.pttl(key);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.subscribe(jedisPubSub, channels);
			return;
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public Long publish(String channel, String message) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.publish(channel, message);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.psubscribe(jedisPubSub, patterns);
			return;
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
				}
			}
		}
	}
}