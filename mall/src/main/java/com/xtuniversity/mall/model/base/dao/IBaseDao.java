package com.xtuniversity.mall.model.base.dao;

import com.xtuniversity.mall.model.util.QueryResult;
import com.xtuniversity.mall.model.util.Pagination;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
public interface IBaseDao<T> {
    /**
     * 添加
     *
     * @param entity
     */
    public void save(T entity);

    /**
     * 删除
     *
     * @param entityClass
     * @param pk
     */
    public void delete(Serializable pk);

    /**
     * 批量删除
     *
     * @param entityClass
     */
    public void deleteIn(List<Long> ids);

    /**
     * 更新
     *
     * @param entity
     */
    public void update(T entity);

    /**
     * 查询，按ID
     *
     * @param entityClass
     * @param pk
     * @return
     */
    public T findById(Serializable pk);

    /**
     * 查询全部信息
     *
     * @param entityClass
     * @return
     */
    public List<T> findAll();

    /**
     * 根据条件查询全部信息
     *
     * @param entityClass
     * @return
     */
    public List<T> findAllByParam(Map<String, Object> maps, LinkedHashMap<String, String> orderMap);

    /**
     * 带多条件的分页
     *
     * @param entityClass
     * @param maps
     * @return
     */
    public QueryResult<T> findPage(Map<String, Object> maps , LinkedHashMap<String, String> orderMap , Pagination pagination);

    /**
     * 查询数量
     *
     * @param entityClass
     * @param maps
     * @return
     */
    public int findTotalCount(Map<String, Object> maps);

    /**
     * 通过一个参数取到对应的对象
     *
     * @param entityClass
     * @param maps
     * @return
     */
    public T findByParam(Map<String, Object> maps);
}