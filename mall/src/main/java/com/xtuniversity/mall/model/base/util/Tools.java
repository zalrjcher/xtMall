/**    
 * 版本信息：    腾云GAME 
 * Copyright 足下 Corporation 2012     
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.model.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tools {
	private static final Logger log = LoggerFactory.getLogger(Tools.class);
	public static final String DELIMITER_INNER_ITEM = "_";
	public static final String DELIMITER_INNER_ITEM1 = ":";
	public static final String DELIMITER_INNER_ITEM2 = ",";
	public static final String DELIMITER_BETWEEN_ITEMS = "|";
	public static final String DELIMITER_BETWEEN_ITEMS2 = "#";
	public static final String ARGS_DELIMITER = " ";
	public static final String ARGS_ITEMS_DELIMITER = "\\|";
	private static final Random RANDOM = new Random(33081953939456L);

	static Logger logger = LoggerFactory.getLogger(Tools.class);

	public static byte[] object2ByteArray(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			new ObjectOutputStream(bos).writeObject(obj);

			return bos.toByteArray();
		} catch (IOException ex) {
			log.error("failed to serialize obj", ex);
		}
		return null;
	}

	public static Object byteArray2Object(byte[] buffer) {
		if ((buffer == null) || (buffer.length == 0)) {
			return null;
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			Object localObject2 = ois.readObject();
			return localObject2;
		} catch (Exception ex) {
			log.error("failed to deserialize obj", ex);
			return null;
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (Exception localException5) {
			}
			try {
				bais.close();
			} catch (Exception localException6) {
			}
		}
	}

	public static int getRandomInteger(int maxValue) {
		int value = 0;
		if (maxValue > 0) {
			value = RANDOM.nextInt(maxValue);
		}
		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String[]> delimiterString2Array(String delimiterString) {
		if ((delimiterString == null) || (delimiterString.trim().length() == 0)) {
			return null;
		}

		String[] ss = delimiterString.trim().split("\\|");
		if ((ss != null) && (ss.length > 0)) {
			List list = new ArrayList();
			for (int i = 0; i < ss.length; i++) {
				list.add(ss[i].split("_"));
			}

			return list;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String[]> delimiterString2Map(String delimiterString) {
		Map map = new HashMap();
		if ((delimiterString == null) || (delimiterString.trim().length() == 0)) {
			return map;
		}

		String[] ss = delimiterString.trim().split("\\|");
		if ((ss != null) && (ss.length > 0)) {
			for (int i = 0; i < ss.length; i++) {
				String[] str = ss[i].split("_");
				if (str.length > 0) {
					map.put(str[0], str);
				}
			}
			return map;
		}
		return map;
	}

	public static String delimiterCollection2String(Collection<String[]> collection) {
		if ((collection == null) || (collection.isEmpty())) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();
		for (String[] strings : collection) {
			if ((strings == null) || (strings.length == 0)) {
				continue;
			}
			for (int i = 0; i < strings.length; i++)
				if (i == strings.length - 1) {
					subContent.append(strings[i]).append("|");
				} else {
					subContent.append(strings[i]).append("_");
				}
		}
		return subContent.toString();
	}

	public static String array2DelimiterString(String[] subArray) {
		if ((subArray == null) || (subArray.length == 0)) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();

		for (int i = 0; i < subArray.length; i++) {
			subContent.append(subArray[i]).append("_");
		}

		String tmp = subContent.toString().substring(0, subContent.lastIndexOf("_"));

		return tmp + "|";
	}

	public static String listArray2DelimiterString(List<String[]> subArrayList) {
		if ((subArrayList == null) || (subArrayList.isEmpty())) {
			return "";
		}

		StringBuffer subContent = new StringBuffer();
		for (String[] strings : subArrayList) {
			if ((strings == null) || (strings.length == 0)) {
				continue;
			}
			for (int i = 0; i < strings.length; i++)
				if (i == strings.length - 1) {
					subContent.append(strings[i]).append("|");
				} else {
					subContent.append(strings[i]).append("_");
				}
		}
		return subContent.toString();
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 4).doubleValue();
	}

	public static double roundDown(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 1).doubleValue();
	}

	public static double roundUp(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 0).doubleValue();
	}

	public static double divideAndRoundUp(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);

		return bd1.divide(bd2, scale, 0).doubleValue();
	}

	public static double divideAndRoundDown(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);

		return bd1.divide(bd2, scale, 1).doubleValue();
	}

	public static long getQuot(String time1, String time2) {
		long quot = 0L;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000L / 60L / 60L / 24L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static <T> List<T> pageResult(List<T> list, int startIndex, int fetchCount) {
		if ((list != null) && (list.size() > 0)) {
			if (startIndex >= list.size()) {
				return null;
			}
			startIndex = startIndex < 0 ? 0 : startIndex;
			if (fetchCount <= 0) {
				return list.subList(startIndex, list.size());
			}
			int toIndex = Math.min(startIndex + fetchCount, list.size());
			return list.subList(startIndex, toIndex);
		}

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void add2MapList(Map map, Object key, Object value) {
		if ((map != null) && (key != null)) {
			List list = (List) map.get(key);
			if (list == null) {
				list = new ArrayList();
				map.put(key, list);
			}
			list.add(value);
		}
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		long end = System.currentTimeMillis();
		System.err.println("SecureRandom: " + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			System.err.println(RANDOM.nextInt(1000));
		}
		end = System.currentTimeMillis();
		System.err.println("RANDOM:" + (end - start));
	}

	/**
	 * 排序
	 * 
	 * @param <T>
	 * 
	 * @param list
	 *            排序的集合
	 * @param orderMap
	 *            排序的字段--排序的方式
	 */
	public <T> void Sort(List<T> list, final LinkedHashMap<String, String> orderMap) {
		Collections.sort(list, new Comparator<T>() {
			public int compare(T a, T b) {
				int ret = 0;
				try {
					for (String method : orderMap.keySet()) {
						String sort = orderMap.get(method);

						String newMethod = "get" + method.substring(0, 1).toUpperCase() + method.substring(1);
						Method m1 = a.getClass().getMethod(newMethod);
						Method m2 = b.getClass().getMethod(newMethod);
						if (sort != null && "desc".equals(sort))
						// 倒序
						{
							ret = m2.invoke((b)).toString().compareTo(m1.invoke((a)).toString());
						} else
						// 正序
						{
							ret = m1.invoke((a)).toString().compareTo(m2.invoke((b)).toString());
						}
						if (ret != 0) {
							break;
						}
					}
				} catch (Exception it) {
					System.out.println(it);
				}
				return ret;
			}
		});
	}
}
