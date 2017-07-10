package com.dryork.vision.manage.po;

import java.util.HashMap;

public class DBRecord {
	private static final long serialVersionUID = 1L;

	private String sqlStr;
	
	private String sqlSort;
	
	private Long limitStart;
	
	private Integer limitCount;
	
	private HashMap<String,String> paramExt = new HashMap<String,String>();

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlSort() {
		return sqlSort;
	}

	public void setSqlSort(String sqlSort) {
		this.sqlSort = sqlSort;
	}

	public Long getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Long limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public HashMap<String, String> getParamExt() {
		return paramExt;
	}

	public void setParamExtKV(String key, String value) {
		this.paramExt.put(key, value);
	}
}
