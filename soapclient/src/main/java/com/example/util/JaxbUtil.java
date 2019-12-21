package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public class JaxbUtil {
    @SuppressWarnings("rawtypes")
	public static String jaxbObjectToXmlString(Object object, Class clazz) throws ParserConfigurationException, JAXBException, SOAPException, IOException
    {   	
        //1) Marshal to object
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
        marshaller.marshal(object, document);
        
        //2) Create Soap Message
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();          
        soapMessage.getSOAPBody().addDocument(document);
        soapMessage.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
        soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
        
        //3) Convert to string
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        soapMessage.writeTo(outputStream);
        return new String(outputStream.toByteArray());
    }
    
    //Note: String className is generated from wsdl, which will help to strip the required section from the SOAP response to unmarshall back to the generated model
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Object xmlStringToJaxbObject(Class clazz, String className, String xml) throws XMLStreamException, JAXBException {
    	
    	//1) Read the response XML
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xmlStreamSource = new StreamSource(new StringReader(xml));
        XMLStreamReader xsr = xif.createXMLStreamReader(xmlStreamSource);
        
        //2) Skip to the XML tag by matching with the generated response class name
        xsr.nextTag();
        while(!xsr.getLocalName().equals(className)) {
            xsr.nextTag();
        }
        
        //3) Unmarshall to response object
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<T> root = jaxbUnmarshaller.unmarshal(xsr, clazz);
        Object object = root.getValue();
        
        return object;
    }
}
