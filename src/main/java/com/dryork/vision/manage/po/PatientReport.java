package com.dryork.vision.manage.po;


/** (VISION_PATIENT_REPORT) **/
public class PatientReport{

	/** 患者编号 */
	private String patNo;
	/** 患者姓名 */
	private String name;
	/** 性别 */
	private String sex;
	/** 年龄 */
	private Integer age;
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
	/** 影像逗号分隔 */
	private String image;
	/** 体检中心ID */
	private String mecId;
	/**isLeft 1 left 0 right*/
	private String isLeft;
	
	public String getIsLeft() {
		return isLeft;
	}

	public void setIsLeft(String isLeft) {
		this.isLeft = isLeft;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getPatNo() {
		return patNo;
	}

	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIopLeft() {
		return iopLeft;
	}

	public void setIopLeft(String iopLeft) {
		this.iopLeft = iopLeft;
	}

	public String getIopRight() {
		return iopRight;
	}

	public void setIopRight(String iopRight) {
		this.iopRight = iopRight;
	}

	public String getVisionLeft() {
		return visionLeft;
	}

	public void setVisionLeft(String visionLeft) {
		this.visionLeft = visionLeft;
	}

	public String getVisionRight() {
		return visionRight;
	}

	public void setVisionRight(String visionRight) {
		this.visionRight = visionRight;
	}

	public String getCorrectedLeft() {
		return correctedLeft;
	}

	public void setCorrectedLeft(String correctedLeft) {
		this.correctedLeft = correctedLeft;
	}

	public String getCorrectedRight() {
		return correctedRight;
	}

	public void setCorrectedRight(String correctedRight) {
		this.correctedRight = correctedRight;
	}

	public String getMecId() {
		return mecId;
	}

	public void setMecId(String mecId) {
		this.mecId = mecId;
	}

	public PatientReport() {
		super();
	}

}