package com.sean.auth.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:07
 */
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -6069341318238174696L;

    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页数量
     */
    private int pageSize = 10;
    /**
     * 每页数量
     */
    private Map<String, ColumnFilter> columnFilters = new HashMap<String, ColumnFilter>();

    public Map<String, ColumnFilter> getColumnFilters() {
        return columnFilters;
    }

    public void setColumnFilters(Map<String, ColumnFilter> columnFilters) {
        this.columnFilters = columnFilters;
    }

    public ColumnFilter getColumnFilter(String name) {
        return columnFilters.get(name);
    }

}
