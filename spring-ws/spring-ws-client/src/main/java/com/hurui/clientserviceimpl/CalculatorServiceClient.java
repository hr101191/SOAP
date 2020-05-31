package com.hurui.clientserviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.hurui.stub.calculatorservice.AddResponse;
import com.hurui.stub.calculatorservice.ObjectFactory;

@Component
public class CalculatorServiceClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	public void Add() {
		ObjectFactory objectFactory = new ObjectFactory();
		com.hurui.stub.calculatorservice.Add add = objectFactory.createAdd();
		
		add.setIntA(1);
		add.setIntB(2);
		
		AddResponse addResponse = (AddResponse) webServiceTemplate.marshalSendAndReceive(add);
		System.out.println(addResponse.getAddResult());
	}
}
