package com.xtuniversity.mall.model.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private Map<String, String> parameters = new HashMap<String, String>();

	public Parameter(HttpServletRequest request) {
		Enumeration<String> enm = request.getParameterNames();
		while (enm.hasMoreElements()) {
			String key = enm.nextElement();
			parameters.put(key, request.getParameter(key));
		}
	}

	public void put(String key, String value) {
		this.parameters.put(key, value);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}


}
