package com.nju.urs.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@MapperScan("com.nju.urs.dao.mysql")
@EnableMongoRepositories(basePackages = {"com.nju.urs.dao.mongo"})
@ComponentScan("com.nju.urs")
public class UrsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrsServiceApplication.class, args);
	}

}
