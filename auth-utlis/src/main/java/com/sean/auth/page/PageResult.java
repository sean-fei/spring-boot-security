package com.sean.auth.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:08
 */
@Data
public class PageResult implements Serializable {

    private static final long serialVersionUID = 743627003064506118L;

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 分页数据
     */
    private List<?> content;

}
