package com.xtuniversity.mall.model.base.util;

import java.util.List;

/**
 * 业务处理结果集
 * 
 * @author sky
 *
 */
public class ResultPage<T extends Object> {

	/** 返回值 */
	private int code;

	/** 返回的对象集合 */
	private List<T> data;
	/** 返回的错误信息 */
	private String msg;
	/** 返回的总条数 */
	private int count;

	/** 构造器私有化 */
	public ResultPage() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
