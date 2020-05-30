package com.hurui.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
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
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * It's OK to use the deprecated HttpClient (as of HttpClient 4.3), compile dependency for Axis2 uses version 4.5.3 which still contains the deprecated methods
 * Please note that the Axis2 was written for HttpClient 4.2.x and should work with 4.3.x and 4.4.x, but is incompatible with 4.5.x
 * Reference: https://axis.apache.org/axis2/java/core/release-notes/1.7.0.html
 * 
 * HttpClient v 4.3x ++ will cause casting error as the Constructor is different.
 * *Note that the last release for Axis2 was version 1.7.9 (used in this project), which was released late 2018. It seems unlikely to have a newer version anytime soon
 * Version 1.8.0 (WIP) seems to have support for the later versions of HttpClient, for information see Axis2 SVN repo trunk: http://svn.apache.org/repos/asf/axis/axis2/java/transports/trunk/
 */
@SuppressWarnings("deprecation")
@Configuration
public class HttpConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private AppProperties appProperties;
	
	public HttpConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}
	
	@Bean
	public HttpClient httpClient() throws Exception {
		try {			
			logger.info("Building HttpClient...");
			HttpClient httpClient = new DefaultHttpClient(buildConnectionManager());
			logger.info("HttpClient built successfully...");
			return httpClient;
		} catch(Exception ex) {
			logger.error("Failed to build HttpClient instance... Stacktrace: ", ex);
			throw ex;
		}		
	}
	
	private ClientConnectionManager buildConnectionManager() throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, CertificateException, FileNotFoundException, IOException {
		logger.info("Building Client Connection Manager...");
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");		
		sslContext.init(buildKeyManagerFactory().getKeyManagers(), buildTrustManagerFactory().getTrustManagers(), new SecureRandom());
		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);
	    Scheme scheme = new Scheme("https", 443, sslSocketFactory);
	    SchemeRegistry schemeRegistry = new SchemeRegistry();
	    schemeRegistry.register(scheme);
		return new ThreadSafeClientConnManager(schemeRegistry); //Spring default scope is Singleton, it is important to use the thread safe version!
	}
	
	private KeyManagerFactory buildKeyManagerFactory() throws NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");	    
		KeyStore keyStore = KeyStore.getInstance("JKS");
		logger.info("Loading key store from path: [{}]", appProperties.getKeyStorePath());
		keyStore.load(new FileInputStream(appProperties.getKeyStorePath()), appProperties.getKeyStorePassword().toCharArray());
		keyManagerFactory.init(keyStore, appProperties.getKeyPass().toCharArray());
		return keyManagerFactory;
	}
	
	private TrustManagerFactory buildTrustManagerFactory() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");	    
		KeyStore trustStore = KeyStore.getInstance("JKS");
		logger.info("Loading trust store from path: [{}]", appProperties.getTrustStorePath());
		trustStore.load(new FileInputStream(appProperties.getTrustStorePath()), appProperties.getTrustStorePassword().toCharArray());
		trustManagerFactory.init(trustStore);
		return trustManagerFactory;
	}
	

}
