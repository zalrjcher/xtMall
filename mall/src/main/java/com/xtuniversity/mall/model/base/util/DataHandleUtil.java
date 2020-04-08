package com.xtuniversity.mall.model.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataHandleUtil {
	public static String getPercentage(int i, int j) {
		float m;
		NumberFormat PercentFormat = NumberFormat.getPercentInstance();
		PercentFormat.setMaximumFractionDigits(2);
		PercentFormat.setMinimumFractionDigits(2);
		if (j == 0) {
			return "0";
		} else {
			m = (float) i / (float) j;
		}
		return PercentFormat.format(m);
	}

	public static String getDecimal(int i, int j) {
		DecimalFormat df = new DecimalFormat("0.00");
		double m = (double) i / (double) j;
		return df.format(m);
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public static String MD5BY32(String key) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(key.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuilder buf = new StringBuilder("");
			for (byte aB : b) {
				i = aB;
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

}
