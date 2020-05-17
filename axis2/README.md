# Axis2 Demo
This folder contains project templates on how to host SOAP services utilizing Apache Axis2

## Description
1) how to host axis2 servlet in an executable jar

Note that the

## Code Walkthrough

TLDR: For those with prior experience with Axis2, the steps on how to create a server can be broken 
down into steps
1. Generating code from wsdl 
2. Configurating ServletRegistrationBean

Generating code from wsdl 

###Prerequisites
1. Download the binary distribution of Axis2 as per the instructions [on the Axis2 website](https://axis.apache.org/axis2/java/core/download.cgi).
2. Create a wsdl. This demo will use the wsdl from http://www.dneonline.com/calculator.asmx?wsdl with some modifications\
   *Copy the content and save as calculator.wsdl
### Generate java code from wsdl
Java code can be generated using wsdl2java.bat or wsdl2java.sh from the axis2\bin folder

Command (The {placeholders} shown need to be replaced with actual values): 
```
wsdl2java.bat -uri {full_path_of_wsdl_file} -ss -sd -o {output_path_of_generated_java_code}
```
![Alt text](README_IMG/wsdl2java_command.PNG?raw=true "wsdl2java_command")

#### Customizing Generated Package Name

To customize the package name, make the following changes in the wsdl

1. Wsdl definitions\
<wsdl:definitions xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" 
xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" 
xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" 
xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" 
xmlns:tns="http://calculator.hurui.com/" 
xmlns:s="http://www.w3.org/2001/XMLSchema" 
xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" 
xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" 
xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" 
targetNamespace="http://calculator.hurui.com/">

You can modify the following:
```
xmlns:tns="http://calculator.hurui.com/"
targetNamespace="http://calculator.hurui.com/
```


The above setting will result in the packages generated to be:
```
com.hurui.calculator
```

This demo will make use of the targetNamespace to customize package name but it actually has other uses.\
You can read more about the purpose of targetNamespace at: https://www.w3.org/TR/REC-xml-names/

#### SOAPAction
SOAPAction is a mandatory header for SOAP1.1 messages. If your wsdl supports SOAP1.1, you must set it in your request header.\
This demo will show you how you can bypass this restriction programmatically. SOAPAction from the source wsdl will not be modified.

Here's the workaround... REST is enabled by default for Axis2 and you simply have change the content-type to anything \
other than text/xml. Full ocumentation [on the Axis2 website](http://axis.apache.org/axis2/java/core/docs/rest-ws.html#rest_with_get).
```
If REST is enabled, the Axis2 server will act as both a REST endpoint and a SOAP endpoint.
When a message is received, if the content type is text/xml and if the SOAPAction Header is missing, 
then the message is treated as a RESTful Message, if not it is treated as a usual SOAP Message. 
```

As some client libraies will not have full control on setting the content type, we will override the content-type when we detect that it's text/xml

### Implementation
This section will describe the steps to generate java code from the wsdl and host the servioes in an executable jar.

1. Follow the [steps](#Generate-java-code-from-wsdl) above to generate java code from wsdl, you should see the following being generated:\
![Alt text](README_IMG/wsdl2java_output.PNG?raw=true "wsdl2java_output")
2. Create a new Spring Boot project, copy everything inside your output/src to src/main/java
 * Copy from:
```
![Alt text](README_IMG/output_to_copy.PNG?raw=true "output_to_copy")
```
 * Copy to:
```
```