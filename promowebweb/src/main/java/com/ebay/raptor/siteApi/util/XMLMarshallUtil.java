package com.ebay.raptor.siteApi.util;

import java.io.InputStream;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;

/**
 * 
 * @author lyan2
 */
public class XMLMarshallUtil {
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Reader reader, Class<T> type) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(type);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (T) jaxbUnmarshaller.unmarshal(reader);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Node node, Class<T> type) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(type);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (T) jaxbUnmarshaller.unmarshal(node);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(InputStream stream, Class<T> type) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(type);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (T) jaxbUnmarshaller.unmarshal(stream);
	}
}
