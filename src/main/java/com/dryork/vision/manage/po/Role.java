package com.dryork.vision.manage.po;

import java.util.Date;

import com.dryork.vision.manage.common.DBRecord;

/** (SYS_ROLE) **/
public class Role extends DBRecord {

	private static final long serialVersionUID = 1L;

	/** 角色id (Not Null) */
	private Integer id;
	/** 角色名 */
	private String name;
	/** 创建时间 */
	private Date createTime;
	/** 状态0:禁用1:使用 默认使用1 */
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}