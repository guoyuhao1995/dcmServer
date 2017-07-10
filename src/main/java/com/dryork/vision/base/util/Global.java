package com.dryork.vision.base.util;

public class Global {

	String appUrl = PropertiesUtil.getProp("config/device-info.properties", "app_url");
	String macMacUri = PropertiesUtil.getProp("config/device-info.properties", "all_mac_uri");
	String url = appUrl + macMacUri;

	/**
	 * OSS配置
	 */
	public static String ENDPOINT = PropertiesUtil.getProp("config/upload.properties", "ENDPOINT");

	public static String BUCKETNAME = PropertiesUtil.getProp("config/upload.properties", "BUCKETNAME");

	public static String ACCESS_KEYID = PropertiesUtil.getProp("config/upload.properties", "ACCESS_KEYID");

	public static String ACCESS_KEYSECRET = PropertiesUtil.getProp("config/upload.properties", "ACCESS_KEYSECRET");

	/**
	 * 软件上传地址
	 */
	public static String SOFT_PATH = PropertiesUtil.getProp("config/upload.properties", "SOFT_PATH");

	/**
	 * 插件上传地址
	 */
	public static String PLUGIN_PATH = PropertiesUtil.getProp("config/upload.properties", "PLUGIN_PATH");
}
