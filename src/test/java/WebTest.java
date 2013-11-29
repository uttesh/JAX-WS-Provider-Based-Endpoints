
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import junit.framework.TestCase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rivet Systems
 */
public class WebTest extends TestCase {
    private static String in_str = "wiseking";
   private static String request = 
      "<ns1:sayHello xmlns:ns1=\"http://example.org\"><arg0>"+in_str+"</arg0></ns1:sayHello>";
 
   private static final QName portQName = new QName("http://example.org", "SimplePort");
   private Service service = null;

   protected void setUp() throws Exception {
      System.out.println("set up");
      String url_str = System.getProperty("wsdl");
       System.out.println("url_str "+url_str);
      URL url = new URL(url_str);
      QName serviceName = new QName("http://example.org", "SimpleImplService");
      service = Service.create(serviceName);
      service.addPort(portQName, SOAPBinding.SOAP11HTTP_BINDING, url_str);
      System.out.println("Setup complete.");
 
   }
 
   public void testSayHelloSource() throws Exception {
      setUp();
      Dispatch<Source> sourceDispatch = 
         service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
      System.out.println("\nInvoking xml request: " + request);
      Source result = sourceDispatch.invoke(new StreamSource(new StringReader(request)));
      String xmlResult = sourceToXMLString(result);
      System.out.println("Received xml response: " + xmlResult);
      assertTrue(xmlResult.indexOf("HELLO:"+in_str)>=0);
   }
 
   private String sourceToXMLString(Source result) {
      String xmlResult = null;
      try {
         TransformerFactory factory = TransformerFactory.newInstance();
         Transformer transformer = factory.newTransformer();
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         OutputStream out = new ByteArrayOutputStream();
         StreamResult streamResult = new StreamResult();
         streamResult.setOutputStream(out);
         transformer.transform(result, streamResult);
         xmlResult = streamResult.getOutputStream().toString();
      } catch (TransformerException e) {
         e.printStackTrace();
      }
      return xmlResult;
   }
}
