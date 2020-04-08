package com.xtuniversity.mall.model.util;

import java.io.Serializable;

/**
 * @author zlj
 * @introduce
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = -2595616300982589085L;

    private int currentPage = 1;// 当前页
    private int pageSize = 10; // 每页显示行数
    private int totalPage;// 总页数
    private int totalCount = 0; // 总共条数

    public Pagination() {
    }

    public Pagination(int size, Integer pageSize) {
        if (pageSize != null) {
            this.pageSize = pageSize;
        }
        if (currentPage == 0) {
            this.currentPage = 1;
            return;
        } else {
            this.currentPage = size / pageSize + 1;
        }
    }

    public Pagination(int page) {
        this.currentPage = page;
        this.pageSize = 8;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        int temp = totalCount % pageSize;
        if (temp == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        this.totalCount = totalCount;
    }
}
