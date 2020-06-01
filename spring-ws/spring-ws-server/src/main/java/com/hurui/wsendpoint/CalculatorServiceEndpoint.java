package com.hurui.wsendpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.hurui.stub.calculatorservice.Add;
import com.hurui.stub.calculatorservice.AddResponse;
import com.hurui.stub.calculatorservice.Divide;
import com.hurui.stub.calculatorservice.DivideResponse;
import com.hurui.stub.calculatorservice.Multiply;
import com.hurui.stub.calculatorservice.MultiplyResponse;
import com.hurui.stub.calculatorservice.ObjectFactory;
import com.hurui.stub.calculatorservice.Subtract;
import com.hurui.stub.calculatorservice.SubtractResponse;

@Endpoint
public class CalculatorServiceEndpoint {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	/*
	 * @PayloadRoot - these are the required parameters from your wsdl for the servlet to map the incoming request to invoke this method
	 * 
	 *  1) namespace - copy the targetNameSpace from this part of your wsdl:
	 *  <s:schema elementFormDefault="qualified" targetNamespace="http://CalculatorService.hurui.com/">
	 *  
	 *  2) localPart: Should be the same as your Generated @RequestPayload class name
	 */
	@PayloadRoot(namespace = "http://CalculatorService.hurui.com/", localPart = "Add")
	@ResponsePayload
	public AddResponse add(@RequestPayload Add add) {		
		try {
			logger.info("Handling Incoming Request... Route: [/services/CalculatorService]");
			logger.info("Performing operation: [Add]");
			// 1) Create an instance of CalculatorService ObjectFactory
			ObjectFactory objectFactory = new ObjectFactory();
			
			// 2) Create a new instance of the @ResponsePayload AddResponse object from the ObjectFactory
			AddResponse addResponse = objectFactory.createAddResponse();
			
			// 3) Process the @RequestPayload object and implement custom business logic
			logger.info("Integer A from request object: [{}]", add.getIntA());
			
			logger.info("Integer B from request object: [{}]", add.getIntB());
			int value = add.getIntA() + add.getIntB();

			// 4) Set values to the @ResponsePayload and return it
			addResponse.setAddResult(value);
			return addResponse;
		} catch(Exception ex) {
			// Impt: you have to handle any exception and return a valid response
			AddResponse addResponse = new AddResponse();
			addResponse.setAddResult(0);
			return addResponse;
		}
	}
	
	@PayloadRoot(namespace = "http://CalculatorService.hurui.com/", localPart = "Subtract")
	@ResponsePayload
	public SubtractResponse subtract(@RequestPayload Subtract subtract) {		
		try {
			logger.info("Handling Incoming Request... Route: [/services/CalculatorService]");
			logger.info("Performing operation: [Subtract]");
			// 1) Create an instance of CalculatorService ObjectFactory
			ObjectFactory objectFactory = new ObjectFactory();
			
			// 2) Create a new instance of the @ResponsePayload AddResponse object from the ObjectFactory
			SubtractResponse subtractResponse = objectFactory.createSubtractResponse();
			
			// 3) Process the @RequestPayload object and implement custom business logic
			logger.info("Integer A from request object: [{}]", subtract.getIntA());
			
			logger.info("Integer B from request object: [{}]", subtract.getIntB());
			int value = subtract.getIntA() - subtract.getIntB();

			// 4) Set values to the @ResponsePayload and return it
			subtractResponse.setSubtractResult(value);
			return subtractResponse;
		} catch(Exception ex) {
			// Impt: you have to handle any exception and return a valid response
			SubtractResponse subtractResponse = new SubtractResponse();
			subtractResponse.setSubtractResult(0);
			return subtractResponse;
		}
	}	
	
	@PayloadRoot(namespace = "http://CalculatorService.hurui.com/", localPart = "Multiply")
	@ResponsePayload
	public MultiplyResponse multiple(@RequestPayload Multiply multiply) {
		
		try {
			logger.info("Handling Incoming Request... Route: [/services/CalculatorService]");
			logger.info("Performing operation: [Multiply]");
			// 1) Create an instance of CalculatorService ObjectFactory
			ObjectFactory objectFactory = new ObjectFactory();
			
			// 2) Create a new instance of the @ResponsePayload AddResponse object from the ObjectFactory
			MultiplyResponse multiplyResponse = objectFactory.createMultiplyResponse();
			
			// 3) Process the @RequestPayload object and implement custom business logic
			logger.info("Integer A from request object: [{}]", multiply.getIntA());
			
			logger.info("Integer B from request object: [{}]", multiply.getIntB());
			int value = multiply.getIntA() * multiply.getIntB();

			// 4) Set values to the @ResponsePayload and return it
			multiplyResponse.setMultiplyResult(value);
			return multiplyResponse;
		} catch(Exception ex) {
			// Impt: you have to handle any exception and return a valid response
			MultiplyResponse multiplyResponse = new MultiplyResponse();
			multiplyResponse.setMultiplyResult(0);
			return multiplyResponse;
		}
	}
	
	
	@PayloadRoot(namespace = "http://CalculatorService.hurui.com/", localPart = "Divide")
	@ResponsePayload
	public DivideResponse divide(@RequestPayload Divide divide) {		
		try {
			logger.info("Handling Incoming Request... Route: [/services/CalculatorService]");
			logger.info("Performing operation: [Divide]");
			// 1) Create an instance of CalculatorService ObjectFactory
			ObjectFactory objectFactory = new ObjectFactory();
			
			// 2) Create a new instance of the @ResponsePayload AddResponse object from the ObjectFactory
			DivideResponse divideResponse = objectFactory.createDivideResponse();
			
			// 3) Process the @RequestPayload object and implement custom business logic
			logger.info("Integer A from request object: [{}]", divide.getIntA());
			
			logger.info("Integer B from request object: [{}]", divide.getIntB());
			int value = divide.getIntA() / divide.getIntB();

			// 4) Set values to the @ResponsePayload and return it
			divideResponse.setDivideResult(value);
			return divideResponse;
		} catch(Exception ex) {
			// Impt: you have to handle any exception and return a valid response
			DivideResponse divideResponse = new DivideResponse();
			divideResponse.setDivideResult(0);
			return divideResponse;
		}
	}
}
