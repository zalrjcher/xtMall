/**    
 * 文件名：MemberDaoImpl.java    
 *    
 * 版本信息：    腾云IT
 * 日期：Dec 12, 2012    
 * Copyright 足下 Corporation 2012     
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.model.user.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;

import com.xtuniversity.mall.model.user.dao.IUserDao;
import com.xtuniversity.mall.model.user.entity.User;
import com.xtuniversity.mall.model.base.dao.impl.BaseDao;

/**
 * 
 * 类名称：user数据实现层 </br>
 * 类描述： </br>
 * 创建人：sky.jiang </br>
 * 
 * @version 1.0
 * 
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDao<User> implements IUserDao {
@Override
	public void auditing(Map<String, Object> maps) {
		sqlSession.update(clazz.getName() + ".auditing", maps);
	}

	@Override
	public void recommend(Map<String, Object> maps) {
		sqlSession.update(clazz.getName() + ".recommend", maps);		
	}
}
