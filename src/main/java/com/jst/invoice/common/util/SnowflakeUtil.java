/*
* Copyright (c) 2015-2018 SHENZHEN TOMTOP SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市通拓科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.common.util;

import java.security.SecureRandom;

import com.jst.invoice.config.ConfigSetting;


/**
 * 
 * 
 * @Package: com.tomtop.framework.common.utils  
 * @ClassName: SnowflakeUtil 
 * @Description: Twitter的分布式自增全局唯一ID生成算法snowflake (Java版)
 * 
 * 概念：分布式系统中，有一些需要使用全局唯一ID的场景，这种时候为了防止ID冲突可以使用36位的UUID，
 * 但是UUID有一些缺点，首先他相对比较长，另外UUID一般是无序的。 有些时候我们希望能使用一种简单一些的ID，
 * 并且希望ID能够按照时间有序生成。所以开发了这样一套全局唯一ID生成服务。
 * 
 * 核心算法实现原理：
 * snowflake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，
 *然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，
 *最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
 *一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
 *snowflake生成的ID整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和workerId作区分），
 *并且效率较高。据说：snowflake每秒能够产生26万个ID。
 * ID 生成规则: ID长达 64 bits
 * 
 * | 41 bits: Timestamp (毫秒) | 3 bits: 区域（机房） | 10 bits: 机器编号 | 10 bits: 序列号 |
 * @author: lixin 
 * @date: 2016年8月24日 下午7:49:47 
 * @version V1.0
 */
public class SnowflakeUtil {
	 // 基准时间
    private long twepoch = 1288834974657L; //Thu, 04 Nov 2010 01:42:54 GMT
    // 区域标志位数
    private final static long regionIdBits = 3L;
    // 机器标识位数
    private final static long workerIdBits = 10L;
    // 序列号识位数
    private final static long sequenceBits = 10L;

    // 区域标志ID最大值
    private final static long maxRegionId = -1L ^ (-1L << regionIdBits);
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 序列号ID最大值
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 机器ID偏左移10位
    private final static long workerIdShift = sequenceBits;
    // 业务ID偏左移20位
    private final static long regionIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移23位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + regionIdBits;

    private static long lastTimestamp = -1L;

    private long sequence = 0L;
    
    /**
     * 第几个机器，数值不可用超过1023，从0开始，最多可以扩到第1024个机器
     */
    private final long workerId;
    
    /**
     * 第几个机房 ，数值不可用超过7，从0开始，最多可以扩到8个机房
     */
    private final long regionId;
    
    
    //system.region.index=0
   // system.worker.index=0
    public static SnowflakeUtil snowflake = new SnowflakeUtil(SystemPropUtil.
    		getIntValue("system.worker.index"),ConfigSetting.getIntProperty("system.region.index"));
	
	static {
		if(snowflake == null){
				snowflake = new SnowflakeUtil(SystemPropUtil.
						getIntValue("system.worker.index"),ConfigSetting.getIntProperty("system.region.index"));
		}
		
	}

    private SnowflakeUtil(long workerId, long regionId) {

        // 如果超出范围就抛出异常
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (regionId > maxRegionId || regionId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }

        this.workerId = workerId;
        this.regionId = regionId;
    }

    private SnowflakeUtil(long workerId) {
        // 如果超出范围就抛出异常
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        this.workerId = workerId;
        this.regionId = 0;
    }

    public static long generate() {
        return snowflake.nextId(false, 0);
    }

    /**
     * 实际产生代码的
     *
     * @param isPadding
     * @param busId
     * @return
     */
    private synchronized long nextId(boolean isPadding, long busId) {

        long timestamp = timeGen();
        long paddingnum = regionId;

        if (isPadding) {
            paddingnum = busId;
        }

        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            //sequence自增，因为sequence只有10bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & sequenceMask;
            //判断是否溢出,也就是每毫秒内超过1024，当为1024时，与sequenceMask相与，sequence就等于0
            if (sequence == 0) {
                //自旋等待到下一毫秒
                timestamp = tailNextMillis(lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加,
            // 为了保证尾数随机性更大一些,最后一位设置一个随机数
            sequence = new SecureRandom().nextInt(10);
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (paddingnum << regionIdShift) | (workerId << workerIdShift) | sequence;
    }

    // 防止产生的时间比之前的时间还要小（由于NTP回拨等问题）,保持增量的趋势.
    private long tailNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    // 获取当前的时间戳
    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    public static void main(String args[]) {
//    	SnowflakeUtil uid = new SnowflakeUtil(1023,7);
    	for (int i = 0; i < 1000; i++) {
    		System.out.println(SnowflakeUtil.generate());
    	}
    }
}
