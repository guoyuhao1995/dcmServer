package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.Resource;

/** (SYS_RESOURCE) **/
public interface ResourceDao {

	public Resource find(Serializable id);

	public List<Resource> findAll();

	public int create(Resource t);

	public int update(Resource t);

	public int delete(Serializable id);

	public int deleteAll();

	/** codegen **/

	public List<Resource> getResourcesByUserId(Integer userId);
}