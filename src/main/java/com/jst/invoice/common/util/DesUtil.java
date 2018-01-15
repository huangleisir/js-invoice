package com.jst.invoice.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by asus on 2016/9/11.
 */
public class DesUtil {

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static String encryptBasedDes(String data, String keyStr) {
		keyStr = StringUtils.leftPad(keyStr, 16, "0");
		byte[] DES_KEY = ByteUtil.hexStr2Byte(keyStr);
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = ByteUtil.hexToStr(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static String decryptBasedDes(String cryptData, String keyStr) {
		keyStr = StringUtils.leftPad(keyStr, 16, "0");
		byte[] DES_KEY = ByteUtil.hexStr2Byte(keyStr);
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(ByteUtil
					.hexStr2Byte(cryptData)), "utf-8");
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str = "123456789测试ABC";

		// DES数据加密
		long time = System.currentTimeMillis();
		System.out.println("加密前：" + str);
		String s1 = encryptBasedDes(str, "1122334411223344");
		System.out.println("time1:" + (System.currentTimeMillis() - time));

		System.out.println("加密：" + s1);

		// DES数据解密
		long time2 = System.currentTimeMillis();
		String s2 = decryptBasedDes(s1, "1122334411223344");
		System.out.println("time2:" + (System.currentTimeMillis() - time2));

		System.err.println("解密" + s2);
	}

}
