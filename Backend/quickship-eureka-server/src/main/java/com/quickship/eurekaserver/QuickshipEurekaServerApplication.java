package com.quickship.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class QuickshipEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickshipEurekaServerApplication.class, args);
	}

}
