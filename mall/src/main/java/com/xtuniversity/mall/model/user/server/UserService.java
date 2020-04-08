package com.xtuniversity.mall.model.user.server;

import com.xtuniversity.mall.cache.UserCache;
import com.xtuniversity.mall.model.user.dao.IUserDao;
import com.xtuniversity.mall.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
@Service
public class UserService {
    @Qualifier(value = "userDao")
    @Autowired
    IUserDao userDao;
    public List<User> getAllUser() {
       return  userDao.findAll();
    }

    public User getUserByTel(String tel) {
        Map<String ,Object> map = new HashMap<>();
        map.put("tel",tel);
      return   userDao.findByParam(map);
    }

    public void slowSaveUser(User user) {
        UserCache.addUser(user,true);
    }

    public User getUserById(long userId) {
        return UserCache.getUserById(userId);
    }

    public void saveUser(User user) {
        userDao.save(user);
        UserCache.addUser(user,false);
    }

    public void updateUser(User user) {
        userDao.update(user);
        UserCache.addUser(user,false);
    }

    public User getUserByNick(String nick) {
        Map<String ,Object> map = new HashMap<>();
        map.put("nick",nick);
        return   userDao.findByParam(map);
    }

    public void removeUserById(long userId) {
        UserCache.removeUserById(userId);
    }

    public User getUserByIdMast(long userId) {
       return userDao.findById(userId);
    }
}
