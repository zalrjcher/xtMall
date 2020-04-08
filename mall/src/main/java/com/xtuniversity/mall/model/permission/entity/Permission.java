/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.permission.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * permission实体类
 * 
 * @author sky.jiang
 *
 */
public class Permission extends BaseEntity {
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 权限名字
	 */
	private String name;
	/**
	 * 权限地址
	 */
	private String url;
	/**
	 * 权限地址
	 */
	private Integer level;
	/**
	 * 菜单级别
	 */
	private Integer grade;
	/**
	 * 父亲id
	 */
	private Long paretId;
	
	
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public Integer getLevel() {
			return level;
		}
		public void setLevel(Integer level) {
			this.level = level;
		}
		public Integer getGrade() {
			return grade;
		}
		public void setGrade(Integer grade) {
			this.grade = grade;
		}
		public Long getParetId() {
			return paretId;
		}
		public void setParetId(Long paretId) {
			this.paretId = paretId;
		}
	
}
