package com.sean.auth.repository;

import com.sean.auth.model.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:38
 */
@Repository
public interface UserRepository extends CrudRepository<SysUser, Long> {

    @Query("from SysUser where name =:name ")
    SysUser getUserByName(@Param("name") String name);

}
