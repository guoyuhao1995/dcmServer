package com.dryork.vision.base.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static String jsonResultCode = "jsonResultCode";
	public static String jsonResultDesc = "jsonResultDesc";
	public static String jsonResultData = "jsonResultData";
	public static String pageResultData = "pageResultData";
	public static String jsonResultSize = "jsonResultSize";
	public static String jsonHasMore = "jsonHasMore";

	public static String clientInfo = "clientInfo";
	
	public static String listPatient = "listPatient";
	
	public static String listDoctor = "listDoctor";

	// 管理系统分页，每页显示的数目
	public static int pageSize = 10;

	// 返回状态码对应表
	public static final Map<Integer, String> codeMap = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			// 系统级错误
			put(0, "请求成功");
			put(-1, "系统错误");
			put(-2, "参数不正确");
			put(-3, "旧密码错误");
			put(-4, "云端无数据");
			put(-5, "目标有欠款");
			put(-6, "该条目已申请退费，正在等待集团审核");
			put(-7, "用户名或密码错误");
			put(-8, "无此卡号");
			put(-9, "不可转给自己");
			put(-10, "您的处置项不符合当前活动");
			put(-20, "网络错误");
			put(-100, "权限验证失败，已经登出");
			put(-110, "不能重复提交！");
		}
	};

}
