package com.hurui.springwsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.hurui")
@SpringBootApplication
public class SpringWsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWsServerApplication.class, args);
	}

}
