package com.hurui.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class WebServiceClientConfig {

	@Value("${server.ssl.enabled}")
	private boolean isHttpsEnabled;

	@Value("${server.ssl.key-store}")
	private Resource keyStore;

	@Value("${server.ssl.key-store-password}")
	private String keyStorePassword;
	
	@Value("${server.ssl.key-password}")
	private String keyPassword;
	  
	@Value("${server.ssl.trust-store}")
	private Resource trustStore;
	
	@Value("${server.ssl.trust-store-password}")
	private String trustStorePassword;
	  
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.hurui.stub.calculatorservice");
		return jaxb2Marshaller;
	}
	
	@Bean
	public WebServiceTemplate webServiceTemplate() throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
		if(isHttpsEnabled) {
			webServiceTemplate.setDefaultUri("https://localhost:8080/services/CalculatorService");
			webServiceTemplate.setMessageSender(httpComponentsMessageSender());
		}else {
			webServiceTemplate.setDefaultUri("http://localhost:8080/services/CalculatorService");
		}	
	    return webServiceTemplate;
	}
	
	public HttpComponentsMessageSender httpComponentsMessageSender() throws Exception {
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
	    httpComponentsMessageSender.setHttpClient(httpClient());
	    return httpComponentsMessageSender;
	}

	public HttpClient httpClient() throws Exception {
	    return HttpClientBuilder
	    		.create()
	    		.setSSLSocketFactory(sslConnectionSocketFactory())
	    		.addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()) //To remove soapAction header from request
	    		.build();
	}

	public SSLConnectionSocketFactory sslConnectionSocketFactory() throws Exception {
	    // NoopHostnameVerifier essentially turns hostname verification off as otherwise following error
	    // is thrown: java.security.cert.CertificateException: No name matching localhost found
	    return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
	}

	public SSLContext sslContext() throws Exception {
		 return SSLContextBuilder.create()
				 	.loadKeyMaterial(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray())
			        .loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray())
			        .build();
	}	
	
}
