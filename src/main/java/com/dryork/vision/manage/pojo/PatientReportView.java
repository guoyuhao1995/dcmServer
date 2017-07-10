package com.dryork.vision.manage.pojo;

import java.sql.Date;

import com.dryork.vision.base.util.DateUtils;


public class PatientReportView {
	//筛查日期
	private Date screenTime;
	//筛查数量
	private Integer num;
	//筛查日期
	private String screenTimeStr;
	//上传日期
	private Date uploadTime;
	//待筛查数量
	private Integer dNum;
	//已筛查数量
	private Integer yNum;
	//上传日期str
	private String uploadTimeStr;
	
	public String getUploadTimeStr() {
		String result = "";
		if(uploadTime != null){
			result = DateUtils.formatDate(uploadTime);
		}
		return result;
	}
	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getdNum() {
		return dNum;
	}
	public void setdNum(Integer dNum) {
		this.dNum = dNum;
	}
	public Integer getyNum() {
		return yNum;
	}
	public void setyNum(Integer yNum) {
		this.yNum = yNum;
	}
	public String getScreenTimeStr() {
		String result = "";
		if(screenTime != null){
			result = DateUtils.formatDate(screenTime);
		}
		return result;
	}
	public void setScreenTimeStr(String screenTimeStr) {
		this.screenTimeStr = screenTimeStr;
	}
	public Date getScreenTime() {
		return screenTime;
	}
	public void setScreenTime(Date screenTime) {
		this.screenTime = screenTime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
