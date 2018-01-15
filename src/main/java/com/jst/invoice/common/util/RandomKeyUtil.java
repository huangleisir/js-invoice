/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.common.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 
 * 
 * @Package: com.tomtop.framework.common.utils  
 * @ClassName: RandomKeyUtil 
 * @Description: 生成随机字符串，采用CSPRNG算法实现，可用来做加盐哈西的随机盐生成，
 * 也可以用来生成随机token,第一次生成预热较慢，但从第二次生成之后很快，基本消耗为0到1ms
 *
 * @author: lixin 
 * @date: 2016年8月30日 上午9:36:06 
 * @version V1.0
 */
public class RandomKeyUtil {
	
	/**
	 * 默认随机字符串缺省长度
	 */
	public static final int RANDOM_KEY_BYTE_SIZE = 32;
	
	/**
	 * 生成缺省长度的随机字符串（32位）
	 * @return
	 */
	public static String generate() {
   	 SecureRandom random = new SecureRandom();
        byte[] salt = new byte[RANDOM_KEY_BYTE_SIZE];
        random.nextBytes(salt);
        return toHex(salt);
   }
	
	/**
	 * 生成size长度的随机字符串
	 * @param size  字符串长度
	 * @return
	 */
	public static String generate(int  size) {
	   	 SecureRandom random = new SecureRandom();
	        byte[] salt = new byte[size];
	        random.nextBytes(salt);
	        return toHex(salt);
	}
	
	/**
     * 对字节数组转换为16进制字符串
     *
     * @param   array       要转换的字节数组
     * @return              a length*2 character string encoding the byte array
     */
   private static String toHex(byte[] array)
   {
       BigInteger bi = new BigInteger(1, array);
       String hex = bi.toString(16);
       int paddingLength = (array.length * 2) - hex.length();
       if(paddingLength > 0)
           return String.format("%0" + paddingLength + "d", 0) + hex;
       else
           return hex;
   }
   
   
   public static void main(String args[]) {
	   
	   for (int i = 0 ; i < 1000; i ++) {
		   
		   long begineTime = System.currentTimeMillis();
		   
		   RandomKeyUtil.generate();
		   
		   System.out.println(" 耗时：   "  + (System.currentTimeMillis() - begineTime));
	   }
	   
	   
	   
   }
   
}
