/**
 * 友友互娱
 */
package com.xtuniversity.mall.model.permissionuser.entity;

import java.util.Date;
import com.xtuniversity.mall.model.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * permissionUser实体类
 * 
 * @author sky.jiang
 *
 */
public class PermissionUser extends BaseEntity {
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 权限id
	 */
	private Long permissionId;
	/**
	 * 用户id
	 */
	private Long userId;
	
	
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getPermissionId() {
			return permissionId;
		}
		public void setPermissionId(Long permissionId) {
			this.permissionId = permissionId;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
	
}
