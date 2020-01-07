package com.zab.netty.protal;

import com.zab.netty.protal.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("com.zab.netty.protal")
@MapperScan("com.zab.netty.protal.mapper")
public class ProtalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProtalApplication.class, args);
    }

    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }

}
