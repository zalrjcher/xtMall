/**
 * 文件名：CookieUtil.java
 * 版本信息：Version 1.0
 * 日期：Apr 9, 2011
 * Copyright 腾云IT科技有限公司 2011 
 * 版权所有
 */
package com.xtuniversity.mall.model.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 类描述：coolie工具类
 * @version: 1.0
 * @author: jianghuati
 * @version: Apr 9, 2011 12:51:25 AM 
 */
public class CookieUtil {

	public CookieUtil() {
	}

	public static String getString(HttpServletRequest request, String key) {
		String value = null;
		Cookie cookie = null;
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (!cookies[i].getName().equals(key))
					continue;
				cookie = cookies[i];
				break;
			}

		}
		if (cookie != null)
			value = cookie.getValue();
		if (value == null)
			value = "";
		return value;
	}

	public static void setCookie(HttpServletResponse response, String name,
                                 String value, int seconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(seconds);
		response.addCookie(cookie);
	}
}
