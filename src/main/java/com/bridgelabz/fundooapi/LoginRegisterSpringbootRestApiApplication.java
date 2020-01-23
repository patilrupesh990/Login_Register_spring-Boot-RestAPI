package com.bridgelabz.fundooapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LoginRegisterSpringbootRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginRegisterSpringbootRestApiApplication.class, args);
	}

}