JAX-WS-Provider-Based-Endpoints
===============================

Simple JAX-WS provider webservice implemetation, as we know this type implementation is required only when there is huge xml file data input, JAXB will be performance pain for huge xml data.


<h4>Why Webservice Provider?</h4>
<hr/>

As we know SEI webservices are easy to implement and maintain, but fails on xml data parsing for huge data in soap message.

checkout the reference on web service provider basic information in below link

http://docs.oracle.com/cd/E24329_01/web.1211/e24965/provider.htm#WSADV191


<h4>How To implemet the Provider Service</h4>
<hr/>

We have to use @webserviceprovider annotation instead of @webservice, and @serverMode as SOAP/PAYLOAD. provider wont be having wsdl by default we have to write manual wsdl and xsd file, wegen tool won't help generation as well for webservice 
provider end point service.

sample URL: <a href="ttp://localhost:8080/wsprovider/secure/soap/SimpleProviderService?wsdl">http://localhost:8080/wsprovider/secure/soap/SimpleProviderService?wsdl</a>



