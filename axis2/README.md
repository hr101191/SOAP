# Axis2 Demo
This folder contains project templates on how to host SOAP services utilizing Apache Axis2

## Description
1) how to host axis2 servlet in an executable jar

## Code Walkthrough

TLDR: For those with prior experience with Axis2, the steps on how to create a server can be broken 
down into steps
1. Generating code from wsdl 
2. Configurating ServletRegistrationBean

Generating code from wsdl 

### Prerequisites
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
xmlns:tns="http://CalculatorService.hurui.com/"
targetNamespace="http://CalculatorService.hurui.com/
```

2. Schema

Modify this:
```
<s:schema elementFormDefault="qualified" targetNamespace="http://CalculatorService.hurui.com/">
```


The above setting will result in the packages generated to be:
```
com.hurui.calculatorservice
```

#### Customizing URL Pattern
Modify the following in the wsdl:
```
<wsdl:service name="CalculatorService">
```
Service Url:
```
/server-prefix/axis2-prefix/CalculatorService

e.g. standard localhost env url: http://localhost:8080/services/CalculatorService
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

As some client libraies will not have full control on setting the content type, we will override the content-type when we detect that it's text/xml, see code snippet below:
```java
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
  //omitted non-relavant code

  @Override
  public String getContentType() {
    if(StringUtils.containsIgnoreCase(super.getContentType(), "xml")) {
      //this forces Axis2 to ignore the SOAPAction verification
      return "text/plain"; 
    }
    return super.getContentType();
  }

  //omitted non-relavant code
}
```

#### HTTPS Support

Simply add this chunk in the Transport Ins Section in axis2.xml:
```xml
    <!-- ================================================= -->
    <!-- Transport Ins -->
    <!-- ================================================= -->
    <transportReceiver name="https"
                       class="org.apache.axis2.transport.http.SimpleHTTPServer">
        <parameter name="port">50302</parameter>
        <!-- Here is the complete list of supported parameters (see example settings further below):
            port: the port to listen on (default 6060)
            hostname:  if non-null, url prefix used in reply-to endpoint references                                 (default null)
            originServer:  value of http Server header in outgoing messages                                         (default "Simple-Server/1.1")
            requestTimeout:  value in millis of time that requests can wait for data                                (default 20000)
            requestTcpNoDelay:  true to maximize performance and minimize latency                                   (default true)
                                false to minimize bandwidth consumption by combining segments
            requestCoreThreadPoolSize:  number of threads available for request processing (unless queue fills up)  (default 25)
            requestMaxThreadPoolSize:  number of threads available for request processing if queue fills up         (default 150)
                                       note that default queue never fills up:  see HttpFactory
            threadKeepAliveTime:  time to keep threads in excess of core size alive while inactive                  (default 180)
                                  note that no such threads can exist with default unbounded request queue
            threadKeepAliveTimeUnit:  TimeUnit of value in threadKeepAliveTime (default SECONDS)                    (default SECONDS)
        -->
        <!-- <parameter name="hostname">http://www.myApp.com/ws</parameter> -->
        <!-- <parameter name="originServer">My-Server/1.1</parameter>           -->
        <!-- <parameter name="requestTimeout">10000</parameter>                   -->
        <!-- <parameter name="requestTcpNoDelay">false</parameter>                   -->
        <!-- <parameter name="requestCoreThreadPoolSize">50</parameter>                      -->
        <!-- <parameter name="requestMaxThreadPoolSize">100</parameter>                     -->
        <!-- <parameter name="threadKeepAliveTime">240000</parameter>                  -->
        <!-- <parameter name="threadKeepAliveTimeUnit">MILLISECONDS</parameter>            -->
    </transportReceiver>
    <!-- This is where you'd put custom transports.  See the transports project -->
    <!-- for more.  http://ws.apache.org/commons/transport                      -->
```
\*Note: the value of port parameter does not have to be equal to the Spring Container port. 


### Implementation
This section will describe the steps to generate java code from the wsdl and host the servioes in an executable jar.

1. Follow the [steps](#Generate-java-code-from-wsdl) above to generate java code from wsdl, you should see the following being generated:\
![Alt text](README_IMG/wsdl2java_output.PNG?raw=true "wsdl2java_output")
2. Create a new Spring Boot project, copy everything inside your output/src to src/main/java
 * Copy from:

![Alt text](README_IMG/output_to_copy.PNG?raw=true "output_to_copy")

 * Copy to:
 
![Alt text](README_IMG/output_dest.PNG?raw=true "output_dest")

 * Generated files:
 
 ![Alt text](README_IMG/generated_files.PNG?raw=true "generated_files")
 
 3. Copy everything in output/resources to your src/main/resource with the following structure:
![Alt text](README_IMG/service_xml_folder_structure.PNG?raw=true "service_xml_folder_structure")\
The service.xml contains the classpaths which are called by Axis2 Servlet via reflection. Note that 
this service.xml is unique for each wsdl. On startup, the service.xml will be loaded by Axis2 Servlet.

4. Start coding!

## Test
As you can see from the screenshot below, SOAP1.1 request can be made without SOAPAction header
![Alt text](README_IMG/test1.PNG?raw=true "test1")
