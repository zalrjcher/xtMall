/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.address.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * address实体类
 * 
 * @author sky.jiang
 *
 */
public class Address extends BaseEntity {
	/**
	 * 0表示删除，1表示正常
	 */
	private Integer state;
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 收货地址
	 */
	private String adress;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 用户id
	 */
	private Long userId;
	
	
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
		public String getAdress() {
			return adress;
		}
		public void setAdress(String adress) {
			this.adress = adress;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
	
}
