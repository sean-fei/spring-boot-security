package com.sean.auth.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/8 20:16
 */
@Component
public class KaptchaConfig {

    /**
     * 图片边框
     */
    @Value("${kaptcha.border:no}")
    String KAPTCHA_BORDER;

    /**
     * 边框颜色
     */
    @Value("${kaptcha.border.color:black}")
    String KAPTCHA_BORDER_COLOR;

    /**
     * 边框厚度
     */
    @Value("${kaptcha.border.thickness:1}")
    String KAPTCHA_BORDER_THICKNESS;

    /**
     * 图片宽
     */
    @Value("${kaptcha.image.width:200}")
    String KAPTCHA_IMAGE_WIDTH;

    /**
     * 图片高
     */
    @Value("${kaptcha.image.height:50}")
    String KAPTCHA_IMAGE_HEIGHT;

    /**
     * 图片实现类
     */
    @Value("${kaptcha.producer.impl:com.google.code.kaptcha.impl.DefaultKaptcha}")
    String KAPTCHA_PRODUCER_IMPL;

    /**
     * 文本实现类
     */
    @Value("${kaptcha.textproducer.impl:com.google.code.kaptcha.text.impl.DefaultTextCreator}")
    String KAPTCHA_TEXTPRODUCER_IMPL;

    /**
     * 文本集合，验证码值从此集合中获取
     */
    @Value("${kaptcha.textproducer.char.string:0123456789}")
    String KAPTCHA_TEXTPRODUCER_CHAR_STRING;

    /**
     * 验证码长度
     */
    @Value("${kaptcha.textproducer.char.length:4}")
    String KAPTCHA_TEXTPRODUCER_CHAR_LENGTH;

    /**
     * 字体
     */
    @Value("${kaptcha.textproducer.font.names:宋体}")
    String KAPTCHA_TEXTPRODUCER_FONT_NAMES;

    /**
     * 字体颜色
     */
    @Value("${kaptcha.textproducer.font.color:black}")
    String KAPTCHA_TEXTPRODUCER_FONT_COLOR;

    /**
     * 文字间隔
     */
    @Value("${kaptcha.textproducer.char.space:5}")
    String KAPTCHA_TEXTPRODUCER_CHAR_SPACE;

    /**
     * 干扰实现类
     */
    @Value("${kaptcha.noise.impl:com.google.code.kaptcha.impl.DefaultNoise}")
    String KAPTCHA_NOISE_IMPL;

    /**
     * 干扰颜色
     */
    @Value("${kaptcha.noise.color:blue}")
    String KAPTCHA_NOISE_COLOR;

    /**
     * 干扰图片样式
     */
    @Value("${kaptcha.obscurificator.impl:com.google.code.kaptcha.impl.WaterRipple}")
    String KAPTCHA_OBSCURIFICATOR_IMPL;

    /**
     * 背景实现类
     */
    @Value("${kaptcha.background.impl:com.google.code.kaptcha.impl.DefaultBackground}")
    String KAPTCHA_BACKGROUND_IMPL;

    /**
     * 背景颜色渐变，结束颜色
     */
    @Value("${kaptcha.background.clear.to:white}")
    String KAPTCHA_BACKGROUND_CLEAR_TO;

    /**
     * 文字渲染器
     */
    @Value("${kaptcha.word.impl:com.google.code.kaptcha.text.impl.DefaultWordRenderer}")
    String KAPTCHA_WORD_IMPL;

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();

        properties.setProperty("kaptcha.border", KAPTCHA_BORDER);
        // 边框颜色
        properties.setProperty("kaptcha.border.color", KAPTCHA_BORDER_COLOR);
        //边框厚度
        properties.setProperty("kaptcha.border.thickness", KAPTCHA_BORDER_THICKNESS);
        // 图片宽
        properties.setProperty("kaptcha.image.width", KAPTCHA_IMAGE_WIDTH);
        // 图片高
        properties.setProperty("kaptcha.image.height", KAPTCHA_IMAGE_HEIGHT);
        //图片实现类
        properties.setProperty("kaptcha.producer.impl", KAPTCHA_PRODUCER_IMPL);
        //文本实现类
        properties.setProperty("kaptcha.textproducer.impl", KAPTCHA_TEXTPRODUCER_IMPL);
        //文本集合，验证码值从此集合中获取
        properties.setProperty("kaptcha.textproducer.char.string", KAPTCHA_TEXTPRODUCER_CHAR_STRING);
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", KAPTCHA_TEXTPRODUCER_CHAR_LENGTH);
        //字体
        properties.setProperty("kaptcha.textproducer.font.names", KAPTCHA_TEXTPRODUCER_FONT_NAMES);
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", KAPTCHA_TEXTPRODUCER_FONT_COLOR);
        //文字间隔
        properties.setProperty("kaptcha.textproducer.char.space", KAPTCHA_TEXTPRODUCER_CHAR_SPACE);
        //干扰实现类
        properties.setProperty("kaptcha.noise.impl", KAPTCHA_NOISE_IMPL);
        //干扰颜色
        properties.setProperty("kaptcha.noise.color", KAPTCHA_NOISE_COLOR);
        //干扰图片样式
        properties.setProperty("kaptcha.obscurificator.impl", KAPTCHA_OBSCURIFICATOR_IMPL);
        //背景实现类
        properties.setProperty("kaptcha.background.impl", KAPTCHA_BACKGROUND_IMPL);
        //背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", KAPTCHA_BACKGROUND_CLEAR_TO);
        //文字渲染器
        properties.setProperty("kaptcha.word.impl", KAPTCHA_WORD_IMPL);

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;

    }

}
