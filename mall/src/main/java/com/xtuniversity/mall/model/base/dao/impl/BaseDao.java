package com.xtuniversity.mall.model.base.dao.impl;

import com.xtuniversity.mall.model.base.dao.IBaseDao;
import com.xtuniversity.mall.model.util.QueryResult;

import com.xtuniversity.mall.model.util.Pagination;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
public class BaseDao<T> implements IBaseDao<T> {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    protected Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    @Override
    public void save(T entity) {
        sqlSession.insert(clazz.getName() + ".add", entity);
    }

    @Override
    public void delete(Serializable pk) {
        sqlSession.delete(clazz.getName() + ".delete", pk);
    }

    @Override
    public void deleteIn(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        sqlSession.delete(clazz.getName() + ".deleteIn", ids);
    }

    @Override
    public void update(T entity) {
        sqlSession.update(entity.getClass().getName() + ".update", entity);
    }

    @Override
    public T findById(Serializable pk) {
        return sqlSession.selectOne(clazz.getName() + ".findById", pk);
    }

    @Override
    public List<T> findAll() {
        return sqlSession.selectList(clazz.getName() + ".findAll");
    }

    @Override
    public List<T> findAllByParam(Map<String, Object> maps, LinkedHashMap<String, String> orderMap) {
        if (orderMap != null && orderMap.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String key : orderMap.keySet()) {
                String value = orderMap.get(key);
                stringBuffer.append(key);
                stringBuffer.append(" ");
                stringBuffer.append(value);
                stringBuffer.append(",");
            }
            String sortColumns = stringBuffer.substring(0, stringBuffer.length()-1);
            maps.put("sortColumns", sortColumns);
        }
        return sqlSession.selectList(clazz.getName() + ".findAllByParam", maps);
    }

    @Override
    public QueryResult<T> findPage(Map<String, Object> maps, LinkedHashMap<String, String> orderMap, Pagination pagination) {
        if (maps == null) {
            maps = new HashMap<>();
        }
        maps.put("showPages", (pagination.getCurrentPage() - 1) * pagination.getPageSize());
        maps.put("pageSize", pagination.getPageSize());

        if (orderMap != null && orderMap.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String key : orderMap.keySet()) {
                String value = orderMap.get(key);
                stringBuffer.append(key);
                stringBuffer.append(" ");
                stringBuffer.append(value);
                stringBuffer.append(",");
            }
            String sortColumns = stringBuffer.substring(0, stringBuffer.length()-1);
            maps.put("sortColumns", sortColumns);
        }
        List<T> pageList = sqlSession.selectList(clazz.getName() + ".findPage", maps);
        int total = findTotalCount(maps);
        pagination.setTotalCount(total);
        return new QueryResult<T>(pageList, pagination, maps);
    }


    @Override
    public int findTotalCount(Map<String, Object> maps) {
        int total = 0;
        total = sqlSession.selectOne(clazz.getName() + ".findTotal", maps);
        return total;
    }

    @Override
    public T findByParam(Map<String, Object> maps) {
        return sqlSession.selectOne(clazz.getName() + ".findByParam", maps);
    }
}
