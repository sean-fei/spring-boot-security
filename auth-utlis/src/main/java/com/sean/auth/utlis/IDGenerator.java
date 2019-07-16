package com.sean.auth.utlis;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:04
 */
public class IDGenerator implements Serializable {

    private static final long serialVersionUID = 2274065090570831704L;

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生产业务CODE
     * @return
     */
    public static String getBusinessCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()).toString();
    }

}
