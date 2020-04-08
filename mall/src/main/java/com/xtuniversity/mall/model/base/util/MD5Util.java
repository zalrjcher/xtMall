/**
 * 
 */
package com.xtuniversity.mall.model.base.util;

import java.security.MessageDigest;
import java.util.TreeMap;

/**
 * @author John
 *
 */
public class MD5Util {

	/**
	 * md5 加密
	 * 
	 * @param object
	 * @return
	 */
	public static String ToMd5(String object) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5.update(object.getBytes());
			return ByteToString(md5.digest());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 字节转字符串
	 * 
	 * @param byteValue
	 * @return
	 */
	private static String ByteToString(byte[] byteValue) {
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteValue.length; i++) {
			if (Integer.toHexString(0xFF & byteValue[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteValue[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteValue[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * 获取签名码
	 * 
	 * @param map
	 * @return
	 */
	public static String makeSign(TreeMap<String, Object> map, String secretKey) {
		StringBuffer signSb = new StringBuffer();
		for (String s : map.keySet()) {
			Object oj = map.get(s);
			if (oj == null) {
				continue;
			}
			signSb.append(oj);
		}
		signSb.append(secretKey);
		
		return ToMd5(signSb.toString());
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	private static String convertMD5(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	/**
	 * md5解密
	 * 
	 * @param inStr
	 *            m5码
	 * @return
	 */
	public static String decryptMd5(String inStr) {
		return convertMD5(convertMD5(inStr));
	}

	/**
	 * md5加密
	 * 
	 * @param inStr
	 *            m5码
	 * @return
	 */
	public static String encryptMd5(String inStr) {
		return convertMD5(inStr);
	}

	// 测试主函数
	public static void main(String args[]) {

		System.out.println(MD5Util.ToMd5("123456").equals(MD5Util.ToMd5("123456")));
		System.out.println(MD5Util.ToMd5("123456789"));
		System.out.println(MD5Util.ToMd5("125"));
	}

}
