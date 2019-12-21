package com.example.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httpUtil {
	
    public static Map<String, String> callSoapService(String xml, String url) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, ParserConfigurationException {
    	Map<String, String> responseMap = new HashMap<>();
    	
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/xml");

        RequestBody body = RequestBody.create(mediaType, xml);

        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .build();
        
        System.out.println("==========================================Request==============================================");
        System.out.println("Url: [" + url + "]");
        System.out.println("Request Body:\n" + formatXmlString(xml));
        System.out.println("========================================End Request============================================");
        Response response = client.newCall(request).execute();
        System.out.println("=========================================Response==============================================");
        System.out.println("Response code: [" + response.code() + "]");
        System.out.println("Response message: [" + response.message()+ "]");
        String responseString = formatXmlString(response.body().string().toString());
        System.out.println("Response Body:\n" + responseString);
        System.out.println("=======================================End Response============================================");
        responseMap.put("code", "" + response.code());
        responseMap.put("responseBody", responseString);
    	return responseMap;
    }
    
    private static String formatXmlString(String xml) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		final InputSource src = new InputSource(new StringReader(xml));
		final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
		final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));
	
		//May need this: 
		System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");
	
		final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
		final LSSerializer lsSerializer = impl.createLSSerializer();
	
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
