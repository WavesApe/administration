package com.jn.aristotle.administration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdministrationApplication {

	public static void main(String[] args) {

	    SpringApplication.run(AdministrationApplication.class, args);
	    System.out.println("阿里士多德-报餐系启动");
	}
}
