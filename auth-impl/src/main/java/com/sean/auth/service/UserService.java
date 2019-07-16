package com.sean.auth.service;

import com.sean.auth.vo.UserVO;

import java.util.Set;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:32
 */
public interface UserService extends BaseService<UserVO> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    UserVO findByName(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);

}
