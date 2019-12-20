In the world of REST api, many are used to work with json exclusively. What if one day someone in your organization passes you a wsdl file and tell you that there's no way someone will wrap it into json for you and you will have to consume the web service as it is?
"A web service is much like a REST api in terms of functionality. A web service is like a script running on a web server which receives requests/queries in XML format and replies/sends a response to the corresponding request in XML format using SOAP over HTTP/HTTPS protocol." Do check out Ankit Lohani's article: https://medium.com/@innovationchef/web-services-client-in-java-72386ea55ee4 for a detailed explanation. This project is based on the article.
So, to put it simply, in order to consume a web service, you simply have to replace the json payload with a xml payload and that's it!
I've seen the following examples in other projects:
1) creating an xml file in src/resources and use string replacements
2) use StringBuilder to create a xml request string from scratch
However, these are too time consuming and error prone if the request is huge. Besides, you still need to extract the required data from the response xml string using regular expressions.

In this project, I am about to demonstrate how to:
1) use ws import in jdk to generate POJO classes 
2) initialize the variables in the generated POJO request class and marshal it into xml string 
3) consume a web service and unmarshall the response back to the generated POJO response class

Since the generate POJO classes highly resembles those model classes you create to map a json request/ response, you may now pass it on to your own methods.
What is not covered:
1) Handling of error response. Since the unmarshaller is only able to map the response xml to the generated response POJO, you will have to handle error response separately
