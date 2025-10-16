package com.quickship.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class QuickshipConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickshipConfigServerApplication.class, args);
	}

}
