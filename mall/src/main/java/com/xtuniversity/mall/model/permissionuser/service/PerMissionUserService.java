package com.xtuniversity.mall.model.permissionuser.service;

import com.xtuniversity.mall.model.permissionuser.dao.IPermissionUserDao;
import com.xtuniversity.mall.model.permissionuser.entity.PermissionUser;
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
public class PerMissionUserService {

    @Autowired
    @Qualifier("permissionUserDao")
    IPermissionUserDao permissionUserDao;

    public void savePermissionUser(PermissionUser permissionUser) {
        permissionUserDao.save(permissionUser);
    }

    public List<PermissionUser> getPermissionUserByUserId(long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        return permissionUserDao.findAllByParam(map,new LinkedHashMap<>());
    }
}
