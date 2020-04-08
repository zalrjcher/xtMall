package com.xtuniversity.mall.model.base;

import com.xtuniversity.mall.cache.UserCache;
import com.xtuniversity.mall.model.user.entity.User;

/**
 * @author zlj
 * @introduce
 */
public class BaseController {
    public boolean  checkToken(String token ,long userId){
       User user =  UserCache.getUserById(userId);
       if(user == null){
           return false;
       }
       if(!token.equals(user.getToken())){
           return false ;
       }
       return true ;
    }
}
