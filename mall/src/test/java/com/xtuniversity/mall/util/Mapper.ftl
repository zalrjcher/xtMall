<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"     
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first}">
	<resultMap id="resultMap" type="com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first}">
		<id column="id" property="id" />
		<#list columnAndfieldMap?keys as key>
		<#if key != "id">  
		<result column="${key}" property="${columnAndfieldMap["${key}"]}" />
		</#if>
		</#list>
	</resultMap>

	<!-- 插入操作 -->
	<insert id="add" parameterType="com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first}"  useGeneratedKeys="true" keyProperty="id">
		insert into ${tableName}
		 <trim prefix="(" suffix=")" suffixOverrides="," >
		<#list columnAndfieldMap?keys as key>
			<if test="${columnAndfieldMap[key]} != null">
				${key},
			</if>
		</#list>
		</trim>
     <trim prefix="values (" suffix=")" suffixOverrides="," >
     	<#list columnAndfieldMap?keys as key>
			<if test="${columnAndfieldMap[key]} != null">
				${"#{"}${columnAndfieldMap["${key}"]}${"}"},
			</if>
		</#list>
	 </trim>
	</insert>

	<!-- 删除操作 -->
	<delete id="delete" parameterType="java.lang.Long">
		DELETE FROM ${tableName} WHERE id = ${r"#{id}"} 
	</delete>
	<!-- 批量删除 -->
	<delete id="deleteIn" parameterType="java.util.List">
		DELETE FROM ${tableName} WHERE id IN 
        <foreach item="item" collection="list" open="(" separator="," close=")">
			${r"#{item}"}	
		</foreach>  
	</delete>

	<!-- 修改操作 -->
	<update id="update" parameterType="com.xtuniversity.mall.${parentCatalogue}.${mop?lower_case}.entity.${mop?cap_first}">
		update ${tableName} set ${updateInfo} where id = ${r"#{id}"}
	</update>
	
	<!-- 审核操作 -->
	<update id="auditing" parameterType="map">
		update ${tableName} set status=${r"#{status}"} where id IN 
        <foreach item="item" collection="ids" open="(" separator="," close=")">
			${r"#{item}"}
		</foreach>  
	</update>
	<!-- 审核操作 -->
	<update id="recommend" parameterType="map">
		update ${tableName} set recommend= ${r"#{status}"} where id IN 
        <foreach item="item" collection="ids" open="(" separator="," close=")">
			${r"#{item}"}
		</foreach>  
	</update>

	<!-- 根据id查询 -->
	<select id="findById" parameterType="java.lang.Long" resultMap="resultMap">
		select * from ${tableName} where id=${r"#{id}"}
	</select>
	
	<!-- 查询所有 -->
	<select id="findAll" resultMap="resultMap">
		select * from ${tableName} ORDER BY id desc
	</select>
	
	<!-- 查询所有符合条件的 -->
	<select id="findAllByParam" resultMap="resultMap" parameterType="map">
		select * from ${tableName} <include refid="base_where" />
	</select>

	<!-- 总页数 -->
	<select id="findTotal" resultType="int" parameterType="map">
		select count(*) from ${tableName} <include refid="base_where" />
	</select>
	
	<!-- 根据条件查询对象 -->
	<select id="findByParam" resultMap="resultMap" parameterType="map">
		select * from ${tableName} <include refid="base_where" /> limit 1
	</select>
	
	<!-- 分页查询 -->
	<select id="findPage" resultMap="resultMap" parameterType="map">
		SELECT * FROM ${tableName} <include refid="base_where" />
		<if test="sortColumns != null">
			ORDER BY ${r"${sortColumns}"}
		</if>
		LIMIT ${r"#{showPages}"}, ${r"#{pageSize}"}
	</select>

	<!-- 提取公共WHERE -->
	<sql id="base_where">
		<where>
		<#list columnAndfieldMap?keys as key>
			<if test="${columnAndfieldMap[key]} != null">
				AND ${key} = ${"#{"}${columnAndfieldMap["${key}"]}${"}"}
			</if>
		</#list>
		</where>
	</sql>
</mapper>    