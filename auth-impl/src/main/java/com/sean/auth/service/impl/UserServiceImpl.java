package com.sean.auth.service.impl;

import com.sean.auth.annotation.Operation;
import com.sean.auth.model.SysUser;
import com.sean.auth.page.PageRequest;
import com.sean.auth.page.PageResult;
import com.sean.auth.repository.UserRepository;
import com.sean.auth.service.UserService;
import com.sean.auth.utlis.BeanUtils;
import com.sean.auth.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserVO findByName(String username) {
        SysUser user = userRepository.getUserByName(username);
        return modelToVO(user);
    }

    @Override
    public int save(UserVO record) {
        SysUser user = vOToModel(record);
        userRepository.save(user);
        return 1;
    }

    @Override
    public int delete(UserVO record) {
        SysUser user = vOToModel(record);
        userRepository.delete(user);
        return 1;
    }

    @Override
    public int delete(List<UserVO> records) {
        List<SysUser> userList = new ArrayList<SysUser>();
        try {
            userList = BeanUtils.copyToPropertiesIgnoreNull(records, SysUser.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        userRepository.deleteAll(userList);
        return userList.size();
    }

    @Override
    @Operation("根据ID获取用户信息")
    public UserVO findById(Long id) {
        Optional<SysUser> user = userRepository.findById(id);
        return modelToVO(user.get());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Set<String> findPermissions(String userName) {
        Set<String> perms = new HashSet<>();
//        List<SysMenu> sysMenus = menuMapper.findByUserName(userName);
//        for(SysMenu sysMenu:sysMenus) {
//            if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
//                perms.add(sysMenu.getPerms());
//            }
//        }
        return perms;
    }

    UserVO modelToVO(SysUser user) {
        UserVO entity = new UserVO();
        BeanUtils.copyPropertiesIgnoreNull(user, entity);
        return entity;
    }

    SysUser vOToModel(UserVO vo) {
        SysUser entity = new SysUser();
        BeanUtils.copyPropertiesIgnoreNull(vo, entity);
        return entity;
    }

}
