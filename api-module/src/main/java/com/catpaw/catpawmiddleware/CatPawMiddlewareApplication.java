package com.catpaw.catpawmiddleware;

import com.catpaw.catpawmiddleware.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = { "com.catpaw.catpawcore", "com.catpaw.catpawmiddleware" })
public class CatPawMiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatPawMiddlewareApplication.class, args);
	}

	@Autowired(required = false)
	TestService testService;

	@EventListener(ApplicationReadyEvent.class)
	public void dataInit() {
		if (testService != null) testService.dataInit();
	}
}
