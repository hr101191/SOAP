package com.hurui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	private Boolean isHttpsEnabled;
	private String axis2XmlBasePath;
	private String axis2XmlFullPath;
	private String keyStorePath;
	private String keyStorePassword;
	private String keyPass;
	private String trustStorePath;
	private String trustStorePassword;
	
	public Boolean getIsHttpsEnabled() {
		return isHttpsEnabled;
	}
	public void setIsHttpsEnabled(Boolean isHttpsEnabled) {
		this.isHttpsEnabled = isHttpsEnabled;
	}
	public String getAxis2XmlBasePath() {
		return axis2XmlBasePath;
	}
	public void setAxis2XmlBasePath(String axis2XmlBasePath) {
		this.axis2XmlBasePath = axis2XmlBasePath;
	}
	public String getAxis2XmlFullPath() {
		return axis2XmlFullPath;
	}
	public void setAxis2XmlFullPath(String axis2XmlFullPath) {
		this.axis2XmlFullPath = axis2XmlFullPath;
	}
	public String getKeyStorePath() {
		return keyStorePath;
	}
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	public String getKeyStorePassword() {
		return keyStorePassword;
	}
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	public String getKeyPass() {
		return keyPass;
	}
	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}
	public String getTrustStorePath() {
		return trustStorePath;
	}
	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}
	public String getTrustStorePassword() {
		return trustStorePassword;
	}
	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}
}
