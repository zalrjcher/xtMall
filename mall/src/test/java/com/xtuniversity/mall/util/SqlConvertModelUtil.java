package com.xtuniversity.mall.util;

public class SqlConvertModelUtil {
	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 替换字符串并让它的下一个字母为大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}

	public static String sqlTypeConvertModelType(String sqlType) {
		String modelType = null;
		switch (sqlType) {
		case "DOUBLE":
			modelType = "Double";
			break;
		case "BIGINT":
			modelType = "Long";
			break;
		case "INT":
			modelType = "Integer";
			break;
		case "VARCHAR":
			modelType = "String";
			break;
		case "TEXT":
			modelType = "String";
			break;
		case "DATETIME":
			modelType = "Date";
			break;
		case "DATE":
			modelType = "Date";
			break;
		case "FLOAT":
			modelType = "Float";
			break;
		case "LONGBLOB":
			modelType = "String";
			break;
		case "BLOB":
			modelType = "String";
			break;
		case "BIT":
			modelType = "Boolean";
			break;
		default:
			break;
		}
		return modelType;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(replaceUnderlineAndfirstToUpper("nihaoabc", "_", ""));
	}
}