package com.example.util;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class httpUtil {
    public static Map<String, String> callSoapService(String xml, String url) {
    	Map<String, String> responseMap = new HashMap<String, String>();
    	try {
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
        	System.out.println("Request Body:\n" + xml);
        	System.out.println("========================================End Request============================================");
        	Response response = client.newCall(request).execute();
        	System.out.println("=========================================Response==============================================");
        	System.out.println("Response code: [" + response.code() + "]");
        	System.out.println("Response message: [" + response.message()+ "]");
        	String responseString = JaxbUtil.formatXmlString(response.body().string().toString());
        	System.out.println("Response Body:\n" + responseString);
        	System.out.println("=======================================End Response============================================");
    		responseMap.put("code", "" + response.code());
    		responseMap.put("xml", responseString);
    		responseMap.put("error", "");
    		return responseMap;
    	}catch(Exception ex) {
    		responseMap.put("code", "500");
    		responseMap.put("xml", "");
    		responseMap.put("error", ex.toString());
    		return responseMap;
    	}
    }
}
