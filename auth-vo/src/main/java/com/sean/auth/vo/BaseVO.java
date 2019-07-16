package com.sean.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 15:55
 */
@Data
public class BaseVO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

}
