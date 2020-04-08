/**
 * 友友互娱
 */
package com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity;

import java.util.Date;
import com.xtuniversity.mall.${parentCatalogue}.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * ${desc}实体类
 * 
 * @author sky.jiang
 *
 */
public class ${mop?cap_first} extends BaseEntity {
	<#list fieldInfos as field>
	/**
	 * ${field[2]}
	 */
	 <#if (field[1]=="Date")>
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 </#if>
	private ${field[1]} ${field[0]};
	</#list>
	
	
	<#list fieldInfos as field>
		public ${field[1]} get${field[0]?cap_first}() {
			return ${field[0]};
		}
		public void set${field[0]?cap_first}(${field[1]} ${field[0]}) {
			this.${field[0]} = ${field[0]};
		}
	</#list>
	
}
