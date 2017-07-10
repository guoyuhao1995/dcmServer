package com.dryork.vision.base.util;

/**
 * 项目中HTPP请求调用的远程接口
 * @author leoliang
 *
 */
public class TelnetAPIConfig {
	
	
	/**
	 * 【device manage接口】获取VOD设备MAC地址等信息
	 */
	public static final String D_GETMACINFO="/api/gethostinfo";
	
	/**
	 * 【device manage接口】将商家信息同步到设备管理系统
	 */
	public static final String D_ALTERVENDORINFO="/api/postmerchantinfo";
	
	/**
	 * 【cloud server接口】将商家信息同步到云端server,请求类型post
	 */
	public static final String C_ALTERVENDORINFO="/api/ktv/add.json";
	
	/**
	 * 【cloud server接口】删除商家信息同步到云端server
	 */
	public static final String C_DELVENDORINFO="/api/ktv/del.json";
	
	/**
	 * 【cloud server接口】商家添加VOD设备同步到云端server
	 */
	public static final String C_ADDVODDEVICE="/api/vod/add.json";
	
	/**
	 * 【cloud server接口】删除商家VOD设备同步到云端server
	 */
	public static final String C_DELVODDEVICE="/api/vod/delVod.json";
	
}
