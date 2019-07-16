package com.sean.auth.service.impl;

import com.sean.auth.annotation.Operation;
import com.sean.auth.model.SysLog;
import com.sean.auth.page.PageRequest;
import com.sean.auth.page.PageResult;
import com.sean.auth.repository.LogRepository;
import com.sean.auth.service.LogService;
import com.sean.auth.utlis.BeanUtils;
import com.sean.auth.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int save(LogVO record) {
        logRepository.save(vOToModel(record));
        return 1;
    }

    @Override
    public int delete(LogVO record) {
        logRepository.delete(vOToModel(record));
        return 1;
    }

    @Override
    public int delete(List<LogVO> records) {
        List<SysLog> entityList = new ArrayList<SysLog>();
        try {
            entityList = BeanUtils.copyToPropertiesIgnoreNull(records, SysLog.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        logRepository.deleteAll(entityList);
        return entityList.size();
    }

    @Override
    public LogVO findById(Long id) {
        Optional<SysLog> entity = logRepository.findById(id);
        return modelToVO(entity.get());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }

    LogVO modelToVO(SysLog user) {
        LogVO entity = new LogVO();
        BeanUtils.copyPropertiesIgnoreNull(user, entity);
        return entity;
    }

    SysLog vOToModel(LogVO vo) {
        SysLog entity = new SysLog();
        BeanUtils.copyPropertiesIgnoreNull(vo, entity);
        return entity;
    }

}
