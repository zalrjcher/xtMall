/**    
 * 文件名：MemberDaoImpl.java    
 *    
 * 版本信息：    腾云IT
 * 日期：Dec 12, 2012    
 * Copyright 足下 Corporation 2012     
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;

import com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.dao.I${mop?cap_first}Dao;
import com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first};
import com.xtuniversity.mall.${parentCatalogue}.base.dao.impl.BaseDao;

/**
 * 
 * 类名称：${desc}数据实现层 </br>
 * 类描述： </br>
 * 创建人：sky.jiang </br>
 * 
 * @version 1.0
 * 
 */
@Repository
public class ${mop?cap_first}DaoImpl extends BaseDao<${mop?cap_first}> implements I${mop?cap_first}Dao {
@Override
	public void auditing(Map<String, Object> maps) {
		sqlSession.update(clazz.getName() + ".auditing", maps);
	}

	@Override
	public void recommend(Map<String, Object> maps) {
		sqlSession.update(clazz.getName() + ".recommend", maps);		
	}
}
