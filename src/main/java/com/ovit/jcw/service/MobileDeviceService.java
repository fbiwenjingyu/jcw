package com.ovit.jcw.service;

import com.ovit.jcw.model.MobileDevice;
import java.util.List;
import java.util.Map;

public abstract interface MobileDeviceService {
	public abstract List<MobileDevice> query(Map<String, Object> paramMap);

	public abstract void insert(MobileDevice paramMobileDevice);

	public abstract void updateBindByIMEI(MobileDevice paramMobileDevice);

	public abstract void updateStatusByIMEI(MobileDevice paramMobileDevice);

	public abstract Integer selectCountByIMEI(String paramString);

	public abstract Integer selectCountByIMEIAndUser(Map<String, Object> paramMap);

	public abstract Integer selectStatusByIMEI(String paramString);

	public abstract void updatePositionByIMEI(Map<String, Object> paramMap);

	public abstract void insertObject(Map<String, Object> paramMap);

	public abstract List<String> selectDeviceName();

	public abstract void deleteByPrimaryKey(Integer paramInteger);
}