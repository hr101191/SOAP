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
		try {
			//1) init the request
			Add add = new Add();
			add.setIntA(66);
			add.setIntB(99);
			
			//2) marshall the request object to xml string
			String xmlString = JaxbUtil.jaxbObjectToXmlString(add, Add.class);
			
			//3) call web service with the xml string, map it to AddResponse class object if response code is 200			
			Map<String, String> responseMap = httpUtil.callSoapService(xmlString, "http://www.dneonline.com/calculator.asmx");
			AddResponse addResponse = new AddResponse();
			if (Integer.parseInt(responseMap.get("code")) == 200) {			
				addResponse = (AddResponse) JaxbUtil.xmlStringToJaxbObject(AddResponse.class, AddResponse.class.getSimpleName(), responseMap.get("responseBody"));
			}else {
				System.out.println("Unable to map to response object");
				System.out.println(responseMap.get("responseBody"));
				System.out.println("Existing the program!!");
				System.exit(1);
			}
			
			//4) Verify that we are able to get the AddResult from AddResponse object
			System.out.println("Verifying output in response object:");
			System.out.println("Result is: " + addResponse.getAddResult());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
}
