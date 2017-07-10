package com.dryork.vision.base.util;

/**
 * 项目中所用到的常量
 * 
 * @author leoliang
 *
 */
public class ConstantsPond {

	//============================== Cloud server 相关配置=================================
	/**
	 * 服务器地址
	 */
	//public static final String SERVER_PATH = "http://123.57.149.211:8080/shikang/";
	public static final String SERVER_PATH = PropertiesUtil.getProp("config/config.properties", "SERVER_PATH");
	/**
	 * dicom server url
	 */
	//public static final String DCM_SERVER_URL = "192.168.177.136";
	public static final String DCM_SERVER_URL = PropertiesUtil.getProp("config/config.properties", "DCM_SERVER_URL");
	
	/**
	 * dicom server port
	 */
	//public static final int DCM_SERVER_PORT = 10400;
	public static final int DCM_SERVER_PORT = Integer.parseInt(PropertiesUtil.getProp("config/config.properties", "DCM_SERVER_PORT"));
	
	/**
	 * dicom file url
	 */
	//public static final String DEFAULT_STORAGE_DIRECTORY = "/opt/dcm/";
	public static final String DEFAULT_STORAGE_DIRECTORY = PropertiesUtil.getProp("config/config.properties", "DEFAULT_STORAGE_DIRECTORY");
	
	/**
	 * er wei ma file url
	 */
	public static final String PATIENT_QRCODE = "";
	
	/**
	 * mecid
	 */
	public static final String mecId = PropertiesUtil.getProp("config/config.properties", "MEC_ID");
}
