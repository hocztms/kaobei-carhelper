package com.kaobei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled =true)
@EnableCaching
public class KaobeiCarhelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaobeiCarhelperApplication.class, args);
    }

}
