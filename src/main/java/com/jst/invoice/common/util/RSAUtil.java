/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.common.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {
	
	
	private final String publickey = "";
	private final String privateKey = "";

	private PrivateKey localPrivKey;
	private PublicKey  localPubKey;
	
	private String  exponent = "10001";
	
	public RSAUtil() {

	}
	
	public RSAPublicKey getPublicKey(String publicKeyStr,String exponent) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			BigInteger n = new BigInteger(publicKeyStr, 16);
			BigInteger e = new BigInteger(exponent, 16);   //"10001"  "65537"
			RSAPublicKeySpec keySpec1 = new RSAPublicKeySpec(n, e);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory
					.generatePublic(keySpec1);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	
	public RSAPublicKey getPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64AndZip.decode(publicKeyStr.toCharArray());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory
					.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public RSAPrivateKey getPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64AndZip.decode(privateKeyStr.toCharArray());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		}catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
	
	public void initPublic(String publickey1) {
		if(!publickey1.isEmpty()) {
			try {
				localPubKey = getPublicKey(publickey1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initPublic(String publickey1,String exponent) {
		if(!publickey1.isEmpty()) {
			try {
				localPubKey = getPublicKey(publickey1,exponent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initPublicKey(String publickey1) {
		if(!publickey1.isEmpty()) {
			try {
				localPubKey = getPublicKey(publickey1,exponent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initPrivate(String privateKey1) {
		if(!privateKey1.isEmpty()) {
			try {
				localPrivKey = getPrivateKey(privateKey1) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * RSA签名
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] signRSA(byte[] plainBytes, boolean useBase64Code,
			String charset) throws Exception {
		String SIGNATURE_ALGORITHM = "SHA1withRSA";
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(localPrivKey);
		signature.update(plainBytes);

		// 如果是Base64编码的话，需要对签名后的数组以Base64编码
		if (useBase64Code) {
			return new String(Base64AndZip.encode(signature.sign()))
					.getBytes(charset);
		} else {
			return signature.sign();
		}
	}

	/**
	 * 验签操作
	 * @param plainBytes
	 *            需要验签的信息
	 * @param signBytes
	 *            签名信息
	 * @return boolean
	 */
	public boolean verifyRSA(byte[] plainBytes, byte[] signBytes,
			boolean useBase64Code, String charset)  {
		boolean isValid = false;
		try {
			String SIGNATURE_ALGORITHM = "SHA1withRSA";
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(localPubKey);
			signature.update(plainBytes);

			// 如果是Base64编码的话，需要对验签的数组以Base64解码
			if (useBase64Code) {
				isValid = signature.verify(Base64AndZip.decode(new String(
						signBytes, charset).toCharArray()));
			} else {
				isValid = signature.verify(signBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValid;
	}
	
	

	public static void main(String []args) throws Exception {
		RSAUtil r = new RSAUtil() ;
		RSAUtil wer = new RSAUtil() ;
		//r.initKey();
		String s = "{\"txncode\":\"charge\",\"cardno\":\"2253123456781234\"}" ;
//		s = "{\"appid\": \"89966566548\",\"appkey\": \"sizmdekc234km2kgh23ddh42do32fd\",\"txntime\": \"201701091513\",\"seqid\": \"2017010915130031234\",\"userid\": \"23432\",\"carno\": \"B12312\",\"messagetype\": \"302\",\"message\": \"您爱车XXXXX已进入停车场\",\"url\": \"baidu.com\",\"showtype\": \"01\",\"pushtime\": \"201701091513\",\"sign\": \"acljdaowejklrjwlkerjkjhkh\"}" ;
//		s = "{\"appid\": \"89966566548\",\"appkey\": \"sizmdekc234km2kgh23ddh42do32fd\",\"txntime\": \"201701091513\",\"seqid\": \"2017010915130031234\",\"userid\": \"23432\",\"carno\": \"B12312\",\"messagetype\": \"302\",\"message\": \"您爱车XXXXX已进入停车场\",\"url\": \"baidu.com\",\"showtype\": \"01\",\"pushtime\": \"201701091513\"}" ;
    	
		
		String sign = "8501C57883A26FFDA679403816E31BC3B455C5E04BDE47711BAF50A926151BEB0BE895E2563C1BE205D5E2054A1321FFF5A09D39677A4EE7A856AB1FD74F0A38641A868C5C7A781F89D2496D978DDF6B63E44110C52D58E5D1106271A75083A4E948D19B7BB9BE0040D85536FDAC1649015C2049586590CF878DFE110507E87A" ;
		String wesign = "2CE7830F5E388FC2E175F6552788168BAF8A3374FED00BA1712FF8F6749B09014ADE9435A1BCD41A69975F5098D9C70040E72B03935F897119464F388C46BE3325DF2ECF3B5CCA352A41CFE7851811BE2F757D1C74AFB2FE3479C414C67A4AF71E0F6D84E9F1CA83DF45ED994F9E19ECD93266F1DC5F7F633C1BB77F81F6C349" ;
		
		String wepub = "931adc20e6a57e308c5517fc564e3a9078ab959e5bb3c62d039eb429b309333cac6525a721df793295a04028ee2ac43e9e727fae277fab5ee24ce4a2f64f47bdfdb6524ead9e5001e26c6bcf705a149958146c89205b28189754a6938185e95fd93751603b292f0cb716911a063b8106c29d7d31bce46c1c9333d0b6c3010eb7" ;
		String pub = "8B6D8CAE9C9494FC1AE3C90E5869111447FA19F66F62D904787C973862D08B0056CA891ECA0CA7CF5D38407BA7AF8FC9A83624CDC46B9A47B0FEBAD0FC730D80C7C1CA1088731D758C6D26A3A2DF7BD7634EEA107B6D752609A16C9C671758A853135214425C58B0DD25779DFB070B08817F0828C72BEF11A5D7F998D0F81D49" ;
		
		
		String wepri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJMa3CDmpX4wjFUX/FZOOpB4q5WeW7PGLQOetCmzCTM8rGUlpyHfeTKVoEAo7irEPp5yf64nf6te4kzkovZPR739tlJOrZ5QAeJsa89wWhSZWBRsiSBbKBiXVKaTgYXpX9k3UWA7KS8MtxaRGgY7gQbCnX0xvORsHJMz0LbDAQ63AgMBAAECgYAsHro3A/ZOyrDMNM1x9TSLcCXGUiCe4qlNlKPkfdYcj+4EyC5Aco4fJdFSbPRBz2oYi67PxPj9pQ+qkEhwJ4cUdlxcDv5UbaiY1lbUYZmtpyt+1vVmd8HdbtbXE8x7xyt0SkybRsNZcyT2qUg73QJIMwn1hylJie4J+rv9MV5HgQJBAOh4w/5FPsQnIaKlVuqMKHgFk48e81Bi/ZLt1x9Fdt6vSEPlWe540wS1NqKzpbea8MxkWHLj+gJGS2uNAb4nlX8CQQCh/keg0BqflebVCEjmunK8PbDdkWK87yGtIxzVm5vB+T9LI9nerhCC+YQOgWZiyjLboElNZcY3hIr54ung9lLJAkAYOUVLAJIqxF0X4pys8g/hectHdZUrAjWkEs6Avq9FQwSHtqVpWQO8ENnxmECjYizHT4l750+M3yBKw7Wzx9InAkBdQ+0xZR8u6jswQ8jVLluIjWwa5O2YIYWeDS04vpJ6p2oNdOKnFkhjYAzr3Qx5rDiDwlDxwrrQk/r/y+kjoC4hAkBdw/Ky7ddAytkj6itU6TNwkCzYTlLFSnbscAzIoV744Zc8Wza79Y3MwBg0qpLIQ27RniwrwMIsHFZ1jcvh5Q07" ;
		String pri = "4782B76DF3372DB6A3E00285CE088D4EF69FAAE33F3BD68CB9734568111E5EBC9CC7224EDD58625533BD9F2D0CD3398F372C15F24774DCDB3A475BEFC61B08682A941F8B170B82944FB3CEBBD12E37190853271B88018F55A30E788CA2EF944BB0DDB3BA7D08426D0825A040AF3D082DDCA238EAC5445D372891AE6AF11D7FB5" ;
        

        r.initPublic(pub, "10001");
//        r.initPrivate(pri);
		wer.initPublic(wepub, "10001");
		wer.initPrivate(wepri);
		
        System.out.println(NumberStringUtil.bytesToHexString(wer.signRSA(s.getBytes(), false, "utf-8")));
		
        System.out.println(r.verifyRSA(s.getBytes(),NumberStringUtil.hexStringToBytes(sign), false, "utf-8"));
        System.out.println("we :"+wer.verifyRSA(s.getBytes(),NumberStringUtil.hexStringToBytes(wesign), false, "utf-8"));
//for(int i=0;i< 100;i++) {
//    		
//    		
//    			long begineTime = System.currentTimeMillis();
//    			
//    			System.out.println(r.verifyRSA(s.getBytes(),NumberStringUtil.hexStringToBytes(sign), false, "utf-8"));
//				
//				System.out.println(" 耗时：   "  + (System.currentTimeMillis() - begineTime));
//			
//    	}
		
	}

}
