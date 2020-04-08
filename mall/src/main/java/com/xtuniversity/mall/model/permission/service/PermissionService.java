package com.xtuniversity.mall.model.permission.service;

import com.xtuniversity.mall.model.permission.dao.IPermissionDao;
import com.xtuniversity.mall.model.permission.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */

@Service
public class PermissionService {

    @Autowired
    @Qualifier("permissionDao")
    IPermissionDao permissionDao ;

    public List<Permission> getPermissionServiceByLevel(int level) {
        Map<String,Object> map = new HashMap<>();
        map.put("level",level);
       return permissionDao.findAllByParam(map,new LinkedHashMap<>());
    }

    public Permission getPermissionServiceById(Long permissionId) {
      return   permissionDao.findById(permissionId);
    }
}
