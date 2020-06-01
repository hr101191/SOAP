package com.hurui.clientserviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import com.hurui.stub.calculatorservice.Add;
import com.hurui.stub.calculatorservice.AddResponse;
import com.hurui.stub.calculatorservice.Divide;
import com.hurui.stub.calculatorservice.DivideResponse;
import com.hurui.stub.calculatorservice.Multiply;
import com.hurui.stub.calculatorservice.MultiplyResponse;
import com.hurui.stub.calculatorservice.ObjectFactory;
import com.hurui.stub.calculatorservice.Subtract;
import com.hurui.stub.calculatorservice.SubtractResponse;

@Component
public class CalculatorServiceClient {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	public void add() {
		ObjectFactory objectFactory = new ObjectFactory();
		Add add = objectFactory.createAdd();
		
		add.setIntA(4);
		add.setIntB(2);
		
		/*
		 * Use this method to set soapAction header if server operates in SOAP1.1 exclusively
		 * soapAction header is mandatory for SOAP1.1 protocol
		 * 
		 * Otherwise simply use the method marshalSendAndReceive(object), see subtract() method in this class
		 */	
		AddResponse addResponse = (AddResponse) webServiceTemplate.marshalSendAndReceive(add, new WebServiceMessageCallback() {				
		    public void doWithMessage(WebServiceMessage message) {
		        ((SoapMessage)message).setSoapAction("http://tempuri.org/Action"); //Sending a dummy soapAction, not required by the server in this example
		    }
		});
		logger.info("Result of 4 plus by 2 is: [{}]", addResponse.getAddResult());
	}
	
	public void subtract() {
		ObjectFactory objectFactory = new ObjectFactory();
		Subtract subtract = objectFactory.createSubtract();
		
		subtract.setIntA(4);
		subtract.setIntB(2);
		
		SubtractResponse subtractResponse = (SubtractResponse) webServiceTemplate.marshalSendAndReceive(subtract);
		logger.info("Result of 4 minus by 2 is: [{}]", subtractResponse.getSubtractResult());
	}
	
	public void multiply() {
		ObjectFactory objectFactory = new ObjectFactory();
		Multiply multiply = objectFactory.createMultiply();
		
		multiply.setIntA(4);
		multiply.setIntB(2);
		
		MultiplyResponse multiplyResponse = (MultiplyResponse) webServiceTemplate.marshalSendAndReceive(multiply);
		logger.info("Result of 4 multiply by 2 is: [{}]", multiplyResponse.getMultiplyResult());
	}
	
	public void divide() {
		ObjectFactory objectFactory = new ObjectFactory();
		Divide divide = objectFactory.createDivide();
		
		divide.setIntA(4);
		divide.setIntB(2);
		
		DivideResponse divideResponse = (DivideResponse) webServiceTemplate.marshalSendAndReceive(divide);
		logger.info("Result of 4 divide by 2 is: [{}]", divideResponse.getDivideResult());
	}
}
