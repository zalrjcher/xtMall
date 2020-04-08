/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.commodity.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * commodity实体类
 * 
 * @author sky.jiang
 *
 */
public class Commodity extends BaseEntity {
	/**
	 * 0表示审核中，1表示上架中
	 */
	private Integer state;
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品类型
	 */
	private Integer type;
	/**
	 * 商品价格
	 */
	private Long price;
	/**
	 * 商品库存
	 */
	private Integer number;
	/**
	 * 商品图片
	 */
	private String image;
	/**
	 * 上架商品人的id
	 */
	private Long userId;
	/**
	 * 商品的详细图片
	 */
	private String detailed;
	/**
	 * 商品的介绍
	 */
	private String introduce;
	
	
		public Integer getState() {
			return state;
		}
		public void setState(Integer state) {
			this.state = state;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Long getPrice() {
			return price;
		}
		public void setPrice(Long price) {
			this.price = price;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getDetailed() {
			return detailed;
		}
		public void setDetailed(String detailed) {
			this.detailed = detailed;
		}
		public String getIntroduce() {
			return introduce;
		}
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
	
}
