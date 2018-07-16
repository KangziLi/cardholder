package com.lkz.cardholder;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.lkz.cardholder.dao")
public class CardholderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardholderApplication.class, args);
    }
}
