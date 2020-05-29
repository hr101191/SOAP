package com.hurui.axis2client;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.axis2.client.Options;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.security.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.ssl.SSLContexts;

import com.hurui.clientstub.calculator.CalculatorServiceStub;
import com.hurui.clientstub.calculator.CalculatorServiceStub.Add;
import com.hurui.clientstub.calculator.CalculatorServiceStub.AddResponse;

public class Axis2ClientApplication {

	public static void main(String[] args) throws RemoteException {
		try {
			char[] pwdArray = "11111111".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream("C:\\ssl_workspace\\server-a-keystore.jks"), pwdArray);
			
			KeyStore ys = KeyStore.getInstance("JKS");
			ys.load(new FileInputStream("C:\\ssl_workspace\\server-b-truststore.jks"), pwdArray);
			
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		    keyManagerFactory.init(ks, pwdArray);
		    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
		    trustManagerFactory.init(ks);
		    
		    SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		    sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
		    SSLSocketFactory sf = new SSLSocketFactory(sslContext);
		    Scheme httpsScheme = new Scheme("https", 8080, sf);
		    SchemeRegistry schemeRegistry = new SchemeRegistry();
		    schemeRegistry.register(httpsScheme);
		    ClientConnectionManager cm =  new SingleClientConnManager(schemeRegistry);
		    
//			SSLContext sslContext = SSLContexts.custom()
//					.loadKeyMaterial(ks, pwdArray)
//					.loadTrustMaterial(null, new TrustSelfSignedStrategy())
//	                .build();
			org.apache.commons.httpclient.protocol.ProtocolSocketFactory factory = new SSLProtocolSocketFactory(sslContext);
			
			Protocol protocol = new Protocol("https", factory, 8080);

			
			
			//https://stackoverflow.com/questions/52554807/how-to-configure-ssl-with-axis2-using-httpclient4
			// 1. Build HttpClient - Always use internal HttpClient (3.1) provided by axis2 to avoid complications	
			ConfigurationContext ctx = ConfigurationContextFactory.createConfigurationContextFromFileSystem("src/main/resources", "src/main/resources/axis2.xml");
			

			org.apache.http.client.HttpClient c = HttpClientBuilder.create().build();
			HttpClient httpClient = new DefaultHttpClient(cm);
			//Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
			// 2. Init the stub, provided the url to the ctor
			CalculatorServiceStub stub = new CalculatorServiceStub(ctx, "https://localhost:8080/services/CalculatorService");
			
			// 3. Manipulate the stub options here. We want to use the HttpClient instance we initialized manually (e.g. for https)
		    Options options = stub._getServiceClient().getOptions();	
		    options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
			options.setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, protocol);
			
		    
			Add add = new Add();
			add.setIntA(1);
			add.setIntB(2);
			AddResponse resp = stub.add(add);
			System.out.println("Done");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
