/**    
 * 版本信息：    友友互娱
 * 日期：Dec 14, 2018    
 * Copyright 足下 Corporation 2018    
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.model.order.dao;
import java.util.Map;
import com.xtuniversity.mall.model.order.entity.Order;
import com.xtuniversity.mall.model.base.dao.IBaseDao;

/**
 * 
 * 类名称：order数据接口层 </br>
 * 类描述： </br>
 * 创建人：sky.jiang </br>
 * 
 * @version 1.0
 * 
 */
public interface IOrderDao extends IBaseDao<Order> {
	void auditing(Map<String, Object> maps);

	void recommend(Map<String, Object> maps);
}
