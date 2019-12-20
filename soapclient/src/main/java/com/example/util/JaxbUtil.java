package com.example.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public class JaxbUtil {
    @SuppressWarnings("rawtypes")
	public static Map<String, String> jaxbObjectToXML(Object object, Class clazz)
    {
    	Map<String, String> response = new HashMap<String, String>();
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(object, sw);
            String xmlContent = toSoapRequestXmlString(sw.toString());
            response.put("xml", xmlContent);
            response.put("error", "");
            return response;
            
        } catch (Exception ex) {
            response.put("xml", "");
            response.put("error", ex.toString());
            return response;
        }
    }
    
    //Note: String className is generated from wsdl, which will help to strip the required section from the SOAP response to unmarshall back to the generated model
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Object xmlToJaxbObject(Class clazz, String className, String xml) {
        try
        {
        	XMLInputFactory xif = XMLInputFactory.newFactory();
            StreamSource xmlStreamSource = new StreamSource(new StringReader(xml));
            XMLStreamReader xsr = xif.createXMLStreamReader(xmlStreamSource);
            
            xsr.nextTag();
            while(!xsr.getLocalName().equals(className)) {
                xsr.nextTag();
            }
            
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
 
            JAXBElement<T> root = jaxbUnmarshaller.unmarshal(xsr, clazz);
            Object object = root.getValue();
            
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Object();
        }
    }
    
    private static String toSoapRequestXmlString (String messageFragment) {
        String lvMessageFragment = messageFragment.substring(56);
        String top = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        		"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
        		"  <soap12:Body>\n";         
        String bottom = "  </soap12:Body>\n" +
        		"</soap12:Envelope>";
        String wholeMessage = top + lvMessageFragment + bottom;
        String formattedXmlString = formatXmlString(wholeMessage);

        return formattedXmlString;
    }
    
    public static String formatXmlString(String xml) {

        try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

            //May need this: 
            System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");

            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer lsSerializer = impl.createLSSerializer();

            lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            lsSerializer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.
            
            LSOutput lsOutput = impl.createLSOutput();
            lsOutput.setEncoding("UTF-8");
            
            Writer stringWriter = new StringWriter();
            lsOutput.setCharacterStream(stringWriter);
            lsSerializer.write(document, lsOutput);
            
            String resultString = stringWriter.toString();

            return resultString;
        } catch (Exception ex) {
            System.out.println(ex);
            return "";
        }
    }
}
