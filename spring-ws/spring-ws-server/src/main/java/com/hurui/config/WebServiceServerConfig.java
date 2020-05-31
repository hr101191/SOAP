package com.hurui.config;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
public class WebServiceServerConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean<Servlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	    servlet.setApplicationContext(applicationContext);
	    /*
	     * "/services/* sets the prefix for all SOAP servlet routings
	     * Order:
	     * 1) Container prefix
	     * 2) /services/*
	     * 3) @Bean(name = "service_name")
	     */
	    return new ServletRegistrationBean<>(servlet, "/services/*");
	  }

	/*
	 * Convenient implementation of Wsdl11Definition that creates a SOAP 1.1 or 1.2 binding 
	 * based on naming conventions in one or more inlined XSD schemas. Delegates to 
	 * InliningXsdSchemaTypesProvider, DefaultMessagesProvider, SuffixBasedPortTypesProvider, 
	 * SoapProvider underneath; effectively equivalent to using a ProviderBasedWsdl4jDefinition with all these providers. 
	 * 
	 * ref: https://docs.spring.io/spring-ws/sites/2.0/apidocs/org/springframework/ws/wsdl/wsdl11/DefaultWsdl11Definition.html
	 */
	@Bean(name = "CalculatorService")
	public Wsdl11Definition defaultWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition(); //As per comment on top, it does support SOAP 1.2 despite the Object Name!
		wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/Calculator.wsdl"));
	    return wsdl11Definition;
	}
}
