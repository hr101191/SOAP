package com.example.soapclient;

import java.util.Map;

import org.soa.Add;
import org.soa.AddResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.util.JaxbUtil;
import com.example.util.httpUtil;

@SpringBootApplication
public class SoapclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapclientApplication.class, args);

		//1) init the request
		Add add = new Add();
		add.setIntA(66);
		add.setIntB(99);
		
		//2) marshall the request object to xml string
		Map<String, String> objectMap = JaxbUtil.jaxbObjectToXML(add, Add.class);
		if (!objectMap.get("xml").isEmpty()) {
			System.out.println(objectMap.get("xml"));
		}			
		else {
			System.out.println("error transforming object into xml String, exiting the program");
			System.out.println("error message: " + objectMap.get("error"));
			System.exit(1);
		}
				
		//3) call web service with the xml string, map it to AddResponse class if response code is 200
		AddResponse addResponse = new AddResponse();
		Map<String, String> responseMap = httpUtil.callSoapService(objectMap.get("xml"), "http://www.dneonline.com/calculator.asmx");
		if (Integer.parseInt(responseMap.get("code")) == 200) {
			try {
				addResponse = (AddResponse) JaxbUtil.xmlToJaxbObject(AddResponse.class, AddResponse.class.getSimpleName(), responseMap.get("xml"));
			}catch(Exception ex){
				System.out.println("error transforming object into xml String, exiting the program");
				System.exit(1);
			}
		}			
		else {
			System.out.println("error transforming object into xml String, exiting the program");
			System.out.println("error message: " + responseMap.get("error"));
			System.exit(1);
		}
		
		//4) Verify that we are able to get the AddResult from AddResponse object
		System.out.println("Result is: " + addResponse.getAddResult());
	}
}
