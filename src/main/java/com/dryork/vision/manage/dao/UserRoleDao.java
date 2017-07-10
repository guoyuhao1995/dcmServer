package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.UserRole;

/** (SYS_USER_ROLE) **/
public interface UserRoleDao {

	public UserRole find(Serializable id);

	public List<UserRole> findAll();

	public int create(UserRole t);

	public int update(UserRole t);

	public int delete(Serializable id);

	public int deleteAll();

	/** codegen **/
}