package com.catpaw.catpawchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CatPawChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatPawChatApplication.class, args);
	}
}
