package com.sean.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:10
 */
@SpringBootApplication(scanBasePackages = "com.sean.auth")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SecurityOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauth2Application.class, args);
    }

}
