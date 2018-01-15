package com.jst.invoice.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 字符工具类</p>
 * <p>Description: </p>
 */
public class NumberStringUtil {
	
	/**
	 * 左补标志位
	 */
	public static final int LPAN = 1;
	/**
	 * 右补标志位
	 */
	public static final int RPAN = 2;
	
	 final static int BUFFER_SIZE = 4096;
	public NumberStringUtil(){
	}
	
	/**
	 * 
	 * 功能：判断字符串是否为数字
	 */
	public static boolean isNumeric(String str){ 
		if(str == null){
			return false;
		}
		if(str.matches("\\d+(.\\d+){0,1}") && str.length() != 0){
			return true; 
		}else{
			return false;
		}
	}
	

	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Integer parseInt(String str){
		return Integer.parseInt(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	
	/**
	 * 
	 * 功能：把字符串转换成单精度浮点数
	 */
	public static float parseFloat(String str){
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		Float num = Float.parseFloat(NumberStringUtil.isNumeric(str)? str:"0");
		String numStr = decimalformat.format(num);
		return Float.parseFloat(numStr);
	}
	
	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Integer valueOf(String str){
		return Integer.valueOf(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Short valueOfShort(String str){
		return Short.valueOf(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Long valueOfLong(String str){
		return Long.valueOf(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	/**
	 * (HexStr) "123456" --> "563412"
	 * 字符串高低字节转换
	 * @param str
	 * @param nBytes
	 * @return
	 */
	public static String highLowConvert(String str) {
	   int nBytes = str.length();
	   char[] temp = new char[5];
	   StringBuffer tempstr = new StringBuffer();
	   int i;
	   if (nBytes % 2 != 0) {
	     return null;
	   }
	   for (i = (nBytes / 2); i > 0; i -= 1) {
	     str.getChars(i * 2 - 2, i * 2, temp, 0);
	     tempstr = tempstr.append(temp, 0, 2);
	   }
	   return tempstr.toString();
	}
	
	
	/**  
	 * Convert byte[] to hex string.
	 * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	 * @param src byte[] data  
	 * @return hex string  
	 */       
	  
	public static String bytesToHexString(byte[] src){   
		StringBuilder stringBuilder = new StringBuilder("");   
		if (src == null || src.length <= 0) {   
			return "";   
		}   
		for (int i = 0; i < src.length; i++) {   
			int v = src[i] & 0xFF;   
			String hv = Integer.toHexString(v);   
			if (hv.length() < 2) {   
			stringBuilder.append(0);   
			}   
			stringBuilder.append(hv);   
		}   
		return stringBuilder.toString().toUpperCase();   
	}
		 /**
		   * 将int转为低字节在前，高字节在后的byte数组
		   * @param n int
		   * @return byte[]
		   */
		 public static byte[] lowToHigh(int n) {
		   byte[] b = new byte[7];
		   b[0] = (byte) (n & 0xff);
		   b[1] = (byte) (n >> 8 & 0xff);
		   b[2] = (byte) (n >> 16 & 0xff);
		   b[3] = (byte) (n >> 24 & 0xff);
		   return b;
		 } 
	
		 /**
		   * 将int转为高字节在前，低字节在后的byte数组
		   * @param n int
		   * @return byte[]
		   */
		 public static byte[] highToLow(int n) {
		   byte[] b = new byte[7];
		   b[3] = (byte) (n & 0xff);
		   b[2] = (byte) (n >> 8 & 0xff);
		   b[1] = (byte) (n >> 16 & 0xff);
		   b[0] = (byte) (n >> 24 & 0xff);
		   return b;
		 } 
  
	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
		if (hexString == null || hexString.equals("")) {   
			return null;   
		}   
		hexString = hexString.toUpperCase();   
		int length = hexString.length() / 2;   
		char[] hexChars = hexString.toCharArray();   
		byte[] d = new byte[length];   
		for (int i = 0; i < length; i++) {   
			int pos = i * 2;   
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
		}   
		return d;   
	}     
	  
	/**  
	* Convert char to byte  
	* @param c char  
	* @return byte  
	*/  
	private static byte charToByte(char c) {   
		return (byte) "0123456789ABCDEF".indexOf(c);   
	} 
	
	/**
	 * 左补零
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addLeftZero(String str,int length){
		int str_length = str.length();
		for(int i=0; i<(length-str_length); i=i+1){
			str = '0' + str;
		}
		return str;
	}
	
	/**
	 * 右补零
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addRightZero(String str,int length){
		int str_length = str.length();
		for(int i=0; i<(length-str_length); i=i+1){
			str = str + '0';
		}
		return str;
	}
	/**
	 * 右补字符
	 * @param str
	 * @param length
	 * @param lpad
	 * @return
	 */
	public static String addRightChar(String str,int length, String lpad){
		if(str!=null){
			int str_length = str.length();
			for(int i=0; i<(length-str_length); i=i+1){
				str = str + lpad;
			}
		}
		return str;
	}
	/**
	 * 左补字符
	 * @param str
	 * @param length
	 * @param lpad
	 * @return
	 */
	public static String addLeftChar(String str,int length, String lpad){
		if(str!=null){
			int str_length = str.length();
			for(int i=0; i<(length-str_length); i=i+1){
				str = lpad + str;
			}
		}
		return str;
	}
	
	/**
	 * 左补字符,按字节长度计算
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addLeftChar(String str,int length, char c, String encode){
		if(str == null){
			str = "";
		}
		int str_length=0;
		try {
			str_length = str.getBytes(encode).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(int i=0; i<(length-str_length); i=i+1){
			str = c + str;
		}
		return str;
	}
	
	/**
	 * 右补字符,按字节长度计算
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addRightChar(String str,int length, char c, String encode){
		if(str == null){
			str = "";
		}
		int str_length=0;
		try {
			str_length = str.getBytes(encode).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(int i=0; i<(length-str_length); i=i+1){
			str = str + c;
		}
		return str;
	}
	
	/**
	 * 获取特定字节长度的字符串
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getByteLengthStr(String str, int length, String encode){
		if(str == null){
			str = "";
		}
		int str_length=0;
		try {
			str_length = str.getBytes(encode).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(str_length <= length){
			return str;
		}else{
			byte[] byteTmp = new byte[length];
			String strTmp = "";
			try {
				byte[] strByte = str.getBytes(encode);
				
				for(int i=0; i<length; i++){
					byteTmp[i] = strByte[i];
				}
				strTmp = new String(byteTmp, encode);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return strTmp;
		}
	}
	
	/**
	 * 字符串截取补位高级函数
	 * 1.获取特定字节长度的字符串
	 * 2.左右补特定字符
	 * @param str
	 * @param length
	 * @param flag LPAN:左，RPAN:右
	 * @param c
	 * @return
	 */
	public static String getStrAd(String str, int length, int flag, char c, String encode){
		if(flag == LPAN){
			return NumberStringUtil.addLeftChar(NumberStringUtil.getByteLengthStr(str, length, encode), length, c, encode);
		}else{
			return NumberStringUtil.addRightChar(NumberStringUtil.getByteLengthStr(str, length, encode), length, c, encode);
		}
	}
	
	public static String getStringByByteNumber(String dealString, int startIndex, 
			int byteLength, String charset) {
		String result = "";
		try {
			byte[] stringBytes = dealString.getBytes(charset);
			if(stringBytes.length < byteLength) {
				return result;
			}
			byte[] bytes = new byte[byteLength];
			for(int i = 0; i< byteLength; i++) {
				bytes[i] = stringBytes[startIndex + i];
			}
			result =new String(bytes,charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static byte bswap(byte a)
    {
        byte b = 0;
        for(int i = 0; i < 8; ++i)
            b |= ((a & (1 << i)) == 0 ? 0 : 1) << (7-i);
        return b;
    }
	
	/**
	 * 字节数据相加
	 * @param sByte 
	 * @param aByte
	 * @return
	 */
	public static byte[] byteArrayAdd(byte[] sByte, byte[] aByte){
		byte[] tByte = new byte[sByte.length + aByte.length];
		for(int i=0; i<sByte.length; i++){
			tByte[i] = sByte[i];
		}
		for(int i=0; i<aByte.length; i++){
			tByte[sByte.length+i] = aByte[i];
		}
		return tByte;
	}
	
	/**
	 * 在字节数组获取，从第index开始，length长度的字节数
	 * @param byteArray
	 * @param index 从0开始
	 * @param length
	 * @return
	 */
	public static byte[] getBytes(byte[] byteArray, int index, int length){
		byte[] getByteArray = new byte[length];
		
		for(int i=0; i<length; i++){
			getByteArray[i] = byteArray[index + i];
		}
		
		return getByteArray;
	}
	
	/**
	 * 根据每段length长度分隔字符串
	 * @param str
	 * @param length
	 * @return
	 */
	public static List<String> splitString(String str, int length){
		String strHex = "";
		length = length * 2;
		try {
			strHex = bytesToHexString(str.getBytes("UTF-8"));
			str = strHex;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		List<String> stringList = new ArrayList<String>();
		int strLength = str.length();
		int num = strLength / length;
		
		try {
			for(int i=0; i<num; i++){
				String strtmp = str.substring(i*length, (i+1)*length);
				stringList.add(new String(hexStringToBytes(strtmp), "UTF-8"));
			}
			
			if(strLength % length != 0){
				String strtmp = str.substring(num * length);
				stringList.add(new String(hexStringToBytes(strtmp), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return stringList;
	}
	
	/**
	 * 左补字符串
	 * @param str
	 * @param length
	 * @param padStr
	 * @return
	 */
	public static String lpadStr(String str, int length, String padStr){
		if(str == null){
			str = "";
		}
		String lpadStr = str;
		while(true){
			int strLength = lpadStr.length();
			if(strLength >= length){
				break;
			}else{
				lpadStr = padStr + lpadStr;
			}
		}
		return lpadStr;
	}
	
	/**
	 * 右补字符串
	 * @param str
	 * @param length
	 * @param padStr
	 * @return
	 */
	public static String rpadStr(String str, int length, String padStr){
		if(str == null){
			str = "";
		}
		String lpadStr = str;
		while(true){
			int strLength = lpadStr.length();
			if(strLength >= length){
				break;
			}else{
				lpadStr = lpadStr + padStr;
			}
		}
		return lpadStr;
	}
	
	
	public static String hexStringBack(String hexStr){
		StringBuffer sb = new StringBuffer();
		hexStr = hexStr.toUpperCase();
		for(int i=0; i<hexStr.length(); i++){
			//0123456789ABCDEF
			//FEDCBA9876543210
			
			if("0".equals(hexStr.substring(i, i+1))){
				sb.append("F");
			}
			if("1".equals(hexStr.substring(i, i+1))){
				sb.append("E");
			}
			if("2".equals(hexStr.substring(i, i+1))){
				sb.append("D");
			}
			if("3".equals(hexStr.substring(i, i+1))){
				sb.append("C");
			}
			if("4".equals(hexStr.substring(i, i+1))){
				sb.append("B");
			}
			if("5".equals(hexStr.substring(i, i+1))){
				sb.append("A");
			}
			if("6".equals(hexStr.substring(i, i+1))){
				sb.append("9");
			}
			if("7".equals(hexStr.substring(i, i+1))){
				sb.append("8");
			}
			if("8".equals(hexStr.substring(i, i+1))){
				sb.append("7");
			}
			if("9".equals(hexStr.substring(i, i+1))){
				sb.append("6");
			}
			if("A".equals(hexStr.substring(i, i+1))){
				sb.append("5");
			}
			if("B".equals(hexStr.substring(i, i+1))){
				sb.append("4");
			}
			if("C".equals(hexStr.substring(i, i+1))){
				sb.append("3");
			}
			if("D".equals(hexStr.substring(i, i+1))){
				sb.append("2");
			}
			if("E".equals(hexStr.substring(i, i+1))){
				sb.append("1");
			}
			if("F".equals(hexStr.substring(i, i+1))){
				sb.append("0");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 字节转换二进制 
	 * @param byteArray
	 * @return
	 */
	public static String byteToBinary(byte[] byteArray){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<byteArray.length; i++){
			byte b = byteArray[i];
			int z = b;
			z |= 256;
			String str = Integer.toBinaryString(z);
			int len = str.length();
			sb.append(str.substring(len - 8, len));
		}
		return sb.toString();
	}
	
	/**
	 * 二进制转换字节
	 * @param byteArray
	 * @return
	 */
	public static byte[] binaryToByte(String bString){
		byte[] bByte = new byte[0];
		for(int k=0; k<bString.length()/8; k++){
			String bStringTmp = bString.substring(k*8, (k+1)*8);
			byte result = 0;
			for(int i=bStringTmp.length()-1,j=0;i>=0;i--,j++){
				result += (Byte.parseByte(bStringTmp.charAt(i)+"")*Math.pow(2, j));
			}
			
			byte[] aByte = new byte[1];
			aByte[0] = result;
			
			bByte = byteArrayAdd(bByte, aByte);
		}
		
		return bByte;
		
	}
	
	public static String rPadEightZero(String str){ 
		if(str == null){
			return "00000000";
		}
		int strLen = str.length();
		if(strLen % 8 == 0){
			return str;
		}
		int padLen = 8 - strLen % 8;
		String padStr = NumberStringUtil.addRightZero(str, strLen+padLen);
		return padStr;
	}
	
	public static String moveLeftZero(String str){
		if(str == null || str.length() == 0){
			return "";
		}
		int i;
		for(i=0; i<str.length(); i++ ){
			if("0".equals(str.substring(i, i+1))){
				
			}else{
				break;
			}
		}
		return str.substring(i);
	}
	
	/**
	 * 寻找特定字符在字符串中第几次出现，前面的数据字符串
	 * @param str 原始数据
	 * @param findValue 特定字符
	 * @param index 第几次
	 * @return
	 */
	public static String findSpIndex(String str, char findValue, int index){
		char[] chArray = str.toCharArray();
		int num = 0;
		int indexRet = -1;
		for(int i=0; i<chArray.length; i++){
			char ch = chArray[i];
			if(ch == findValue){
				num++;
			}
			if(num == index){
				indexRet = i;
				break;
			}
		}
		return str.substring(0, indexRet);
	}
	  /**
     * 将InputStream转换成byte数组
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException{
         
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
         
        data = null;
        return outStream.toByteArray();
    }
	public static void main(String[] args) {
		String str = "012700000000000001";
		System.out.println("["+NumberStringUtil.findSpIndex(str, ',', 0)+"]");
		
		
	}
}
