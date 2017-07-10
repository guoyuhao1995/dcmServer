package com.dryork.vision.manage.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.tool.common.DicomFiles;

import com.dryork.vision.base.util.ObjectUtil;
import com.dryork.vision.base.util.dcm.DisplayTag;


public class test {
	public static void main(String[] args) {
		try {
			File file = new File("/home/guoyh/yueke/gyh1.dcm");
			DisplayTag d = new DisplayTag(file);
			@SuppressWarnings("static-access")
			Attributes attr1 = d.loadDicomObject(file);
			byte[] bytename = attr1.getBytes(Tag.PatientName);
			System.out.println(new String(bytename,"gb18030"));
			byte[] bytesex = attr1.getBytes(Tag.PatientSex);
			System.out.println(new String(bytesex,"gb18030"));
			//String patientSex = "男";
			//attr1.setBytes(Tag.PatientSex, VR.CS, patientSex.getBytes("GB18030"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
	
