package com.ebay.raptor.promotion.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Node;

/**
 * Create CS api request messages.
 * 
 * @author lyan2
 */
public class CSSOAPMessageFactory {
	private static final String API_USERNAME = "cn_cbt_sv_batch"; // TODO - persist in DB
	private static final String API_PASSWORD = "11aa!!AA";
	private static final String APP_ID = "EBAYSLCTNSS2VCD9FGBWF7K53IIFIS";
	private static final String DEV_ID = "Z938W1651D97433L537737638H8289";
	private static final String AUTH_CERT = "P1HE1AE7LE1$2C31H57DH-EN943I68";

	/**
	 * Create a SOAPMessage and initialize it, including namespaces and prefix.
	 * 
	 * @return SOAPMessage
	 * @throws SOAPException
	 */
	private static SOAPMessage createMessage() throws SOAPException {
		SOAPMessage message = null;

		MessageFactory mf = MessageFactory.newInstance();
		message = mf.createMessage();
		message.getSOAPPart();
		SOAPPart sp = message.getSOAPPart();
		SOAPEnvelope se = sp.getEnvelope();
		SOAPHeader sh = message.getSOAPHeader();
		SOAPBody sb = message.getSOAPBody();

		se.removeNamespaceDeclaration("SOAP-ENV");
		se.addNamespaceDeclaration("xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		se.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
		se.setPrefix("soap");
		sh.setPrefix("soap");
		sb.setPrefix("soap");
		return message;
	}

	/**
	 * Create a SOAPMessage which could be used to call CS API 'GetToken'.
	 * @return SOAPMessage
	 * @throws SOAPException
	 */
	public static SOAPMessage createGetTokenMessage() throws SOAPException {
		SOAPMessage message = createMessage();
		SOAPHeader sh = message.getSOAPHeader();
		
		SOAPElement requestCredentials = sh.addChildElement("RequesterCredentials", null, "urn:ebay:apis:eBLBaseComponents");
		SOAPElement cs = requestCredentials.addChildElement("Credentials");
		
		cs.addChildElement("AppId").addTextNode(APP_ID);
		cs.addChildElement("DevId").addTextNode(DEV_ID);
		cs.addChildElement("AuthCert").addTextNode(AUTH_CERT);
		cs.addChildElement("Username").addTextNode(API_USERNAME);
		cs.addChildElement("Password").addTextNode(API_PASSWORD);
		
		SOAPBody sb = message.getSOAPBody();
		SOAPElement request = sb.addChildElement("CSGetTokenRequest", null, "urn:ebay:apis:eBLBaseComponents");
		request.addChildElement("Version").addTextNode("999");
		return message;
	}
	
	/**
	 * Create a SOAPMessage which could be used to call CS API 'CSGetUserCore'.
	 * @return SOAPMessage
	 * @throws SOAPException
	 */
	public static SOAPMessage createGetUserCoreByNameMessage(String token, String userName) throws SOAPException {
		SOAPMessage message = createMessage();
		SOAPHeader sh = message.getSOAPHeader();
		
		SOAPElement requestCredentials = sh.addChildElement("RequesterCredentials", null, "urn:ebay:apis:eBLBaseComponents");
		requestCredentials.setAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "0");
		requestCredentials.addChildElement("eBayAuthToken").addTextNode(token);
		
		SOAPBody sb = message.getSOAPBody();
		
		SOAPElement request = sb.addChildElement("CSGetUserCoreRequest", null, "urn:ebay:apis:eBLBaseComponents");
		request.addChildElement("Version").addTextNode("999");
		request.addChildElement("OutputSelector").addTextNode("IpGeoLocation");
		request.addChildElement("UserID").addTextNode(userName);
		
		return message;
	}
	
	/**
	 * Create a SOAPMessage which could be used to call CS API 'CSGetItemDetail'.
	 * @return SOAPMessage
	 * @throws SOAPException
	 */
	public static SOAPMessage createGetItemDetailMessage(String token, long itemId) throws SOAPException {
		SOAPMessage message = createMessage();
		SOAPHeader sh = message.getSOAPHeader();
		
		SOAPElement requestCredentials = sh.addChildElement("RequesterCredentials", null, "urn:ebay:apis:eBLBaseComponents");
		requestCredentials.setAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "0");
		requestCredentials.addChildElement("eBayAuthToken").addTextNode(token);
		
		SOAPBody sb = message.getSOAPBody();
		
		SOAPElement request = sb.addChildElement("CSGetItemDetailRequest", null, "urn:ebay:apis:eBLBaseComponents");
		request.addChildElement("Version").addTextNode("999");
		request.addChildElement("OutputSelector").addTextNode("ItemHeader.ItemID");
		request.addChildElement("OutputSelector").addTextNode("ItemHeader.ItemTitle");
		request.addChildElement("OutputSelector").addTextNode("ItemHeader.ItemStatus");
		request.addChildElement("OutputSelector").addTextNode("ItemHeader.Seller.LongId");
		request.addChildElement("OutputSelector").addTextNode("ItemHeader.Seller.DisplayLoginName");
		request.addChildElement("ItemID").addTextNode(String.valueOf(itemId));
		
		return message;
	}
	
	/**
	 * Translate SOAP message into XML string.
	 * @param message 
	 * @return SOAPMessage
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static String parseToString(SOAPMessage message) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		message.writeTo(out);
		return new String(out.toByteArray());
	}
	
	/**
	 * Get the real first node with the specified node name in SOAP body.
	 * @param message
	 * @param nodeName
	 * @return
	 * @throws SOAPException
	 */
	public static Node getResponse(SOAPMessage message, String nodeName) throws SOAPException {
		@SuppressWarnings("unchecked")
		Iterator<Node> iter = message.getSOAPBody().getChildElements();
		while (iter.hasNext()) {
			Node response = iter.next();
			if (nodeName.equalsIgnoreCase(response.getNodeName())) {
				return response;
			}
		}
		
		return null;
	}
}
