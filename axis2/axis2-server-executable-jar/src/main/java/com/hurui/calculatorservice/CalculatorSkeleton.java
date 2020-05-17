/**
 * CalculatorSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */
package com.hurui.calculatorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  CalculatorSkeleton java skeleton for the axisService
 */
public class CalculatorSkeleton {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
    /**
     * Auto generated method signature
     *
     * @param multiply
     * @return multiplyResponse
     */
    public com.hurui.calculatorservice.MultiplyResponse multiply(
        com.hurui.calculatorservice.Multiply multiply) {
    	MultiplyResponse resp = new MultiplyResponse();
        try {     
        	logger.info("Performing multiply operation...");
        	logger.info("First Integer: {}", multiply.localIntA);
        	logger.info("Second Integer: {}", multiply.localIntB);
        	int value = multiply.localIntA * multiply.localIntB;
        	logger.info("Value: {}", value);
        	resp.setMultiplyResult(value);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param divide
     * @return divideResponse
     */
    public com.hurui.calculatorservice.DivideResponse divide(
        com.hurui.calculatorservice.Divide divide) {
    	DivideResponse resp = new DivideResponse();
        try {  
        	logger.info("Performing divide operation...");
        	logger.info("First Integer: {}", divide.localIntA);
        	logger.info("Second Integer: {}", divide.localIntB);
        	int value = divide.localIntA / divide.localIntB;
        	logger.info("Value: {}", value);
        	resp.setDivideResult(value);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
        return resp;
    }

    /**
     * Auto generated method signature
     * Adds two integers. This is a test WebService. ©DNE Online
     * @param add
     * @return addResponse
     */
    public com.hurui.calculatorservice.AddResponse add(
        com.hurui.calculatorservice.Add add) {
    	AddResponse resp = new AddResponse();
        try {        	
        	logger.info("Performing add operation...");
        	logger.info("First Integer: {}", add.localIntA);
        	logger.info("Second Integer: {}", add.localIntB);
        	int value = add.localIntA + add.localIntB;
        	logger.info("Value: {}", value);
        	resp.setAddResult(value);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
        return resp;
    }

    /**
     * Auto generated method signature
     *
     * @param subtract
     * @return subtractResponse
     */
    public com.hurui.calculatorservice.SubtractResponse subtract(
        com.hurui.calculatorservice.Subtract subtract) {
    	SubtractResponse resp = new SubtractResponse();
        try {    
        	logger.info("Performing subtract operation...");
        	logger.info("First Integer: {}", subtract.localIntA);
        	logger.info("Second Integer: {}", subtract.localIntB);
        	int value = subtract.localIntA - subtract.localIntB;
        	logger.info("Value: {}", value);
        	resp.setSubtractResult(value);
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
        return resp;
    }
}
