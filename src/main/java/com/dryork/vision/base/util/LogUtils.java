/**
 *Copyright: Copyright (c) 2015
 */
package com.dryork.vision.base.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author wenghongbo
 *
 * 2015年8月24日下午8:29:17
 */
public class LogUtils {

	/**控制台日志*/
	public static Logger CONSOLE = LogManager.getLogger();
	/**info日志*/
	public static Logger INFO = LogManager.getLogger("info");
	/**error日志*/
	public static Logger ERROR = LogManager.getLogger("error");
	/**debug日志*/
	public static Logger DEBUG = LogManager.getLogger("debug");
	/**记录request请求信息日志*/
	public static Logger REQUEST_TIME=LogManager.getLogger("request.time");
	/**证书下载记录日志*/
	public static Logger AUTHUPLOAD_RECORD=LogManager.getLogger("authUpload.record");
	
}
