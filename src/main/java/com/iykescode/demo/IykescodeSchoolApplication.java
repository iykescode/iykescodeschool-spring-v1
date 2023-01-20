package com.iykescode.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.iykescode.demo.repository")
@EntityScan("com.iykescode.demo.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class IykescodeSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(IykescodeSchoolApplication.class, args);
	}

}
