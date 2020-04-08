package com.xtuniversity.mall.model.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zlj
 * @introduce
 */
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = 3610173017027792309L;

    private List<T> resultList;
    private Pagination pagination;
    private String queryConditions;// 查询条件
    private String orderByConditions;// 排序条件

    public QueryResult() {
    }

    public QueryResult(List<T> resultList, Pagination pagination) {
        this.pagination = pagination;
        this.resultList = resultList;
    }

    public QueryResult(List<T> resultList, Pagination pagination, Map<String, Object> params) {
        this.pagination = pagination;
        this.resultList = resultList;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getQueryConditions() {
        return queryConditions;
    }

    public void setQueryConditions(Map<String, Object> params, Map<String, String> orderMap) {
    }

    public String getOrderByConditions() {
        return orderByConditions;
    }

}
