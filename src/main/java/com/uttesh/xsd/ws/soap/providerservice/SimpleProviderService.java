/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uttesh.xsd.ws.soap.providerservice;

import java.io.ByteArrayInputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Node;

/**
 *
 * @author Rivet Systems
 */
@ServiceMode(value = Service.Mode.PAYLOAD)
@WebServiceProvider(portName = "SimpleProviderServicePort",
    serviceName = "SimpleProviderService",
    targetNamespace = "http://soap.ws.xsd.uttesh.com/",
    wsdlLocation = "WEB-INF/wsdl/SimpleProviderService.wsdl")
public class SimpleProviderService extends SpringBeanAutowiringSupport implements Provider<Source> {

//Invokes an operation according to the contents of the request message.
  public Source invoke(Source source) {
    try {
      DOMResult dom = new DOMResult();
      Transformer trans = TransformerFactory.newInstance().newTransformer();
      trans.transform(source, dom);
      Node node = dom.getNode();
      // Get the operation name node.
      Node root = node.getFirstChild();
      // Get the parameter node.
      Node first = root.getFirstChild();
      String input = first.getFirstChild().getNodeValue();
      // Get the operation name.
      String op = root.getLocalName();
      if ("invokeNoTransaction".equals(op)) {
        return sendSource(input);
      } else {
        return sendSource2(input);
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Error in provider endpoint", e);
    }
    
  }
  
    private Source sendSource(String input) {
    String body =
        "<ns:invokeNoTransactionResponse xmlns:ns=\"http://jaxws.webservices.examples/\"><return>"
            + "constructed:" + input
            + "</return></ns:invokeNoTransactionResponse>";
    Source source = new StreamSource(new ByteArrayInputStream(body.getBytes()));
    return source;
  }
 
  private Source sendSource2(String input) {
    String body =
        "<ns:invokeTransactionResponse xmlns:ns=\"http://jaxws.webservices.examples/\"><return>"
            + "constructed:" + input
            + "</return></ns:invokeTransactionResponse>";
    Source source = new StreamSource(new ByteArrayInputStream(body.getBytes()));
    return source;
  }
    
}
