package com.hurui.springwsclient;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import com.hurui.clientserviceimpl.CalculatorServiceClient;

@ComponentScan("com.hurui")
@SpringBootApplication
public class SpringWsClientApplication {

	@Autowired
	private CalculatorServiceClient client;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringWsClientApplication.class, args);
	}

	@EventListener
	private void init(ApplicationStartedEvent event) {
		try {
			client.Add();
		} catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	@PreDestroy
	private void destroy() {
		System.out.println("Completed");
	}
}
