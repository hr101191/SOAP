package com.example.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	
    public static Map<String, String> callSoapService(String xml, String soapAction, String url) throws URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, SAXException, IOException, ParserConfigurationException {
    	Map<String, String> responseMap = new HashMap<>();
    	
        OkHttpClient client = new OkHttpClient(); //refer to OkHttpClient manual for instructions to add ssl options

        MediaType mediaType = MediaType.parse("text/xml");

        RequestBody body = RequestBody.create(mediaType, xml);

        Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("SOAPAction", soapAction) //hack: use SOAPUi to reveal this value by importing the wsdl. Set this as a static final string in a class
                    .addHeader("content-type", "text/xml")
                    .build();
        
        System.out.println("==========================================Request==============================================");
        System.out.println("Url: [" + url + "]");
        System.out.println("Request Body:\n" + XmlUtil.formatXmlString(xml));
        System.out.println("========================================End Request============================================");
        Response response = client.newCall(request).execute();
        System.out.println("=========================================Response==============================================");
        System.out.println("Response code: [" + response.code() + "]");
        System.out.println("Response message: [" + response.message()+ "]");
        String responseString = XmlUtil.formatXmlString(response.body().string().toString());
        System.out.println("Response Body:\n" + responseString);
        System.out.println("=======================================End Response============================================");
        responseMap.put("code", "" + response.code());
        responseMap.put("responseBody", responseString);
    	return responseMap;
    }   
}
