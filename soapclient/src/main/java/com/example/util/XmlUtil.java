package com.example.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtil {
	
    public static String formatXmlString(String xml) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		InputSource src = new InputSource(new StringReader(xml));
		Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
		Boolean keepDeclaration = Boolean.valueOf(xml.toLowerCase().startsWith("<?xml"));
	
		//May need this: 
		System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");
	
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
		LSSerializer lsSerializer = impl.createLSSerializer();
	
		lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
		lsSerializer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be adopted.
		
		LSOutput lsOutput = impl.createLSOutput();
		lsOutput.setEncoding("UTF-8");
		
		Writer stringWriter = new StringWriter();
		lsOutput.setCharacterStream(stringWriter);
		lsSerializer.write(document, lsOutput);
		
		String resultString = stringWriter.toString();
	
		return resultString;
    }
    
}
