package com.hurui.config;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxisConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	
	private AppProperties appProperties;
	
	public AxisConfig(AppProperties appProperties) {
		this.appProperties= appProperties;
	}
	
	@Bean
	public ConfigurationContext configurationContext() throws AxisFault {
		try {
			logger.info("Initializing Axis2 client resources...");
			logger.info("Axis2 xml base path: [{}]", appProperties.getAxis2XmlBasePath());
			logger.info("Axis2 xml full path: [{}]", appProperties.getAxis2XmlFullPath());
			ConfigurationContext configurationContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(appProperties.getAxis2XmlBasePath(), appProperties.getAxis2XmlFullPath());
			logger.info("Initialized Axis2 client resources successfully");
			return configurationContext;
		} catch(AxisFault axisFault) {
			logger.error("Failed to load Axis2 resources... Stacktrace: ", (Throwable) axisFault);
			throw axisFault;
		}
		
	}
}
