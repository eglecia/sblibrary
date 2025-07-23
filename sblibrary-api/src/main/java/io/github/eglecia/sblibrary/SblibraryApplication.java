package io.github.eglecia.sblibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SblibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SblibraryApplication.class, args);
	}

}
