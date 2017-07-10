package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.RoleResource;

/** (SYS_ROLE_RESOURCE) **/
public interface RoleResourceDao {

	public RoleResource find(Serializable id);

	public List<RoleResource> findAll();

	public int create(RoleResource t);

	public int update(RoleResource t);

	public int delete(Serializable id);

	public int deleteAll();

	/** codegen **/
}