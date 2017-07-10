package com.dryork.vision.manage.po;

import com.dryork.vision.manage.common.DBRecord;

/** (SYS_USER_ROLE) **/
public class UserRole extends DBRecord {

	private static final long serialVersionUID = 1L;

	/** (Not Null) */
	private Integer id;
	/**  */
	private Integer userId;
	/**  */
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}