package com.dryork.vision.manage.po;

import java.util.List;

import com.dryork.vision.manage.common.DBRecord;

/** (SYS_RESOURCE) **/
public class Resource extends DBRecord {

	private static final long serialVersionUID = 1L;

	/** 资源id (Not Null) */
	private Integer id;
	/** 资源名称 */
	private String name;
	/** 地址 */
	private String url;
	/** 父节点 */
	private Integer pid;
	/** 状态(0禁用，1使用) */
	private Integer status;
	/** 图标 */
	private String icon;

	List<Resource> menus;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Resource> getMenus() {
		return menus;
	}

	public void setMenus(List<Resource> menus) {
		this.menus = menus;
	}

}