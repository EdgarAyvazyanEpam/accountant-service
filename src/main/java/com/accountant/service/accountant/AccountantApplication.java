package com.accountant.service.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class AccountantApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AccountantApplication.class, args);
    }

    /**
     * For error page
     *
     * @return InternalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
