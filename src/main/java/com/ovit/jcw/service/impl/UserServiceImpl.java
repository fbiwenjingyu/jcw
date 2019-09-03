package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.BasicUser;
import com.ovit.jcw.model.User;
import com.ovit.jcw.mysqlmapper.UserMapper;
import com.ovit.jcw.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	public User queryUserByLoginName(String loginName) {
		return userMapper.queryUserByLoginName(loginName);
	}

	public BasicUser authBasicUser(Map<String, Object> map) {
		return userMapper.authBasicUser(map);
	}

	public User authUser(Map<String, Object> map) {
		return userMapper.authUser(map);
	}

	public Integer insert(User user) {
		return userMapper.insert(user);
	}

	public void insertUserRoleTable(Map<String, Object> map) {
		userMapper.insertUserRoleTable(map);
	}

	public List<User> query(Map<String, Object> map) {
		List<User> userList = userMapper.query(map);
		return userList;
	}

	public User selectByPrimaryKey(String loginUserId) {
		return userMapper.selectByPrimaryKey(loginUserId);
	}

	public void deleteByUserId(String userId) {
		userMapper.deleteByUserId(userId);
	}

	public Integer updateByPrimaryKey(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	public void deleteByPrimaryKey(String id) {
		userMapper.deleteByPrimaryKey(id);
	}

	public void updatePassword(User user) {
		userMapper.updatePassword(user);
	}

	public void updateIsLock(User user) {
		userMapper.updateIsLock(user);
	}

	public List<String> selectLoginName() {
		return userMapper.selectLoginName();
	}
}