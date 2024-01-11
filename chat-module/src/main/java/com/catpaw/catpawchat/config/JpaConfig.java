package com.catpaw.catpawchat.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan(basePackages = "com.catpaw.catpawcore.domain.entity")
@EnableJpaRepositories(basePackages = { "com.catpaw.catpawcore", "com.catpaw.catpawchat" })
public class JpaConfig {

}
