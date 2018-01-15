package com.jst.invoice.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class PasswordUtil {

	/**
	 * @param args
	 */
	
	//生成10位随机数
	private static String getRandom(){
		int a []= new int[10];
		String result = "";
		for(int i = 0;i<10;i++){
			a[i]= (int) (Math.random()*10); 
			result = result + a[i];
			//System.out.println(a[i]);
		}
		return result;
	}
	
	//密码生成规则:10位随机数+Base64({（10位随机数+注册码）MD5})
	/**
	 * @param regCode注册码
	 * @return
	 */
	public static String getPassword(String regCode){
		String sRandom = getRandom();
		//return sRandom+getBASE64(MD5(sRandom+regCode));
		
		 MessageDigest md5 = null;
	     try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update((sRandom+regCode).getBytes());			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	     return sRandom+Base64.encode(md5.digest());
		
	}
	
	/**
	 * 生成新的加密后的数据
	 * @param sRandom
	 * @param regCode
	 * @return
	 */
	public static String getPassword(String sRandom, String regCode){
		
		 MessageDigest md5 = null;
	     try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update((sRandom+regCode).getBytes());			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return sRandom+Base64.encode(md5.digest());
		
	}
	
	/**
	 * 判断密码是否正确
	 * @param psw
	 * @param regCode
	 * @return
	 * @throws Exception
	 */
	public static boolean isPermit(String psw, String regCode) throws Exception{
		String sRandom = psw.substring(0, 10);
		String sa = getPassword(sRandom, regCode);
		System.out.println(sa);
		if (sa.equals(psw)) {
			return true;
		}
		return false;
	}
	
		
	public static void main(String[] args) throws Exception {
	/*	String code = "29829888";
		String ss = getPassword(code);
		System.out.println(ss);
		System.out.println(ss.substring(10));
		System.out.println(ss.substring(0, 10));*/
//		boolean iss=isPermit("572718588897hh94dLi2tElQENlI22aQ==", "123456");
//		System.out.println(iss);
		System.out.println(isPermit("339395931723JaRmxmwWRMvt9HNE/UOw==", "26372417"));
		System.out.println(getPassword("26372417"));
	}

}
