package edu.sustech.oj_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OjServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OjServerApplication.class, args);
	}

}
