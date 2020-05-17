package com.hurui.servletfiler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

@Component
public class Axis2ServletFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

	@Override 
	public void init(FilterConfig filterConfig) {
		
	}
	
	@Override 
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {	

		//1) Cache the request to allow multiple read
		MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);
		MultiReadHttpServletResponse multiReadHttpServletResponse = new MultiReadHttpServletResponse((HttpServletResponse) response);		
		
		//2) Intercept and log the request
		if (request instanceof HttpServletRequest) {			
			Map<String, List<String>> headerMap = Collections.list(multiReadHttpServletRequest.getHeaderNames())
					.stream()
					.collect(Collectors.toMap(
							Function.identity(), 
							value -> Collections.list(multiReadHttpServletRequest.getHeaders(value)))
					);

			logger.info("===========================================Begin Request========================================================");
			logger.info("\n[Request Header]\n{} \n\n[Request Body]\n{}", headerMap, IOUtils.toString(multiReadHttpServletRequest.getReader()));
			logger.info("============================================End Request=========================================================");
			
		}
		
		//3) Allow chain to complete and log cached response
		try {
			chain.doFilter(multiReadHttpServletRequest, multiReadHttpServletResponse);
			multiReadHttpServletResponse.flushBuffer();
		} finally {
			byte[] respFromCache = multiReadHttpServletResponse.getCopy();			
			logger.info("===========================================Begin Response========================================================");
	    	try {
				logger.info("\n[Status]: {}\n\n[Response Body]:\n{}", multiReadHttpServletResponse.getStatus(), formatXmlString(new String(respFromCache, response.getCharacterEncoding())));
			} catch (Exception ex) {
				logger.error("Error formatting response to XML, stacktrace: ", ex);
			}
	    	logger.info("============================================End Response=========================================================");
		}
	}
	
	private String formatXmlString(String xml) throws IOException, ParserConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException, org.xml.sax.SAXException {
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
