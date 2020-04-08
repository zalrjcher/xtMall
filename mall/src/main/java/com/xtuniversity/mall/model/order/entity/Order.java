/**
 */
package com.xtuniversity.mall.model.order.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * order实体类
 * 
 * @author sky.jiang
 *
 */
public class Order extends BaseEntity {
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 商品id
	 */
	private Long commodityId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 0表示加入购物车,1表示购买成功(带确认收货)，2表示确认收货(待评价状态)，3(已经评价)
	 */
	private Integer state;
	/**
	 * 添加商品的数量
	 */
	private Integer number;
	/**
	 * 需要支付的金额
	 */
	private Long price;
	/**
	 * 购买时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 收货地址id
	 */
	private Long addressId;
	
	
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getCommodityId() {
			return commodityId;
		}
		public void setCommodityId(Long commodityId) {
			this.commodityId = commodityId;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Integer getState() {
			return state;
		}
		public void setState(Integer state) {
			this.state = state;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public Long getPrice() {
			return price;
		}
		public void setPrice(Long price) {
			this.price = price;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Long getAddressId() {
			return addressId;
		}
		public void setAddressId(Long addressId) {
			this.addressId = addressId;
		}
	
}
