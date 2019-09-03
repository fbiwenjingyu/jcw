package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.DataConfig;
import com.ovit.jcw.mysqlmapper.DataConfigMapper;
import com.ovit.jcw.service.DataConfigService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataConfigServiceImpl implements DataConfigService {
	private static Logger logger = LogManager.getLogger(DataConfigServiceImpl.class);
	@Autowired
	private DataConfigMapper dataConfigMapper;

	public List<DataConfig> query(String dataType) {
		List<DataConfig> dataConfigList = new ArrayList();
		dataConfigList = dataConfigMapper.query(dataType);
		return dataConfigList;
	}

	public List<String> queryData(String dataType) {
		List<String> dataConfigList = dataConfigMapper.queryData(dataType);
		return dataConfigList;
	}
}