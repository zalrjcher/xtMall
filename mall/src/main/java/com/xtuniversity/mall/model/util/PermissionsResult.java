package com.xtuniversity.mall.model.util;

import com.xtuniversity.mall.model.permission.entity.Permission;

import java.util.List;

/**
 * @author zlj
 * @introduce
 */
public class PermissionsResult {

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
    private List<Permission> permissions ;
    /**
     * 菜单级别
     */
    private Integer grade;

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public static PermissionsResult  valueOf(Long id,String name,String url,Integer level,Integer grade,List<Permission> permissions){
        PermissionsResult permissionsResult = new PermissionsResult();
        permissionsResult.setId(id);
        permissionsResult.setLevel(level);
        permissionsResult.setName(name);
        permissionsResult.setUrl(url);
        permissionsResult.setPermissions(permissions);
        permissionsResult.setGrade(grade);
        return  permissionsResult;
    }
}
