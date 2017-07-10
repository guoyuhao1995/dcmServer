package com.dryork.vision.manage.pojo;

import java.util.Date;
import java.util.List;

import com.dryork.vision.manage.po.Image;

public class PatientReportPojo {
	/** 主键 (Not Null) */
	private Long id;
	/** 患者编号 */
	private String patNo;
	/** 患者姓名 */
	private String name;
	/** 性别 */
	private String sex;
	/** 年龄 */
	private String age;
	/** 眼压（左） */
	private String iopLeft;
	/** 眼压（右） */
	private String iopRight;
	/** 视力（左） */
	private String visionLeft;
	/** 视力（右） */
	private String visionRight;
	/** 矫正视力（左） */
	private String correctedLeft;
	/** 矫正视力（右） */
	private String correctedRight;
	/** 是否转诊 */
	private Integer isTransfer;
	/** 印象(多个逗号分隔) */
	private String impression;
	/** 建议 */
	private String suggest;
	/** 影像逗号分隔 */
	private String image;
	/** 筛查日期 */
	private Date screenTime;
	/** 上传日期 */
	private Date uploadTime;
	/** 二维码 */
	private String url;
	/** 筛查状态(0待筛选 1已筛选) */
	private Integer screenStatus;
	/** 筛查医生ID */
	private Integer doctorId;
	/** 体检中心ID */
	private String mecId;
	/** 患者影像信息*/
	private List<Image> images;
	
	/**印象id*/
	private String[] impressions;
	
	/**印象Str*/
	private String impressionStr;
	
	//建议Str
	private String suggestStr;
	
	public String getSuggestStr() {
		String result = "";
		if(suggest != null){
			switch (Integer.parseInt(suggest)) {
			case 1:
				result = "定期随访,3个月后复查眼底";
				break;
			case 2:
				result = "定期随访,半年复查眼底";
				break;
			case 3:
				result = "定期随访,一年复查眼底";
				break;
			case 4:
				result = "转眼科会诊,明确诊断";
				break;
			}
		}
		return result;
	}

	public void setSuggestStr(String suggestStr) {
		this.suggestStr = suggestStr;
	}

	public String getImpressionStr() {
		return impressionStr;
	}

	public void setImpressionStr(String impressionStr) {
		this.impressionStr = impressionStr;
	}

	/**返回值id  0参数异常 1没有上一条  2没有下一条*/
	private Integer result;
	
	/**返回值描述*/
	private String resultDesc;
	
	/**
	 * 体检中心名称
	 */
	private String mecName;
	
	public String getMecName() {
		return mecName;
	}

	public void setMecName(String mecName) {
		this.mecName = mecName;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	//平台带审查所有数量
	private Long num;
	
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String[] getImpressions() {
		String[] result = null;
		if(impression != null){
			result = impression.split("-");
		}
		return result;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatNo() {
		return patNo;
	}

	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}

	public String getName() {
		String result = "未输入";
		if(name != null || !"".equals(name)){
			result = name;
		}
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		String result = "未输入";
		if("0".equals(sex)){
			result = "女";
		}else if("1".equals(sex)){
			result = "男";
		}
		return result;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		String result = "未输入";
		if(age != null && !"".equals(age)){
			result = age;
		}
		return result;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIopLeft() {
		String result = "未输入";
		if(iopLeft != null && !"".equals(iopLeft)){
			result = iopLeft;
		}
		return result;
	}

	public void setIopLeft(String iopLeft) {
		this.iopLeft = iopLeft;
	}

	public String getIopRight() {
		String result = "未输入";
		if(iopRight != null && !"".equals(iopRight)){
			result = iopRight;
		}
		return result;
	}

	public void setIopRight(String iopRight) {
		this.iopRight = iopRight;
	}

	public String getVisionLeft() {
		String result = "未输入";
		if(visionLeft != null && !"".equals(visionLeft)){
			result = visionLeft;
		}
		return result;
	}

	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}

	public String getVisionRight() {
		String result = "未输入";
		if(visionRight != null && !"".equals(visionRight)){
			result = visionRight;
		}
		return result;
	}

	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}

	public String getCorrectedLeft() {
		String result = "未输入";
		if(correctedLeft != null && !"".equals(correctedLeft)){
			result = correctedLeft;
		}
		return result;
	}

	public void setCorrectedLeft(String correctedLeft) {
		this.correctedLeft = correctedLeft;
	}

	public String getCorrectedRight() {
		String result = "未输入";
		if(correctedRight != null && !"".equals(correctedRight)){
			result = correctedRight;
		}
		return result;
	}

	public void setCorrectedRight(String correctedRight) {
		this.correctedRight = correctedRight;
	}

	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getScreenTime() {
		return screenTime;
	}

	public void setScreenTime(Date screenTime) {
		this.screenTime = screenTime;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getScreenStatus() {
		return screenStatus;
	}

	public void setScreenStatus(Integer screenStatus) {
		this.screenStatus = screenStatus;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getMecId() {
		return mecId;
	}

	public void setMecId(String mecId) {
		this.mecId = mecId;
	}

}
