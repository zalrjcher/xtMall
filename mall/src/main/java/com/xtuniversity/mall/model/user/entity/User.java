/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.user.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * user实体类
 * 
 * @author sky.jiang
 *
 */
public class User extends BaseEntity {
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 登录名
	 */
	private String nick;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 投诉次数
	 */
	private Integer comCount;
	/**
	 * 状态：0.正常 1.禁用 2禁止登录
	 */
	private Integer status;
	/**
	 * 最后登录时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 余额
	 */
	private Long money;
	/**
	 * tkoen
	 */
	private String token;
	/**
	 * 评分
	 */
	private Float score;
	
	
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNick() {
			return nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public Integer getComCount() {
			return comCount;
		}
		public void setComCount(Integer comCount) {
			this.comCount = comCount;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Date getLastTime() {
			return lastTime;
		}
		public void setLastTime(Date lastTime) {
			this.lastTime = lastTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Long getMoney() {
			return money;
		}
		public void setMoney(Long money) {
			this.money = money;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Float getScore() {
			return score;
		}
		public void setScore(Float score) {
			this.score = score;
		}
	
}
