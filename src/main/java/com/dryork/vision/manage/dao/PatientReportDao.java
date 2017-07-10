package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.PatientReport;
import com.dryork.vision.manage.pojo.PatientReportView;

/** (VISION_PATIENT_REPORT) **/
public interface PatientReportDao {

	public PatientReport find(Serializable id);

	public List<PatientReport> findAll();

	public void create(PatientReport t);

	public void update(PatientReport t);

	public void delete(Serializable id);

	public void deleteAll();

	public List<PatientReport> selectByRecord(PatientReport record);

	
	public Integer selectCount(PatientReport record);
	
	public List<PatientReportView> selectViewByRecord(PatientReport record);
	
	public List<PatientReportView> selectMecViewByRecord(PatientReport record);
	/** codegen **/
}