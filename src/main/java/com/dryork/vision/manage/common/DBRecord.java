package com.dryork.vision.manage.common;

import java.io.Serializable;
import java.util.HashMap;

public class DBRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sqlStr;

	private String sqlSort;

	private Long limitStart;

	private Integer limitCount;

	private HashMap<String, String> paramExt = null;

	public String getSqlStr() {
		return sqlStr;
	}

	/**
	 * 自定义sql
	 */
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlSort() {
		return sqlSort;
	}

	/**
	 * 排序
	 */
	public void setSqlSort(String sqlSort) {
		this.sqlSort = sqlSort;
	}

	/**
	 * 开始页(pageNo*pageSize*1L)
	 */
	public Long getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Long limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * pageSize
	 */
	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public HashMap<String, String> getParamExt() {
		return paramExt;
	}

	public void setParamExt(HashMap<String, String> paramExt) {
		this.paramExt = paramExt;
	}
}
