package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.Role;

/** (SYS_ROLE) **/
public interface RoleDao {

	public Role find(Serializable id);

	public List<Role> findAll();

	public int create(Role t);

	public int update(Role t);

	public int delete(Serializable id);

	public int deleteAll();

	/** codegen **/
}