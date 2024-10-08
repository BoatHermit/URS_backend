package com.nju.urs.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@MapperScan("com.nju.urs.dao.mysql.mapper")
@EnableMongoRepositories(basePackages = {"com.nju.urs.dao.mongo.mapper", "com.nju.urs.user.repository"})
@ComponentScan("com.nju.urs")
@EnableCaching
public class UrsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrsWebApplication.class, args);
    }

}
