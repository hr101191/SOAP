package com.hurui.axis2serverexecutablejar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.hurui")
@SpringBootApplication
public class Axis2ServerExecutableJarApplication {

	public static void main(String[] args) {
		SpringApplication.run(Axis2ServerExecutableJarApplication.class, args);
	}

}
