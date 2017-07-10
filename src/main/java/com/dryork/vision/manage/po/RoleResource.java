package com.dryork.vision.manage.po;

import com.dryork.vision.manage.common.DBRecord;

/** (SYS_ROLE_RESOURCE) **/
public class RoleResource extends DBRecord {

	private static final long serialVersionUID = 1L;

	/** (Not Null) */
	private Integer id;
	/**  */
	private Integer roleId;
	/**  */
	private Integer resourceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}