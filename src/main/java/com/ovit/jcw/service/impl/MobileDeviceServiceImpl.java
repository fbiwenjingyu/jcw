package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.MobileDevice;
import com.ovit.jcw.mysqlmapper.MobileDeviceMapper;
import com.ovit.jcw.service.MobileDeviceService;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileDeviceServiceImpl implements MobileDeviceService {
	private static Logger logger = LogManager.getLogger(MobileDeviceServiceImpl.class);
	@Autowired
	private MobileDeviceMapper mobileDeviceMapper;

	public List<MobileDevice> query(Map<String, Object> map) {
		return mobileDeviceMapper.query(map);
	}

	public void insert(MobileDevice mobileDevice) {
		mobileDeviceMapper.insert(mobileDevice);
	}

	public void updateBindByIMEI(MobileDevice mobileDevice) {
		mobileDeviceMapper.updateBindByIMEI(mobileDevice);
	}

	public void updateStatusByIMEI(MobileDevice mobileDevice) {
		mobileDeviceMapper.updateStatusByIMEI(mobileDevice);
	}

	public Integer selectCountByIMEI(String imei) {
		return mobileDeviceMapper.selectCountByIMEI(imei);
	}

	public Integer selectCountByIMEIAndUser(Map<String, Object> map) {
		return mobileDeviceMapper.selectCountByIMEIAndUser(map);
	}

	public Integer selectStatusByIMEI(String imei) {
		return mobileDeviceMapper.selectStatusByIMEI(imei);
	}

	public void updatePositionByIMEI(Map<String, Object> map) {
		mobileDeviceMapper.updatePositionByIMEI(map);
	}

	public void insertObject(Map<String, Object> map) {
		mobileDeviceMapper.insertObject(map);
	}

	public List<String> selectDeviceName() {
		return mobileDeviceMapper.selectDeviceName();
	}

	public void deleteByPrimaryKey(Integer id) {
		mobileDeviceMapper.deleteByPrimaryKey(id);
	}
}