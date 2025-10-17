package com.quickship.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuickshipAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickshipAuthServiceApplication.class, args);
	}

}
