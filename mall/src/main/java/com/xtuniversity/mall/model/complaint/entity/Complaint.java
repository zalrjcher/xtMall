/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.complaint.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * complaint实体类
 * 
 * @author sky.jiang
 *
 */
public class Complaint extends BaseEntity {
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 被投诉人id
	 */
	private Long userId;
	/**
	 * 被投诉商品
	 */
	private Long commodityId;
	/**
	 * 投诉原因
	 */
	private String text;
	/**
	 * 1表示通过，0表示不通过
	 */
	private Integer state;
	
	
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Long getCommodityId() {
			return commodityId;
		}
		public void setCommodityId(Long commodityId) {
			this.commodityId = commodityId;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Integer getState() {
			return state;
		}
		public void setState(Integer state) {
			this.state = state;
		}
	
}
