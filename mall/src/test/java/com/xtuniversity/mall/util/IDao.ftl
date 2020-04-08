/**    
 * 版本信息：    友友互娱
 * 日期：Dec 14, 2018    
 * Copyright 足下 Corporation 2018    
 * 版权所有    
 *    
 */
package com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.dao;
import java.util.Map;
import com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first};
import com.xtuniversity.mall.${parentCatalogue}.base.dao.IBaseDao;

/**
 * 
 * 类名称：${desc}数据接口层 </br>
 * 类描述： </br>
 * 创建人：sky.jiang </br>
 * 
 * @version 1.0
 * 
 */
public interface I${mop?cap_first}Dao extends IBaseDao<${mop?cap_first}> {
	void auditing(Map<String, Object> maps);

	void recommend(Map<String, Object> maps);
}
