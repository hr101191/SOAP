package com.hurui.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PreDestroy;

import org.apache.axis2.transport.http.AxisServlet;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileSystemUtils;

import com.hurui.servletfiler.Axis2ServletFilter;

@Configuration
public class Axis2ServletConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());
	private static File repo;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public ServletRegistrationBean axisServlet() {		
		
		//1 Create temp directory 
		//Consider modifying this part of the code if your id does not have the rights to create file in temp directory
		repo = new File(System.getProperty("java.io.tmpdir"), "axis2repo_" + UUID.randomUUID());
		if (!repo.exists()) {
			repo.mkdirs();
		}

		//2) Copy all the service.xml files to temp directory
		ClassPathResource [] resourceList = {
					//Add all the services.xml on the classpath, folder structure doesn't matter here as long as they are nested in src/main/resources
					new ClassPathResource("Axis2Services/Calculator/services.xml"), 
				};	
		for(ClassPathResource resource : resourceList) {
			/*
			 * folder structure matters here... Axis 2 will detect all services.xml following the structure /services/{random_service_name}/META-INF
			 * finally, provide the base path to axisServlet
			 */
			File serviceDir = new File(repo, "services/" + UUID.randomUUID() + "/META-INF");
			serviceDir.mkdirs();
			try(FileOutputStream fileOutputStream = new FileOutputStream(new File(serviceDir, "services.xml"))) {
				IOUtils.copy(resource.getInputStream(), fileOutputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		
		//3 Get base Path of repo and load to axis2
		String repoPathString = repo.getAbsolutePath();
		logger.info("Loading all axis2 service.xml from: " + repoPathString);		
        ServletRegistrationBean axisServlet = new ServletRegistrationBean(new AxisServlet(), "/services/*");
        axisServlet.addInitParameter("axis2.repository.path", repoPathString);
        //Add axis2.xml to allow custom configurations to be applied
        try {
			axisServlet.addInitParameter("axis2.xml.url", new ClassPathResource("axis2.xml").getURL().toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        axisServlet.setLoadOnStartup(1);

        return axisServlet;
    }
	
	@Bean
	public FilterRegistrationBean<Axis2ServletFilter> axis2FilterRegistrationBean(){
		FilterRegistrationBean<Axis2ServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new Axis2ServletFilter());
		/*
		 * Only register the filter for Axis2 services
		 * This is compatible with server prefix e.g. setting server.servlet.context-path=/api in application.properties
		 * All routes with /api/services/* will also have this filter applied 
		 * 
		 * We do not want other routes to be applied with this filter e.g. actuator		
		 */
		filterRegistrationBean.addUrlPatterns("/services/*");
		return filterRegistrationBean;
	}

    @PreDestroy
    public void onDestroy() throws Exception {
    	boolean result = FileSystemUtils.deleteRecursively(repo);	    	
    	if(result) {
    		logger.info("Successfully deleted temp resources recursively, path: " + repo.getAbsolutePath());
    	} else {
    		logger.warn("Failed to delete temp resources from path: " + repo.getAbsolutePath());
    	}
        logger.info("Spring Container is destroyed!");
    }
}
