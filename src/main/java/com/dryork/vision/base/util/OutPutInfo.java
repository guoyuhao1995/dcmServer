package com.dryork.vision.base.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;

/**
 * 系统输出信息
 * 
 * @author Andy
 * @Date 2016/4/28
 *
 */
public class OutPutInfo {

	/**
	 * 信息系统状态 成功
	 */
	public static final int STATUS_SUCCESS = 1;

	/**
	 * 信息系统状态 失败
	 */
	public static final int STATUS_ERROR = 0;

	/**
	 * 系统信息返回 提示 成功
	 */
	public static final String MESSAGE_SUCCESS = "成功";

	/**
	 * 系统信息返回 提示 失败
	 */
	public static final String MESSAGE_ERROR = "失败";

	/**
	 * 系统信息返回提示 参数为空
	 */
	public static final String MESSAGE_PARAM_ERROR = "参数错误";

	/**
	 * JOSN数组
	 */
	public static final JSONArray array = new JSONArray();

	/**
	 * JOSN对象
	 */
	public static final JSONObject json = new JSONObject();

	/**
	 * 返回JOSN对象信息
	 * 
	 * @param msg
	 * @param code
	 * @return
	 */
	public static JSONObject jsonObject(String msg, int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json;
	}

	/**
	 * JSON
	 * 返回JSON数组格式为{"code:":1,"msg":"成功","data":[{"name":"jack","sex":"男"},{"name":"Jim","sex":"女"}]
	 * }
	 * 
	 * @param msg
	 * @param code
	 * @param obj
	 * @return
	 */
	public static JSONObject jsonObject(String msg, int code, Object obj) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", obj);
		return json;
	}

	/**
	 * 返回JOSN字符串提示
	 * 
	 * @param msg
	 * @param code
	 * @return
	 */
	public static String jsonToString(String msg, int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return JSONUtils.toJSONString(json);
	}

	/**
	 * JOSN 返回JSON数组格式[{"code:":1,"msg":"成功","data":{"name":"jack","sex":"男"}}]
	 * 
	 * @param msg
	 * @param code
	 * @param obj
	 * @return
	 */
	public static JSONArray jsonArray(String msg, int code, JSONObject obj) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", obj);
		array.add(json);
		return array;
	}

	public static JSONObject jsonObjectPage(String msg, int code, Object obj, int totalPage, int totalRecord) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("data", obj);
		json.put("totalPage", totalPage);
		json.put("totalRecord", totalRecord);
		return json;
	}

	/**
	 * app 客户端json格式
	 * 
	 * @param list
	 * @param hasMore
	 * @param statusCode
	 * @param statusDesc
	 * @return
	 */
	public static String getJson(Object list, Integer hasMore, Integer statusCode, String statusDesc) {
		Map<String, Object> remap = new HashMap<String, Object>();
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("code", statusCode);
		statusMap.put("desc", statusDesc);
		remap.put("returndata", list);
		remap.put("hasMore", hasMore);
		remap.put("status", statusMap);
		return JSONUtils.toJSONString(remap);
	}

}
