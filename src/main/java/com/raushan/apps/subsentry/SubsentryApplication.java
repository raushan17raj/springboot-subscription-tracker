package com.raushan.apps.subsentry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SubsentryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubsentryApplication.class, args);
	}

}
