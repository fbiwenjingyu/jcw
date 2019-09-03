package com.ovit.jcw.service;

import com.ovit.jcw.model.DataConfig;
import java.util.List;

public abstract interface DataConfigService {
	public abstract List<DataConfig> query(String paramString);

	public abstract List<String> queryData(String paramString);
}