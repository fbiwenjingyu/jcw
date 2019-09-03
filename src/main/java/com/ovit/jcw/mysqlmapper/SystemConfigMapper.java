package com.ovit.jcw.mysqlmapper;

import com.ovit.jcw.model.SystemConfig;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface SystemConfigMapper {
	public abstract SystemConfig queryByIp(String paramString);

	public abstract SystemConfig queryByIpAndMac(Map<String, Object> paramMap);
}