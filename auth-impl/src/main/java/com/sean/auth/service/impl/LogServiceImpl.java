package com.sean.auth.service.impl;

import com.sean.auth.model.SysLog;
import com.sean.auth.repository.LogRepository;
import com.sean.auth.service.LogService;
import com.sean.auth.utlis.ModelMapperConfig;
import com.sean.auth.vo.LogVO;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 17:01
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Autowired
    ModelMapperConfig modelMapperConfig;

    @Override
    public int save(LogVO record) {
        logRepository.save(modelMapperConfig.getModelMapper().map(record, SysLog.class));
        return 1;
    }

    @Override
    public int delete(LogVO record) {
        logRepository.delete(modelMapperConfig.getModelMapper().map(record, SysLog.class));
        return 1;
    }

    @Override
    public int delete(List<LogVO> records) {
        List<SysLog> entityList = modelMapperConfig.getModelMapper().map(records, new TypeToken<List<SysLog>>(){}.getType());
        logRepository.deleteAll(entityList);
        return entityList.size();
    }

    @Override
    public LogVO findById(Long id) {
        Optional<SysLog> entity = logRepository.findById(id);
        return modelMapperConfig.getModelMapper().map(entity.get(), LogVO.class);
    }

    @Override
    public Page<LogVO> search(LogVO logVO, Pageable pageable) {
        Specification<SysLog> spec = (Specification<SysLog>) (root, query, cb) -> {
            if(null == logVO) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(!StringUtils.isBlank(logVO.getUserName())) {
                Predicate userNameLike = cb.like(root.get("userName"), "%" + logVO.getUserName() + "%");
                predicates.add(userNameLike);
            }
            if(!StringUtils.isBlank(logVO.getOperation())) {
                Predicate operationLike = cb.like(root.get("operation"), "%" + logVO.getOperation() + "%");
                predicates.add(operationLike);
            }
            if(!StringUtils.isBlank(logVO.getMethod())) {
                Predicate methodLike = cb.like(root.get("method"), "%" + logVO.getMethod() + "%");
                predicates.add(methodLike);
            }
            if(!StringUtils.isBlank(logVO.getParams())) {
                Predicate paramsLike = cb.like(root.get("params"), "%" + logVO.getParams() + "%");
                predicates.add(paramsLike);
            }
            if(null != logVO.getStartTime() && null != logVO.getEntTime()) {
                Predicate timeBetween = cb.between(root.get("time"), logVO.getStartTime(), logVO.getEntTime());
                predicates.add(timeBetween);
            }
            if(null != logVO.getStartTime()) {
                Predicate timeLessThanOrEqualTon = cb.lessThanOrEqualTo(root.get("time"), logVO.getStartTime());
                predicates.add(timeLessThanOrEqualTon);
            }
            if(null != logVO.getEntTime()) {
                Predicate timeGreaterThanOrEqualTo = cb.greaterThanOrEqualTo(root.get("time"), logVO.getEntTime());
                predicates.add(timeGreaterThanOrEqualTo);
            }
            if(!StringUtils.isBlank(logVO.getIp())) {
                Predicate ipLike = cb.like(root.get("ip"), "%" + logVO.getIp() + "%");
                predicates.add(ipLike);
            }
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
        Page<SysLog> logList = logRepository.findAll(spec, pageable);
        return logList.map(item -> modelMapperConfig.getModelMapper().map(item, LogVO.class));
    }

    @Override
    public Page<LogVO> search(LogVO logVO) {
        Pageable pageable = PageRequest.of(logVO.getPageNo(), logVO.getPageSize());
        return this.search(logVO, pageable);
    }

}
