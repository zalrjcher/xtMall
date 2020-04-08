package com.xtuniversity.mall.cache;

import com.xtuniversity.mall.model.user.dao.IUserDao;
import com.xtuniversity.mall.model.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这是一个缓存用户数据的容器
 *
 * @author zlj
 * @introduce
 */
@Component
public class UserCache {
    @Autowired
    IUserDao userDao;
    private final static Logger logger = LoggerFactory.getLogger(UserCache.class);
    private static String KEY_PREFIX = "USER_ID_";
    private static long LAST_CACHE_TIME = System.currentTimeMillis();
    private static long UPDATE_TIME = 2 * 60 * 1000;
    private static long LOGIN_KEEP_TIME = 2 * 24 * 60 * 60 * 1000;
    private static final ConcurrentHashMap<String, User> UPDATE_USER = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, User> USER_CACHE = new ConcurrentHashMap<>();

    public static void addUser(User user ,boolean isCache) {
        USER_CACHE.put(KEY_PREFIX + user.getId(), user);
        if(isCache){
            UPDATE_USER.put(KEY_PREFIX + user.getId(), user);
        }
    }

    public static void removeUser(User user) {
        USER_CACHE.remove(KEY_PREFIX + user.getId());
    }
    public static void removeUserById(long userId) {
        USER_CACHE.remove(KEY_PREFIX + userId);
    }
    public static User getUserByParam(Map<String, Object> map) {
        for (String userId : USER_CACHE.keySet()) {
            Class clazz = USER_CACHE.get(userId).getClass();
            for (String key : map.keySet()) {
                try {
                    Field field = clazz.getDeclaredField(key);
                    if (!map.get(key).equals(field.get(key))) {
                        break;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.error("{}对象不存在{}属性", clazz.getName(), key);
                    e.printStackTrace();
                }

            }
            return USER_CACHE.get(userId);
        }
        return null;
    }

    public static User getUserById(long userId) {
        return USER_CACHE.get(KEY_PREFIX + userId);
    }

    public static void updateUser(User user,boolean isCache) {
        USER_CACHE.put(KEY_PREFIX + user.getId(), user);
        if(isCache){
            UPDATE_USER.put(KEY_PREFIX + user.getId(), user);
        }
    }

    @Scheduled(fixedRate = 500)
    private void checkTokenStatus() {
        logger.info("当前容器中有User对象个数为 {}", USER_CACHE.size());
        for (String key : USER_CACHE.keySet()) {
            User user = USER_CACHE.get(key);
            long downTime = System.currentTimeMillis() - user.getLastTime().getTime() - LOGIN_KEEP_TIME;
            if (downTime >= 0) {
                logger.info("当前用户id为{}，手机号码为{}的,登录已经失效", user.getId(), user.getTel());
                USER_CACHE.remove(key);
            }

        }
        long lastUpdateTime = System.currentTimeMillis() - LAST_CACHE_TIME - UPDATE_TIME;
        if (lastUpdateTime >= 0) {
            LAST_CACHE_TIME = System.currentTimeMillis();
            logger.info("进行一次数据更新到数据库，本次共更新数据{}条", UPDATE_USER.size());
            for (String key : UPDATE_USER.keySet()) {
                userDao.update(UPDATE_USER.get(key));
                UPDATE_USER.remove(key);
            }
        }
    }
}
