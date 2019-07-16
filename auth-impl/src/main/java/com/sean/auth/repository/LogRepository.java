package com.sean.auth.repository;

import com.sean.auth.model.SysLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 17:02
 */
@Repository
public interface LogRepository extends CrudRepository<SysLog, Long> {
}
