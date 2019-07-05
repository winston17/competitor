package com.yzc.bigdata.competitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.yzc.bigdata.competitor.dao")
public class CompetitorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CompetitorApplication.class, args);
	}

}
