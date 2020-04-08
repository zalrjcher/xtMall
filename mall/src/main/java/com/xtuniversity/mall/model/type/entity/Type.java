/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.type.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * type实体类
 * 
 * @author sky.jiang
 *
 */
public class Type extends BaseEntity {
	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 类型
	 */
	private String type;
	
	
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	
}
