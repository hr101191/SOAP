The wsdl in this sample project is taken from http://www.dneonline.com/calculator.asmx?wsdl

Generated class will be in the package org.soa. You may change the package name to your preference using -p in in the wsimport command

You may delete the soapclient\src\main\java\org folder in this project and practice generating jaxb classes from wsdl:
1) Open command prompt at [your_local_basepath]\soapclient\src\main\java folder
2) wsimport -keep -p org.soa -Xnocompile -verbose [your_local_basepath]\soapclient\src\main\resources\calculator.wsdl
