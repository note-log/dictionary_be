package com.snowwarrior.directory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SnowWarrior
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@MapperScan(basePackages = "com.snowwarrior.directory.mapper")
@EnableTransactionManagement
public class DictionaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictionaryApplication.class, args);
    }
}
