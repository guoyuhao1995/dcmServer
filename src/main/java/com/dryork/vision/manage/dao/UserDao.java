package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.User;

/** (SYS_USER) **/
public interface UserDao {

	public User find(Serializable id);

	public List<User> findAll();

	public int create(User t);

	public int update(User t);

	public int delete(Serializable id);

	/** codegen **/

	public User selectOne(User user);

	public List<User> selectList(User user);

	public int userCount(User user);

}