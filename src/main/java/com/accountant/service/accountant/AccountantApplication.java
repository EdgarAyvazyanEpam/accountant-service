package com.accountant.service.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AccountantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountantApplication.class, args);
	}
}
