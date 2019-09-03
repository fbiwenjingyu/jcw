package com.ovit.jcw.mysqlmapper;

import com.ovit.jcw.model.DataConfig;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface DataConfigMapper {
	public abstract List<DataConfig> query(String paramString);

	public abstract List<String> queryData(String paramString);
}