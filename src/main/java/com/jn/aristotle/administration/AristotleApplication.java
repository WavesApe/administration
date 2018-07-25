package com.jn.aristotle.administration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AristotleApplication {

	public static void main(String[] args) {

	    SpringApplication.run(AristotleApplication.class, args);
	    System.out.println("Aristotle-报餐系启动");
	}
}
