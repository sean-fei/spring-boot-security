package com.sean.auth.utlis;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/4 10:55
 */
public class IOUtils implements Serializable {

    private static final long serialVersionUID = -2103588064439685070L;

    /**
     * 关闭连接对象
     * @param closeable
     */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

}
