package com.sean.auth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/11/5 15:20
 */
@Data
class PageVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -7478882106554896486L;

    public int pageNo = 0;

    public int pageSize = 10;

}
