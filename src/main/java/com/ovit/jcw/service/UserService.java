package com.ovit.jcw.service;

import com.ovit.jcw.model.BasicUser;
import com.ovit.jcw.model.User;
import java.util.List;
import java.util.Map;

public abstract interface UserService {
	public abstract User queryUserByLoginName(String paramString);

	public abstract BasicUser authBasicUser(Map<String, Object> paramMap);

	public abstract User authUser(Map<String, Object> paramMap);

	public abstract Integer insert(User paramUser);

	public abstract void insertUserRoleTable(Map<String, Object> paramMap);

	public abstract List<User> query(Map<String, Object> paramMap);

	public abstract User selectByPrimaryKey(String paramString);

	public abstract void deleteByUserId(String paramString);

	public abstract void deleteByPrimaryKey(String paramString);

	public abstract Integer updateByPrimaryKey(User paramUser);

	public abstract void updatePassword(User paramUser);

	public abstract void updateIsLock(User paramUser);

	public abstract List<String> selectLoginName();
}