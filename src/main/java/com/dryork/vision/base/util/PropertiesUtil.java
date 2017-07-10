package com.dryork.vision.base.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties props;

	private synchronized static void initProps(String path) {
		props = new Properties();
		try {
			props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProp(String path, String key) {
		initProps(path);
		return props.getProperty(key);

	}

}
