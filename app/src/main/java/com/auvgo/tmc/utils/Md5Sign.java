package com.auvgo.tmc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

public class Md5Sign {
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * @Description： sign签名
	 * @param dataJson
	 *            分配的密钥
	 * @param appsecret
	 * @return
	 */
	public static String createSign(String dataJson, String appsecret) {
		String key = MD5Encode(appsecret).toUpperCase();
		String sign = MD5Encode(key + dataJson);
		return sign;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getObjectMap(Map<String, String> parameters, Object beanClass) {
		Field[] declaredFields = beanClass.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			try {
				if (field.get(beanClass) instanceof List) {
					for (Object item : (List) field.get(beanClass)) {
						getObjectMap(parameters, item);
					}
				} else {
					if (null != field.get(beanClass)) {
						parameters.put(field.getName(), field.get(beanClass).toString());
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return parameters;
	}
}
