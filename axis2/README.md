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

##### Prerequisites
1. Download the binary distribution of Axis2 as per the instructions [on the Axis2
    website](https://axis.apache.org/axis2/java/core/download.cgi).
2. Create a wsdl, this demo will use the wsdl from http://www.dneonline.com/calculator.asmx?wsdl
   *Copy the content and save as calculator.wsdl
##### Generate java code from wsdl
Java code can be generated using wsdl2java.bat or wsdl2java.sh from the axis2\bin folder

#####Customizing Generated Package Name

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

Command: 
```
wsdl2java.bat -uri {full_path_of_wsdl_file} -ss -sd -o {output_path_of_generated_java_code}
```

*Note: Customizing package name of the output wsdl is not discussed in this demo. 
       You can change it by modifying the namespace in the wsdl. 
	   https://www.w3.org/TR/REC-xml-names/