package com.catpaw.catpawchat.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EntityScan(basePackages = "com.catpaw.catpawcore.domain.entity")
public class JpaConfig {

}
