package com.catpaw.catpawmiddleware.config;

import com.catpaw.catpawmiddleware.service.test.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class TestConfig {

    @Bean
    public TestService testService() {
        return new TestService();
    }
}
