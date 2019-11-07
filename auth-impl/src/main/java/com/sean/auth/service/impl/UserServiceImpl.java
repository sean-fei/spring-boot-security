package com.sean.auth.service.impl;

import com.sean.auth.annotation.Operation;
import com.sean.auth.model.SysUser;
import com.sean.auth.repository.UserRepository;
import com.sean.auth.service.UserService;
import com.sean.auth.utlis.ModelMapperConfig;
import com.sean.auth.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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

    @Autowired
    ModelMapperConfig modelMapperConfig;

    @Override
    public UserVO findByName(String username) {
        SysUser user = userRepository.getUserByName(username);
        return modelMapperConfig.getModelMapper().map(user, UserVO.class);
    }

    @Override
    public int save(UserVO record) {
        userRepository.save(modelMapperConfig.getModelMapper().map(record, SysUser.class));
        return 1;
    }

    @Override
    public int delete(UserVO record) {
        userRepository.delete(modelMapperConfig.getModelMapper().map(record, SysUser.class));
        return 1;
    }

    @Override
    public int delete(List<UserVO> records) {
        List<SysUser> userList = modelMapperConfig.getModelMapper().map(records, new TypeToken<List<SysUser>>(){}.getType());
        userRepository.deleteAll(userList);
        return userList.size();
    }

    @Override
    @Operation("根据ID获取用户信息")
    public UserVO findById(Long id) {
        Optional<SysUser> user = userRepository.findById(id);
        return modelMapperConfig.getModelMapper().map(user.get(), UserVO.class);
    }

    @Override
    public Page<UserVO> search(UserVO userVO, Pageable pageable) {
        Specification<SysUser> spec = (Specification<SysUser>) (root, query, cb) -> {
            if(null == userVO) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(!StringUtils.isBlank(userVO.getName())) {
                Predicate nameLike = cb.like(root.get("name"), "%" + userVO.getName() + "%");
                predicates.add(nameLike);
            }
            if(!StringUtils.isBlank(userVO.getEmail())) {
                Predicate emailLike = cb.like(root.get("email"), "%" + userVO.getEmail() + "%");
                predicates.add(emailLike);
            }
            if(!StringUtils.isBlank(userVO.getMobile())) {
                Predicate mobileLike = cb.like(root.get("mobile"), userVO.getMobile());
                predicates.add(mobileLike);
            }
            if(null != userVO.getDelFlag()) {
                Predicate delFlagEqual = cb.equal(root.get("delFlag"), userVO.getDelFlag());
                predicates.add(delFlagEqual);
            }
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
        Page<SysUser> userList = userRepository.findAll(spec, pageable);
        return userList.map(item -> modelMapperConfig.getModelMapper().map(item, UserVO.class));
    }

    @Override
    public Page<UserVO> search(UserVO userVO) {
        Pageable pageable = PageRequest.of(userVO.getPageNo(), userVO.getPageSize());
        return this.search(userVO, pageable);
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

}
