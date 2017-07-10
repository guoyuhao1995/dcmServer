package com.dryork.vision.manage.po;

import com.dryork.vision.manage.common.DBRecord;

/** (VISION_IMAGE) **/
public class Image extends DBRecord {

	private static final long serialVersionUID = 1L;

	/** 主键 (Not Null) */
	private Long id;
	/** 哪只眼(1左眼 2右眼 3不确定) */
	private Integer which;
	/** 是否正常(1正常, 0不正常) */
	private Integer ifNormal;
	/** C/D */
	private float cd;
	/** 盘沿形式(1可 2上方偏窄 3下方偏窄 4上下方偏窄 5看不清) */
	private Integer plateForm;
	/** A/V值(1尚可 2.1:2 3.1:3 4看不清) */
	private Integer av;
	/** 黄斑区出血(1未见明显 2可见 3看不清) */
	private Integer macularRegionBleeding;
	/** 黄斑区渗出(1未见明显 2可见 3看不清) */
	private Integer macularRegionExudation;
	/** 周边视网膜出血(1未见明显 2可见 3看不清) */
	private Integer retinaRegionBleeding;
	/** 周边视网膜渗出(1未见明显 2可见 3看不清) */
	private Integer retinaRegionExudation;
	/** 其他 */
	private String other;
	
	private String[] others;
	
	private String otherStr;
	//哪只眼
	private String whichStr;
	//cd
	private String cdStr;
	
	//盘沿形态
	private String plateFormStr;
	
	//AV值
	private String avStr;
	
	//黄斑区信息
	private String macularRegionStr;
	
	//周边视网膜信息
	private String retinaRegionStr;
	
	public String getRetinaRegionStr() {
		StringBuffer result = new StringBuffer();
		if(retinaRegionBleeding != null){
			switch (retinaRegionBleeding) {
			case 1:
				result.append("未见明显出血");
				break;
			case 2:
				result.append("可见明显出血");	
				break;
			case 3:
				result.append("看不清是否出血");
				break;
			}
		}
		if(retinaRegionBleeding != null && retinaRegionExudation != null){
			result.append(",");
		}
		if(retinaRegionExudation != null){
			switch (retinaRegionExudation) {
			case 1:
				result.append("未见明显渗出");
				break;
			case 2:
				result.append("可见明显渗出");	
				break;
			case 3:
				result.append("看不清是否渗出");
				break;
			}
		}
		return result.toString();
	}

	public void setRetinaRegionStr(String retinaRegionStr) {
		this.retinaRegionStr = retinaRegionStr;
	}

	public String getMacularRegionStr() {
		StringBuffer result = new StringBuffer();
		if(macularRegionBleeding != null){
			switch (macularRegionBleeding) {
			case 1:
				result.append("未见明显出血");
				break;
			case 2:
				result.append("可见明显出血");	
				break;
			case 3:
				result.append("看不清是否出血");
				break;
			}
		}
		if(macularRegionBleeding != null && macularRegionExudation != null){
			result.append(",");
		}
		if(macularRegionExudation != null){
			switch (macularRegionExudation) {
			case 1:
				result.append("未见明显渗出");
				break;
			case 2:
				result.append("可见明显渗出");	
				break;
			case 3:
				result.append("看不清是否渗出");
				break;
			}
		}
		return result.toString();
	}

	public void setMacularRegionStr(String macularRegionStr) {
		this.macularRegionStr = macularRegionStr;
	}

	public String getAvStr() {
		String result= "";
		if(av != null){
			switch (av) {
			case 1:
				result = "尚可";
				break;
			case 2:
				result = "1:2";		
				break;
			case 3:
				result = "1:3";
				break;
			case 4:
				result = "看不清";
				break;
			}
		}
		return result;
	}

	public void setAvStr(String avStr) {
		this.avStr = avStr;
	}

	public String getPlateFormStr() {
		String result = "";
		if(plateForm != null){
			switch (plateForm) {
			case 1:
				result = "可";
				break;
			case 2:
				result = "上方偏窄";	
				break;
			case 3:
				result = "下方偏窄";
				break;
			case 4:
				result = "上下方偏窄";
				break;
			case 5:
				result = "看不清";
				break;
			}
		}
		return result;
	}

	public void setPlateFormStr(String plateFormStr) {
		this.plateFormStr = plateFormStr;
	}

	public String getCdStr() {
		if(cd == -1){
			return "看不清";
		}else{
			return cd+"";
		}
	}

	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}

	public String getWhichStr() {
		if(which == null){
			return null;
		}else if(which == 1){
			return "左眼";
		}else if(which == 2){
			return "右眼";
		}else{
			return "不确定";
		}
	}

	public void setWhichStr(String whichStr) {
		this.whichStr = whichStr;
	}

	public String getOtherStr() {
		return otherStr;
	}

	public void setOtherStr(String otherStr) {
		this.otherStr = otherStr;
	}

	public String[] getOthers() {
		String[] result = null;
		if(getOther() != null){
			result = getOther().split("-");
		}
		return result;
	}

	public void setOthers(String[] others) {
		this.others = others;
	}

	//图片地址
	private String url;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWhich() {
		return which;
	}

	public void setWhich(Integer which) {
		this.which = which;
	}

	public Integer getIfNormal() {
		return ifNormal;
	}

	public void setIfNormal(Integer ifNormal) {
		this.ifNormal = ifNormal;
	}

	public float getCd() {
		return cd;
	}

	public void setCd(float cd) {
		this.cd = cd;
	}

	public Integer getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(Integer plateForm) {
		this.plateForm = plateForm;
	}

	public Integer getAv() {
		return av;
	}

	public void setAv(Integer av) {
		this.av = av;
	}

	public Integer getMacularRegionBleeding() {
		return macularRegionBleeding;
	}

	public void setMacularRegionBleeding(Integer macularRegionBleeding) {
		this.macularRegionBleeding = macularRegionBleeding;
	}

	public Integer getMacularRegionExudation() {
		return macularRegionExudation;
	}

	public void setMacularRegionExudation(Integer macularRegionExudation) {
		this.macularRegionExudation = macularRegionExudation;
	}

	public Integer getRetinaRegionBleeding() {
		return retinaRegionBleeding;
	}

	public void setRetinaRegionBleeding(Integer retinaRegionBleeding) {
		this.retinaRegionBleeding = retinaRegionBleeding;
	}

	public Integer getRetinaRegionExudation() {
		return retinaRegionExudation;
	}

	public void setRetinaRegionExudation(Integer retinaRegionExudation) {
		this.retinaRegionExudation = retinaRegionExudation;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}