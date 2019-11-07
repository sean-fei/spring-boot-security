package com.sean.auth.utlis;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/11/5 10:06
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    @Scope("singleton")
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //默认为standard模式，设置为strict模式
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //验证映射
        modelMapper.validate();

        // 配置代码
        return modelMapper;
    }

}
