package com.jst.invoice.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jst.invoice.dao.base.BaseDao;



/**
 * 
 * 
 * @Package: 
 * @ClassName: DruidDataSourceConfig 
 * @Description: Druid的DataResource配置类
 *
 * @author: lixin 
 * @date: 2016年12月14日 下午9:15:45 
 * @version V1.0
 */

@Configuration  
public class DaoConfig  {

	@Resource
	SqlSessionFactory sqlSessionFactory ;
	@Resource
	SqlSessionFactory sqlSessionFactory1 ;
    
    @Bean(name = "baseDao")
    public BaseDao baseDao()  throws Exception {
    	BaseDao baseDao = new BaseDao() ;
    	baseDao.setSqlSessionFactory(sqlSessionFactory);
    	return baseDao ;
    }
    
    @Bean(name = "baseDao1")
    public BaseDao baseDao1()  throws Exception {
    	BaseDao baseDao = new BaseDao() ;
    	baseDao.setSqlSessionFactory(sqlSessionFactory1);
    	return baseDao ;
    }


}
