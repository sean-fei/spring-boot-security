package com.sean.auth.page;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询列过滤器
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:07
 */
@Data
public class ColumnFilter implements Serializable {

    private static final long serialVersionUID = 8909348271120077710L;

    /**
     * 过滤列名
     */
    private String name;

    /**
     * 查询的值
     */
    private String value;

}
