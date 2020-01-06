package com.example.soapclient;

import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.soa.Add;
import org.soa.AddResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.util.JaxbUtil;
import com.example.util.HttpUtil;

@SpringBootApplication
public class SoapclientApplication {
	
	private static final String SOAP_ACTION_ADD = "http://tempuri.org/Add";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		SpringApplication.run(SoapclientApplication.class, args);
		
		try {
			//1) init the request
			Add add = new Add();
			add.setIntA(66);
			add.setIntB(99);
			
			//2) Convert the request object to JAXBElement marshall it to xml string
			//note: depending on the wsdl style, QNAME could be in different generated class
			//note: some wsdl wil have toJaxbElement method generated which eliminates the need of this step
			JAXBElement<?> jaxbElement = new JAXBElement(new QName("http://tempuri.org/", "Calculator"), Add.class, add); 
			String xmlString = JaxbUtil.jaxbElementToXmlString(jaxbElement, Add.class);
			
			//3) call web service with the xml string, map it to AddResponse class object if response code is 200			
			Map<String, String> responseMap = HttpUtil.callSoapService(xmlString, SOAP_ACTION_ADD, "http://www.dneonline.com/calculator.asmx");
			AddResponse addResponse = new AddResponse();
			if (Integer.parseInt(responseMap.get("code")) >= 200 && Integer.parseInt(responseMap.get("code")) < 300) {			
				addResponse = (AddResponse) JaxbUtil.xmlStringToJaxbObject(AddResponse.class, responseMap.get("responseBody"));
			}else {
				//handle the exception
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
