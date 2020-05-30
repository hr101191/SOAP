package com.hurui.axis2client;

import java.rmi.RemoteException;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import com.hurui.clientstub.calculator.CalculatorServiceStub;
import com.hurui.clientstub.calculator.CalculatorServiceStub.Add;
import com.hurui.clientstub.calculator.CalculatorServiceStub.AddResponse;
import com.hurui.config.AppProperties;

@ComponentScan("com.hurui")
@SpringBootApplication
public class Axis2ClientApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private HttpClient httpClient;
	
	@Autowired
	private ConfigurationContext configurationContext;

	public static void main(String[] args) throws RemoteException {
		SpringApplication.run(Axis2ClientApplication.class, args);
	}
	
	@EventListener
	private void init(ApplicationStartedEvent event) {
		try {
			// 1. Create a new instance of Axis2 stub, providing our instance of Configuration Context loaded from axis2.xml as well as the web service endpoint
			CalculatorServiceStub stub = new CalculatorServiceStub(configurationContext, "http://localhost:8080/services/CalculatorService");
			//stub._getServiceClient().getServiceContext().setProperty(HTTPConstants.CACHED_HTTP_CLIENT, c);
			
			// 2. Manipulate the stub options here. We want to use the HttpClient instance we initialized manually (e.g. for https)	   
		    if (appProperties.getIsHttpsEnabled()) { 
		    	logger.warn("HTTPS is not enabled!");
		    	Options options = stub._getServiceClient().getOptions();
		    	options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
		    } 
			    
		    // 3. Using the Axis2 stub as it is to invoke business logic
		    // You might wanna log the request and response xml... For simplicity, I'll omit those ehre
			Add add = new Add();
			add.setIntA(1);
			add.setIntB(2);
			AddResponse resp = stub.add(add);
			logger.info("Response: " + resp.getAddResult());
			logger.info("Done");
		} catch(Exception ex) {
			logger.error("Oops something went wrong! Stacktrace: ", ex);
		}

	}

}
