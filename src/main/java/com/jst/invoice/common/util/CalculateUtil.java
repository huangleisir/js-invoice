/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2016-2020 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.jst.invoice.common.util;

import java.math.BigDecimal;

/** 
 * 
 * @Package: com.jst.invoice.common.util  
 * @ClassName: CalculateUtil 
 * @Description: 计算操作工具类 
 *
 * @date: 2017年10月30日 下午6:38:58 
 * @version V1.0 
 

 */
public class CalculateUtil {

	   public  static double add(double v1,double v2){  
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	        return b1.add(b2).doubleValue();  
	    }  
	// 进行减法运算
	    public static double subtract(double v1,double v2){
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
	        return b1.subtract(b2).doubleValue();  
	    }
	// 进行加法运算
	    public static double mul(double d1, double d2){        // 进行乘法运算
	         BigDecimal b1 = new BigDecimal(d1);
	         BigDecimal b2 = new BigDecimal(d2);
	        return b1.multiply(b2).doubleValue();
	     }
	// 进行除法运算
	    public static  double div(double d1,double d2,int len) {// 进行除法运算
	         BigDecimal b1 = new BigDecimal(d1);
	         BigDecimal b2 = new BigDecimal(d2);
	        return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
	     }
	// 进行四舍五入操作
	    public  static double round(double d,int len) {     // 进行四舍五入操作
	         BigDecimal b1 = new BigDecimal(d);
	         BigDecimal b2 = new BigDecimal(1);
	        // 任何一个数字除以1都是原数字
	        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
	        return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
	     }
}
