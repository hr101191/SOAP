package com.hurui.servletfiler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private byte[] cachedRequest;
	
	public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		InputStream requestInputStream = request.getInputStream();
        this.cachedRequest = StreamUtils.copyToByteArray(requestInputStream);
	}
	
    @Override
    public String getContentType() {
    	if(org.apache.commons.lang.StringUtils.containsIgnoreCase(super.getContentType(), "xml")) {
    		logger.info("Overriden getContentType() method invoked by Axis2 HTTPTransportUtils.class. Current content-type is: {}", super.getContentType());
    		logger.info("Overriding the content-type value as [text/plain] to force Axis2 to process this request as REST");
    		/*
    		 * SOAPAction is a mandatory as per SOAP1.1 specification
    		 * We are overriding the content-type header to force Axis2 to treat SOAP1.1 message indifferently 
    		 * since Axis 2 is able to parse the request and invoke the corresponding method without
    		 * validating the SOAPAction header
    		 * 
    		 * Axis2 HTTPTransportUtils.class will call this overriden getContentType() method
    		 * and set VERSION_UNKNOWN = 0 if content-type is "text/plain", which then process this as a REST call
    		 * 
    		 * Official documentation: If REST is enabled, the Axis2 server will act as both a REST endpoint and a SOAP endpoint.
    		 * When a message is received, if the content type is text/xml and if the SOAPAction Header is missing, 
    		 * then the message is treated as a RESTful Message, if not it is treated as a usual SOAP Message. 
    		 * 
    		 * URL: http://axis.apache.org/axis2/java/core/docs/rest-ws.html#rest_with_get
    		 */
    		return "text/plain"; //this force Axis2 to use 
    	}
        return super.getContentType();
    }

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new CachedServletInputStream(this.cachedRequest);
	}

	@Override
	public BufferedReader getReader() throws IOException{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedRequest);
	    return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

	private class CachedServletInputStream extends ServletInputStream {
		
		private InputStream cachedRequestInputStream;

	    public CachedServletInputStream(byte[] cachedRequest) {
	        this.cachedRequestInputStream = new ByteArrayInputStream(cachedRequest);
	    }
		
		@Override
		public int read() throws IOException {
			return cachedRequestInputStream.read();
		}

		@Override
		public boolean isFinished() {
			try {
				return cachedRequestInputStream.available() == 0;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return false;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			//NOOP	
		}
	}
}

