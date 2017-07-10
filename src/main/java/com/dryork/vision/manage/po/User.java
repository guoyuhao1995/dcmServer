package com.dryork.vision.manage.po;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dryork.vision.manage.common.DBRecord;

/** (SYS_USER) **/
public class User extends DBRecord {

	private static final long serialVersionUID = 1L;
	/** 主键 (Not Null) */
	private Integer id;
	/** 姓名 */
	private String name;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String password;
	/** 创建时间 */
	@JSONField
	private Date createTime;
	/** 状态(0停用，1正常) */
	private Integer status;
	/** 体检中心编号 */
	private String mecNo;
	/** 账号说明 */
	private String info;
	/** 角色ID */
	private Integer roleId;
	
	// 已筛选人数
	private int screenCount;

	// 未筛选人数
	private int noScreenCount;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getMecNo() {
		return mecNo;
	}

	public void setMecNo(String mecNo) {
		this.mecNo = mecNo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public int getScreenCount() {
		return screenCount;
	}

	public void setScreenCount(int screenCount) {
		this.screenCount = screenCount;
	}

	public int getNoScreenCount() {
		return noScreenCount;
	}

	public void setNoScreenCount(int noScreenCount) {
		this.noScreenCount = noScreenCount;
	}

}