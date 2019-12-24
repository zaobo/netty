package com.zab.netty.protal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ProtalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProtalApplication.class, args);
    }

}
