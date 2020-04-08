package com.xtuniversity.mall.model.base.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

public class JsonpResult {
	/**
	 * 成功的返回值
	 * 
	 * @param <T>
	 * @return {@link JsonpResult} 返回值对象
	 */
	public static <T extends Object> JSONPObject SUCCESS(String callback, T value) {
		JSONPObject jsonpObject = new JSONPObject(callback);
		JSONObject data = new JSONObject();
		data.put("ret", 0);
		data.put("data", value);
		jsonpObject.addParameter(data);
		System.out.println(jsonpObject.toJSONString());
		return jsonpObject;
	}

	/**
	 * 错误码返回值
	 * 
	 * @param <T>
	 * @return {@link JsonpResult} 返回值对象
	 */
	public static <T extends Object> JSONPObject ERROR(String callback, int ret) {
		JSONPObject jsonpObject = new JSONPObject(callback);
		JSONObject data = new JSONObject();
		data.put("ret", ret);
		jsonpObject.addParameter(data);
		return jsonpObject;
	}
}
